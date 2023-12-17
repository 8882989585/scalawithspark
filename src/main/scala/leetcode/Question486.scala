package leetcode

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Question486 extends App {

  private case class Node(head: Int, tail: Int, turn: Boolean)

  private def recursiveSolution(nums: Array[Int], head: Int, tail: Int, turn: Boolean): Int = {
    if (head - tail == 0) {
      return if (turn) nums(head) else -1 * nums(head)
    }
    if (turn) {
      Math.max(
        nums(head) + recursiveSolution(nums, head + 1, tail, turn = false),
        nums(tail) + recursiveSolution(nums, head, tail - 1, turn = false))
    } else {
      Math.min(
        (-1 * nums(head)) + recursiveSolution(nums, head + 1, tail, turn = true),
        (-1 * nums(tail)) + recursiveSolution(nums, head, tail - 1, turn = true))
    }
  }

  private def iterativeSolution(nums: Array[Int]): Int = {
    val lb = new ListBuffer[Node]
    val hm = mutable.HashMap[(Int, Int), Int]()
    for (i <- nums.indices) {
      lb.append(Node(i, i, !(nums.length % 2 == 1)))
      hm((i, i)) = if (nums.length % 2 == 1) nums(i) else -1 * nums(i)
    }
    while (lb.nonEmpty) {
      val t1 = lb.remove(0)
      val nh = t1.head - 1
      val nt = t1.tail + 1
      if (nh > -1) {
        val cs = hm((t1.head, t1.tail)) + (if (t1.turn) nums(nh) else -1 * nums(nh))
        if (!hm.contains((nh, t1.tail))) {
          hm((nh, t1.tail)) = cs
          lb.append(Node(nh, t1.tail, !t1.turn))
        } else {
          hm((nh, t1.tail)) = if (t1.turn) Math.max(hm((nh, t1.tail)), cs) else Math.min(hm((nh, t1.tail)), cs)
        }
      }
      if (nt < nums.length) {
        val cs = hm((t1.head, t1.tail)) + (if (t1.turn) nums(nt) else -1 * nums(nt))
        if (!hm.contains((t1.head, nt))) {
          hm((t1.head, nt)) = cs
          lb.append(Node(t1.head, nt, !t1.turn))
        } else {
          hm((t1.head, nt)) = if (t1.turn) Math.max(hm((t1.head, nt)), cs) else Math.min(hm((t1.head, nt)), cs)
        }
      }
    }
    hm((0, nums.length - 1))
  }

  private def predictTheWinner(nums: Array[Int]): Boolean = {
    //    recursiveSolution(nums, 0, nums.length - 1, turn = true) >= 0
    iterativeSolution(nums) >= 0
  }

//  println(predictTheWinner(Array(1, 5, 2)))
//  println(predictTheWinner(Array(1, 5, 233, 7)))
//  println(predictTheWinner(Array(2, 4, 55, 6, 8)))
  println(predictTheWinner(Array(1000,999,999,1000,555,400)))

}
