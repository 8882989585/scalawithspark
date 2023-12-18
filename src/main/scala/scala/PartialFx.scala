package scala

object PartialFx extends App {

  private val pf: PartialFunction[(Int, Int), Int] = new PartialFunction[(Int, Int), Int] {
    override def isDefinedAt(x: (Int, Int)): Boolean = x._1 > x._2

    override def apply(v1: (Int, Int)): Int = v1._1 * v1._2
  }

  private val pf1: PartialFunction[(Int, Int), Int] = {
    case (k, v) => if (k > v) 0 else k * k
  }

  println(pf((1, 2)))

  val a = pf andThen (k => k * k)
  println(a(4, 2))


  val b = pf.orElse(pf1)

  b((7,1))
}
