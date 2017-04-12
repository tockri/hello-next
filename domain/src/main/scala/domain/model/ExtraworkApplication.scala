package domain.model

import org.joda.time._

class ExtraworkApplicationId(value:Long) extends EntityId(value)

object ExtraworkApplicationId {
  val Undef = new ExtraworkApplicationId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):ExtraworkApplicationId = new ExtraworkApplicationId(value)
}

case class ExtraworkApplication(
                                 id: ExtraworkApplicationId,
                                 startTime: DateTime,
                                 endTime: DateTime,
                                 status: Boolean,
                                 applierId: UserId,
                                 approverId: UserId,
                                 reason: String,
                                 comment: Option[String] = None,
                                 creatorId: UserId,
                                 created: DateTime,
                                 updatorId: UserId,
                                 updated: DateTime) extends Entity(id)