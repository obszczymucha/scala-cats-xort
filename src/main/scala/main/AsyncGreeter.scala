package main

import cats.data.XorT
import cats.implicits._
import main.AsyncGreeter.{AppError, NoNameGiven}

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.{Future, Promise}

object AsyncGreeter {

  trait AppError

  case object NoNameGiven extends AppError

}

class AsyncGreeter {

  def greet(name: Option[String]): Future[String] = {
    val promise: Promise[String] = Promise[String]

    val result = for {
      greeting <- sayHello(name)
    } yield greeting

    result.map(greeting => promise.success(greeting)).recover {
      case NoNameGiven => promise.success("I cannot greet you if I don't know your name!")
      case _ => promise.success("Boom headshot!")
    }

    promise.future
  }


  private def sayHello(maybeName: Option[String]): XorT[Future, AppError, String] = {
    maybeName match {
      case Some(name) => XorT.right(Future {
        s"Hello, $name!"
      })
      case None => XorT.left(Future.successful {
        NoNameGiven
      })
    }
  }
}
