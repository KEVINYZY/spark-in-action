package xingoo.source.core

object SparkSessionBuilderExample {
  def main(args: Array[String]): Unit = {
    MySparkSession
      .builder()
      .config("a","1")
      .config("b","2")
      .getOrCreate()
  }
}

object MySparkSession{
  // 创建者模式
  class Builder{
    def config(key:String, value:String):Builder = {
      println(key+"-->"+value)
      this
    }

    def getOrCreate():Unit = {
      println("创建成功！")
    }
  }

  def builder(): Builder = new Builder
}
