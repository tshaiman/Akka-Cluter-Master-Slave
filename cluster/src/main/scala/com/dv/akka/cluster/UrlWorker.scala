package com.dv.akka.cluster

import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.dispatch.{PriorityGenerator, UnboundedStablePriorityMailbox}
import akka.routing.{BalancingPool, FromConfig}
import com.typesafe.config.Config
import com.dv.akka.{BrandSafetyResponse, ImpressionMessage}


object UrlWorker {
  def props()(implicit actorSys:ActorSystem): Props = {
    otherWorkers =  implicitly[ActorSystem].actorOf(FromConfig.props(Props.empty),"others")
    FromConfig.props(Props[UrlWorker]).withMailbox("priority-mailbox")
  }

  var workTime:Int = 400 // default unless set
  var otherWorkers:ActorRef =  null
  val workStr:String = "abcdefghijklmnopqrstuvwxyz1234567890"

}

class MyPrioMailbox(settings: ActorSystem.Settings, config: Config)
  extends UnboundedStablePriorityMailbox(// Create a new PriorityGenerator, lower prio means more important
    PriorityGenerator {
      case evt:ImpressionMessage =>
        evt.evtType
    })

class UrlWorker() extends Actor{

  def receive = {
    case evt:ImpressionMessage if evt.evtType > 0 =>
      workAndForward(UrlWorker.workTime,evt)
  }


  private def workAndForward( workInMicro:Int,
                           evt:ImpressionMessage): Unit = {

    val end = System.nanoTime() + (workInMicro * 1000)
    while (System.nanoTime() < end) {UrlWorker.workStr.reverse.reverse}

    if(evt.evtType > 1 ) {
      //move to next actor
      val nextMessage = ImpressionMessage(evt.data, evt.evtType - 1)
      UrlWorker.otherWorkers forward nextMessage
    }
    else {
      //return the final response
      sender ! BrandSafetyResponse(evt.data,true)

    }
  }
}