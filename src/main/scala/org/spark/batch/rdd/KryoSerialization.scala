package org.spark.batch.rdd

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * save as kryo object
 * read from kryo object
 * custom class serializer
 */
object KryoSerialization extends App {

  private class Person(name: String, age: Integer) extends Serializable {
    override def toString: String = {
      this.name + " " + this.age.toString
    }
  }

  private def createSparkSession() = {
    val sparkConf = new SparkConf()
    sparkConf
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryo.registrationRequired", "true")
      .registerKryoClasses(
        Array(classOf[Person], classOf[Array[Person]],
          Class.forName("org.apache.spark.internal.io.FileCommitProtocol$TaskCommitMessage"))
      )
    SparkSession.builder()
      .master("local[4]")
      .appName("read text file")
      .config(sparkConf)
      .getOrCreate()
  }

  private def readRDDAsKryoObject(ss: SparkSession): Unit = {
    val words = Array(new Person("Abhishek", 29), new Person("Abhinav", 44))
    val rdd = ss.sparkContext.parallelize(words, 2)
    rdd.saveAsObjectFile("C:\\Users\\Abhishek Chauhan\\workspace\\scalawithspark\\src\\main\\temp\\org\\spark\\batch\\rdd\\kryoserialization")
  }

  private def readRDDFromKryoObject(ss: SparkSession): Unit = {
    val rdd = ss.sparkContext.objectFile[Person]("C:\\Users\\Abhishek Chauhan\\workspace\\scalawithspark\\src\\main\\temp\\org\\spark\\batch\\rdd\\kryoserialization")
    rdd.foreach(line => println(line.toString))
  }

  private def run(): Unit = {
    val ss = createSparkSession()
    ss.sparkContext.setLogLevel("ERROR")
    readRDDAsKryoObject(ss)
    readRDDFromKryoObject(ss)
  }

  run()
}
