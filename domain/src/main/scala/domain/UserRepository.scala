package domain

import domain.model.{SpaceId, User, UserId}
import support.{IOContext, PagedList, PageQuery}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Userリポジトリ
  */
trait UserRepository extends Repository[User, UserId] {
  def list(spaceId:SpaceId, pq:PageQuery)(implicit io:IOContext): Future[PagedList[User]]
  def listApplier(approverId:UserId, pq:PageQuery)(implicit io:IOContext): Future[PagedList[User]]
}
