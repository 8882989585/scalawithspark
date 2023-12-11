package spark.batch.rdd

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{avg, col, desc, lit, row_number, sha2, when}
import org.apache.spark.sql.types.StringType

object NurseDataAnalysis extends App {

  private def createContext(): SparkSession = {
    SparkSession.builder()
      .config(
        new SparkConf()
          .set("spark.sql.shuffle.partitions", "10"))
      .master("local[4]").getOrCreate()
  }

  private def readTheFile(ss: SparkSession): DataFrame = {
    val df = ss.read
      .options(Map("header" -> "true"))
      .schema("X double,Y double,Z double,EDA double,HR double, TEMP double, id int, datetime timestamp")
      .csv("/home/abhishek/Downloads/merged_data.csv")
    df
  }

  /**
   * getting distinct count on nurse data
   *
   * @param ss
   * @return
   */
  private def fx1(df: DataFrame): Unit = {
    println(df.select(col("id")).distinct().count())
  }

  /**
   * get average values for nurses
   *
   * @param df
   */
  private def fx2(df: DataFrame): Unit = {
    df.groupBy(col("id"))
      .agg(
        avg("X").alias("X"),
        avg("Y").alias("Y"),
        avg("Z").alias("Z"),
        avg("EDA").alias("EDA"),
        avg("HR").alias("HR"),
        avg("TEMP").alias("TEMP")).show(truncate = true)
  }

  private def fx3(df: DataFrame): DataFrame = {
    df.withColumn("hash_id",
        sha2(when(col("id").isNull, lit("1"))
          .otherwise(col("id")), 256))
      .drop("id")
      .withColumn("id", col("hash_id"))
      .drop("hash_id")
  }

  private def fx4(df: DataFrame): Unit = {
    df.select(col("id"), col("HR"), col("datetime"))
      .withColumn("r_no", row_number() over Window.partitionBy("id").orderBy(desc("datetime")))
      .createOrReplaceTempView("fx4")
    df.sparkSession.sql("select t1.id, t1.r_no, avg(t2.HR) from fx4 t1 join fx4 t2 on t1.id = t2.id and t2.r_no >= t1.r_no and t2.r_no < t1.r_no + 7 group by t1.id, t1.r_no")
      .show(truncate = false)
  }

  private def execute(): Unit = {
    val ss = createContext()
    val df: DataFrame = fx3(readTheFile(ss))
    //    fx1(df)
    //    fx2(df)
    fx4(df)


  }


  execute()
}
