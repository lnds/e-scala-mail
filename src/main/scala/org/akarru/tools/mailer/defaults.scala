package org.akarru.tools.mailer


import javax.mail.{Session => MailSession}
import java.util.Properties
import scala.concurrent.ExecutionContext

/**
 * Defaults mail sessions
 * Based on https://github.com/softprops/courier
 * Created by ediaz on 04-08-15.
 */

object Defaults {
  val session = MailSession.getDefaultInstance(new Properties())
  val transport = session.getTransport("smtp")

  implicit val executionContext = ExecutionContext.Implicits.global
}
