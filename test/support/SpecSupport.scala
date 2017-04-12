package support

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

/**
  * Created by fujita on 2017/04/12.
  */
trait SpecSupport {
  def await[A](f:Future[A])(implicit ex:ExecutionContext): A = Await.result(f, Duration.Inf)
}
