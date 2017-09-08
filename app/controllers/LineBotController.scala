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

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.math._
import scala.util.Random
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
        var jsonlist = Json.toJson(datalist).toString()
        Ok(jsonlist)
      }
    )
  }


  def sendPHP = Action.async { request =>
    //bodyを代入
    val formdata = request.body.asJson
    var json_request = Json.toJson(formdata).validate[TestJsonData].get
    var jsonlist: String = ""
    var coun: Int = 0
    var rnd = new Random()
//    println("[my Info] 関数呼び出し前 coun-> " + coun)
//    println("[my Info] POSTされた値(Class) " + json_request.getClass)
//    println("[my Info] POSTされた値(Json) " + formdata)
    var endname:Future[Test] = for {
      num <- testDao.countData()
      ids <- testDao.findIdList()
      one <- testDao.findById(ids(rnd.nextInt(num)))

    }yield one

//    println("[my Info] endname -> "+endname.value)
    endname.map(
      result =>{
        jsonlist=Json.toJson(result.name).toString()
//        println("[my Info] jsonlist -> "+jsonlist)
        Ok(result.name)
      }
    )

  }

  def getOneData : Future[Test] = {
    val result:Future[Test] = for {
      num <- testDao.countData()
      one <- testDao.findById((floor(random * num).toLong)+1)
    }yield one
    println(result)
      result
  }

}
