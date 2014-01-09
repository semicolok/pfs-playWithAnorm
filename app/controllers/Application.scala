package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current

object Application extends Controller {

  def index = Action {
    // Ok("hello~")
    Redirect(routes.Boards.list())
  }

  def getDbUrl = Action {
    current.configuration.getConfig("db.default").map {
      dbConf =>
      dbConf.getString("driver").map(Logger.info(_))
      dbConf.getString("url").map(Logger.info(_))
    }

    current.configuration.getString("db.default.url").map {
      Ok(_)
    }.getOrElse(NotFound)
  }
}