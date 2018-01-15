package xingoo.core

import org.apache.spark.sql.SparkSession

object SparkSessionExample {
  def main(args: Array[String]): Unit = {
    val spark1 = SparkSession.builder().master("local[*]").appName("test1").getOrCreate()
    spark1.createDataFrame(Seq((1,2),(3,4))).show()

    val spark2 = SparkSession.builder().master("local[*]").appName("test2").getOrCreate()
    spark2.createDataFrame(Seq((1,2),(3,4))).show()
    spark2.createDataFrame(Seq((1,2),(3,4))).count()
  }
}
