# Scala DSL for mail dispatching

Based on this beautiful code: https://gist.github.com/mariussoutier/3436111  and this project: https://github.com/softprops/courier

# Usage

For bulk email (uses futures):

```scala

  import org.akarru.tools.mail._
  import scala.concurrent.ExecutionContext.Implicits.global

  using (Server("localhost", 25).connect()) { implicit server =>

    send a new Mail (
        from = "rena@akarru.com" -> "Rena",
        to = Seq("mampato@akarru.com", "ogu@akarru.com"),
        subject = "Help!",
        message = "Please, come it's urgent!",
        richMessage = "Please, come <blink>it's</blink> <strong>urgent!</strong>..."
    ) onComplete {
        case Success(id) => println("delivered with id: "+id)
        case Failure(t) => println("failure: "+t)
    }
  }


```
