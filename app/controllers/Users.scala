package controllers

import play.api.mvc.{Action, Controller}

object Users extends Controller {
  import models.User
	

  def getAll = Action { implicit req =>
    println("getAll User List")
    println(User.getAll)
    Ok("success")
  }

  def insertUser = Action { implicit req =>
    User.insert(User(2L, "testUser"))
    Ok("success")
  }

  def deleteUser(id: Long) = Action {implicit req =>
    User.delete(id)
    Ok("success")
  }
}