package controllers

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc._
import dao.testDao

/**
  * created by mmr 2017/08/31
  * DataAccessTest Controller
  */

@Singleton
class DataAccessController @Inject()(testDao: testDao) extends Controller {

  def show = Action.async {
    testDao.all().map {
      test => Ok(views.html.result("出力")(test))
    }
  }
}
