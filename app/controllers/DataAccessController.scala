package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc._
import play.api.data.Forms._
import dao.testDao
import models.Test
import play.api.data.Form
import play.api.i18n.{I18nSupport, MessagesApi}

/**
  * created by mmr 2017/08/31
  * DataAccessTest Controller
  */
case class FormGetter(text: String)

@Singleton
class DataAccessController @Inject()(testDao: testDao)(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  //データの入力用フォーム作成
  val inputform = Form(mapping(
    "id" -> longNumber,
    "name" -> text)
  (Test.apply)(Test.unapply)
  )

  val formGetter = Form(mapping(
    "name" -> text)(FormGetter.apply)(FormGetter.unapply))

  //データの追加(Create)
  def InsertData = Action.async { implicit request =>
    val param: FormGetter = formGetter.bindFromRequest.get
    testDao.insert(new Test(1,param.text)).map(_ => Redirect(routes.DataAccessController.show()))

  }


  //move to edit window
  def editContent(id: Long) = Action.async {
    testDao.findById(id).map {
      editData => Ok(views.html.edit("編集ページ")(inputform.fill(editData)))
    }
  }


  //update method
  def update(id:Long) = Action.async { implicit request =>
    val param: FormGetter = formGetter.bindFromRequest.get
    testDao.edit(new Test(id,param.text)).map(_ => Redirect(routes.DataAccessController.show()))
  }

  //  データの表示
  def show = Action.async {
    testDao.all().map {
      test => Ok(views.html.result("DBテスト")(test)(inputform))
    }
  }
}
