package spark.playground

import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Row, SparkSession}


object Application2 extends App {

  private case class Person(name: String, age: Int)

  private def execute(): Unit = {
    val ss = SparkSession.builder().master("local[*]").getOrCreate()
    val schema: StructType = ScalaReflection.schemaFor[Person].dataType.asInstanceOf[StructType]
    val df = ss.createDataFrame(ss.sparkContext.parallelize(Seq(Row("Abhishek", 34), Row("Abhinav", 45))), schema)

    df.show()

  }

  execute()
}
