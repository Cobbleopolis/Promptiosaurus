package controllers

import auth.Role
import buildinfo.BuildInfo
import jp.t2v.lab.play2.auth._
import play.api.mvc._

class Application extends Controller with AuthElement with AuthConfigImpl {
    def index = Action { implicit request =>
        Ok(views.html.index(s"${BuildInfo.name} is ready. \n Version: ${BuildInfo.version}"))
    }

    def user = StackAction(AuthorityKey -> Role.NormalUser) { implicit request =>
        Ok(views.html.user())
    }

}