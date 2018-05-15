
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
    case res:Int if res != _evt.request.evtType=>
      throw new IllegalArgumentException(s"Invalid message !!! existing:${_evt.request.evtType}, got:$res")

    case evt : MessageWithCallback  =>
      _evt = evt
      originCount = evt.request.evtType
      workerRouter ! evt.request
      //OrchestratorActor.reqCount.incrementAndGet()

    //This piece of code mocks going back and forth to 7 diffrent microservices and getting a reply from them
    case res:Int if res > 1 =>
      _evt.request.evtType = res -1
      workerRouter ! _evt.request
      //OrchestratorActor.atomicReqCount.incrementAndGet()
    case res:Int =>
      _evt.complete(UrlInfo(true,originCount))
      context.parent ! ImIdleNow
      _evt = null
      //OrchestratorActor.atomicReqCount.incrementAndGet()
  }


}



