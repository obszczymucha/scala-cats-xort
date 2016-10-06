package greeter

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpec, Matchers}

class AsyncGreeterSpec extends FunSpec with Matchers {
  describe("AsyncGreeter") {
    it("should complain if no name is given") {
      // Given
      val greeter = new AsyncGreeter

      // When
      val f = greeter.greet(None)

      // Then
      ScalaFutures.whenReady(f) { result => result shouldBe "I cannot greet you if I don't know your name!" }
    }

    it("should greet me if I give it my name") {
      // Given
      val greeter = new AsyncGreeter

      // When
      val f = greeter.greet(Some("Princess Kenny"))

      // Then
      ScalaFutures.whenReady(f) { result => result shouldBe "Hello, Princess Kenny!" }
    }
  }
}
