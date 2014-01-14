package models

case class Board (id:Long, title:String, writer:String, content:String)

object Board {
  var boards = Set(
    Board(1L, "testTitle1", "testWriter1", "testContent1"),
    Board(2L, "testTitle2", "testWriter2", "testContent2"),
    Board(3L, "testTitle3", "testWriter3", "testContent3"),
    Board(4L, "testTitle4", "testWriter4", "testContent4"),
    Board(5L, "testTitle5", "testWriter5", "testContent5"),
    Board(6L, "testTitle6", "testWriter6", "testContent6")
    )

  def findAll = boards.toList.sortBy(_.id)

  def findById(id:Long) = boards.find(_.id == id)

  def add(board: Board) {
    boards = boards + board
  }

  def delete(id:Long) {
    println(id)
  }
}