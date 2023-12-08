//https://leetcode.com/problems/maximum-average-subtree/

package leetcode

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Question1120 extends App {
  class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
    var value: Int = _value
    var left: TreeNode = _left
    var right: TreeNode = _right
  }

  def maximumAverageSubtree(root: TreeNode): Double = {
    val lb = new ListBuffer[TreeNode]()
    val vs = new mutable.HashMap[TreeNode, (Double, Double)]()
    lb.append(root)
    while (lb.nonEmpty) {
      val itrNode = lb.last
      var visited = false
      if (itrNode.right != null && !vs.contains(itrNode.right)) {
        lb.append(itrNode.right)
        visited = true
      }
      if (itrNode.left != null && !vs.contains(itrNode.left)) {
        lb.append(itrNode.left)
        visited = true
      }
      if (!visited) {
        var count = 1.0
        var sum: Double = itrNode.value
        if (itrNode.left != null) {
          count = count + vs(itrNode.left)._1
          sum = sum + vs(itrNode.left)._2
        }
        if (itrNode.right != null) {
          count = count + vs(itrNode.right)._1
          sum = sum + vs(itrNode.right)._2
        }
        vs.put(itrNode, (count, sum))
        lb.remove(lb.length - 1)
      }
    }
    val res = vs.values.toArray.reduce((a, b) => {
      if (a._2 / a._1 > b._2 / b._1) a else b
    })
    res._2 / res._1
  }

  println(maximumAverageSubtree(
    new TreeNode(1,
      new TreeNode(2,
        new TreeNode(3),
        new TreeNode(4,
          new TreeNode(5),
          new TreeNode(6,
            new TreeNode(7,
              new TreeNode(8))))),
      new TreeNode(9,
        new TreeNode(10,
          null,
          new TreeNode(11)))))
  )

}
