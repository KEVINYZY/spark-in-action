package xingoo.ml.basic

import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.{Row, SparkSession}

object CorrealtionDFTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("tf-idf").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    import spark.implicits._

    val data = Seq(
      (1,Vectors.sparse(4, Seq((0, 1.0), (3, -2.0)))),
      (2,Vectors.dense(4.0, 5.0, 0.0, 3.0)),
      (3,Vectors.dense(6.0, 7.0, 0.0, 8.0)),
      (4,Vectors.sparse(4, Seq((0, 9.0), (3, 1.0))))
    )

    val df = spark.createDataFrame(data).toDF("label", "features")

    //val df = data.map(Tuple1.apply).toDF("features")
    val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
    val list = df.map(r => r.getInt(0)).collectAsList()
    println(s"Pearson correlation matrix:\n $coeff1")
    coeff1.colIter.foreach(v=>v.foreachActive((i,d)=>println(s"$i ${list.get(i)} $d ")))
    //coeff1.toArray.foreach(println)

//    val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
//    println(s"Spearman correlation matrix:\n $coeff2")
  }
}
