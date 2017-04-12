package infra.jdbc

import domain.GivenHolidayRepository
import domain.model.{GivenHoliday, GivenHolidayId, UserId}
import org.joda.time.DateTime
import scalikejdbc._
import support.IOContext

import scala.concurrent.Future

/**
  *
  */
class GivenHolidayRepositoryOnDB extends GivenHolidayRepository with RepositoryOnDB[GivenHoliday, GivenHolidayId] {

  override val tableName = "given_holiday"

  override val columns = Seq("id", "user_id", "days", "created", "expire")

  override def entityMap(e:GivenHoliday):Map[SQLSyntax, ParameterBinder] =
    Map(
      column.userId -> e.userId,
      column.days -> e.days,
      column.expire -> e.expire
    )

  override def entity(ea: ResultName[GivenHoliday])(rs: WrappedResultSet): GivenHoliday =
    GivenHoliday(
      id = GivenHolidayId(rs.get(ea.id)),
      userId = UserId(rs.get(ea.userId)),
      days = rs.get(ea.days),
      created = rs.get(ea.created),
      expire = rs.get(ea.expire)
    )

  override def createdEntity(e: GivenHoliday, newId: Long) =
    e.copy(id = GivenHolidayId(newId), created = DateTime.now())

  override val stx = syntax("gh")

  override def list(userId: UserId)(implicit io: IOContext): Future[List[GivenHoliday]] =
    Future{
      val where = sqls"${stx.userId} = $userId"
      _select(where)
    }
}
