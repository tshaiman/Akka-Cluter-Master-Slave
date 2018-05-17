import com.dv.akka.serializer.AvroSerializer
import com.dv.poc.DvImpression
import org.scalatest.FlatSpec

class AvroSerializerTest extends FlatSpec{

  "An Avro Serialzier" should "Serialize messages " in {
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

}
