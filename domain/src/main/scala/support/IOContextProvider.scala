package support

import scala.concurrent.{ExecutionContext, Future}

/**
  *
  */
trait IOContextProvider {

  def get(): IOContext

  def readOnly(): IOContext

  def localTx[A](execution: IOContext => A): A

  def futureLocalTx[A](execution: IOContext => Future[A])(implicit ec: ExecutionContext): Future[A]

}
