package domain

import domain.model.{Space, SpaceId}
import support.IOContext

import scala.concurrent.Future

/**
  * spaceの操作
  */
trait SpaceRepository extends Repository[Space, SpaceId] {
  def spaceByKey(nulabAppsSpaceKey:String)(implicit io: IOContext): Future[Option[Space]]
  def list()(implicit io:IOContext): Future[List[Space]]
}
