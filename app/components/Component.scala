package components

import play.twirl.api.Html

trait Component {

    val defaultParams: Map[Symbol, Any] = Map()

    def render(params: (Symbol, Any)*): Html

}
