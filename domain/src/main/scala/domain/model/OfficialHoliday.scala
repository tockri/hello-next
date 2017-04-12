package domain.model

import org.joda.time._


class OfficialHolidayId(value:Long) extends EntityId(value)

object OfficialHolidayId {
  val Undef = new OfficialHolidayId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):OfficialHolidayId = new OfficialHolidayId(value)
}

case class OfficialHoliday(
  id: OfficialHolidayId,
  spaceId: SpaceId,
  date: LocalDate,
  name: String,
  creatorId: UserId,
  created: DateTime,
  updatorId: UserId,
  updated: DateTime) extends Entity(id)