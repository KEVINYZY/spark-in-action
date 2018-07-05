package xingoo.ml.features.tranformer

import org.apache.spark.ml.feature.{IndexToString, StringIndexer}
import org.apache.spark.sql.SparkSession

object IndexToString3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("dct").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    val df = spark.createDataFrame(Seq(
      (0, "a"),
      (1, "b"),
      (2, "c"),
      (3, "a"),
      (4, "a"),
      (5, "c")
    )).toDF("id", "category")

    val df2 = spark.createDataFrame(Seq(
      (0, 2.0),
      (1, 1.0),
      (2, 1.0),
      (3, 0.0)
    )).toDF("id", "index")

    val indexer = new StringIndexer()
      .setInputCol("category")
      .setOutputCol("categoryIndex")
      .fit(df)
    val indexed = indexer.transform(df)

    val converter = new IndexToString()
      .setInputCol("categoryIndex")
      .setOutputCol("originalCategory")

    val converted = converter.transform(df2)
    converted.show()
  }
}
