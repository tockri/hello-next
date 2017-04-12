package domain

import domain.model.{GivenHoliday, GivenHolidayId, UserId}
import support.IOContext

import scala.concurrent.Future

/**
  * Created by fujita on 2017/04/13.
  */
trait GivenHolidayRepository extends Repository[GivenHoliday, GivenHolidayId] {
  def list(userId:UserId)(implicit io:IOContext): Future[List[GivenHoliday]]
}
