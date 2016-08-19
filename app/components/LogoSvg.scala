package components

import play.twirl.api.Html

object LogoSvg extends Component {

    def render(params: (Symbol, Any)*): Html = {
        views.html.componentTemplates.logoSvg(defaultParams ++ params)
    }
    
}
