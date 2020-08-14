import java.util.Date

class MockAccountingService extends AccountingService {

  // A total for a given site/date/account.
  //case class AccountingTotal(siteId: Int, date: Date, accountNumber: Int, amount: BigDecimal)
  override def postAccountingTotal(total: AccountingTotal): Boolean = {
    true
  }

}

object MockAccountingService {
  def apply(): MockAccountingService = new MockAccountingService()
}