package domain.model

import org.joda.time._

class HolidayApplicationId(value:Long) extends EntityId(value)

object HolidayApplicationId {
  val Undef = new HolidayApplicationId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):HolidayApplicationId = new HolidayApplicationId(value)

}

case class HolidayApplication(
  id: HolidayApplicationId,
  date: LocalDate,
  amount: Float,
  status: Boolean,
  paid: Boolean,
  applierId: UserId,
  approverId: UserId,
  reason: String,
  comment: Option[String] = None,
  creatorId: UserId,
  created: DateTime,
  updatorId: UserId,
  updated: DateTime) extends Entity(id)