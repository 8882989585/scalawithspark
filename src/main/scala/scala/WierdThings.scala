package scala

object WierdThings extends App {

  private def openSeqAsInfinityParams(): Unit = {
    val seq = Seq("a", "b", "c")
    println("openSeqAsInfinityParams ====>")
    //    seq: _*
    Unit
  }

  private def convertMapToList(): Unit = {
    val seq = Map(1 -> 1, 2 -> 4, 3 -> 9)
    println("convertMapToList ====>")
    println(seq.map({ case (k, v) => k * v }).toList)
  }

  private def convertListToList(): Unit = {
    val seq = Seq(1, 8, 9)
    println("convertListToList ====>")
    println(seq.map(k => k * k).toList)
  }

  private def usePartialFunction(): Unit = {
    val seq = Seq(1, 8, 9)
    println("usePartialFunction ====>")
    println(seq.collect({ case k => if (k > 5) 0 else k * k }))
  }

  openSeqAsInfinityParams()
  convertMapToList()
  convertListToList()
  usePartialFunction()
}
