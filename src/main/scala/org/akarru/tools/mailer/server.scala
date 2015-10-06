package org.akarru.tools.mailer

import javax.mail.{Session => MailSession, Message, Transport}

/**
 * Mail Server
 * Created by ediaz on 05-08-15.
 */

object Server {

  def apply(host: String, port: Int): Session.Builder =
    Server().session.host(host).port(port)
}

case class Server(_session: MailSession = Defaults.session) {


  var _transport : Transport = null

  def session = Session.Builder(this)

  def close(): Unit = {
    if (_transport != null)
      _transport.close()
    _transport = null
  }


  def send(msg:Message) : Unit = {
    if (_transport == null)
      Transport.send(msg)
    else {
      msg.saveChanges()
      _transport.sendMessage(msg, msg.getAllRecipients)
    }
  }

  def open() : Unit = {
    _transport = _session.getTransport
    if (_transport != null)
      _transport.connect()
  }

}

