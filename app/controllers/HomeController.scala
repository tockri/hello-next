package controllers

import javax.inject._

import domain.UserRepository
import domain.model.UserId
import play.api.mvc._
import support.IOContextProvider

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(userRepository:UserRepository,
                               iOContextProvider: IOContextProvider) extends Controller {
  import support.PooledContexts.appContext
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action.async {
    iOContextProvider.futureLocalTx{implicit io =>
      for {
        u <-userRepository.get(new UserId(1))
      } yield {
        Ok(views.html.index("Your new application is ready.", u.get))
      }
    }
  }

}
