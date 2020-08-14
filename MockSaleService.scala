import java.time.ZonedDateTime

class MockSaleService(itemService: ItemService) extends SaleService{

  // Creating stream
  var saleStream:Stream[Sale] = (
    Sale(id=0, itemsSold=Set( SaleLineItem(getItem(0), count=1), SaleLineItem(getItem(1), count=2)), siteId=0, dateTime=ZonedDateTime.now()) #::
      Sale(id=1, itemsSold=Set( SaleLineItem(getItem(1), count=2), SaleLineItem(getItem(3), count=2)), siteId=1, dateTime=ZonedDateTime.now()) #::
      Sale(id=2, itemsSold=Set( SaleLineItem(getItem(1), count=1), SaleLineItem(getItem(4), count=3)), siteId=2, dateTime=ZonedDateTime.now()) #::
      Stream.empty
    )

  override def getStream(): Stream[Sale] = {
   saleStream
  }

  def printStream() = {
    // Taking elements from stream
    print("Take first 2 elemes from stream = ")
    saleStream.take(2).print
    print("\nTake first 10 elems from stream2 = ")
    saleStream.take(10).print
  }

  def getItem(id:Int): Item = {
    // this should be the regular ItemService, need to pass this dependency in to be used
    itemService.getItemDetail(id)
  }
}


object MockSaleService {
  def apply(): MockSaleService = new MockSaleService(MockItemService())
}