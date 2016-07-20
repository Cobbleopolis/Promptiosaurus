package controllers

import auth.Role
import jp.t2v.lab.play2.auth.AuthElement
import play.api.mvc._

class Application extends Controller with AuthElement with AuthConfigImpl {

	def index = Action { implicit request =>
        Ok(views.html.index("Your new application is ready."))
	}
    
    def user = StackAction(AuthorityKey -> Role.NormalUser) { implicit request =>
        Ok(views.html.user(loggedIn))
    }

}