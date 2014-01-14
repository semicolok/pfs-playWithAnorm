package controllers

import play.api.mvc.{Action, Controller}

object Users extends Controller {
  import models.User
	

  def getAll = Action { implicit req =>
    println("getAll User List")
    println(User.getAll)
    Ok("success")
  }
}