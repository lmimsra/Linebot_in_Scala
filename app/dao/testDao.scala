package dao

import javax.inject.Inject

import models.Test
import org.mariadb.jdbc.MariaDbConnection
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class testDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{
  import driver.api._
  private val Tests = TableQuery[TestTable]

  private class TestTable(tag: Tag) extends Table[Test](tag,"connection"){
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    def * = (id,name)<>(Test.tupled,Test.unapply _)
  }
  def all():Future[Seq[Test]] = db.run(Tests.result)
  def insert(test:Test):Future[Unit]=db.run(Tests += test).map{ _ => ()}
}
