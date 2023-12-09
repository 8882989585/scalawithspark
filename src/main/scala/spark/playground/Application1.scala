package spark.playground

import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

import java.util

object Application1 extends App {

  private case class Employee(name: String, age: Int)

  private def createSparkContext(): SparkSession = {
    SparkSession.builder().master("local[*]").appName("application").getOrCreate()
  }

  private def genDataFrame(ss: SparkSession): Unit = {
    import ss.implicits._
    val ll: Seq[Employee] = Seq(Employee("Abhishek", 31), Employee("Abhinav", 56))
    val df = ss.createDataFrame(ll).toDF()
    df.show(truncate = false)

    val ll2: util.List[Row] = new util.ArrayList[Row]()
    ll2.add(Row("Abhishek", 56))
    ll2.add(Row("Abhinav", 67))
    val df2 = ss.createDataFrame(ll2,
      StructType(Array(
        StructField("Name", StringType),
        StructField("age", IntegerType))))
    df2.show()

    val jString =
      """{
        |  "name":"Abhishek",
        |  "age":23,
        |  "children" : [{"name":"Neer", "age":2}],
        |  "spouse" : {"name":"Shalu"}
        |}""".stripMargin
    val df3 = ss.read
      .schema(StructType(Array(
        StructField("name", StringType),
        StructField("children", ArrayType(StructType(Array(
          StructField("name", StringType),
          StructField("age", IntegerType))))),
        StructField("spouse", StructType(Array(StructField("name", StringType)))))))
      .json(Seq(jString).toDS())
    df3.show(truncate = false)
    println(df3.schema.json)
  }

  val ss = createSparkContext()
  genDataFrame(ss)
}
