# Scala DSL for mail dispatching

Based on this beautiful code: https://gist.github.com/mariussoutier/3436111  and this project: https://github.com/softprops/courier

# Usage

For bulk email (uses futures):

```scala

  import org.akarru.tools.mail._

  new Mailer(Server("localhost", 25).connect()) {

    send a new Mail (
        from = "rena@akarru.com" -> "Rena",
        to = Seq("mampato@akarru.com", "ogu@akarru.com"),
        subject = "Help!",
        message = "Please, come it's urgent!",
        richMessage = "Please, come <blink>it's</blink> <strong>urgent!</strong>..."
    ) 
    
    server.close()
  }


```

If you want to capture the status:

```scala
    new Mailer(Server("localhost", 25).bounceAddress("bounces@akarru.com").connect()) {

      send a new Mail(
        from = "rena@akarru.com" -> "Rena",
        to = Seq("mampato@akarru.com", "ogu@akarru.com"),
        subject = "Help!",
        message = "Please, come it's urgent!",
        richMessage = "Please, come <blink>it's</blink> <strong>urgent!</strong>..."
      )
      match  {
        case Success(id) => println("enviado con exito id: %s".format(id))
        case Failure(t) => println("fallido, razón: ".format(t))
      }

      server.close()

    }
 ```
