package infra.jdbc

import domain.Repository
import domain.model.{Entity, EntityId}
import scalikejdbc.{AutoSession, DBSession}
import support.IOContext

import scala.concurrent.{ExecutionContext, Future}

/**
  *
  */
trait RepositoryOnDB[E<:Entity[ID], ID<:EntityId] extends BaseDao[E, ID] with Repository[E, ID] {
  implicit val executorContext: ExecutionContext = support.PooledContexts.dbContext

  implicit def ioContext2dbSession(implicit ioContext:IOContext):DBSession =
    ioContext match {
      case iox:IOContextOnJDBC => iox.session
      case _ => AutoSession
    }

  def get(id: ID)(implicit io: IOContext): Future[Option[E]] =
    Future(_selectOne(id))

  def store(s: E)(implicit io: IOContext): Future[E] =
    Future(_updateInsert(s))

  def remove(id: ID)(implicit io: IOContext): Future[Unit] =
    Future(_delete(id))
}
