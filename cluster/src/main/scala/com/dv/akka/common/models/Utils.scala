package com.dv.akka.common.models

object Utils {

  def getRemoteConfig(hostname: String, port: Int): String = {
    s"""
      akka.remote.netty.tcp.hostname=$hostname
      akka.remote.netty.tcp.port=$port
      akka.remote.artery.canonical.port=$port
      akka.remote.artery.canonical.hostname=$hostname
      """
  }

  def hostname:String = java.net.InetAddress.getLocalHost().getHostAddress()
}
