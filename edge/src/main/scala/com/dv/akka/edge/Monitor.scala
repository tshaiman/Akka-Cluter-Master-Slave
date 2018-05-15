/*
 * Copyright (c) 2018. Bionic 8 Analytics Ltd.
 * The Software and accompanying documentation is owned by Bionic 8 Analytics Ltd (Bionic). Bionic reserves all rights in and to the Software and documentation.
 * You may not use, copy, modify, distribute or make any other disposition in the software or documentation without the express written permission and subject to the terms of a written license from Bionic.
 * BIONIC SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". UNLESS AGREED IN A WRITTEN LICENSE AGREEMENT, BIONIC HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 * IN NO EVENT SHALL BIONIC BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF BIONIC HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.dv.akka.edge

import akka.actor.{Actor, Props}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext

class Monitor() extends Actor {
  object PrintCounter

  implicit val executionContext: ExecutionContext = context.system.dispatcher
  def receive = {

    case PrintCounter =>
      val ms = (System.currentTimeMillis() % 1000000).toDouble / 1000
      //println(s"$ms: work req: ${OrchestratorActor.reqCount.get()} atomicReq:${OrchestratorActor.atomicReqCount.get()}")
      //OrchestratorActor.reqCount.set(0)
      //OrchestratorActor.atomicReqCount.set(0)
  }

  override def preStart(): Unit ={

    //context.system.scheduler.schedule(0 seconds, 1 second)(self ! PrintCounter)

    //println(s"preStart: starting actor with path:${self.path}")
  }
}

object Monitor {
  def props(): Props =
    Props(classOf[Monitor])
}
