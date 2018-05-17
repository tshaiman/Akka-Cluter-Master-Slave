import java.io.ByteArrayOutputStream

import com.dv.akka.serializer.AvroSerializer
import com.dv.poc.DvImpression
import com.sksamuel.avro4s.{AvroInputStream, AvroOutputStream}
import java.io.File

import com.dv.test.data.{Pizza}
import org.apache.avro.{Schema, SchemaBuilder}
import org.scalatest.FlatSpec



class AvroSerializerTest extends FlatSpec{

  "An Avro Serialzier" should "Serialize messages " in {

    /*val baos = new ByteArrayOutputStream()
    val os = AvroOutputStream.data[Pizza](baos)

    val pizza = Pizza("Pep","Onion",324)
    os.write(pizza)
    os.flush()
    os.close()
    val pBytes = baos.toByteArray*/

    val pBytes = Array[Byte](6,80,101,112,10,79,110,105,111,110,-120,5)



    val wSchema = getWriterSchema()
    val rSchema = getReaderSchema()
    val ino = AvroInputStream.binary[Pizza](pBytes,wSchema)
    val p2 = ino.iterator().next()


    val as = new AvroSerializer()
    val s: String = "abcdefghijklmnopqrstuvwxyz0123456AB"
    val d:Int = 42
    val imp = DvImpression(0,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d)
    val bin = as.toBinary(imp);
    assert(bin != null)
    assert(bin.length > 0)
    println("got " + bin.length + " bytes")


    val imp2 = as.fromBinary(bin,"impression").asInstanceOf[DvImpression]
    assert(imp2 != null)
    assert(imp2.f32.equals(s))

  }

  def getWriterSchema() : Schema = {
    SchemaBuilder
      .record("Pizza").namespace("com.dv.test.data")
        .fields().name("name").`type`("string").noDefault()
        .name("toping").`type`("string").noDefault()
        .name("calories").`type`("int").noDefault()
       .endRecord()



  }

  def getReaderSchema() : Schema = {
    SchemaBuilder
      .record("Pizza").namespace("com.dv.test.data")
      .fields().name("name").`type`("string").noDefault()
      .name("toping").`type`("string").noDefault()
      .name("calories").`type`("int").noDefault()
      //.name("rate").`type`("string").noDefault().nu
      .nullableString("rate","")
      .nullableString("geo","")
      .endRecord()



  }

}
