package xingoo.ml.features.tranformer

import org.apache.spark.ml.attribute.Attribute
import org.apache.spark.ml.feature.{IndexToString, StringIndexer}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types._

object IndexToStringTest {
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

    val indexer = new StringIndexer()
      .setInputCol("category")
      .setOutputCol("categoryIndex")
      .setHandleInvalid("skip")
      .fit(df)
    val indexed = indexer.transform(df)

    indexed.show()

    val df2 = spark.createDataFrame(Seq(
      (0, 2.0),
      (1, 1.0),
      (2, 1.0),
      (3, 0.0)
    )).toDF("id", "index").select(col("*"),col("index").as("formated_index", indexed.schema("categoryIndex").metadata))

    val converter = new IndexToString()
      .setInputCol("formated_index")
      .setOutputCol("origin_col")

    val converted = converter.transform(df2)
    converted.show(false)

    val df3 = spark.createDataFrame(Seq(
      (0, 2.0),
      (1, 1.0),
      (2, 1.0),
      (3, 0.0)
    )).toDF("id", "index")

    val converter2 = new IndexToString()
      .setInputCol("index")
      .setOutputCol("origin_col")
      .setLabels(indexed.schema("categoryIndex").metadata.getMetadata("ml_attr").getStringArray("vals"))

    val converted2 = converter2.transform(df3)
    converted2.show(false)
  }
}
