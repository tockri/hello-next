package infra.jdbc

import domain.AttendanceApplicationRepository
import domain.model.{AttendanceApplication, AttendanceApplicationId, AttendanceId, UserId}
import org.joda.time.DateTime
import scalikejdbc._
import support.IOContext

import scala.concurrent.Future

class AttendanceApplicationRepositoryOnDB
  extends AttendanceApplicationRepository
    with RepositoryOnDB[AttendanceApplication, AttendanceApplicationId] {

  override val tableName = "attendance_application"

  override val columns = Seq("id", "start_time", "end_time", "attendance_id", "status", "applier_id", "approver_id", "reason", "comment", "creator_id", "created", "updator_id", "updated")

  override def entityMap(e:AttendanceApplication):Map[SQLSyntax, ParameterBinder] =
    Map(
      column.startTime -> e.startTime,
      column.endTime -> e.endTime,
      column.attendanceId -> e.attendanceId,
      column.status -> e.status,
      column.applierId -> e.applierId,
      column.approverId -> e.approverId,
      column.reason -> e.reason,
      column.comment -> e.comment,
      column.creatorId -> e.creatorId,
      column.updatorId -> e.updatorId
    )

  override def entity(aa: ResultName[AttendanceApplication])(rs: WrappedResultSet) =
    AttendanceApplication(
      id = AttendanceApplicationId(rs.get(aa.id)),
      startTime = rs.get(aa.startTime),
      endTime = rs.get(aa.endTime),
      attendanceId = AttendanceId(rs.get(aa.attendanceId)),
      status = rs.get(aa.status),
      applierId = UserId(rs.get(aa.applierId)),
      approverId = UserId(rs.get(aa.approverId)),
      reason = rs.get(aa.reason),
      comment = rs.get(aa.comment),
      creatorId = UserId(rs.get(aa.creatorId)),
      created = rs.jodaDateTime(aa.created),
      updatorId = UserId(rs.get(aa.updatorId)),
      updated = rs.jodaDateTime(aa.updated)
    )

  override def createdEntity(e: AttendanceApplication, newId: Long): AttendanceApplication =
    e.copy(id = AttendanceApplicationId(newId), updated = DateTime.now(), created = DateTime.now())

  override val stx = syntax("aa")

  override def listByApplierId(applierId: UserId)(implicit io: IOContext): Future[List[AttendanceApplication]] =
    Future{
      _select(sqls"applier_id = $applierId")
    }

  override def listByApproverId(approverId: UserId)(implicit io: IOContext): Future[List[AttendanceApplication]] =
    Future{
      _select(sqls"approver_id = $approverId")
    }
}
