package controllers

import play.api.mvc.{Action, Controller}
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages
import play.api.mvc.Flash

import models.Board

object Boards extends Controller {
  private val boardForm: Form[Board] = Form(
      mapping(
          "id" -> longNumber.verifying("validation.id.duplicate", Board.findById(_).isEmpty),
          "title" -> nonEmptyText,
          "writer" -> nonEmptyText,
          "content" -> nonEmptyText
        )(Board.apply)(Board.unapply)
    )

	def list = Action { implicit request =>
    val boards = Board.findAll
    Ok(views.html.boards.list(boards))
  }

  def detail(id: Long) = Action {implicit request =>
    Board.findById(id).map {board =>
      Ok(views.html.boards.detail(board))
    }.getOrElse(NotFound)
  }

  def save = Action {implicit request =>
    val newBoardForm = boardForm.bindFromRequest()

    newBoardForm.fold(
      hasErrors = { form =>
        Redirect(routes.Boards.newBoard()).flashing(Flash(form.data) + ("error" -> Messages("validation.errors")))
      },
      success = { newBoard =>
        Board.add(newBoard)
        val message = Messages("boards.new.success", newBoard.title)
        Redirect(routes.Boards.detail(newBoard.id)).flashing("success" -> message)
      }
    )
  }

  def newBoard = Action { implicit request =>
    val form = if (flash.get("error").isDefined)
      boardForm.bind(flash.data)
    else
      boardForm
    Ok(views.html.boards.form(form))
  }

  def delete(id:Long) = Action {
    Board.delete(id)
    Redirect(routes.Boards.list())
  }
}