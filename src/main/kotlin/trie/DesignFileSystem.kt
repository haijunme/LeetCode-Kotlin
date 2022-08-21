package trie

class DesignFileSystem {
    class FileSystem {
        class PathPart {
            var value: Int? = null
            var next: HashMap<String, PathPart> = HashMap()
        }

        private var root = PathPart()

        private fun traversal(path: String, doCreation: Boolean): PathPart? {
            val parts = path.split("/")
            var root = this.root
            var index = 1
            while (index < parts.size) {
                val current = parts[index]
                if (!root.next.containsKey(current)) {
                    if (doCreation && index == parts.size - 1)
                        root.next[current] = PathPart()
                    else
                        return null
                }
                root = root.next[current]!!
                index++
            }
            return root
        }

        fun createPath(path: String, value: Int): Boolean {
            val end = traversal(path, true)
            if (end == null || end.value != null) return false
            end.value = value
            return true
        }

        fun get(path: String): Int {
            val end = traversal(path, false)
            if (end?.value == null) return -1
            return end.value!!;
        }
    }
}

fun main() {
    val fileSystem = DesignFileSystem.FileSystem()
    println(fileSystem.createPath("/leet", 1)) // return true
    println(fileSystem.createPath("/leet/code", 2)) // return true
    println(fileSystem.get("/leet/code")) // return 2
    println(fileSystem.createPath("/c/d", 1)) // return false because the parent path "/c" doesn't exist.
    println(fileSystem.get("/c")) // return -1 because this path doesn't exist.
}