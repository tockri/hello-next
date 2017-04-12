package support

import java.util.concurrent.{ExecutorService, Executors, TimeUnit}

import scala.concurrent.ExecutionContext

/**
  * ExecutorContext
  */
object PooledContexts extends LoggerSupport {
  private def newService(threadCount:Int):ExecutorService =
    ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(threadCount))
  private def ctx(service:ExecutorService) = ExecutionContext.fromExecutor(service)

  private val db = newService(10)
  implicit val dbContext:ExecutionContext = ctx(db)

  private val app = newService(5)
  implicit val appContext:ExecutionContext = ctx(app)

  def shutdown(): Unit = {
    Seq(db, app).foreach {svc =>
      try {
        svc.awaitTermination(Long.MaxValue, TimeUnit.NANOSECONDS)
      } catch {
        case ex:Exception => Logger.warn("termination failed", ex)
      } finally {
        svc.shutdownNow()
      }
    }
  }

}
