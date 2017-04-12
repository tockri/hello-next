package infra.jdbc

import domain.RestRepository
import domain.model.{AttendanceId, Rest, RestId, UserId}
import org.joda.time.DateTime
import scalikejdbc._

/**
  * Created by fujita on 2017/04/12.
  */
class RestRepositoryOnDB extends RepositoryOnDB[Rest, RestId] with RestRepository {

  override val tableName = "rest"

  override val columns = Seq("id", "attendance_id", "start_time", "end_time", "creator_id", "created", "updator_id", "updated")

  override def entityMap(e:Rest):Map[SQLSyntax, ParameterBinder] = Map(
    column.attendanceId -> e.attendanceId,
    column.startTime -> e.startTime,
    column.endTime -> e.endTime,
    column.creatorId -> e.creatorId,
    column.updatorId -> e.updatorId
  )

  override def entity(r: ResultName[Rest])(rs: WrappedResultSet): Rest = new Rest(
    id = RestId(rs.get(r.id)),
    attendanceId = AttendanceId(rs.get(r.attendanceId)),
    startTime = rs.get(r.startTime),
    endTime = rs.get(r.endTime),
    creatorId = UserId(rs.get(r.creatorId)),
    created = rs.get(r.created),
    updatorId = UserId(rs.get(r.updatorId)),
    updated = rs.get(r.updated)
  )

  override def createdEntity(e: Rest, newId: Long) =
    e.copy(id = RestId(newId), created = DateTime.now(), updated = DateTime.now())

  override val stx = syntax("r")

}
