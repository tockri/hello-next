package domain.model

import org.joda.time._

class RestId(value:Long) extends EntityId(value)

object RestId {
  val Undef = new RestId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):RestId = new RestId(value)
}

case class Rest(
  id: RestId,
  attendanceId: AttendanceId,
  startTime: DateTime,
  endTime: Option[DateTime] = None,
  creatorId: UserId,
  created: DateTime,
  updatorId: UserId,
  updated: DateTime) extends Entity(id)