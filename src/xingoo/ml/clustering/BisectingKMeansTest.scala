package xingoo.ml.clustering

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.clustering.BisectingKMeans

object BisectingKMeansTest {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[*]").appName("tf-idf").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    // Loads data.
    val dataset = spark.read.format("libsvm")
      .load("/Users/xingoo/IdeaProjects/Spark-MLlib-Learning/src/xingoo/ml/clustering/sample_kmeans_data.txt")

    // Trains a bisecting k-means model.
    val bkm = new BisectingKMeans().setK(4).setSeed(1)
    val model = bkm.fit(dataset)



    // Evaluate clustering.
    val cost = model.computeCost(dataset)
    println(s"Within Set Sum of Squared Errors = $cost")

    // Shows the result.
    println("Cluster Centers: ")
    val centers = model.clusterCenters
    centers.foreach(println)

    model.summary.clusterSizes.foreach(println)
  }
}
