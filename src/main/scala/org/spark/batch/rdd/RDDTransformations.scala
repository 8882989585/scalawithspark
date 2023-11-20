package org.spark.batch.rdd

import org.apache.spark.sql.SparkSession

/**
 * flatmap, aggregatebykey, sortbyKey, reducebykey
 */
object RDDTransformations extends App {

  private class Person(name: String, age: Integer, children: Seq[String]) extends Serializable {

    def getChildren(): Seq[String] = {
      this.children
    }

    override def toString: String = {
      this.name + " " + this.age.toString
    }
  }

  private def createSparkSession() = {
    SparkSession.builder()
      .master("local[4]")
      .appName("read text file")
      .getOrCreate()
  }

  private def transform(ss: SparkSession): Unit = {
    val data1 = Array(
      new Person("abhishek", 34, Array("Neer", "Tathagat")),
      new Person("abhinav", 28, Array("Neer", "Suray")))
    val rdd1 = ss.sparkContext.parallelize(data1, 2)
    rdd1.flatMap(u => u.getChildren()).foreach(c => println(c))

    val data2 = Array(
      ("Abhishek", 1, 2), ("Abhinav", 67, 4), ("Abhishek", 1, 5), ("Abhishek", 1, 6), ("Abhishek", 1, 7),
      ("Abhishek", 1, 8), ("Abhinav", 67, 7), ("Abhinav", 67, 8), ("Abhinav", 45, 7))
    val rdd2 = ss.sparkContext.parallelize(data2, 4)
    rdd2.map(line => (line._1, (line._2, line._3)))
      .aggregateByKey(0)((a: Int, v: (Int, Int)) => (a + v._2), (a: Int, v: Int) => (a + v))
      .sortByKey(true, 1)
      .foreach(line => println(line.toString()))

    val data3 = Array(
      ("Abhishek", 1, 2), ("Abhinav", 67, 4), ("Abhishek", 1, 5), ("Abhishek", 1, 6), ("Abhishek", 1, 7),
      ("Abhishek", 1, 8), ("Abhinav", 67, 7), ("Abhinav", 67, 8), ("Abhinav", 45, 7))
    val rdd3 = ss.sparkContext.parallelize(data3, 4)
    rdd3.map(line => (line._1, (line._2, line._3)))
      .reduceByKey((a, b) => (a._1, a._2 + b._2))
      .sortByKey(true, 1)
      .foreach(line => println(line.toString()))
  }

  private def run(): Unit = {
    val ss = createSparkSession()
    ss.sparkContext.setLogLevel("ERROR")
    transform(ss)
  }

  run()
}
