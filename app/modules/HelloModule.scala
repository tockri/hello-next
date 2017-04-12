package modules

import javax.inject._

import com.google.inject.AbstractModule
import domain._
import infra.jdbc._
import net.codingwell.scalaguice.ScalaModule
import scalikejdbc.AutoSession
import support.IOContextProvider

/**
  * DI definitions
  */
class HelloModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bindRepository()
    bindIOContext()
  }

  private def bindRepository():Unit = {
    bind[AttendanceApplicationRepository].to[AttendanceApplicationRepositoryOnDB].in[Singleton]
    bind[AttendanceRepository].to[AttendanceRepositoryOnDB].in[Singleton]
    bind[ExtraworkApplicationRepository].to[ExtraworkApplicationRepositoryOnDB].in[Singleton]
    bind[GivenHolidayRepository].to[GivenHolidayRepositoryOnDB].in[Singleton]
    bind[HolidayApplicationRepository].to[HolidayApplicationRepositoryOnDB].in[Singleton]
    bind[OfficeRepository].to[OfficeRepositoryOnDB].in[Singleton]
    bind[OfficialHolidayRepository].to[OfficialHolidayRepositoryOnDB].in[Singleton]
    bind[RestRepository].to[RestRepositoryOnDB].in[Singleton]
    bind[SpaceRepository].to[SpaceRepositoryOnDB].in[Singleton]
    bind[TypetalkRepository].to[TypetalkRepositoryOnDB].in[Singleton]
    bind[UserRepository].to[UserRepositoryOnDB].in[Singleton]
  }

  private def bindIOContext():Unit = {
    bind[IOContextProvider].toInstance(new IOContextProviderOnJDBC(AutoSession))
  }
}
