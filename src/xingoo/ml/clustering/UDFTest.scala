package xingoo.ml.clustering

import org.apache.spark.sql.SparkSession

object UDFTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("tf-idf").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    spark.udf.register("merge_arr",(arr:Seq[String])=>arr.mkString(","))

//    val df = spark.createDataFrame(Seq(
//      (1,Array("1","2")),
//      (2,Array("3","4")))).toDF("a","b")
//
//    df.createOrReplaceTempView("xt")
//
//    spark.sql(
//      s"""
//         |select a,stddev_samp(a) from xt group by a
//       """.stripMargin).show()

    println(math.log10(Long.MaxValue) / math.log10(2))
  }
}
