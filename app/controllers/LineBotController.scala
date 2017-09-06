package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._
import dao.testDao
import models.Test
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.Controller
import play.api.mvc._

import scala.util.parsing.json.JSONArray

@Singleton
class LineBotController @Inject()(testDao: testDao)(val messagesApi: MessagesApi) extends Controller with I18nSupport{

  implicit val testWrites = new Writes[Test] {
    override def writes(o: Test): JsValue = Json.obj(
      "id" -> o.id,
      "name" -> o.name
    )
  }

  def getJsonString = Action.async {
    testDao.all().map(
      datalist=>{
//       var jsonlist = JSONArray(datalist.toList).toString()
       var jsonlist = Json.toJson(datalist).toString()
        Ok(jsonlist)
      }
    )
  }

  def getTestJson = Action{
    var jsondata = "Test"
    Ok(jsondata)
  }
}
