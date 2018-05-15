package com.dv.akka.cluster

import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.dispatch.{PriorityGenerator, UnboundedStablePriorityMailbox}
import akka.routing.BalancingPool
import com.typesafe.config.Config
import com.dv.akka.ImpressionMessage


object UrlWorker {
  def props(count:Int): Props = BalancingPool(count)
    .props( Props[UrlWorker])
    .withMailbox("priority-mailbox")
  var workTime:Int = 400 // default unless set
  var consolidationTime: Int = 50

  val workStr:String = "abcdefghijklmnopqrstuvwxyz1234567890"

  //var counterConsolidation = new AtomicInteger(0)
  //var counterWork = new AtomicInteger(0)
}

class MyPrioMailbox(settings: ActorSystem.Settings, config: Config)
  extends UnboundedStablePriorityMailbox(// Create a new PriorityGenerator, lower prio means more important
    PriorityGenerator {
      case evt:ImpressionMessage =>
        evt.evtType
    })

class UrlWorker() extends Actor{

  override def preStart(): Unit = {
    println("Hello from Url Worker !!!")
  }

  def receive = {
    case evt:ImpressionMessage if evt.evtType > 0 =>
      workAndReply(sender(),UrlWorker.workTime,evt.evtType)

    case evt:ImpressionMessage if evt.evtType == 0 =>
      workAndReply(sender(),UrlWorker.consolidationTime,evt.evtType)

  }


  private def workAndReply(replyTo:ActorRef,
                           workInMicro:Int,
                           whatToReply:Any): Unit = {
    val end = System.nanoTime() + (workInMicro * 1000)
    while (System.nanoTime() < end) {UrlWorker.workStr.reverse.reverse}
    replyTo ! whatToReply
    /*if (workInMicro == UrlWorker.consolidationTime)
      UrlWorker.counterConsolidation.incrementAndGet()
    else
      UrlWorker.counterWork.incrementAndGet()*/
  }
}