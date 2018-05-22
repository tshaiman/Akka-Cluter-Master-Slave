package com.dv.akka.cluster


import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.dispatch.{PriorityGenerator, UnboundedStablePriorityMailbox}
import akka.routing.{BalancingPool, FromConfig}
import com.dv.akka.model.AvroSchemaUtil
import com.dv.akka.{DvImpression, DvMessage}
import com.typesafe.config.Config


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
      case evt:DvImpression =>
        evt.evtType
    })

class UrlWorker() extends Actor{

  def receive = {
    case msg:DvMessage if msg.evtType > 0 =>
      workAndForward(UrlWorker.workTime,msg)
    /*case evt:DvImpression if evt.evtType > 0 =>
      workAndForward(UrlWorker.workTime,evt)*/
  }

  private def workAndForward( workInMicro:Int,
                           evt:DvMessage): Unit = {

    ///////////////////////////////////
    //////simulate Deserialize
    deserializeImpression(evt)
    /////////////////////////////////

    val end = System.nanoTime() + (workInMicro * 1000)
    while (System.nanoTime() < end) {UrlWorker.workStr.reverse.reverse}
    if(evt.evtType > 1 ) {
      //move to next actor
      val nextMessage = evt.copy(evtType = evt.evtType -1)
      UrlWorker.otherWorkers forward nextMessage
    }
    else {
      //return the final response
      sender ! evt

    }
  }

  def deserializeImpression(evt:DvMessage) : Int = {
    val dvImpression:DvImpression = AvroSchemaUtil.fromByteRecord(evt.data)
    if (dvImpression == null) 0 else 1
  }
}