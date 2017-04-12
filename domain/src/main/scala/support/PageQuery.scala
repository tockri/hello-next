package support

/**
  * Pageを返すための引数
  */
class PageQuery(pageNum:Int, pageSize:Int) {
  val isNone:Boolean = false
  val page:Int = Math.max(pageNum, 1)
  val size:Int = Math.max(pageSize, 1)
  val limit: Int = size
  val offset: Int = (page - 1) * size
}
object PageQuery {
  val None: PageQuery = new PageQuery(0, -1) {
    override val isNone: Boolean = true
  }
  def apply(pageNum:Int, pageSize:Int):PageQuery = new PageQuery(pageNum, pageSize)

}
