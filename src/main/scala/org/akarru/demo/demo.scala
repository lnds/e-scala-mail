package org.akarru.demo

object DemoMail {

    import org.akarru.tools.mail._

    def main(args:Array[String]) : Unit = {
	
	smtp (host= "localhost", port= 1025)

	send a new Mail(
		from = "john.smith@compan.com" -> "John Smith",
		to = "boss@company.com",
		cc = "hr@company.com",
		subject = "Import Stuff",
		message = "Dear Boss...",
	        bounce = "bounce@company.com"
	)

	send a new Mail(
		from = "john.smit@company.com" -> "John Smit",
                to = "boss@company.com",
		subject = "Test",
		message = "Hello Boss...",
		richMessage = "<p>Hello Bosss</p>",
		bounce = "bounce@company.com"
	)
    }
}

