package infra.jdbc

import scalikejdbc.DBSession
import support.IOContext

/**
  *
  */
case class IOContextOnJDBC(session: DBSession) extends IOContext
