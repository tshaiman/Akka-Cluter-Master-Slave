package com.dv.akka

import akka.actor.{Actor, ActorLogging}

class KafkaActor extends Actor with ActorLogging{

  override def receive: Receive = {

    case evt:DvImpression => {
      //println("Kafka Actor sending")
      //log.info("sending event to kafka")
    }
  }
}
