package greeter

import cats.data.Xor
import greeter.AsyncGreeter.{AppError, NoNameGiven}

import scala.concurrent.{Future}

object AsyncGreeter {

  trait AppError

  case object NoNameGiven extends AppError

}

class AsyncGreeter {

  def greet(name: Option[String]): Future[String] = {

    val response = sayHello(name).getOrElse("I cannot greet you if I don't know your name!")

    Future.successful(response)
  }


  private def sayHello(maybeName: Option[String]): Xor[AppError, String] = {
    maybeName match {
      case Some(name) => Xor.Right(s"Hello, $name!")
      case None => Xor.Left(NoNameGiven)
    }
  }
}
