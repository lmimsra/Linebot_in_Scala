package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc._
import play.api.data.Forms._
import dao.testDao
import models.Test
import play.api.data.Form

/**
  * created by mmr 2017/08/31
  * DataAccessTest Controller
  */

@Singleton
class DataAccessController @Inject()(testDao: testDao) extends Controller {

  //データの入力用フォーム作成
  val inputform = Form(mapping(
    "id"->longNumber,
    "name"->text)
  (Test.apply)(Test.unapply)
  )



  //  データの表示
  def show = Action.async {
    testDao.all().map {
      test => Ok(views.html.result("DBテスト")(test))
    }
  }
}
