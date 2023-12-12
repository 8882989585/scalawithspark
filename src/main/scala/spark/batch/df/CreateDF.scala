package spark.batch.df

import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Row, SparkSession}

import java.util

object CreateDF extends App {

  private case class Person(name: String, age: Int)

  private def createContext(): SparkSession = {
    SparkSession.builder().master("local[*]").getOrCreate()
  }

  private def createFromClass(ss: SparkSession): Unit = {
    val df = ss.createDataFrame(Seq(Person("a", 10), Person("b", 9)))
    df.show()
  }

  private def createFromScalaRows(ss: SparkSession): Unit = {
    val df = ss.createDataFrame(ss.sparkContext.parallelize(Seq(Row("a", 10), Row("b", 9))), ScalaReflection.schemaFor[Person].dataType.asInstanceOf[StructType])
    df.show()
  }

  private def createFromScalaTuples(ss: SparkSession): Unit = {
    import ss.implicits._
    val df = ss.sparkContext.parallelize(Seq(("a", 10), ("b", 9))).toDF("name", "age")
    df.show()
  }

  private def createFromJavaRows(ss: SparkSession): Unit = {
    val ll = new util.ArrayList[Row]()
    ll.add(Row("a", 10))
    ll.add(Row("b", 9))
    val df = ss.createDataFrame(ll, ScalaReflection.schemaFor[Person].dataType.asInstanceOf[StructType])
    df.show()
  }

  private def createFromScalaObjects(ss: SparkSession): Unit = {
    val ll = Seq(Person("a", 5), Person("f", 89))
    val df = ss.createDataFrame(ll)
    df.show()
  }

  val ss = createContext()
  createFromClass(ss)
  createFromScalaRows(ss)
  createFromJavaRows(ss)
  createFromScalaObjects(ss)
  createFromScalaTuples(ss)
}
