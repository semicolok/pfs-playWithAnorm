package models

case class Board2 (id: Long, title: String, content: String)

case class User (id: Long, name: String)

object Board2 {
  import anorm.SQL
  import anorm.SqlQuery
  val sql: SqlQuery = SQL("select * from Board order by id asc")

  import play.api.Play.current
  import play.api.db.DB

  def getAll: List[Board2] = DB.withConnection { implicit connection =>
    sql().map ( row =>
        Board2(row[Long]("id"), row[String]("title"), row[String]("content"))
      ).toList
  }

  def getAllUserParser: List[Board2] = DB.withConnection { implicit connection =>
    sql.as(boardsParser)
  }

  def getAllWithPatterns: List[Board2] = DB.withConnection { implicit connection =>
    import anorm.Row
    sql().collect {
      case Row(Some(id: Long), Some(title: String), Some(content: String)) => Board2(id, title, content)
    }.toList
  }

  import anorm.RowParser
  val boardParser: RowParser[Board2] = {
    import anorm.~
    import anorm.SqlParser._

    long("id") ~
    str("title") ~
    str("content") map{
      case id ~ title ~ content => Board2(id, title, content)
    }
  }


  import anorm.ResultSetParser
  val boardsParser: ResultSetParser[List[Board2]] = boardParser *
}

object User {
  import anorm.SQL
  import anorm.SqlQuery
  import play.api.Play.current
  import play.api.db.DB

  val sql: SqlQuery = SQL("select * from User order by id asc")

  def findByUserId(id: Long): Map[User, List[Board2]] = {
    DB.withConnection { implicit connection =>
      val sql = SQL("select u.*, b.* from User u inner join Board b on (u.id = b.user_id) where u.id =" + id)
      val results: List[(User, Board2)] = sql.as(userBoardParser *)
      results.groupBy {_._1}.mapValues { _.map {_._2}}
    }
  }

  def getAll: List[User] = DB.withConnection { implicit connection =>
    sql.as(userParser *)
  }

  def getAllUsersWithBoards: Map[User, List[Board2]] = {
    DB.withConnection { implicit connection =>
      val sql = SQL("select u.*, b.* from User u inner join Board b on (u.id = b.user_id)")
      val results: List[(User, Board2)] = sql.as(userBoardParser *)
      results.groupBy {_._1}.mapValues { _.map {_._2}}
    }
  }

  import anorm.RowParser

  def userBoardParser:RowParser[(User, Board2)] = {
    import anorm.~
    import anorm.SqlParser._
    userParser ~ Board2.boardParser map (flatten)
  }
  
  val userParser: RowParser[User] = {
    import anorm.~
    import anorm.SqlParser._
    long("id") ~ str("name") map {
      case id ~ name => User(id, name)
    }
  }

  def insert(user: User): Boolean = {
    DB.withConnection { implicit connection =>
      val addedRows = SQL("""insert
        into User
        values ({name})""").on("name" -> user.name).excuteUpdate()
      addedRows == 1
    }
  }
}