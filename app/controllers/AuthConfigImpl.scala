package controllers

import auth.Role._
import models.User
import jp.t2v.lab.play2.auth.{AsyncIdContainer, AuthConfig, TransparentIdContainer}
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect._

trait AuthConfigImpl extends AuthConfig {
    /**
      * A type that is used to identify a user.
      * `String`, `Int`, `Long` and so on.
      */
    type Id = String

    /**
      * A type that represents a user in your application.
      * `User`, `Account` and so on.
      */
    type User = models.User

    /**
      * A type that is defined by every action for authorization.
      * This sample uses the following trait:
      *
      * sealed trait Role
      * case object Administrator extends Role
      * case object NormalUser extends Role
      */
    type Authority = Role

    /**
      * A `ClassTag` is used to retrieve an email from the Cache API.
      * Use something like this:
      */
    val idTag: ClassTag[Id] = classTag[Id]

    override lazy val idContainer: AsyncIdContainer[Id] = AsyncIdContainer(new TransparentIdContainer[Id])

    /**
      * The session timeout in seconds
      */
    val sessionTimeoutInSeconds: Int = 3600

    /**
      * A function that returns a `User` object from an `Id`.
      * You can alter the procedure to suit your application.
      */
    def resolveUser(id: Id)(implicit ctx: ExecutionContext): Future[Option[User]] =
        Future.successful(User.find(id))

    /**
      * Where to redirect the user after a successful login.
      */
    def loginSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext): Future[Result] =
        Future.successful(Redirect(routes.User.profile()))

    /**
      * Where to redirect the user after logging out
      */
    def logoutSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext): Future[Result] =
        Future.successful(Redirect(routes.Auth.login()))

    /**
      * If the user is not logged in and tries to access a protected resource then redirect them as follows:
      */
    def authenticationFailed(request: RequestHeader)(implicit ctx: ExecutionContext): Future[Result] =
        Future.successful {
            request.headers.get("X-Requested-With") match {
                case Some("XMLHttpRequest") => Unauthorized("Authentication failed")
                case _ => Redirect(routes.Auth.login())
            }
        }

    /**
      * If authorization failed (usually incorrect password) redirect the user as follows:
      */
    override def authorizationFailed(request: RequestHeader, user: User, authority: Option[Authority])(implicit context: ExecutionContext): Future[Result] = {
        Future.successful(Forbidden("no permission"))
    }

    /**
      * A function that determines what `Authority` a user has.
      * You should alter this procedure to suit your application.
      */
    def authorize(user: User, authority: Authority)(implicit ctx: ExecutionContext): Future[Boolean] = Future.successful {
//        (user.role, authority) match {
//            case (Administrator, _) => true
//            case (NormalUser, NormalUser) => true
//            case _ => false
//        }
        true
    }

}
