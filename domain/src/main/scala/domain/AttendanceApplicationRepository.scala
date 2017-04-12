package domain

import domain.model.{AttendanceApplication, AttendanceApplicationId, UserId}
import support.IOContext

import scala.concurrent.Future

/**
  * AttendanceApplicationのリポジトリ
  */
trait AttendanceApplicationRepository extends Repository[AttendanceApplication, AttendanceApplicationId] {
  def listByApplierId(applierId:UserId)(implicit io:IOContext): Future[List[AttendanceApplication]]
  def listByApproverId(approverId:UserId)(implicit io:IOContext): Future[List[AttendanceApplication]]
}
