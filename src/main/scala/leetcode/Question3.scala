package leetcode

object Question3 extends App {
  def lengthOfLongestSubstring(s: String): Int = {
    import scala.collection.mutable.HashMap
    val t: Array[Char] = s.toCharArray
    var res = 0
    var itr = -1
    val map = HashMap[Char, Int]()
    for (i <- t.indices) {
      val v = map.getOrElse(t(i), -1)
      res = Math.max(res, i - Math.max(v, itr))
      map(t(i)) = i
      if (v != -1) itr = Math.max(v, itr)
    }
    res
  }

  println(lengthOfLongestSubstring("aabaab!bb"))
  println(lengthOfLongestSubstring("bbbbb"))
  println(lengthOfLongestSubstring("pwwkew"))
}
