package infra.jdbc

import domain.TypetalkRepository
import domain.model.{SpaceId, Typetalk, TypetalkId}
import scalikejdbc._

/**
  * Created by fujita on 2017/04/12.
  */
class TypetalkRepositoryOnDB extends RepositoryOnDB[Typetalk, TypetalkId]
  with TypetalkRepository {

  override val tableName = "typetalk"

  override val columns = Seq("id", "space_id", "notify_topic_id", "typetalk_token")

  override def entityMap(e:Typetalk):Map[SQLSyntax, ParameterBinder] = Map(
    column.spaceId -> e.spaceId,
    column.notifyTopicId -> e.notifyTopicId,
    column.typetalkToken -> e.typetalkToken
  )

  override def entity(t: ResultName[Typetalk])(rs: WrappedResultSet): Typetalk = new Typetalk(
    id = TypetalkId(rs.get(t.id)),
    spaceId = SpaceId(rs.get(t.spaceId)),
    notifyTopicId = rs.get(t.notifyTopicId),
    typetalkToken = rs.get(t.typetalkToken)
  )

  override def createdEntity(e: Typetalk, newId: Long) =
    e.copy(id = new TypetalkId(newId))

  override val stx = syntax("t")

}
