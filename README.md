# Scala DSL for mail dispatching

Based on this beatiful code: https://gist.github.com/mariussoutier/3436111

# Usage

```scala
  import org.akarru.tools.mail._

  send a new Mail (
    from = ("rena@akarru.com", "Rena"),
    to = "mampato@akarru.com",
    cc = "ogu@akarru.comcom",
    subject = "Pertelar",
    message = "Queridos amigos..."
  )

  send a new Mail (
    from = "rena@akarru.com" -> "Rena",
    to = Seq("mampato@akarru.com", "ogu@akarru.com"),
    subject = "I miss you"
    message = "Do you like to time travel to 40 century?.",
    richMessage = "Please, come <blink>it's</blink> <strong>urgent!</strong>..."
  )
```
