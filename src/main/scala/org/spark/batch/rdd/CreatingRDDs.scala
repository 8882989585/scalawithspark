package org.spark.batch.rdd

import org.apache.spark.sql.SparkSession

/**
 * Create RDD from Java collection
 * save as java object
 * read from java object
 */
object CreatingRDDs extends App {

  private def createSparkSession() = {
    SparkSession.builder()
      .master("local[4]")
      .appName("read text file")
      .getOrCreate()
  }

  private def createRDDFromSeq(ss: SparkSession): Unit = {
    val words = Array("abhishek chauhan", "abhinav chauhan", "shalu", "neer")
    val rdd = ss.sparkContext.parallelize(words, 2)
    println("total no of words in the file "
      .concat(rdd.map(line => line.split(" ").length).reduce((a, b) => a + b).toString))
  }

  private def saveRDDAsJavaObject(ss: SparkSession): Unit = {
    val words = Array("abhishek chauhan", "abhinav chauhan", "shalu", "neer")
    val rdd = ss.sparkContext.parallelize(words, 2)
    rdd.saveAsObjectFile("C:\\Users\\Abhishek Chauhan\\workspace\\scalawithspark\\src\\main\\temp\\org\\spark\\batch\\rdd\\creatingrdd")
  }

  private def readRDDFromJavaObject(ss: SparkSession): Unit = {
    val rdd = ss.sparkContext.objectFile[String]("C:\\Users\\Abhishek Chauhan\\workspace\\scalawithspark\\src\\main\\temp\\org\\spark\\batch\\rdd\\creatingrdd")
    rdd.foreach(line => println(line))
  }

  private def run(): Unit = {
    val ss = createSparkSession()
    ss.sparkContext.setLogLevel("ERROR")
    createRDDFromSeq(ss)
    saveRDDAsJavaObject(ss)
    readRDDFromJavaObject(ss)
  }

  run()
}
