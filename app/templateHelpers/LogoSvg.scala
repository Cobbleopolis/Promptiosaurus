package templateHelpers

import play.twirl.api.Html

object LogoSvg {
    
    val defaultHeight: Int = 48
    
    val aspectRatio: Float = 285f / 176f
    
    val defaultMap: Map[Symbol, Any] = Map(
        'height -> defaultHeight,
        'width -> defaultHeight * aspectRatio
    )
    
    def render(params: (Symbol, Any)*): Html = {
        val combinedList: Map[Symbol, Any] = (params ++ defaultMap.toSeq).groupBy(_._1).mapValues(_.map(_._2).toList.head)
        val paramString: String = combinedList.foldLeft("") { (s: String, pair: (Symbol, Any)) =>
            s + pair._1.name + "=\"" + pair._2.toString + "\""
        }
        views.html.helpers.logoSvg(paramString)
    }
    
}
