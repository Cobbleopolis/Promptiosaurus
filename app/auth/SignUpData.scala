package auth

import models.User
import org.mindrot.jbcrypt.BCrypt

case class SignUpData(username: String, password: String, confirmPassword: String, email: String, confirmEmail: String) {

}
