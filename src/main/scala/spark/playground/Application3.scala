package spark.playground

import org.apache.spark.sql.functions.avg
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object Application3 extends App {

  private def createContext(): SparkSession = {
    SparkSession.builder().master("local[*]").getOrCreate()
  }

  private def execute(): Unit = {
    val ss = createContext()
    val dataRDD = ss.sparkContext.parallelize(Seq(("Brooke", 20), ("Denny", 31), ("Jules", 30),
      ("TD", 35), ("Brooke", 25)))
    dataRDD
      .map(x => (x._1, (x._2, 1)))
      .reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
      .map(x => (x._1, x._2._1 / x._2._2))
      .foreach(x => println(x))

    dataRDD
      .map(x => (x._1, (x._2, 1)))
      .aggregateByKey((0, 0))((x, y) => (x._1 + y._1, x._2 + y._2), (x, y) => (x._1 + y._1, x._2 + y._2))
      .map(x => (x._1, x._2._1 / x._2._2))
      .foreach(x => println(x))

    val dataDF = ss.createDataFrame(
      ss.sparkContext.parallelize(Seq(
        Row("Brooke", 20),
        Row("Denny", 31),
        Row("Jules", 30),
        Row("TD", 35),
        Row("Brooke", 25))),
      StructType(Array(StructField("name", StringType), StructField("age", IntegerType))))
    dataDF.groupBy("name").agg(avg("age").alias("average_age")).show()
  }

  execute()
}
