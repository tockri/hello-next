package domain.model

import org.joda.time._

class AttendanceApplicationId(value:Long) extends EntityId(value)

object AttendanceApplicationId {
  val Undef = new AttendanceApplicationId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):AttendanceApplicationId = new AttendanceApplicationId(value)

}

case class AttendanceApplication(
                                  id: AttendanceApplicationId,
                                  startTime: DateTime,
                                  endTime: DateTime,
                                  attendanceId: AttendanceId,
                                  status: Boolean,
                                  applierId: UserId,
                                  approverId: UserId,
                                  reason: String,
                                  comment: Option[String] = None,
                                  creatorId: UserId,
                                  created: DateTime,
                                  updatorId: UserId,
                                  updated: DateTime) extends Entity(id)
