package util

import models.User
import org.mindrot.jbcrypt.BCrypt

object AuthUtil {

    def authenticate(username: String, password: String): Option[User] = {
        val user = User.find(username)
        if (user.isDefined)
            if (BCrypt.checkpw(password, user.get.password))
                user
            else
                None
        else
            None
    }

}
