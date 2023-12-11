package scala

object PartialFx extends App {

  private val pf: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {

    def isDefinedAt(q: Int): Boolean = q > 4

    def apply(q: Int): Int = 12 * q
  }

  private val pf1:PartialFunction[Int, Int] = { case k => if (k > 5) 0 else k * k }

  println(pf(0))

  val a = pf andThen (k => k * k)
  println(a(4))



  val b = pf.orElse(pf1)

  b(7)
}
