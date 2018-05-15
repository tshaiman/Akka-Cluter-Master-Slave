package com.dv.akka.common.models

object Models {
  trait DVEvent
}

final case class Error(msg:String)
final case class UrlInfo(isValid:Boolean,servicesCount:Int)

