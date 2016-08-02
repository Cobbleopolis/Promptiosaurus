package controllers

import javax.inject.Inject

import auth.{TokenValidateElement, User}
import jp.t2v.lab.play2.auth.LoginLogout
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class Auth @Inject()(val messagesApi: MessagesApi) extends Controller with LoginLogout with AuthConfigImpl with I18nSupport {

    val loginForm = Form {
        mapping("email" -> email, "password" -> text)(User.authenticate)(_.map(u => (u.email, "")))
            .verifying("Invalid email or password", result => result.isDefined)
    }

    def login = Action { implicit request =>
        Ok(views.html.login(loginForm))
    }

    def logout = Action.async { implicit request =>
        gotoLogoutSucceeded.map(_.flashing(
            "success" -> "You've been logged out"
        ))
    }

    def authenticate = Action.async { implicit request =>
        loginForm.bindFromRequest.fold(
            formWithErrors => Future.successful(BadRequest(views.html.login(formWithErrors))),
            user           => gotoLoginSucceeded(user.get.email)
        )
    }
}
