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
                   attachment: Option[(java.io.File)] = None
                   )

  object Server {

    def apply(host: String, port: Int): Session.Builder =
      Server().session.host(host).port(port)
  }

  case class using(mailer:Server) {


    def apply(closure: (Server) => Unit) {
      mailer.open()
      closure(mailer)
      mailer.close()
    }
  }

  case class Server(_session: MailSession = Defaults.session, _transport: Transport = Defaults.transport) {


    def session = Session.Builder(this)

    def close(): Unit = {
      _transport.close()
    }

    def open() : Unit = {
      _transport.connect()
    }

  }

  object send {

    def a(mail: Mail)(implicit mailer: Server, ec: ExecutionContext): Future[String] = {

      val msg = new MimeMessage(mailer._session)

      val format =
        if (mail.attachment.isDefined) MultiPart
        else if (mail.richMessage.isDefined) Rich
        else Plain

      format match {
        case Plain => msg.setText(mail.message)
        case Rich => msg.setContent(new MimeMultipart() {
                                          addBodyPart(new MimeBodyPart {setContent(mail.richMessage.get, "text/html")})
                                          addBodyPart(new MimeBodyPart {setContent(mail.message, "text/plain")})
                                     })
        case MultiPart => msg.setContent(new MimeMultipart() {
                                            addBodyPart(new MimeBodyPart {setContent(mail.richMessage.get, "text/html")})
                                            addBodyPart(new MimeBodyPart {setContent(mail.message, "text/plain")})
                                            addBodyPart(new MimeBodyPart {setDataHandler(new DataHandler(new FileDataSource(mail.attachment.get)))
                                                                          setFileName(mail.attachment.get.getName)})
                                          })
      }

      mail.to foreach (a => msg.addRecipient(Message.RecipientType.TO, new InternetAddress(a)))
      mail.cc foreach (a => msg.addRecipient(Message.RecipientType.CC, new InternetAddress(a)))
      mail.bcc foreach (a => msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(a)))

      msg.setFrom(new InternetAddress(mail.from._1, mail.from._2))
      msg.setSubject(mail.subject)

      Future {
        mailer._transport.sendMessage(msg, msg.getAllRecipients)
          msg.getMessageID
      }
    }
  }

}