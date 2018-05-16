
package com.dv.akka.edge

import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{Actor, ActorRef, Props}
import akka.util.Timeout
import com.dv.akka._
import com.dv.akka.common.models.UrlInfo

import scala.concurrent.duration._

object OrchestratorActor {
  def props(sericeConnector: ActorRef): Props =
    Props(new OrchestratorActor(sericeConnector))

  //var orchCount = new AtomicInteger(0)
  //var reqCount = new AtomicInteger(0)
  //var atomicReqCount = new AtomicInteger(0)
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
      workerRouter ! evt.request

    case BrandSafetyResponse(_,isAllowed) =>
      _evt.complete(UrlInfo(isAllowed,originCount))
      context.parent ! ImIdleNow
      _evt = null
  }


}



