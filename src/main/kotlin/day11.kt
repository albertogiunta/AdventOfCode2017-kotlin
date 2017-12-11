fun main(args: Array<String>) {

    val directions = readInputFromFile("day11input.txt").split(",")

    var n = directions.count { it == "n" }
    var s = directions.count { it == "s" }
    var se = directions.count { it == "se" }
    var nw = directions.count { it == "nw" }
    var ne = directions.count { it == "ne" }
    var sw = directions.count { it == "sw" }

    fun compareAndAssignWithZero(a: Int, b: Int): Pair<Int, Int> = if (a > b) Pair(a - b, 0) else Pair(0, b - a)

    val (tempN, tempS) = compareAndAssignWithZero(n, s)
    n = tempN
    s = tempS

    val (tempNE, tempSW) = compareAndAssignWithZero(ne, sw)
    ne = tempNE
    sw = tempSW

    val (tempNW, tempSE) = compareAndAssignWithZero(nw, se)
    nw = tempNW
    se = tempSE

    if (nw > ne) nw -= ne else ne -= nw
    if (sw > se) sw -= se else se -= sw

    val steps = n + nw + ne + s + sw + se

    println("The least amount of steps required is $steps")

}