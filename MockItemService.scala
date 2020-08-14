class MockItemService extends ItemService {

  val mockItems: List[Item] = List(
    Item(id=0, price=10.5),
    Item(id=1, price=5.3),
    Item(id=2, price=19.8),
    Item(id=3, price=200.4),
    Item(id=4, price=4.2)
  )

  override def getItemDetail(id: Int): Item = {
    mockItems(id)
  }
}

object MockItemService {
  def apply(): MockItemService = new MockItemService()
}