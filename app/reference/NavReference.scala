package reference

object NavReference {
    
    object Pages {
        
        case class Page (label: String, path: String, title: String = App.name)
        
        val HOME   = Page("Home", "/")
        val LOGIN  = Page("Login", "/login")
        val LOGOUT = Page("Logout", "/logout")
        val USER   = Page("User", "/user")
        
        val pageSet: Array[Page] = Array(
            HOME,
            LOGIN,
            LOGOUT,
            USER
        )
    }
    
}
