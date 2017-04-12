package infra.jdbc

import domain.UserRepository
import domain.model.{OfficeId, SpaceId, User, UserId}
import org.joda.time.DateTime
import scalikejdbc._
import support.{IOContext, PagedList, PageQuery}

import scala.concurrent.Future

class UserRepositoryOnDB extends UserRepository with RepositoryOnDB[User, UserId] {
  override val tableName = "user"

  override val columns = Seq("id", "space_id", "name", "icon", "nulabid", "uniqueid", "enter_date", "office_id", "approver_id", "active", "creator_id", "created", "updator_id", "updated")

  override def entityMap(e: User): Map[SQLSyntax, ParameterBinder] = Map(
    column.spaceId -> e.spaceId,
    column.name -> e.name,
    column.icon -> e.icon,
    column.nulabid -> e.nulabid,
    column.uniqueid -> e.uniqueid,
    column.enterDate -> e.enterDate,
    column.officeId -> e.officeId,
    column.approverId -> e.approverId,
    column.active -> e.active,
    column.creatorId -> e.creatorId,
    column.updatorId -> e.updatorId
  )

  override def entity(u: ResultName[User])(rs: WrappedResultSet): User = User(
    id = UserId(rs.get(u.id)),
    spaceId = SpaceId(rs.get(u.spaceId)),
    name = rs.string(u.name),
    icon = rs.stringOpt(u.icon),
    nulabid = rs.string(u.nulabid),
    uniqueid = rs.string(u.uniqueid),
    enterDate = rs.jodaLocalDate(u.enterDate),
    officeId = rs.longOpt(u.officeId).map(v => new OfficeId(v)),
    approverId = rs.longOpt(u.approverId).map(v => UserId(v)),
    active = rs.boolean(u.active),
    creatorId = UserId(rs.long(u.creatorId)),
    created = rs.jodaDateTime(u.created),
    updatorId = UserId(rs.long(u.updatorId)),
    updated = rs.jodaDateTime(u.updated)
  )

  override def createdEntity(e: User, newId: Long): User =
    e.copy(id = UserId(newId), created = DateTime.now(), updated = DateTime.now())

  override val stx = syntax("u")

  override val defaultOrder: SQLSyntax = sqls"id desc"

  override def list(spaceId: SpaceId, pq: PageQuery)(implicit io: IOContext) = Future {
    val where = sqls"space_id = ${spaceId.value} AND active = 1"
    val list = _select(where = where, pq = pq)
    val total = _selectCount(where)
    PagedList(list, total, pq.page, pq.size)
  }

  override def listApplier(approverId: UserId, pq: PageQuery)(implicit io: IOContext) = Future {
    val where = sqls"approver_id = ${approverId.value} AND active = 1"
    val list = _select(where = where, pq = pq)
    val total = _selectCount(where)
    PagedList(list, total, pq.page, pq.size)
  }
}
