package xingoo.ml.features.tranformer

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.PCA
import org.apache.spark.ml.linalg.Vectors

object PCATest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("pca").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    val data = Array(
      Vectors.sparse(5, Seq((1, 1.0), (3, 7.0))),
      Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0),
      Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0)
    )
    val df = spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")

    val pca = new PCA()
      .setInputCol("features")
      .setOutputCol("pcaFeatures")
      .setK(3)
      .fit(df)

    val result = pca.transform(df).select("pcaFeatures")
    result.show(false)
  }
}
