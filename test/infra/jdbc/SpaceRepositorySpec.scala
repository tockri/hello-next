package infra.jdbc

/**
  *
  */
class SpaceRepositorySpec extends JdbcSpec {
  "SpaceRepository" should "find space" in {implicit session =>
    implicit val io = IOContextOnJDBC(session)
    val spaces = await(spaceRepository.list())
    val offices = await(officeRepository.list(spaces(1).id))
    val sysSpace = await(spaceRepository.get(spaces(0).id))
    assert(spaces.length == 2)
    assert(spaces(1).name == "test space")
    assert(sysSpace.isDefined)
    sysSpace.map {sys =>
      assert(sys.name == "system")
    }
    assert(offices.length == 2)
    assert(offices(0).name == "tokyo office")
    assert(offices(1).name == "osaka office")
  }

}
