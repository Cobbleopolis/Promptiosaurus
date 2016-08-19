package controllers

import javax.inject.Inject

import auth.{RegisterData, TokenValidateElement}
import jp.t2v.lab.play2.auth.LoginLogout
import models.User
import org.mindrot.jbcrypt.BCrypt
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import util.AuthUtil

class Auth @Inject()(val messagesApi: MessagesApi) extends Controller with LoginLogout with AuthConfigImpl with I18nSupport {

    val loginForm = Form {
        mapping("username" -> nonEmptyText, "password" -> nonEmptyText)(AuthUtil.authenticate)(_.map(u => (u.username, "")))
            .verifying("Invalid username or password", result => result.isDefined)
    }

    val registerForm = Form {
        mapping("username" -> nonEmptyText,
            "password" -> nonEmptyText, "confirmPassword" -> nonEmptyText,
            "email" -> email, "confirmEmail" -> email) (RegisterData.apply)(RegisterData.unapply)
            .verifying("Passwords must match", regData => regData.password == regData.confirmPassword)
            .verifying("Emails must match", regData => regData.email == regData.confirmEmail)
    }

    def login = Action { implicit request =>
        Ok(views.html.login(loginForm))
    }

    def logout = Action.async { implicit request =>
        gotoLogoutSucceeded.map(_.flashing(
            "success" -> "You've been logged out"
        ))
    }

    def register = Action { implicit request =>
        Ok(views.html.register(registerForm))
    }

    def authenticate = Action.async { implicit request =>
        loginForm.bindFromRequest.fold(
            formWithErrors => Future.successful(BadRequest(views.html.login(formWithErrors))),
            user           => gotoLoginSucceeded(user.get.username)
        )
    }

    def submitRegister = Action.async { implicit request =>
        registerForm.bindFromRequest.fold(
            formWithErrors => Future.successful(BadRequest(views.html.register(formWithErrors))),
            registerData   => Future.successful{
                User.create(registerData.username, registerData.email, BCrypt.hashpw(registerData.password, BCrypt.gensalt(16)))
                Redirect(routes.Auth.login())
            }
        )
    }
}
