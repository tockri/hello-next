package infra.jdbc

import support.PageQuery

/**
  *
  */
class UserRepositorySpec extends JdbcSpec {
  "UserRepository" should "find user" in {implicit session =>
    implicit val io = IOContextOnJDBC(session)
    val space = await(spaceRepository.spaceByKey("nulab")).get
    val users1 = await(userRepository.list(space.id, PageQuery(1, 2)))
    assert(users1.items.length == 2)
    assert(users1.current == 1)
    assert(users1.nextPage == Some(2))
    assert(users1.prevPage == None)
    assert(users1.totalCount == 3)
    assert(users1.items(0).name == "normal user2")
    assert(users1.items(1).name == "normal user1")
    val users2 = await(userRepository.list(space.id, PageQuery(users1.nextPage.get, 2)))
    assert(users2.items.length == 1)
    assert(users2.current == 2)
    assert(users2.nextPage == None)
    assert(users2.prevPage == Some(1))
    assert(users2.totalCount == 3)
    assert(users2.items(0).name == "admin user")

  }
}
