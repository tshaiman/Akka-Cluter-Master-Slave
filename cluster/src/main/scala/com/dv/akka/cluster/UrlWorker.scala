package com.dv.akka.cluster

import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.dispatch.{PriorityGenerator, UnboundedStablePriorityMailbox}
import akka.routing.{BalancingPool, FromConfig}
import com.typesafe.config.Config
import com.dv.akka.ImpressionMessage


object UrlWorker {
  def props(): Props = FromConfig.props(Props[UrlWorker]).withMailbox("priority-mailbox")
  var workTime:Int = 400 // default unless set

  val workStr:String = "abcdefghijklmnopqrstuvwxyz1234567890"
  var n:Int = 0;
}

class MyPrioMailbox(settings: ActorSystem.Settings, config: Config)
  extends UnboundedStablePriorityMailbox(// Create a new PriorityGenerator, lower prio means more important
    PriorityGenerator {
      case evt:ImpressionMessage =>
        evt.evtType
    })

class UrlWorker() extends Actor{

  override def preStart(): Unit = {
    UrlWorker.n += 1
    println("Hello from Url Worker !!!" + UrlWorker.n)

  }

  def receive = {
    case evt:ImpressionMessage if evt.evtType > 0 =>
      workAndReply(sender(),UrlWorker.workTime,evt.evtType)
  }


  private def workAndReply(replyTo:ActorRef,
                           workInMicro:Int,
                           whatToReply:Any): Unit = {
    val end = System.nanoTime() + (workInMicro * 1000)
    while (System.nanoTime() < end) {UrlWorker.workStr.reverse.reverse}
    replyTo ! whatToReply

  }
}