package domain

import domain.model.{Office, OfficeId, SpaceId}
import support.IOContext

import scala.concurrent.Future

/**
  *
  */
trait OfficeRepository extends Repository[Office, OfficeId] {
  def list(spaceId:SpaceId)(implicit io:IOContext): Future[List[Office]]
}
