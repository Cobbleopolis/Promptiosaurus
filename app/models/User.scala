package models

import scalikejdbc._

case class User(
  username: String,
  email: String,
  password: String) {

  def save()(implicit session: DBSession = User.autoSession): User = User.save(this)(session)

  def destroy()(implicit session: DBSession = User.autoSession): Unit = User.destroy(this)(session)

}


object User extends SQLSyntaxSupport[User] {

  override val tableName = "users"

  override val columns = Seq("username", "email", "password")

  def apply(u: SyntaxProvider[User])(rs: WrappedResultSet): User = apply(u.resultName)(rs)
  def apply(u: ResultName[User])(rs: WrappedResultSet): User = new User(
    username = rs.get(u.username),
    email = rs.get(u.email),
    password = rs.get(u.password)
  )

  val u = User.syntax("u")

  override val autoSession = AutoSession

  def find(username: String)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.eq(u.username, username)
    }.map(User(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[User] = {
    withSQL(select.from(User as u)).map(User(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(User as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(User as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    username: String,
    email: String,
    password: String)(implicit session: DBSession = autoSession): User = {
    withSQL {
      insert.into(User).namedValues(
        column.username -> username,
        column.email -> email,
        column.password -> password
      )
    }.update.apply()

    User(
      username = username,
      email = email,
      password = password)
  }

  def batchInsert(entities: Seq[User])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'username -> entity.username,
        'email -> entity.email,
        'password -> entity.password))
        SQL("""insert into users(
        username,
        email,
        password
      ) values (
        {username},
        {email},
        {password}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: User)(implicit session: DBSession = autoSession): User = {
    withSQL {
      update(User).set(
        column.username -> entity.username,
        column.email -> entity.email,
        column.password -> entity.password
      ).where.eq(column.username, entity.username)
    }.update.apply()
    entity
  }

  def destroy(entity: User)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(User).where.eq(column.username, entity.username) }.update.apply()
  }

}
