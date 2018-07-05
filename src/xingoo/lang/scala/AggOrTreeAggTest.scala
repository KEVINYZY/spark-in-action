package xingoo.lang.scala

import org.apache.spark.sql.SparkSession

object AggOrTreeAggTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("tf-idf").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    spark.sparkContext.parallelize(List(1,2,4,5,8,9),3)
      .aggregate(0)(seq, comb)

    spark.sparkContext.parallelize(List(1,2,4,5,8,9),3)
      .treeAggregate(0)(seq, comb)
  }

  def seq(a: Int, b: Int): Int = {
    println(s"seq: $a : $b")
    math.max(a, b)
  }

  def comb(a: Int, b: Int): Int = {
    println(s"comb: $a : $b")
    a+b
  }
}
