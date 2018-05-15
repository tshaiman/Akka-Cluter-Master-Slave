package com.dv.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.dv.akka.common.models.Utils
import com.dv.akka.edge.{HttpServer, Monitor}
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration._

object EdgeRunner {


  def main(args: Array[String]): Unit = {

    println(s"*****Welcome to DV Cluster POC . running mode : http********************")

    //for simplicity this is hard coded
    val nServices = 7

    val hostname = Utils.hostname
    println(s"Http Startup with n Services=$nServices. hostname $hostname")
    val config = ConfigFactory.parseString(Utils.getRemoteConfig(hostname, 0))
      .withFallback(ConfigFactory.load())

    implicit val system = ActorSystem("ClusterSystem", config)
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    new HttpServer(8080, nServices).start()

    //system.actorOf(Monitor.props(), "monitor")
    sys.addShutdownHook(() => {
      val future = system.terminate()
      Await.result(future, 120.seconds)
    })

  }
}

