fun main(args: Array<String>) {

    val input = readInputFromFile("day13input.txt").split("\n")

    val layers = mutableMapOf<Int, Int>()
    val positions = mutableMapOf<Int, Int>()
    val directions = mutableMapOf<Int, Boolean>()
    val remember = mutableMapOf<Int, Int>()

    input.forEach {
        val layer = it.split(": ")
        layers.put(layer[0].toInt(), layer[1].toInt())
        positions.put(layer[0].toInt(), 0)
        directions.put(layer[0].toInt(), true)
    }

    /**
     * PART ONE
     */
    for (packetPosition in 0..layers.keys.max()!!) {

        if (layers.containsKey(packetPosition) && positions[packetPosition]!! == 0) remember.put(packetPosition, layers[packetPosition]!!)

        for (layer in layers.keys) {
            val direction =
                    when {
                        positions[layer]!! == 0 -> true
                        positions[layer]!! == layers[layer]!! - 1 -> false
                        else -> directions[layer]!!
                    }
            directions.put(layer, direction)
            val step = if (directions[layer]!!) 1 else -1
            positions.put(layer, positions[layer]!! + step)
        }
    }

    var sum = 0
    remember.forEach { t, u -> sum += t * u }
    println(sum)

}