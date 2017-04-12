package infra.jdbc

import domain.model.{Entity, EntityId}
import scalikejdbc._
import support.PageQuery

/**
  * Daoの基本的なメソッドの実装
  */
trait BaseDao[ET<:Entity[ID], ID<:EntityId] extends SQLSyntaxSupport[ET] with HelloBinderFactories {

  /** column.*** -> e.*** のMapを返す */
  def entityMap(e: ET): Map[SQLSyntax, ParameterBinder]

  /** ResultNameからEntityオブジェクトを生成するコード */
  def entity(s: ResultName[ET])(rs: WrappedResultSet): ET

  /** syntax("省略名")とする */
  val stx: QuerySQLSyntaxProvider[SQLSyntaxSupport[ET], ET]

  /** 新規生成されたエンティティを返す */
  protected def createdEntity(e: ET, newId: Long): ET

  /** ORDER BY句のデフォルト指定 */
  protected val defaultOrder = sqls"id asc"

  protected val defaultWhere = sqls"1 = 1"

  //
  def entity(s: SyntaxProvider[ET])(rs: WrappedResultSet): ET = entity(s.resultName)(rs)

  def _selectOne(id: ID)(implicit session: DBSession): Option[ET] = {
    withSQL(select.from[ET](this as stx).where(sqls"id = ${id.value}"))
      .map(entity(stx.resultName)).single.apply()
  }

  def _selectCount()(implicit session: DBSession): Long = {
    withSQL(select(sqls.count).from(this as stx)).map(rs => rs.long(1)).single.apply().get
  }

  def _selectOne(where: SQLSyntax)(implicit session: DBSession): Option[ET] = {
    withSQL {
      select.from[ET](this as stx).where.append(where)
    }.map(entity(stx.resultName)).single.apply()
  }

  def _select(where: SQLSyntax = defaultWhere, order: SQLSyntax = defaultOrder, pq: PageQuery = PageQuery.None)(implicit session: DBSession): List[ET] = {
    withSQL {
      val s = select.from[ET](this as stx).where.append(where).orderBy(order)
      if (pq.isNone) s else {
        s.limit(pq.limit).offset(pq.offset)
      }
    }.map(entity(stx.resultName)).list.apply()
  }

  def _selectCount(where: SQLSyntax)(implicit session: DBSession): Long = {
    withSQL {
      select(sqls.count).from(this as stx).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def _insert(e: ET)(implicit session: DBSession): ET = {
    val newId = withSQL {
      insert.into(this).namedValues(entityMap(e))
    }.updateAndReturnGeneratedKey.apply()
    createdEntity(e, newId)
  }

  def _update(e: ET)(implicit session: DBSession): ET = {
    withSQL {
      update(this).set(
        entityMap(e)
      ).where(sqls"id = ${e.entityId.value}")
    }.update.apply()
    e
  }

  def _updateInsert(e: ET)(implicit session: DBSession): ET = {
    if (e.entityId.defined) {
      _update(e)
    } else {
      _insert(e)
    }
  }

  def _delete(id: ID)(implicit session: DBSession = autoSession): Int = {
    withSQL {
      delete.from(this).where(sqls"id = ${id.value}")
    }.update.apply()
  }
}
