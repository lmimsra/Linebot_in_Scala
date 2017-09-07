package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._
import dao.testDao
import models.{Test, TestJsonData}
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.Controller
import play.api.mvc._

import scala.util.parsing.json.JSONArray

@Singleton
class LineBotController @Inject()(testDao: testDao)(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  //mapping class for json
  implicit val testWrites = new Writes[Test] {
    override def writes(o: Test): JsValue = Json.obj(
      "id" -> o.id,
      "name" -> o.name
    )
  }

  implicit val JsonRead = Json.reads[TestJsonData]

  def getJsonString = Action.async {
    testDao.all().map(
      datalist => {
        //       var jsonlist = JSONArray(datalist.toList).toString()
        var jsonlist = Json.toJson(datalist).toString()
        Ok(jsonlist)
      }
    )
  }


  def sendPHP = Action.async { request =>
    //bodyを代入
    val formdata = request.body.asJson
    var json_request = Json.toJson(formdata).validate[TestJsonData]
    var jsonlist: String = ""
    var coun:Int = 0
    testDao.all().map(
      datalist => {
        jsonlist = Json.toJson(datalist).toString()
        Ok(jsonlist)
      }
    )

  }

}
