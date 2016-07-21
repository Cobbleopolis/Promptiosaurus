package auth

import auth.Role.Role

case class User(username: String, email: String, role: Role) {

}

object User {

	def findByEmail(email: String): Option[User] = Option(new User(email, "", Role.NormalUser))
    
    def authenticate(email: String, password: String): Option[User] = findByEmail(email)
}
