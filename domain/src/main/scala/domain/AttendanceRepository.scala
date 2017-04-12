package domain

import domain.model.{Attendance, AttendanceId, UserId}
import support.IOContext

import scala.concurrent.Future

/**
  *
  */
trait AttendanceRepository extends Repository[Attendance, AttendanceId] {
  def list(userId:UserId, year:Int, month:Int)(implicit io:IOContext): Future[List[Attendance]]
}
