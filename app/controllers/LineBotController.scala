package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._
import dao.testDao
import models.{Test, TestJsonData}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.Controller
import play.api.mvc._
import play.api.libs.ws
import play.api.libs.ws.{WSAPI, WSClient, WSRequest, WSResponse}

import scala.concurrent.{Await, Future}
import scala.math._
import scala.util.Random
import models.SecretContent

import scala.io.Source

@Singleton
class LineBotController @Inject()(ws:WSClient)(testDao: testDao)(val messagesApi: MessagesApi) extends Controller with I18nSupport {

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


  //DBからランダムに値を返す
  def getRandData = Action.async { request =>
    //bodyを代入
    val formdata = request.body.asJson
    var json_request = Json.toJson(formdata).validate[TestJsonData].get
    var jsonlist: String = ""
    var coun: Int = 0
    var rnd = new Random()
//    println("[my Info] 関数呼び出し前 coun-> " + coun)
//    println("[my Info] POSTされた値(Class) " + json_request.getClass)
//    println("[my Info] POSTされた値(Json) " + formdata)

    //DBからランダムに値を１つ値を取得
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


  def ReplayTalkApi = Action.async { request =>
    val secret = new SecretContent
    val requestUrl:WSRequest = ws.url("https://api.a3rt.recruit-tech.co.jp/talk/v1/smalltalk")
//    val apiRequest: WSRequest = requestUrl.withHeaders("Accept"->"application/json").withQueryString("apikey"->secret.getApiKey,"query"->"おはようございます")
    val apiRequest: WSRequest = requestUrl.withHeaders("Accept"->"application/json")
    val apiResponse:Future[WSResponse] = apiRequest.post(Map("apikey"->Seq(secret.getApiKey),"query"->Seq("おはようございます")))

    apiResponse.map(response =>{
      val body_text = response.json \"results"
      val body_main = (body_text.get.as[JsArray].value.toList.head \"reply").get.as[String]
      println(body_main)
      Ok(body_main)
    })

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
