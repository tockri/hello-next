package domain.model

import org.joda.time._

class GivenHolidayId(value:Long) extends EntityId(value)

object GivenHolidayId {
  val Undef = new GivenHolidayId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):GivenHolidayId = new GivenHolidayId(value)

}

case class GivenHoliday(
                         id: GivenHolidayId,
                         userId: UserId,
                         days: Float,
                         created: DateTime,
                         expire: Option[DateTime] = None) extends Entity(id)