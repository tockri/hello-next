package infra.jdbc

import domain.model._
import domain.{OfficeRepository, SpaceRepository, UserRepository}
import org.joda.time.{DateTime, LocalDate}
import org.scalatest.{BeforeAndAfterAll, DiagrammedAssertions, fixture}
import play.api.db.DBApi
import play.api.db.evolutions.Evolutions
import play.api.test.FakeApplication
import scalikejdbc.DBSession
import scalikejdbc.scalatest.AutoRollback
import support.{IOContext, SpecSupport}

import scala.concurrent.ExecutionContext
import scala.reflect.ClassTag

/**
  * Repositoryのテスト
  */
trait JdbcSpec extends fixture.FlatSpec
  with BeforeAndAfterAll with DiagrammedAssertions with AutoRollback with SpecSupport {

  implicit val executionContext: ExecutionContext = support.PooledContexts.appContext

  val app = FakeApplication()

  lazy val spaceRepository = injected[SpaceRepository]
  lazy val officeRepository = injected[OfficeRepository]
  lazy val userRepository = injected[UserRepository]

  /**
    * DIされたインスタンスを返す
    */
  def injected[T](implicit ct: ClassTag[T]): T = app.injector.instanceOf(ct)

  /**
    * DBスキーマ定義をアップデートする
    */
  override def beforeAll(): Unit = {
    val dBApi = injected[DBApi]
    Evolutions.applyEvolutions(dBApi.database("default"))
    println("evolutions success.")
  }

  /**
    * DBのデータ準備を変えたい場合オーバーライドする
    * @param session
    */
  override def fixture(implicit session: FixtureParam): Unit = {
    makeBaseFixture
  }

  /**
    * DBのデータ準備
    * @param session
    */
  def makeBaseFixture(implicit session: DBSession):Unit = {
    implicit val io = IOContextOnJDBC(session)
    val space = await(spaceRepository.store(Space.create(
      name = "test space",
      icon = Some("test icon"),
      nulabappsSpaceKey = Some("nulab"),
      creatorId = new UserId(1)
    )))
    val tokyo = await(officeRepository.store(Office.create(
      spaceId = space.id,
      name = "tokyo office",
      latitude = Some(100),
      longitude = Some(200),
      creatorId = new UserId(1)
    )))
    val osaka = await(officeRepository.store(Office.create(
      spaceId = space.id,
      name = "osaka office",
      latitude = Some(150),
      longitude = Some(250),
      creatorId = new UserId(1)
    )))
    val admin = await(userRepository.store(User.create(
      name = "admin user",
      spaceId = space.id,
      icon = Some("http://xxx.xxx.xxx/image/icon"),
      nulabid = "ABCDE12345",
      uniqueid = "admin",
      enterDate = LocalDate.parse("2018-1-1"),
      officeId = Option(tokyo.id),
      approverId = None,
      creatorId = UserId(1)
    )))
    val member1 = await(userRepository.store(User.create(
      name = "normal user1",
      spaceId = space.id,
      icon = Some("http://xxx.xxx.xxx/image/icon1"),
      nulabid = "ABCDE67890",
      uniqueid = "member1",
      enterDate = LocalDate.parse("2015-1-1"),
      officeId = Option(osaka.id),
      approverId = Some(admin.id),
      creatorId = admin.id
    )))
    val member2 = await(userRepository.store(User.create(
      name = "normal user2",
      spaceId = space.id,
      icon = Some("http://xxx.xxx.xxx/image/icon2"),
      nulabid = "FGHIJ12345",
      uniqueid = "member2",
      enterDate = LocalDate.parse("2015-10-1"),
      officeId = Option(osaka.id),
      approverId = Some(admin.id),
      creatorId = admin.id
    )))

  }
}
