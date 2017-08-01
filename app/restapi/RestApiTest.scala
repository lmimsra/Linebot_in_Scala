package restapi

import javax.inject.{Inject, Singleton}

import play.api.libs.json.{JsObject, Json}
import play.api.mvc._

import scala.util.parsing.json.JSONObject

@Singleton
class RestApiTest @Inject() extends Controller {

  def getJsonScala = Action { implicit request =>
    val returnValue = "aiiii"
    val testjson = JSONObject(Map(
      "id" -> 2,
      "name" -> "kondomamoru",
      "place" -> "Aizu",
      "University" -> "University of Aizu"
    )).toString()
    //    Ok(views.html.index("Your new application is ready."))
    Ok(testjson)
  }
}
