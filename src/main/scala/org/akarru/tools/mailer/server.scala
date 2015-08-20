package org.akarru.tools.mailer

import javax.mail.{Transport, Session => MailSession}

/**
 * Mail Server
 * Created by ediaz on 05-08-15.
 */

object Server {

  def apply(host: String, port: Int): Session.Builder =
    Server().session.host(host).port(port)
}

case class Server(_session: MailSession = Defaults.session) {


  def session = Session.Builder(this)

  def close(): Unit = {
  }

  def open() : Unit = {
  }

}

