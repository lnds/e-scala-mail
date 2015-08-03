package org.akarru.demo

object DemoMail {

	import org.akarru.tools.mail._

	def main(args:Array[String]) : Unit = {
	
	smtp (host= "localhost", port= 1025)

	send a new Mail(
		from = "john.smith@companycom" -> "John Smith",
		to = "boss@company.com",
		cc = "hr@company.com",
		subject = "Import Stuff",
		message = "Dear Boss...",
	        bounce = "bounce@company.com"
	)
	}
}

