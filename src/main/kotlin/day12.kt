fun main(args: Array<String>) {

    val pipes = readInputFromFile("day12input.txt").split("\n")

    val hugeDic = mutableMapOf<Int, List<Int>>()

    val target = 0
    var targetGroup = mutableListOf<Int>()
    val visited = mutableListOf<Int>()

    pipes.forEach {
        val keyValue = it.split(" <-> ")
        val key = keyValue[0].toInt()
        val value = keyValue[1].split(", ").map { it.toInt() }
        hugeDic.put(key, value)
    }

    fun dfs(currNode: Int) {
        if (visited.containsAll(hugeDic[currNode]!!)) {
            targetGroup = targetGroup.distinct().toMutableList()
            return
        }

        targetGroup.add(currNode)
        targetGroup.addAll(hugeDic[currNode]!!)
        visited.add(currNode)

        hugeDic[currNode]!!.forEach {
            if (!visited.contains(it)) {
                dfs(it)
            }
        }
    }

    dfs(target)

    println("There are ${targetGroup.count()} elements connected to $target")
}