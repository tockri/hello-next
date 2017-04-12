package support

/**
  * ページング情報つきリスト
  * @param items リスト
  * @param totalCount 全件数
  * @param current 現在のページ。1始まりの数字
  * @param size ページサイズ
  */
case class PagedList[A](items:List[A], totalCount:Long, current:Int, size:Int) {
  require(size > 0, "size must > 0")
  require(current > 0, "page must > 0")

  val last:Int = (totalCount / size).toInt + (if (totalCount % size != 0) 1 else 0)

  val pages:Range = 1 to last

  val prevPage:Option[Int] = if (current > 1) Some(current - 1) else None

  val nextPage:Option[Int] = if (current < last) Some(current + 1) else None
}
