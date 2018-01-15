package xingoo.ml.clustering

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.clustering.KMeans

object KMeansTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("tf-idf").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    // Loads data.
    val dataset = spark.read.format("libsvm")
      .load("/Users/xingoo/IdeaProjects/Spark-MLlib-Learning/src/xingoo/ml/clustering/sample_kmeans_data.txt")

    // Trains a k-means model.
    val kmeans = new KMeans().setK(4).setSeed(1L)
    val model = kmeans.fit(dataset)

    // Evaluate clustering by computing Within Set Sum of Squared Errors.
    val WSSSE = model.computeCost(dataset)
    println(s"Within Set Sum of Squared Errors = $WSSSE")

    // Shows the result.
    println("Cluster Centers: ")
    model.clusterCenters.foreach(println)
  }
}
