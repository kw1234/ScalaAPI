import java.time.ZonedDateTime
import java.util.Date

import scala.collection.JavaConverters._
import scala.util.Try

class AccountingTotalsServiceImpl(itemService: ItemService,
                                  locationService: LocationService,
                                  saleService: SaleService,
                                  inventoryService: InventoryService,
                                  accountingService: AccountingService) extends AccountingTotalsService {

  /**
   * 1. Gross Sales (total of sales)
   * This is the sum of all sales for all sites. If a given site has no sales, post a '0' value.
   */
  override def getGrossSales(): (Int, Map[Int, Int]) = {
    var map:Map[Int, Int]  = Map[Int, Int] ()
    var totalSum = 0
    val saleStream = saleService.getStream
    saleStream.take(saleStream.size).map(sale => {
      val key = sale.siteId
      var sum = 0
      sale.itemsSold.map(lineItem => {
        sum += lineItem.count * lineItem.item.price
      })
      map = map + (key -> sum)
      totalSum += sum
    })
    (totalSum, map)
  }

  /**
   * 2. Inventory Value
   * This is the current value of the inventory in all sites. If a given site has no inventory, or is somehow negative, post that.
   */
    //case class InventoryReset(item: Item, count: Int, siteId: Int, dateTime: ZonedDateTime)
  override def getInventory(): Map[Int, Int]  = {
    var map:Map[Int, Int]  = Map[Int, Int] ()
    val resetStream = inventoryService.getResetStream
    resetStream.take(resetStream.size).map(inventory => {
      val key = inventory.siteId
      val value = inventory.count
      if (!map.contains(key)) map = map + (key -> 0)
      map(key) += value
    })
    map
  }

  /**
   * 3. Inventory Value Change From Yesterday
   * This is the difference between yesterday's and today's inventory value for a given site.
   */
  override def getDailyInventoryChange(): Int = {
    var map:Map[ZonedDateTime, Int]  = Map[ZonedDateTime, Int] ()
    val resetStream = inventoryService.getResetStream
    val yesterday = ZonedDateTime.now() // but its yesterday
    val today = ZonedDateTime.now()
    resetStream.take(resetStream.size).map(inventory => {
      val value = inventory.count
      val key = inventory.dateTime
      map(key) += value;
    })
    map(today) - map(yesterday)
  }

  /**
   * 4. Net Sales (Sales minus incoming inventory) => Account#4928392
   * This is the amount of sales, but we subtract out new inventory value.
   */
  override def getNetSales(): Int = {
    var salesSum = 0
    val saleStream = saleService.getStream
    saleStream.take(saleStream.size).map(sale => {
      sale.itemsSold.map(lineItem => {
        salesSum += lineItem.count * lineItem.item.price
      })
    })
    val resetStream = inventoryService.getResetStream
    var inventorySum = 0;
    resetStream.take(resetStream.size).map(inventory => {
      inventorySum += inventory.item.price * inventory.count
    })
    salesSum - inventorySum
  }
}

