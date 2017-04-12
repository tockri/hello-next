package infra.jdbc

import domain.model._
import scalikejdbc.ParameterBinderFactory

/**
  *
  */
trait HelloBinderFactories {
  private def entityIdBinderFactory[A<:EntityId]:ParameterBinderFactory[A] =
    ParameterBinderFactory(value => (stmt, idx) => stmt.setLong(idx, value.value))

  implicit val userIdParameterBinderFactory = entityIdBinderFactory[UserId]
  implicit val spaceIdParameterBinderFactory = entityIdBinderFactory[SpaceId]
  implicit val attendanceApplicationIdParameterBinderFactory = entityIdBinderFactory[AttendanceApplicationId]
  implicit val attendanceIdParameterBinderFactory = entityIdBinderFactory[AttendanceId]
  implicit val extraworkApplicationIdParameterBinderFactory = entityIdBinderFactory[ExtraworkApplicationId]
  implicit val givenHolidayIdParameterBinderFactory = entityIdBinderFactory[GivenHolidayId]
  implicit val holidayApplicationIdParameterBinderFactory = entityIdBinderFactory[HolidayApplicationId]
  implicit val officeIdParameterBinderFactory = entityIdBinderFactory[OfficeId]
  implicit val officeHolidayIdParameterBinderFactory = entityIdBinderFactory[OfficialHolidayId]
  implicit val restIdParameterBinderFactory = entityIdBinderFactory[RestId]
  implicit val typetalkIdParameterBinderFactory = entityIdBinderFactory[TypetalkId]
}
