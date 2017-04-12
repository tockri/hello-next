package infra.jdbc

import domain.OfficialHolidayRepository
import domain.model.{OfficialHoliday, OfficialHolidayId, SpaceId, UserId}
import org.joda.time.DateTime
import scalikejdbc._

/**
  * Created by fujita on 2017/04/12.
  */
class OfficialHolidayRepositoryOnDB extends RepositoryOnDB[OfficialHoliday, OfficialHolidayId]
  with OfficialHolidayRepository {
  override val tableName = "official_holiday"

  override val columns = Seq("id", "space_id", "date", "name", "creator_id", "created", "updator_id", "updated")

  override def entityMap(e:OfficialHoliday):Map[SQLSyntax, ParameterBinder] = Map(
    column.spaceId -> e.spaceId,
    column.date -> e.date,
    column.name -> e.name,
    column.creatorId -> e.creatorId,
    column.updatorId -> e.updatorId
  )

  override def entity(oh: ResultName[OfficialHoliday])(rs: WrappedResultSet) = new OfficialHoliday(
    id = OfficialHolidayId(rs.long(oh.id)),
    spaceId = SpaceId(rs.long(oh.spaceId)),
    date = rs.get(oh.date),
    name = rs.get(oh.name),
    creatorId = UserId(rs.long(oh.creatorId)),
    created = rs.get(oh.created),
    updatorId = UserId(rs.long(oh.updatorId)),
    updated = rs.get(oh.updated)
  )

  override def createdEntity(e: OfficialHoliday, newId: Long) =
    e.copy(id = OfficialHolidayId(newId), created = DateTime.now(), updated = DateTime.now())

  override val stx = syntax("oh")

  override val autoSession = AutoSession

}
