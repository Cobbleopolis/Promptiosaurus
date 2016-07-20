package templateHelpers

object FormHelpers {
    import views.html.helper.FieldConstructor
    implicit val formFields = FieldConstructor(views.html.helpers.form.render)
}
