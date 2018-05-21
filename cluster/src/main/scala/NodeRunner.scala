package com.dv.akka.cluster

import akka.actor.{ActorRef, ActorSystem}
import akka.routing.FromConfig
import com.dv.akka.DvImpression
import com.dv.akka.common.models.Utils
import com.dv.akka.model.AvroSchemaUtil
import com.typesafe.config.ConfigFactory


object NodeRunner {


  def main(args: Array[String]): Unit = {

    println(s"*****Welcome to DV Cluster POC . running mode : cluster********************")
    val ports:Seq[String] = if (args.length > 0) args else Seq("2551")

    //for simplicity this is hard coded
    val workTimeMicro = 400
    val hostname = Utils.hostname
    println(s"cluster startup with $ports ports. hostname $hostname . worktime: $workTimeMicro")

    ports foreach { port =>
      val config = ConfigFactory.parseString(Utils.getRemoteConfig(hostname, port.toInt))
        .withFallback(ConfigFactory.parseString("akka.cluster.roles = [compute]"))
        .withFallback(ConfigFactory.load())

      implicit val system = ActorSystem("ClusterSystem", config)
      val workers:ActorRef = system.actorOf(UrlWorker.props(), "workerRouter")
   }
  }


}
