package org.akarru.tools

import javax.activation.{FileDataSource, DataHandler}
import javax.mail.internet.{MimeMultipart, MimeBodyPart, InternetAddress, MimeMessage}
import javax.mail.{Session => MailSession, Message, Transport}

import scala.concurrent.{Future, ExecutionContext}
import scala.language.implicitConversions

/**
 * Mailer
 * Created by ediaz on 04-08-15.
 */
package object mailer {

  implicit def stringToSeq(single: String): Seq[String] = Seq(single)
  implicit def liftToOption[T](t: T): Option[T] = Some(t)




  sealed abstract class MailType
  case object Plain extends MailType
  case object Rich extends MailType
  case object MultiPart extends MailType

  case class Mail(
                   from: (String, String), // (email -> name)
                   to: Seq[String],
                   cc: Seq[String] = Seq.empty,
                   bcc: Seq[String] = Seq.empty,
                   subject: String,
                   message: String,
                   richMessage: Option[String] = None,
                   attachment: Option[(java.io.File)] = None,
                   headers : Seq[(String,String)] = Seq.empty[(String,String)]
                   )



  trait MailStatus
  case class Failure(exception:Throwable) extends MailStatus
  case class Success(id:String) extends MailStatus

  class Mailer(val server:Server) {

    object send {

      def a(mail: Mail) : MailStatus = {

        val msg = new MimeMessage(server._session)

        val format =
          if (mail.attachment.isDefined) MultiPart
          else if (mail.richMessage.isDefined) Rich
          else Plain

        format match {
          case Plain => msg.setText(mail.message)
          case Rich => msg.setContent(new MimeMultipart() {
            addBodyPart(new MimeBodyPart {
              setContent(mail.richMessage.get, "text/html")
            })
            addBodyPart(new MimeBodyPart {
              setContent(mail.message, "text/plain")
            })
          })
          case MultiPart => msg.setContent(new MimeMultipart() {
            addBodyPart(new MimeBodyPart {
              setContent(mail.richMessage.get, "text/html")
            })
            addBodyPart(new MimeBodyPart {
              setContent(mail.message, "text/plain")
            })
            addBodyPart(new MimeBodyPart {
              setDataHandler(new DataHandler(new FileDataSource(mail.attachment.get)))
              setFileName(mail.attachment.get.getName)
            })
          })
        }

        mail.to foreach (a => msg.addRecipient(Message.RecipientType.TO, new InternetAddress(a)))
        mail.cc foreach (a => msg.addRecipient(Message.RecipientType.CC, new InternetAddress(a)))
        mail.bcc foreach (a => msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(a)))

        msg.setFrom(new InternetAddress(mail.from._1, mail.from._2))
        msg.setSubject(mail.subject)

        mail.headers.foreach(h => msg.addHeader(h._1, h._2))
        Transport.send(msg)

        try {
          Success(msg.getMessageID)
        } catch {
          case ex:Throwable => Failure(ex)
        }
      }
    }

  }

}