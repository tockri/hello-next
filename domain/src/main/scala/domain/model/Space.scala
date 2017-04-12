package domain.model

import org.joda.time._

class SpaceId(value:Long) extends EntityId(value)

object SpaceId {
  val Undef = new SpaceId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):SpaceId = new SpaceId(value)
}

case class Space(id: SpaceId,
                 name: String,
                 icon: Option[String] = None,
                 nulabappsSpaceKey: Option[String] = None,
                 creatorId: UserId,
                 created: DateTime,
                 updatorId: UserId,
                 updated: DateTime) extends Entity(id)

object Space {
  def create(name: String,
             icon: Option[String] = None,
             nulabappsSpaceKey: Option[String] = None,
             creatorId: UserId):Space =
    new Space(SpaceId.Undef, name, icon, nulabappsSpaceKey,
      creatorId, DateTime.now(), creatorId, DateTime.now())
}