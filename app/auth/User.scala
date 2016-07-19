package auth

import auth.Role.Role

case class User(id: String, role: Role) {

}

object User {

	def findById(id: String): Option[User] = Option(new User(id, Role.NormalUser))
}
