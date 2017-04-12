package domain.model

class TypetalkId(value:Long) extends EntityId(value)

object TypetalkId {
  val Undef = new TypetalkId(0) {
    override val defined: Boolean = false
  }
  def apply(value:Long):TypetalkId = new TypetalkId(value)
}

case class Typetalk(
  id: TypetalkId,
  spaceId: SpaceId,
  notifyTopicId: Int,
  typetalkToken: String) extends Entity(id)
