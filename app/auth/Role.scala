package auth

object Role extends Enumeration {
	type Role = Value
	val NormalUser, Administrator = Value
}
