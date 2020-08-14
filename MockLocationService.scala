class MockLocationService extends LocationService {
  val mockSites: List[Site] = List(
    Site(id=0, name="site1"),
    Site(id=1, name="site2"),
    Site(id=2, name="site3"),
    Site(id=3, name="site4")
  )

  override def getSiteDetail(id: Int): Site = {
    mockSites(id)
  }

  def setSiteDetail(id: Int, site:Site) = {
    mockSites.updated(id, site);
  }

}

object MockLocationService {
  def apply(): MockLocationService = new MockLocationService()
}