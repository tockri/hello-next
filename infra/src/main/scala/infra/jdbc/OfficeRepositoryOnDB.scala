package infra.jdbc

import domain.OfficeRepository
import domain.model.{Office, OfficeId, SpaceId, UserId}
import scalikejdbc._
import support.IOContext

import scala.concurrent.Future

class OfficeRepositoryOnDB extends OfficeRepository with RepositoryOnDB[Office, OfficeId] {

  override val tableName = "office"

  override val columns = Seq("id", "space_id", "name", "latitude", "longitude", "creator_id", "created", "updator_id", "updated")

  override def entityMap(e: Office):Map[SQLSyntax, ParameterBinder] = Map(
    column.spaceId -> e.spaceId,
    column.name -> e.name,
    column.latitude -> e.latitude,
    column.longitude -> e.longitude,
    column.creatorId -> e.creatorId,
    column.updatorId -> e.updatorId
  )

  override def entity(o: ResultName[Office])(rs: WrappedResultSet): Office = Office(
    id = OfficeId(rs.get(o.id)),
    spaceId = SpaceId(rs.get(o.spaceId)),
    name = rs.get(o.name),
    latitude = rs.get(o.latitude),
    longitude = rs.get(o.longitude),
    creatorId = UserId(rs.get(o.creatorId)),
    created = rs.jodaDateTime(o.created),
    updatorId = UserId(rs.get(o.updatorId)),
    updated = rs.jodaDateTime(o.updated)
  )

  override def createdEntity(e: Office, newId: Long): Office =
    e.copy(id = new OfficeId(newId))

  override val stx = syntax("o")

  override def list(spaceId: SpaceId)(implicit io: IOContext): Future[List[Office]] =
    Future{
      _select(sqls"space_id = ${spaceId.value}")
    }
}
