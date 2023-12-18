package scala

import scala.collection.mutable

object CollectionsOfScala extends App {

  def showcaseTreeMap(): Unit = {
    val tm = mutable.TreeMap[Int, String](Array((4, "a"), (7, "df")): _*)
    tm(1) = "a"
    tm(5) = "b"
    tm(3) = "c"
    println(tm.from(2).until(5))
  }

  def twoSumLessThanK(nums: Array[Int], k: Int): Int = {
    import scala.collection.mutable
    val ts = mutable.TreeSet[Int](nums: _*)
    var res = -1
    for (i <- ts) {
      val t = ts.from(i + 1).until(k)
      if (t.nonEmpty) {
        res = Math.max(res, t.lastKey)
      }
    }
    res
  }

  showcaseTreeMap()

  println(twoSumLessThanK(Array(10,20,30), 15))
}
