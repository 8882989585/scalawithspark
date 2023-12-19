package leetcode


object Question146 extends App {

  class LRUCache(_capacity: Int) {

    import scala.collection.mutable

    class Node(var key: Int, var value: Int, var previous: Node, var next: Node)

    val map: mutable.Map[Int, Node] = mutable.HashMap[Int, Node]()
    var last: Node = _
    var first: Node = _
    var capacity: Int = _capacity

    def addToLast(node: Node, fresh: Boolean): Unit = {
      if (_capacity == 1) {
        first = node
        last = node
      } else if (node == last) {
      } else if (node == first) {
        first.next.previous = null
        first = first.next
        node.previous = last
        node.next = null
        last.next = node
        last = node
      } else {
        if (!fresh) {
          node.previous.next = node.next
          node.next.previous = node.previous
        }
        node.previous = last
        node.next = null
        if (last != null) last.next = node
        last = node
        if (first == null) first = node
      }
    }

    def get(key: Int): Int = {
      val t = map.get(key)
      if (t.nonEmpty) {
        addToLast(t.get, fresh = false)
        return t.get.value
      }
      -1
    }

    def put(key: Int, value: Int): Unit = {
      val t = map.get(key)
      var nt: Node = null
      if (t.nonEmpty) {
        nt = t.get
        nt.value = value
        addToLast(nt, fresh = false)
      } else {
        if (capacity == 0) {
          map.remove(first.key)
          first = first.next
          if (first == null) {
            last = null
          }
          capacity = capacity + 1
        }
        nt = new Node(key, value, null, null)
        addToLast(nt, fresh = true)
        capacity = capacity - 1
      }
      map(key) = nt
    }

  }

  val lRUCache = new LRUCache(2);
  lRUCache.put(1, 1)
  lRUCache.put(2, 2) // cache is {1=1, 2=2}
  println(lRUCache.get(1)) // return 1
  lRUCache.put(3, 3) // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
  println(lRUCache.get(2)) // returns -1 (not found)
  lRUCache.put(4, 4) // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
  println(lRUCache.get(1)) // return -1 (not found)
  println(lRUCache.get(3))
  println(lRUCache.get(4))// return 3
  println("end")
}
