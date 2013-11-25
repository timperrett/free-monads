package example 

import org.scalameter.api._
import org.scalameter.persistence.SerializationPersistor

object RangeBenchmark extends PerformanceTest.Quickbenchmark {
  val sizes: Gen[Int] = Gen.range("statement")(30000, 1500000, 30000)

  val ranges: Gen[Range] = for {
    size <- sizes
  } yield 0 until size

  performance of "Logging" in {
    measure method "free" in {
      using(ranges) in {
        r => SLF4J(Main.program)
      }
    }

  }
}
