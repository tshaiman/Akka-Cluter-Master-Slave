package com.dv.akka.common.models

import com.dv.akka.common.models.Models.DVEvent

object Models {
  trait DVEvent
}

final case class Error(msg:String)
final case class UrlInfo(isValid:Boolean,servicesCount:Int)

case class SetWork(workMicro:Int)
