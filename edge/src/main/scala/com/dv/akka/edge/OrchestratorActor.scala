
package com.dv.akka.edge


import akka.actor.{Actor, ActorRef, Props}
import akka.util.Timeout
import com.dv.akka._
import com.dv.akka.common.models.UrlInfo
import com.dv.akka.model.AvroSchemaUtil

import scala.concurrent.duration._

object OrchestratorActor {
  def props(sericeConnector: ActorRef): Props =
    Props(new OrchestratorActor(sericeConnector))

}


class OrchestratorActor(workerRouter: ActorRef) extends Actor {
  var _evt: MessageWithCallback = null
  var originCount = 0;
  implicit val timeout: Timeout = 1 second


  override def receive: Receive = {

    case evt : MessageWithCallback if _evt !=null =>
      throw new IllegalArgumentException("Got message during process")

    case evt : MessageWithCallback  =>
      _evt = evt
      originCount = evt.request.evtType
      val data = AvroSchemaUtil.toByteArray(_evt.request)
      workerRouter ! DvMessage(_evt.request.evtType,"1",data)

    case evt:DvMessage =>
      _evt.complete(UrlInfo(true,originCount))
      context.parent ! ImIdleNow
      _evt = null
  }


}



