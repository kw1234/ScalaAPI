
/**
 * This Service will...
 */
trait AccountingTotalsService {

  /**
   * 1. Gross Sales (total of sales) Account#12345
   * This is the sum of all sales for all sites. If a given site has no sales, post a '0' value.
   */
  def getGrossSales(): (Int, Map[Int, Int])

  /**
   * 2. Inventory Value => Account#2983018
   * This is the current value of the inventory in all sites. If a given site has no inventory, or is somehow negative, post that.
   */
  def getInventory(): Map[Int, Int]

  /**
   * 3. Inventory Value Change From Yesterday => Account#3485719
   * This is the difference between yesterday's and today's inventory value for a given site.
   */
  def getDailyInventoryChange(): Int

  /**
   * 4. Net Sales (Sales minus incoming inventory) => Account#4928392
   * This is the amount of sales, but we subtract out new inventory value.
   */
  def getNetSales(): Int

}

object AccountingTotalsService {
  def apply(): AccountingTotalsService = new AccountingTotalsServiceImpl(MockItemService(), MockLocationService(), MockSaleService(), MockInventoryService(), MockAccountingService())
}

