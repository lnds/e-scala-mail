package org.akarru.demo


import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object DemoMail {

	import org.akarru.tools.mailer._

	def main(args: Array[String]): Unit = {

		using (Mailer("localhost", 1025).bounceAddress("bounces@akarru.com").connect()) { implicit mailer =>
			send a new Mail (
				from = "rena@akarru.com" -> "Rena",
				to = Seq("mampato@akarru.com", "ogu@akarru.com"),
				subject = "I miss you",
				message = "Please, come it's urgent!",
				richMessage = "Please, come <blink>it's</blink> <strong>urgent!</strong>..."
			) onComplete  {
				case Success(id) => println("delivered with id: "+id)
				case Failure(t) => println("failure: "+t)
			}
		}
	}
}

