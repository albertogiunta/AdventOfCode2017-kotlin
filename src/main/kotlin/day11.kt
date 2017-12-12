import kotlin.math.abs

fun main(args: Array<String>) {

    val directions = readInputFromFile("day11input.txt").split(",")

    var dist = mutableListOf<Int>()
    var x = 0
    var y = 0
    var z = 0

    directions.forEach {
        when (it) {
            "n" -> {
                y++; z--
            }
            "s" -> {
                z++; y--
            }
            "ne" -> {
                z--; x++
            }
            "nw" -> {
                y++; x--
            }
            "se" -> {
                x++; y--
            }
            "sw" -> {
                x--; z++
            }
        }
        dist.add((abs(x) + abs(y) + abs(z)) / 2)
    }

    val steps = (abs(x) + abs(y) + abs(z)) / 2

    println("The least amount of steps required is $steps")
    println("The furthest of steps is ${dist.max()}")

}