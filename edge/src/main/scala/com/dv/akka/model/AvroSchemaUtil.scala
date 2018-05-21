package com.dv.akka.model

import org.apache.avro.Schema
import org.apache.avro.Schema.Parser
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericRecord
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}
import java.io.ByteArrayOutputStream

import com.dv.akka.DvImpression
import org.apache.avro.io._

import scala.io.Source

object AvroSchemaUtil {
  private val schema:Schema  = getSchema

  def getSchema():Schema ={
    try{
      val resource  = Source.fromResource("dvSchema.avsc")
      if(resource != null)
        return new Parser().parse(resource.mkString)
    }catch {
      case ex:Exception => println (ex)
    }
    null

  }

  val defaultString: String = "abcdefghijklmnopqrstuvwxyz0123456AB"
  val defualtVar :Int = 42

  def buildMessage(evtType:Int,strVal:String,intVal:Int):GenericRecord = {
    val record: GenericRecord = new GenericData.Record(schema)
    for(i <- 1 to 50) {
      record.put(s"f$i",strVal)
      record.put(s"d$i",intVal)
    }
    record.put("evtType",evtType)
    record
  }

  def toGenericRecord(impression:DvImpression):GenericRecord = {
    buildMessage(impression.evtType,defaultString,defualtVar)
  }

  def toByteArray(impression:DvImpression):Array[Byte] = {
    val writer = new SpecificDatumWriter[GenericRecord](schema)
    val out = new ByteArrayOutputStream()
    val encoder: BinaryEncoder = EncoderFactory.get().binaryEncoder(out, null)
    writer.write(toGenericRecord(impression), encoder)
    encoder.flush()
    out.close()
    out.toByteArray()

  }

  def fromByteRecord(message: Array[Byte]):DvImpression = {
    try {
      val reader: DatumReader[GenericRecord] = new SpecificDatumReader[GenericRecord](schema)
      val decoder: Decoder = DecoderFactory.get().binaryDecoder(message, null)
      val userData: GenericRecord = reader.read(null, decoder)
      val evtType = userData.get("evtType").toString.toInt
      val d = userData.get("d1").toString.toInt
      val s = userData.get("f1").toString
      return DvImpression(evtType,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d)
    }catch{
      case ex:Exception=> println (ex)
    }
    null
  }
}
