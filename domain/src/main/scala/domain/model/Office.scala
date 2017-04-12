package domain.model

import org.joda.time._

class OfficeId(value:Long) extends EntityId(value)

object OfficeId {
  val Undef = new OfficeId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):OfficeId = new OfficeId(value)
}

case class Office(
  id: OfficeId,
  spaceId: SpaceId,
  name: String,
  latitude: Option[Double] = None,
  longitude: Option[Double] = None,
  creatorId: UserId,
  created: DateTime,
  updatorId: UserId,
  updated: DateTime) extends Entity(id)

object Office {

  def create(spaceId: SpaceId,
             name: String,
             latitude: Option[Double] = None,
             longitude: Option[Double] = None,
             creatorId: UserId):Office =
    Office(OfficeId.Undef, spaceId, name, latitude, longitude,
      creatorId, DateTime.now(), creatorId, DateTime.now())


}