package com.dv.akka.serializer

import java.io.ByteArrayOutputStream

import akka.serialization.SerializerWithStringManifest
import com.dv.akka.DvImpression
import com.dv.akka.model.AvroSchemaUtil
import com.sksamuel.avro4s.{AvroInputStream, AvroOutputStream}
import org.apache.avro.generic.GenericRecord
import org.apache.avro.specific.SpecificDatumWriter

class GenericRecordSerializer extends SerializerWithStringManifest {


  def identifier = 14041985
  val ImpressionManifest = "impression"

  /** in-memory output stream used to hold Avro records as bytes */

  def manifest(obj: AnyRef): String =
    obj match {
      case _: DvImpression => ImpressionManifest
      case _ => ""
    }

  def toBinary(obj: AnyRef): Array[Byte] = obj match {
    case record: DvImpression => AvroSchemaUtil.toByteArray(record)
    case _ => {
      val errorMsg = "Can't serialize unknown Type message using " +
        "AvroSerializer [" + obj + "]"
      throw new IllegalArgumentException(errorMsg)
    }
  }

  def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = manifest match{
    case ImpressionManifest => AvroSchemaUtil.fromByteRecord(bytes)
    case _ => {
      val errorMsg = "Need a given manifest in order to " +
        "deserialize bytes into an Avro record"
      throw new IllegalArgumentException(errorMsg)
    }
  }

}

