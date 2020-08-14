import java.time.ZonedDateTime

class MockInventoryService(itemService: ItemService) extends InventoryService {

  var resetStream:Stream[InventoryReset] = (
    InventoryReset(item=getItem(3), count=2, siteId=0, dateTime=ZonedDateTime.now()) #::
      InventoryReset(item=getItem(1), count=1, siteId=1, dateTime=ZonedDateTime.now()) #::
      InventoryReset(item=getItem(0), count=0, siteId=3, dateTime=ZonedDateTime.now()) #::
      Stream.empty
    )

  var receivedStream:Stream[InventoryReceived] = (
    InventoryReceived(item=getItem(0), count=1, siteId=0, dateTime=ZonedDateTime.now()) #::
      InventoryReceived(item=getItem(2), count=1, siteId=3, dateTime=ZonedDateTime.now()) #::
      InventoryReceived(item=getItem(1), count=2, siteId=1, dateTime=ZonedDateTime.now()) #::
      Stream.empty
    )

  var sentStream:Stream[InventorySent] = (
    InventorySent(item=getItem(1), count=1, siteId=3, dateTime=ZonedDateTime.now()) #::
      InventorySent(item=getItem(2), count=2, siteId=2, dateTime=ZonedDateTime.now()) #::
      InventorySent(item=getItem(4), count=3, siteId=1, dateTime=ZonedDateTime.now()) #::
      Stream.empty
    )

  override def getResetStream: Stream[InventoryReset] = {
    resetStream
  }

  override def getReceivedStream: Stream[InventoryReceived] = {
    receivedStream
  }

  override def getSentStream: Stream[InventorySent] = {
    sentStream
  }

  def getItem(id:Int): Item = {
    itemService.getItemDetail(id)
  }
}

object MockInventoryService {
  def apply(): MockInventoryService = new MockInventoryService(MockItemService())
}