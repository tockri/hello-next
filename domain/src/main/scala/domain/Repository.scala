package domain

import domain.model.{Entity, EntityId}
import support.IOContext

import scala.concurrent.Future

/**
  * リポジトリの基底trait
  */
trait Repository[ET<:Entity[ID], ID<:EntityId] {
  def get(id:ID)(implicit io:IOContext): Future[Option[ET]]
  def store(s:ET)(implicit io:IOContext): Future[ET]
  def remove(id:ID)(implicit io:IOContext): Future[Unit]

}
