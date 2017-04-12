package domain.model

import org.joda.time._

class UserId(value:Long) extends EntityId(value)

object UserId {
  val Undef = new UserId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):UserId = new UserId(value)
}

case class User(
  id: UserId,
  spaceId: SpaceId,
  name: String,
  icon: Option[String] = None,
  nulabid: String,
  uniqueid: String,
  enterDate: LocalDate,
  officeId: Option[OfficeId],
  approverId: Option[UserId],
  active: Boolean,
  creatorId: UserId,
  created: DateTime,
  updatorId: UserId,
  updated: DateTime) extends Entity(id)

object User {
  def create(spaceId: SpaceId,
             name: String,
             icon: Option[String] = None,
             nulabid: String,
             uniqueid: String,
             enterDate: LocalDate,
             officeId: Option[OfficeId],
             approverId: Option[UserId],
             creatorId: UserId):User =
    User(UserId.Undef, spaceId, name, icon, nulabid, uniqueid,
      enterDate, officeId, approverId, true,
      creatorId, DateTime.now(), creatorId, DateTime.now())
}

