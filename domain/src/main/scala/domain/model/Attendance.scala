package domain.model

import org.joda.time._

class AttendanceId(value:Long) extends EntityId(value)

object AttendanceId {
  val Undef = new AttendanceId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):AttendanceId = new AttendanceId(value)
}

case class Attendance(
                       id: AttendanceId,
                       userId: UserId,
                       officeId: OfficeId,
                       startTime: DateTime,
                       endTime: Option[DateTime] = None,
                       creatorId: UserId,
                       created: DateTime,
                       updatorId: UserId,
                       updated: DateTime) extends Entity(id)