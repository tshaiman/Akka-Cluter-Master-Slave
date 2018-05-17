package com.dv.akka.edge

import java.util.concurrent.atomic.AtomicLong

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.server.Directives.{get, path, _}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.dv.akka.common.models.{JsonSupport, UrlInfo}
import com.dv.poc.DvImpression

import scala.concurrent.duration._
import scala.util.Random


trait DvRoute extends JsonSupport {

  val orchPool: ActorRef
  val kafkaRouter:ActorRef
  def actorSys: ActorSystem
  def nServices: Int

  val dvRoute: Route = {
    path("parse") {
      implicit val timeout = Timeout(1.seconds)
      implicit val numOfCalls: Int = nServices

      get {
        parameters('url) { url =>
          completeWith(implicitly[ToResponseMarshaller[UrlInfo]]) { f =>
            genMessage() match {
              case m if m.evtType == 0 =>
                kafkaRouter ! m
                f(UrlInfo(true,1))
              case m =>
                orchPool ! MessageWithCallback( m, f, nServices)
            }
          }
        }
      }
    }
  }

  val counter:AtomicLong = new AtomicLong(0)

  def genMessage(): DvImpression = {
    val s: String = "abcdefghijklmnopqrstuvwxyz0123456AB"
    val d:Int = 42
    //In 20% of Cases we want a "long processing" (400 micro) and in the rest 80% we want "short processing" (50 micro)
    //The Event Type flag ensures that
    //when the flag is turned on a long processing will occur
    val evtType = if (counter.incrementAndGet() % 5 ==0) 7 else 7
    DvImpression(evtType,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d)
  }
}


