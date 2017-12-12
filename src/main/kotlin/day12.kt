fun main(args: Array<String>) {

    val pipes = readInputFromFile("day12input.txt").split("\n")

    val hugeDic = mutableMapOf<Int, List<Int>>()
    val targetGroup = mutableListOf<MutableList<Int>>()
    val visited = mutableListOf<Int>()
    val temp = mutableListOf<Int>()

    pipes.forEach {
        val keyValue = it.split(" <-> ")
        val key = keyValue[0].toInt()
        val value = keyValue[1].split(", ").map { it.toInt() }
        hugeDic.put(key, value)
    }

    /**
     * PART ONE
     */
    fun dfs(currNode: Int) {
        if (visited.containsAll(hugeDic[currNode]!!)) {
            return
        }

        temp.add(currNode)
        temp.addAll(hugeDic[currNode]!!)
        visited.add(currNode)

        hugeDic[currNode]!!.forEach {
            if (!visited.contains(it)) {
                dfs(it)
            }
        }
    }

    hugeDic.keys.forEach {
        dfs(it)
        targetGroup.add(temp.distinct().sorted().toMutableList())
        temp.clear()
        visited.clear()
    }

    println("There are ${targetGroup[0].count()} elements connected to 0")

    /**
     * PART TWO
     */
    println(targetGroup.distinct().count())
}