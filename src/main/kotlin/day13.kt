fun main(args: Array<String>) {

    val input = readInputFromFile("day13input.txt").split("\n")

    val layers = mutableMapOf<Int, Int>()
    val positions = mutableMapOf<Int, Int>()
    val directions = mutableMapOf<Int, Boolean>()
    val remember = mutableMapOf<Int, Int>()

    input.forEach {
        val layer = it.split(": ")
        layers.put(layer[0].toInt(), layer[1].toInt())
    }

    var delay = -1
    var packetPosition: Int

    do {
        delay++
        remember.clear()
        packetPosition = -1
        layers.forEach {
            positions.put(it.key, 0)
            directions.put(it.key, true)
        }

        for (pico in 0..layers.keys.max()!! + delay) {

            if (pico >= delay) packetPosition++

            // check if i've been catched
            if (packetPosition >= 0 && layers.containsKey(packetPosition) && positions[packetPosition]!! == 0) remember.put(packetPosition, layers[packetPosition]!!)

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

        var severity = 0
        remember.forEach { t, u -> severity += t * u }
        if (delay == 0) println("PART ONE: severity $severity")
    } while (remember.isNotEmpty())

    println("PART TWO: delay $delay")

}