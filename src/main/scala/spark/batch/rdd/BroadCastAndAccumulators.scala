package org.spark.batch.rdd

import org.apache.spark.sql.SparkSession
import org.apache.spark.util.AccumulatorV2

/**
 * Create RDD from Java collection
 * save as java object
 * read from java object
 */
object BroadCastAndAccumulators extends App {

  class MyCustomAccumulator extends AccumulatorV2[Int, Long] {

    var expoSum = 0L

    override def isZero: Boolean = (expoSum == 0)

    override def copy(): AccumulatorV2[Int, Long] = {
      val cpy = new MyCustomAccumulator()
      cpy.expoSum = this.expoSum
      cpy
    }

    override def reset(): Unit = expoSum = 0

    override def add(v: Int): Unit = expoSum = expoSum + v

    override def merge(other: AccumulatorV2[Int, Long]): Unit = expoSum = value + other.value

    override def value: Long = expoSum
  }

  private def createSparkSession() = {
    SparkSession.builder()
      .master("local[4]")
      .appName("read text file")
      .getOrCreate()
  }

  private def broadCastVariables(ss: SparkSession): Unit = {
    val bVar = ss.sparkContext.broadcast(Array(1, 3, 4, 5, 6))
    bVar.unpersist()
    println(bVar.value.length)
    //    uncomment to check the effect won;t work otherwise
    //    bVar.destroy()
    //    println(bVar.value.length)
  }

  private def accumulatorsVariable(ss: SparkSession): Unit = {
    val acc = ss.sparkContext.longAccumulator("default acc")
    val vals = ss.sparkContext.parallelize(Array(1, 2, 4))
    vals.foreach(v => acc.add(v))
    println(acc.value)
  }

  private def customAccumulatorsVariable(ss: SparkSession): Unit = {
    val vals = ss.sparkContext.parallelize(Array(1, 2, 4))
    val ca = new MyCustomAccumulator
    ss.sparkContext.register(ca)
    vals.foreach(v => ca.add(v))
    println(ca.value)
  }

  private def run(): Unit = {
    val ss = createSparkSession()
    ss.sparkContext.setLogLevel("ERROR")
    broadCastVariables(ss)
    accumulatorsVariable(ss)
    customAccumulatorsVariable(ss)
  }

  run()
}
