package controllers

import javax.inject.Inject

import auth.Role
import buildinfo.BuildInfo
import jp.t2v.lab.play2.auth._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

class User @Inject()(val messagesApi: MessagesApi) extends Controller with AuthElement with AuthConfigImpl with I18nSupport {

    def profile = StackAction(AuthorityKey -> Role.NormalUser) { implicit request =>
        Ok(views.html.user(loggedIn))
    }

}
