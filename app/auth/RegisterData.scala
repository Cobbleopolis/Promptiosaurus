package auth

import models.User
import org.mindrot.jbcrypt.BCrypt

case class RegisterData(username: String, password: String, confirmPassword: String, email: String, confirmEmail: String) {

    def toNewUser: User = new User (username, password, BCrypt.hashpw(password, BCrypt.gensalt(16)))

}
