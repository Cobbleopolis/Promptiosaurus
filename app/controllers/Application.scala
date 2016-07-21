package controllers

import javax.inject.Inject

import auth.{Role, TokenValidateElement}
import buildinfo.BuildInfo
import jp.t2v.lab.play2.auth._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

class Application @Inject()(val messagesApi: MessagesApi) extends Controller with TokenValidateElement with AuthElement with AuthConfigImpl with I18nSupport {
    
    def index = Action { implicit request =>
        Ok(views.html.index(s"${BuildInfo.name} is ready. \n Version: ${BuildInfo.version}"))
    }

    def user = StackAction(AuthorityKey -> Role.NormalUser) { implicit request =>
        Ok(views.html.user(loggedIn))
    }

}