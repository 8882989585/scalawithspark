package org.spark.batch.rdd

import org.apache.spark.sql.SparkSession

/**
 * read a text file
 * scala map and reduce
 */
object ReadTextFile extends App {

  private def createSparkSession() = {
    SparkSession.builder()
      .master("local[4]")
      .appName("read text file")
      .getOrCreate()
  }

  private def readFile(ss: SparkSession): Unit = {
    import ss.implicits._
    val df = ss.read.textFile("C:\\Users\\Abhishek Chauhan\\workspace\\scalawithspark\\src\\main\\data\\org\\spark\\batch\\rdd\\readtextfile.txt")
    println("total no of lines in the file ".concat(df.count().toString))
    println("total no of words in the file ".concat(df.map(line => line.split(" ").length).reduce((a, b) => a + b).toString))
  }

  private def run(): Unit = {
    val ss = createSparkSession()
    ss.sparkContext.setLogLevel("ERROR")
    readFile(ss)
  }

  run()
}
