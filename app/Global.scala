import models.Board

import akka.actor.{Actor, Props}
import play.api.libs.concurrent.Akka
import play.api.Application
import play.api.GlobalSettings
import play.api.templates.Html
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import play.api._

object Global extends GlobalSettings {
	override def onStart (app: Application) {
    Logger.info("call??")
    import scala.concurrent.duration._
    import play.api.Play.current
    for(board <- Board.findAll) {
      val actor = Akka.system.actorOf(
        Props(new BoardActor(board))
        )
      // Akka.system.scheduler.schedule (
      //   3.seconds, 5.seconds, actor, "send"
      //   )
      // Akka.system.scheduler.scheduleOnce(10 seconds) {
      //   file.delete()
      // }
    }
  }
}

import java.util.Date

class BoardActor(board:Board) extends Actor {
  def receive = {
    case "send" => {
      Logger.info("call??")
      println(board) 
    }
    case _ => play.api.Logger.warn("unsupported messages type")
  }

  def send(html:Html) = {
    
  }
}