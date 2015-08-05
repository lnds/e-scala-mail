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

case class Server(_session: MailSession = Defaults.session, _transport: Transport = Defaults.transport) {


  def session = Session.Builder(this)

  def close(): Unit = {
    if (_transport.isConnected)
      _transport.close()
  }

  def open() : Unit = {
    if (!_transport.isConnected)
      _transport.connect()
  }

}

