package infra.jdbc

import scalikejdbc.{DB, DBSession}
import support.{IOContext, IOContextProvider}

import scala.concurrent.{ExecutionContext, Future}

/**
  *
  */
class IOContextProviderOnJDBC(dbSession: DBSession) extends IOContextProvider {

  override def get(): IOContext = IOContextOnJDBC(dbSession)

  override def readOnly(): IOContext = IOContextOnJDBC(dbSession)

  override def localTx[A](execution: (IOContext) => A): A =
    DB localTx (tx => execution(IOContextOnJDBC(tx)))

  override def futureLocalTx[A](execution: (IOContext) => Future[A])(implicit ec: ExecutionContext): Future[A] =
    DB futureLocalTx (tx => execution(IOContextOnJDBC(tx)))
}
