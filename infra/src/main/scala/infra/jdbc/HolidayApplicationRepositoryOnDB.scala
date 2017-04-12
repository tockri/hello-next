package infra.jdbc

import domain.HolidayApplicationRepository
import domain.model.{HolidayApplication, HolidayApplicationId, UserId}
import org.joda.time.DateTime
import scalikejdbc._

/**
  * Created by fujita on 2017/04/12.
  */
class HolidayApplicationRepositoryOnDB extends RepositoryOnDB[HolidayApplication, HolidayApplicationId]
  with HolidayApplicationRepository {

  override val tableName = "holiday_application"

  override val columns = Seq("id", "date", "amount", "status", "paid", "applier_id", "approver_id", "reason", "comment", "creator_id", "created", "updator_id", "updated")

  override def entityMap(e:HolidayApplication):Map[SQLSyntax, ParameterBinder] =
    Map(
      column.date -> e.date,
      column.amount -> e.amount,
      column.status -> e.status,
      column.paid -> e.paid,
      column.applierId -> e.applierId,
      column.approverId -> e.approverId,
      column.reason -> e.reason,
      column.comment -> e.comment,
      column.creatorId -> e.creatorId,
      column.updatorId -> e.updatorId
    )

  override def entity(ha: ResultName[HolidayApplication])(rs: WrappedResultSet) =
    new HolidayApplication(
      id = HolidayApplicationId(rs.get(ha.id)),
      date = rs.get(ha.date),
      amount = rs.get(ha.amount),
      status = rs.get(ha.status),
      paid = rs.get(ha.paid),
      applierId = UserId(rs.get(ha.applierId)),
      approverId = UserId(rs.get(ha.approverId)),
      reason = rs.get(ha.reason),
      comment = rs.get(ha.comment),
      creatorId = UserId(rs.get(ha.creatorId)),
      created = rs.get(ha.created),
      updatorId = UserId(rs.get(ha.updatorId)),
      updated = rs.get(ha.updated)
    )

  override def createdEntity(e: HolidayApplication, newId: Long) =
    e.copy(id = new HolidayApplicationId(newId), created = DateTime.now(), updated = DateTime.now())

  override val stx = syntax("ha")

}
