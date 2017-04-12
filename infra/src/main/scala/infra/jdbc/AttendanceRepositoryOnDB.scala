package infra.jdbc

import domain.AttendanceRepository
import domain.model.{Attendance, AttendanceId, OfficeId, UserId}
import org.joda.time.{DateTime, DurationFieldType, LocalDate}
import scalikejdbc._
import support.IOContext

import scala.concurrent.Future

class AttendanceRepositoryOnDB
  extends AttendanceRepository
    with RepositoryOnDB[Attendance, AttendanceId] {

  override val tableName = "attendance"

  override val columns = Seq("id", "user_id", "office_id", "start_time", "end_time", "creator_id", "created", "updator_id", "updated")

  override def entityMap(e:Attendance):Map[SQLSyntax, ParameterBinder] = Map(
    column.id -> e.id,
    column.userId -> e.userId,
    column.officeId -> e.officeId,
    column.startTime -> e.startTime,
    column.endTime -> e.endTime,
    column.creatorId -> e.creatorId,
    column.updatorId -> e.updatorId
  )

  override def entity(a: ResultName[Attendance])(rs: WrappedResultSet): Attendance =
    Attendance(
      id = AttendanceId(rs.get(a.id)),
      userId = UserId(rs.get(a.userId)),
      officeId = OfficeId(rs.get(a.officeId)),
      startTime = rs.jodaDateTime(a.startTime),
      endTime = rs.jodaDateTimeOpt(a.endTime),
      creatorId = UserId(rs.get(a.creatorId)),
      created = rs.jodaDateTime(a.created),
      updatorId = UserId(rs.get(a.updatorId)),
      updated = rs.jodaDateTime(a.updated)
    )

  override def createdEntity(e: Attendance, newId: Long): Attendance =
    e.copy(id = AttendanceId(newId), created = DateTime.now(), updated = DateTime.now())

  override val stx = syntax("a")

  override def list(userId: UserId, year: Int, month: Int)(implicit io: IOContext): Future[List[Attendance]] =
    Future{
      val from = new LocalDate(year, month, 1)
      val to = from.withFieldAdded(DurationFieldType.months(), 1)
      _select(sqls"user_id = $userId AND start_time >= $from AND start_time < $to")
    }
}
