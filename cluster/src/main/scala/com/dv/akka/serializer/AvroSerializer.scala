package com.dv.akka.serializer

/*!
 * Copyright (c) 2013 Dennis Hoppe
 * www.dennis-hoppe.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


import akka.serialization._
import java.io.ByteArrayOutputStream

import com.dv.akka.DvImpression
import com.sksamuel.avro4s.{AvroInputStream, AvroOutputStream}
import org.apache.avro.generic.{GenericDatumReader, IndexedRecord}
import org.apache.avro.io.{BinaryDecoder, BinaryEncoder, DecoderFactory, EncoderFactory}
import org.apache.avro.Schema
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter, SpecificRecordBase}

/**
  * This proxy is used to encapsulate the schema definition for a given Avro
  * record. Please ensure, that the schema is statically available when
  * instancing a new class using the default constructor.
  *
  * an example (cf. test suite).
  */
trait GenericRecordProxy {
  def getSchema(): Schema
}


/**
  * This Serializer serializes and deserializes Apache Avro records. This
  * includes primarily pre-compiled records that implement the
  * [[org.apache.avro.specific.SpecificRecordBase]] interface. In order to
  * support generic Avro records, i.e. you want to serialized/deserialize Avro
  * records without code generation, please use [[GenericRecordProxy]] as
  * base class.
  */
class AvroSerializer extends SerializerWithStringManifest {


  def identifier = 14041983
  val ImpressionManifest = "impression"
  /** in-memory output stream used to hold Avro records as bytes */

  def manifest(obj: AnyRef): String =
    obj match {
      case _: DvImpression => ImpressionManifest
      case _ => ""
    }

  def toBinary(obj: AnyRef): Array[Byte] = obj match {
    case record: DvImpression => {
      val baos = new ByteArrayOutputStream()
      val os = AvroOutputStream.binary[DvImpression](baos)
      os.write(record)
      os.flush()
      os.close()
      baos.toByteArray
    }
    case _ => {
      val errorMsg = "Can't serialize unknown Type message using " +
        "AvroSerializer [" + obj + "]"
      throw new IllegalArgumentException(errorMsg)
    }
  }

  def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = manifest match{
    case ImpressionManifest => {
      val ino = AvroInputStream.binary[DvImpression](bytes)
      ino.iterator().next()
    }
    case _ => {
      val errorMsg = "Need a given manifest in order to " +
        "deserialize bytes into an Avro record"
      throw new IllegalArgumentException(errorMsg)
    }
  }

}
