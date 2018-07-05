package xingoo.ml.basic

import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, MatrixEntry}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object MatrixTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("tf-idf").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    val data: RDD[MatrixEntry] = spark.sparkContext.parallelize(
      Seq(
        MatrixEntry(1,1,1.0),
        MatrixEntry(1,2,1.0),
        MatrixEntry(2,1,1.0)
      )
    )

    val matrix = new CoordinateMatrix(data)

    matrix.toIndexedRowMatrix().rows.foreach(row => row.index)

  }
}
