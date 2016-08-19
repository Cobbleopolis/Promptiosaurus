package controllers

import javax.inject.Inject

import auth.RegisterData
import jp.t2v.lab.play2.auth.{LoginLogout, OptionalAuthElement}
import models.User
import org.mindrot.jbcrypt.BCrypt
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import scalikejdbc._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import util.AuthUtil

class Auth @Inject()(val messagesApi: MessagesApi) extends Controller with LoginLogout with OptionalAuthElement with AuthConfigImpl with I18nSupport {

    val loginForm = Form {
        mapping(
            "username" -> nonEmptyText,
            "password" -> nonEmptyText
        )(AuthUtil.authenticate)(_.map(u => (u.username, "")))
            .verifying("Invalid username or password", result => result.isDefined)
    }

    val registerForm = Form {
        mapping(
            "username" -> nonEmptyText.verifying("Username already exists", username => User.find(username).isEmpty),
            "password" -> nonEmptyText,
            "confirmPassword" -> nonEmptyText,
            "email" -> email.verifying("Email already in use", email => User.countBy(sqls.eq(User.u.c("email"), email)) == 0),
            "confirmEmail" -> email.verifying("Email already in use", email => User.countBy(sqls.eq(User.u.c("email"), email)) == 0)
        )(RegisterData.apply)(RegisterData.unapply)
            .verifying("Passwords must match", signUpData => signUpData.password == signUpData.confirmPassword)
            .verifying("Emails must match", signUpData => signUpData.email == signUpData.confirmEmail)
    }

    def login = StackAction { implicit request =>
        Ok(views.html.login(loginForm))
    }

    def logout = Action.async { implicit request =>
        gotoLogoutSucceeded.map(_.flashing(
            "success" -> "You've been logged out"
        ))
    }

    def register = StackAction { implicit request =>
        Ok(views.html.register(registerForm))
    }

    def authenticate = AsyncStack { implicit request =>
        loginForm.bindFromRequest.fold(
            formWithErrors => Future.successful(BadRequest(views.html.login(formWithErrors))),
            user => gotoLoginSucceeded(user.get.username)
        )
    }

    def submitRegister = AsyncStack { implicit request =>
        registerForm.bindFromRequest.fold(
            formWithErrors => Future.successful(BadRequest(views.html.register(formWithErrors))),
            registerData => Future.successful {
                User.create(registerData.username, registerData.email, BCrypt.hashpw(registerData.password, BCrypt.gensalt(16)))
                Redirect(routes.Auth.login())
            }
        )
    }
}
