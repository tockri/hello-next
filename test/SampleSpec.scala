import org.scalatest.{DiagrammedAssertions, FlatSpec}

/**
  * sample
  */
class SampleSpec extends FlatSpec with DiagrammedAssertions {
  "sample" should "do something" in {
    assert("1" == "2")
  }
}
