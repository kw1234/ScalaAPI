import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.Matchers
import org.scalatest.FreeSpec

import org.junit.Assert._
import org.junit.Test
import org.junit.Before


@RunWith(classOf[JUnitRunner])
class AccountingTest extends FreeSpec with Matchers {


  "getGrossSales" - {

    "regular case" in {
      val service = AccountingTotalsService()
      val (totalSum, map) = service.getGrossSales()
      totalSum should equal 40
      map(0) should equal 10
      map(1) should equal 15
      map(2) should equal 5
      map(3) should equal 10
    }

    "null case" in {
    }
  }

  "getInventory" - {

    "regular case" in {
      val service = AccountingTotalsService()
      var map = service.getInventory()
      map(0) should equal 5
      map(1) should equal 1
      map(2) should equal 3
      map(3) should equal 1
    }


    "null case" in {

    }
  }

  "getGrossSales" - {

    "regular case" in {
      val service = AccountingTotalsService()
      service.getDailyInventoryChange() should equal 42
    }


    "null case" in {

    }
  }

  "getGrossSales" - {

    "regular case" in {
      val service = AccountingTotalsService()
      service.getNetSales() should equal 5340.3
    }


    "null case" in {

    }
  }

}