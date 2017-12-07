import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {

    // read from file and create map holding the data
    val input = readInputFromFile()
    val map = splitInMap(input)

    /**
     * PART ONE
     */
    val mapWithoutLeaves = map.filter { it.value.isNotEmpty() }
    val root = mapWithoutLeaves.filterKeys { k -> !mapWithoutLeaves.values.flatten().contains(k.first) }.keys.first()

    /**
     * PART TWO
     */

    // setup data structures
    val mapNameChildren = mutableMapOf<String, List<String>>()
    val mapNameWeight = mutableMapOf<String, Int>()
    val mapNameTotWeight = mutableMapOf<String, Int>()
    map.forEach { el ->
        run {
            mapNameChildren.put(el.key.first, el.value)
            mapNameWeight.put(el.key.first, el.key.second)
        }
    }
    mapNameTotWeight.putAll(mapNameWeight)

    // fill the map that holds the total weight for each node up to that node (comprehensive of the weights of its children, recursively)
    fun dfs(currName: String) {
        if (mapNameChildren[currName]!!.isEmpty()) {
            return
        } else {
            mapNameChildren[currName]!!.forEach {
                dfs(it)
                val sum = mapNameTotWeight[it]!! + mapNameTotWeight[currName]!!
                mapNameTotWeight.put(currName, sum)
            }
        }
    }

    // find the result by following the unbalanced path
    fun findUnbalancedPath(currName: String) {
        // base case
        if (mapNameChildren[currName]!!.isEmpty()) return

        mapNameChildren[currName]!!.forEach {
            // create a list of weights of the children of this current node, we will need it to find which weight is different (only 1 can be)
            val currentNodeChildrenNameWeights = mutableMapOf<String, Int>()
            mapNameChildren[it]!!.forEach { currentNodeChildrenNameWeights.put(it, mapNameTotWeight[it]!!) }

            // group together all those weights that appear only once (only 1 can be unique)
            val wrongWeightList = currentNodeChildrenNameWeights.values.groupingBy { it }.eachCount().filter { it.value == 1 }

            // if we found a layer of nodes with different total weights, then we should explore
            if (wrongWeightList.isNotEmpty()) {

                // take the different weight, we will use it to find the name of the unbalanced process
                val differentWeight = wrongWeightList.keys.first()
                // find the name of the unbalanced process
                val unbalancedProcessName: String = mapNameChildren[it]!!.first { mapNameTotWeight[it] == differentWeight }

                // group together all those weights that appear more than once (only 1 can be unique) so that we find what should've been the right total weight
                val rightWeight = currentNodeChildrenNameWeights.values.groupingBy { it }.eachCount().filter { it.value > 1 }.keys.first()

                // find the total weight of the children of the unbalanced node
                val unbalancedNodeChildrenWeight = mapNameChildren[unbalancedProcessName]!!.map { mapNameTotWeight[it]!! }.sum()

                // calculate what should've been the correct weight
                val shouldWeight = rightWeight - unbalancedNodeChildrenWeight

                println("$unbalancedProcessName weights ${mapNameTotWeight[unbalancedProcessName]} instead of $shouldWeight")

                return
            }

            findUnbalancedPath(it)
        }
    }

    dfs(root.first)
    findUnbalancedPath(root.first)
}


fun readInputFromFile(): String {
    val inputStream: InputStream = File("src/inputs/day7input.txt").inputStream()
    return inputStream.bufferedReader().use { it.readText() }
}

fun splitInMap(input: String): MutableMap<Pair<String, Int>, List<String>> {

    val map = mutableMapOf<Pair<String, Int>, List<String>>()
    val lines = input.split("\n")

    lines.forEach { e ->
        run {
            var el = e
            if (!el.contains(" -> ")) el += " -> "
            val (head, tail) = el.split(" -> ")
            val (name, weightStr) = head.split(" (")
            val weightInt = weightStr.substring(0, weightStr.length - 1).toInt()
            var sublist = tail.split(", ")
            if (sublist[0] == "") sublist = emptyList()
            map.put(Pair(name, weightInt), sublist)
        }
    }
    return map
}
