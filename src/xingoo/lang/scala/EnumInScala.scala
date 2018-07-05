package xingoo.lang.scala

object EnumInScala {

  object KingHeros {
    val nameToObjectMap = Map(("ChengYaoJin", ChengYaoJin), ("MiYue", MiYue))
    val defaultName: String = "ChengYaoJin"
  }

  object ChengYaoJin extends Hero{
    def say(): Unit = {
      println("我是程咬金")
    }
  }

  object MiYue extends Hero {
    def say(): Unit = {
      println("我是芈月")
    }
  }

  trait Hero {
    def say()
  }

  def getHero(name: String = KingHeros.defaultName): Hero = {
    try{
      KingHeros.nameToObjectMap(name)
    } catch {
      case nse: NoSuchElementException => throw new IllegalArgumentException("没有这个英雄")
    }
  }

  def main(args: Array[String]): Unit = {
    getHero().say()
    getHero("MiYue").say()
    getHero("LiBai").say()
  }


}
