package xingoo.ml.basic

import org.apache.spark.mllib.linalg._
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.rdd.RDD

object CorrelationTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("tf-idf").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    val sc = spark.sparkContext

//    val seriesX: RDD[Double] = sc.parallelize(Array(1, 2, 3, 3, 5))  // a series
//    // must have the same number of partitions and cardinality as seriesX
//    val seriesY: RDD[Double] = sc.parallelize(Array(11, 22, 33, 33, 555))
//
//    // compute the correlation using Pearson's method. Enter "spearman" for Spearman's method. If a
//    // method is not specified, Pearson's method will be used by default.
//    val correlation: Double = Statistics.corr(seriesX, seriesY, "pearson")
//    println(s"Correlation is: $correlation")

    val data: RDD[Vector] = sc.parallelize(
      Seq(
        Vectors.dense(1.0, 10.0, 100.0),
        Vectors.dense(2.0, 20.0, 200.0),
        Vectors.dense(5.0, 33.0, 366.0))
    )  // note that each Vector is a row and not a column

    // calculate the correlation matrix using Pearson's method. Use "spearman" for Spearman's method
    // If a method is not specified, Pearson's method will be used by default.
    val correlMatrix: Matrix = Statistics.corr(data, "pearson")
    println(correlMatrix.toString)
  }
}
