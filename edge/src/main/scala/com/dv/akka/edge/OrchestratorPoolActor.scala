/*
 * Copyright (c) 2018. Bionic 8 Analytics Ltd.
 * The Software and accompanying documentation is owned by Bionic 8 Analytics Ltd (Bionic). Bionic reserves all rights in and to the Software and documentation.
 * You may not use, copy, modify, distribute or make any other disposition in the software or documentation without the express written permission and subject to the terms of a written license from Bionic.
 * BIONIC SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". UNLESS AGREED IN A WRITTEN LICENSE AGREEMENT, BIONIC HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 * IN NO EVENT SHALL BIONIC BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF BIONIC HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.dv.akka.edge


import akka.actor.{Actor, ActorRef, Props}
import akka.routing.RoundRobinPool
import com.dv.akka.ImpressionMessage
import com.dv.akka.common.models.UrlInfo

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import scala.collection.mutable

case class MessageWithCallback(request: ImpressionMessage,
                               complete: UrlInfo => Unit,
                               numOfCalls: Int)

object ImIdleNow

object PrintCounter

class OrchestratorPoolActor(size: Int, workRouter: ActorRef) extends Actor {

  private  val system                             = context.system
  private  var idle                               = (1 to size)
    .map(t => context.actorOf(OrchestratorActor.props(workRouter))).toList

  private  val queue                              = mutable.Queue.empty[MessageWithCallback]
  implicit val executionContext: ExecutionContext = context.system.dispatcher

  override def receive: Receive = {
    case evt: MessageWithCallback if idle.isEmpty =>
      queue.enqueue(evt)

    case evt: MessageWithCallback =>
      idle.head ! evt
      idle = idle.tail

    case ImIdleNow if queue.nonEmpty =>
      sender ! queue.dequeue()

    case ImIdleNow =>
      idle = sender :: idle

  }

}

object OrchestratorPoolActor {
  def props(count: Int, size: Int, workRouter: ActorRef): Props =
    RoundRobinPool(count).props(Props(new OrchestratorPoolActor(size, workRouter)))
}
