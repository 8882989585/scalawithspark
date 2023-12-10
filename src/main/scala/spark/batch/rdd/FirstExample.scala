package spark.batch.rdd

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, desc, sum}

object FirstExample extends App {

  private def execute(): Unit = {
    val sc = SparkSession.builder().master("local[*]").getOrCreate()

    val df = sc.read
      .options(Map("header" -> "true", "inferSchema" -> "true"))
      .csv("/home/abhishek/workspace/scalawithspark/src/main/data/spark/batch/rdd/mnm_dataset.csv")
    df.groupBy(Seq("State", "Color")
        .map(x => col(x)).toArray: _*)
      .agg(sum(col("Count")).alias("total_count"))
      .orderBy(desc("total_count"))
      .show(10, truncate = false)
  }

  execute()
}
