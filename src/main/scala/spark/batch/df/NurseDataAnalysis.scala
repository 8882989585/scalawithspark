package spark.batch.df

import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.catalyst.plans.JoinType
import org.apache.spark.sql.expressions.{Aggregator, UserDefinedFunction, Window}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Encoder, Encoders, SparkSession}

object NurseDataAnalysis extends App {

  private class CustomAgg extends Aggregator[Double, Double, Int] {
    override def zero: Double = 0.0

    override def reduce(b: Double, a: Double): Double = a + b

    override def merge(b1: Double, b2: Double): Double = b1 + b2

    override def finish(reduction: Double): Int = reduction.toInt

    override def bufferEncoder: Encoder[Double] = Encoders.scalaDouble

    override def outputEncoder: Encoder[Int] = Encoders.scalaInt
  }

  private val rand = new scala.util.Random

  private val disperseData = (id: Int) => {
    rand.nextInt(10000).toString + "_" + (if (!id.isNaN) id.toString else "1")
  }

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
      .csv("/home/abhishek/workspace/scalawithspark/src/main/data/spark/batch/df/merged_data.csv")
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

  private def fx3(df: DataFrame, dData: UserDefinedFunction): DataFrame = {
    df.withColumn("hash_id",
        sha2(dData(col("id")), 256))
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

  private def fx5(df: DataFrame, cAgg: UserDefinedFunction): Unit = {
    val df1 = df.select(col("id"), col("HR"), col("datetime"))
      .withColumn("r_no", row_number() over Window.partitionBy("id").orderBy(desc("datetime")))
      .as("df1")
    var df2 = df1.as("df2")
    df2 = df1.join(df2, df1("id") === df2("id"), "inner")
      .where(col("df2.r_no").geq(col("df1.r_no"))
        .and(col("df2.r_no").lt(col("df1.r_no") + 7)))
      .select(col("df1.id").alias("id"),
        col("df1.r_no").alias("r_no"),
        col("df2.HR").alias("HR"))

    df2.groupBy(col("id"), col("r_no"))
      .agg(cAgg(col("HR")).alias("HR"))
      .show(truncate = false)
  }

  private def execute(): Unit = {
    val ss = createContext()
    val dData = udf(disperseData)
    val cAgg = udaf(new CustomAgg)
    val df: DataFrame = fx3(readTheFile(ss), dData)
    //    fx1(df)
    //    fx2(df)
    //    fx4(df)
    fx5(df, cAgg)

  }


  execute()
}
