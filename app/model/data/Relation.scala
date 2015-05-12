package model.data

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import util.CSV

/**
 * @author BenB
 */
case class Relation(id: Long, label: String, data: String, dataset_id: Long) {
  def parseData() : List[Map[String,String]] = {
    val parser = new CSV()
    parser.parse(this.data)
  }
  /*def parseData() : List[Map[String,String]] = List(Map( "column1" -> "hello","column2" -> "world"),Map( "column1" -> "foo","column2" -> "bar"))*/
  
}

object Relation {
  
  val relation = {
    get[Long]("id") ~ 
    get[String]("label") ~
    get[String]("data") ~
    get[Long]("dataset_id")map {
      case id~label~data~dataset_id => Relation(id,label,data,dataset_id)
      }
  }
  
  def byId(id: Long) : Relation= 
    DB.withConnection { implicit c => SQL("SELECT * FROM relation WHERE id = {id}").on("id" -> id).as(relation single)}
  
}