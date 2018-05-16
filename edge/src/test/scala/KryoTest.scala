import akka.actor.{ActorSystem, ExtendedActorSystem}
import akka.stream.ActorMaterializer
import java.util.UUID.randomUUID

import com.dv.akka.{ImpressionData, ImpressionMessage}
import org.scalatest.FlatSpec

class KryoTest extends FlatSpec {

  "An Kryo Deseriralizer " should "serialize and Deserialize data correctly" in {

    val msg = genMessage()

    val system = ActorSystem("ClusterSystem")
    val serilizer  = new com.twitter.chill.akka.AkkaSerializer(system.asInstanceOf[ExtendedActorSystem])
    val binFields  = serilizer.toBinary(msg)
    val l = binFields.length
    assert(l > 0)
    val newMsg:ImpressionMessage = serilizer.fromBinary(binFields).asInstanceOf[ImpressionMessage]
    assert(newMsg != null)
    //assert(true)


  }

  def getImpressionData() :ImpressionData = {

      val s: String = randomUUID().toString
      val d:Int = 42
      //In 20% of Cases we want a "long processing" (400 micro) and in the rest 80% we want "short processing" (50 micro)
      //The Event Type flag ensures that
      //when the flag is turned on a long processing will occur
      val eventType = 9
      new ImpressionData(randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, randomUUID().toString, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d)
    
    }

  def genMessage(): ImpressionMessage = {
    ImpressionMessage(getImpressionData(),8 )
  }
}
