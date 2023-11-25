package leetcode

import scala.collection.mutable

/**
 * https://leetcode.com/problems/count-nice-pairs-in-an-array/
 * https://leetcode.com/problems/count-nice-pairs-in-an-array/solutions/4312501/video-give-me-6-minutes-hashmap-how-we-think-about-a-solution/
 */
object Question1814 extends App {
  private def countNicePairs(nums: Array[Int]): Int = {
    var result = 0
    val mod = 1000000007
    val reverseMap: mutable.HashMap[Int, Int] = new mutable.HashMap[Int, Int]()
    for (num <- nums) {
      reverseMap.update(
        num - num.toString.reverse.toInt,
        reverseMap.getOrElse(num - num.toString.reverse.toInt, 0) + 1)
      result = (result + reverseMap(num - num.toString.reverse.toInt) - 1) % mod
    }
    result
  }

  println(countNicePairs(Array(13, 10, 35, 24, 76)))
}
