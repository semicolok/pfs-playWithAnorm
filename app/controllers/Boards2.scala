package controllers

import play.api.mvc.{Action, Controller}

import models.Board2
import models.User

object Boards2 extends Controller {

  def getAll = Action {
    println("getAll Board2 List")
    println(Board2.getAllUserParser)
    Ok("success")
  }

  def findByUserId(id: Long) = Action { implicit req =>
    println("findByUserId List")
    println(User.findByUserId(id))
    Ok("success")
  }
}