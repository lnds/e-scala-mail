package org.akarru.demo



object demo {

	import org.akarru.tools.mailer._

	def main(args: Array[String]): Unit = {

    new Mailer(Server("localhost", 1025).bounceAddress("bounces@akarru.com").connect()) {

      send a new Mail(
        from = "rena@akarru.com" -> "Rena",
        to = Seq("mampato@akarru.com", "ogu@akarru.com"),
        subject = "Help!",
        message = "Please, come it's urgent!",
        richMessage = "Please, come <blink>it's</blink> <strong>urgent!</strong>..."
      )
      match  {
        case Success(id) => println("enviado con exito id: %s".format(id))
        case Failure(t) => println("fallido, razón: %s".format(t.getMessage))
      }

    }
	}
}

