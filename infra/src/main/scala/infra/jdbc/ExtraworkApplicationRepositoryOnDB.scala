package infra.jdbc

import domain.ExtraworkApplicationRepository
import domain.model.{ExtraworkApplication, ExtraworkApplicationId, UserId}
import org.joda.time.DateTime
import scalikejdbc._

/**
  *
  */
class ExtraworkApplicationRepositoryOnDB extends RepositoryOnDB[ExtraworkApplication, ExtraworkApplicationId]
  with ExtraworkApplicationRepository {

  override val tableName = "extrawork_application"

  override val columns = Seq("id", "start_time", "end_time", "status", "applier_id", "approver_id", "reason", "comment", "creator_id", "created", "updator_id", "updated")

  override def entityMap(e:ExtraworkApplication):Map[SQLSyntax, ParameterBinder] =
    Map(
      column.startTime -> e.startTime,
      column.endTime -> e.endTime,
      column.status -> e.status,
      column.applierId -> e.applierId,
      column.approverId -> e.approverId,
      column.reason -> e.reason,
      column.comment -> e.comment,
      column.creatorId -> e.creatorId,
      column.updatorId -> e.updatorId
    )

  override def entity(ea: ResultName[ExtraworkApplication])(rs: WrappedResultSet) =
    new ExtraworkApplication(
      id = new ExtraworkApplicationId(rs.get(ea.id)),
      startTime = rs.get(ea.startTime),
      endTime = rs.get(ea.endTime),
      status = rs.get(ea.status),
      applierId = UserId(rs.get(ea.applierId)),
      approverId = UserId(rs.get(ea.approverId)),
      reason = rs.get(ea.reason),
      comment = rs.get(ea.comment),
      creatorId = UserId(rs.get(ea.creatorId)),
      created = rs.get(ea.created),
      updatorId = UserId(rs.get(ea.updatorId)),
      updated = rs.get(ea.updated)
    )

  override def createdEntity(e: ExtraworkApplication, newId: Long) =
    e.copy(id = new ExtraworkApplicationId(newId), updated = DateTime.now(), created = DateTime.now())

  override val stx = syntax("ea")

}
