package reference

import buildinfo.BuildInfo

object NavReference {
    
    object Pages {
        
        case class Page (label: String, path: String, title: String = BuildInfo.name)
        
        val HOME   = Page("pages.home", "/")
        val REGISTER   = Page("pages.register", "/register")
        val LOGIN  = Page("pages.login", "/login")
        val LOGOUT = Page("pages.logout", "/logout")
        val USER   = Page("pages.user", "/user")
        
        val pageSet: Array[Page] = Array(
            HOME,
            REGISTER,
            LOGIN,
            LOGOUT,
            USER
        )
    }
    
}
