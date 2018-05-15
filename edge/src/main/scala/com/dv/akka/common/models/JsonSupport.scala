package com.dv.akka.common.models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val errorFormat = jsonFormat1(Error)
  implicit val urlInfoFormat = jsonFormat2(UrlInfo)
}
