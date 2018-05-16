package com.dv.akka.edge

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.cluster.metrics.AdaptiveLoadBalancingGroup
import akka.cluster.routing.{ClusterRouterGroup, ClusterRouterGroupSettings}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.routing.{ConsistentHashingGroup, FromConfig}
import akka.stream.Materializer
import com.dv.akka.KafkaActor

import scala.concurrent.ExecutionContext
import scala.util.Random


class HttpServer(port:Int,services:Int)
      (implicit actorSystem: ActorSystem, mat: Materializer, ec: ExecutionContext)
  extends DvRoute {

  override def actorSys: ActorSystem = implicitly
  override def nServices:Int = services

  val workerRouter = actorSys.actorOf(FromConfig.props(Props.empty),"workers")

  val orchPool = actorSys.actorOf(OrchestratorPoolActor.props(1, 450, workerRouter))
  val kafkaRouter = actorSys.actorOf(Props[KafkaActor],name = "kafkaActor")

  val route: Route = dvRoute

  def start(): Unit = {
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    Http().bindAndHandle(
      handler = route,
      interface = "0.0.0.0",
      port = 8080
    )
  }

}
