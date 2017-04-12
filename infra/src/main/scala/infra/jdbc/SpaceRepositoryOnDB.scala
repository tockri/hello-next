package infra.jdbc

import domain.SpaceRepository
import domain.model.{Space, SpaceId, UserId}
import org.joda.time.DateTime
import scalikejdbc._
import support.IOContext

import scala.concurrent.Future

class SpaceRepositoryOnDB extends SpaceRepository with RepositoryOnDB[Space, SpaceId] {

  override val tableName = "space"

  override val columns = Seq("id", "name", "icon", "nulabapps_space_key",
    "creator_id", "created", "updator_id", "updated")

  override def entityMap(e:Space):Map[SQLSyntax, ParameterBinder] = Map(
    column.name -> e.name,
    column.icon -> e.icon,
    column.nulabappsSpaceKey -> e.nulabappsSpaceKey,
    column.creatorId -> e.creatorId,
    column.updatorId -> e.updatorId
  )

  override def entity(s: ResultName[Space])(rs: WrappedResultSet): Space =
    Space(
      id = SpaceId(rs.get(s.id)),
      name = rs.get(s.name),
      icon = rs.get(s.icon),
      nulabappsSpaceKey = rs.get(s.nulabappsSpaceKey),
      creatorId = UserId(rs.get(s.creatorId)),
      created = rs.jodaDateTime(s.created),
      updatorId = UserId(rs.get(s.updatorId)),
      updated = rs.jodaDateTime(s.updated)
    )

  override def createdEntity(e: Space, newId: Long): Space =
    e.copy(id = SpaceId(newId), created = DateTime.now(), updated = DateTime.now())

  override val stx = syntax("s")

  override def list()(implicit io: IOContext): Future[List[Space]] =
    Future{
      _select(sqls"1 = 1")
    }

  override def spaceByKey(nulabAppsSpaceKey: String)(implicit io: IOContext): Future[Option[Space]] =
    Future {
      _selectOne(sqls"nulabapps_space_key = $nulabAppsSpaceKey")
    }
}
