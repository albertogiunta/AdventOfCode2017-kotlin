fun main(args: Array<String>) {

    val n = 277678

    /**
     * PART ONE
     * How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?
     */

    var nearestSquareOfOdd = 1

    while(true) {
        if (Math.pow(nearestSquareOfOdd+2.0, 2.0) > n) break
        nearestSquareOfOdd += 2
    }

    val square = nearestSquareOfOdd*nearestSquareOfOdd
    val edgeLength  = if (square == n) nearestSquareOfOdd else nearestSquareOfOdd + 2
    val difference = n - square
    var prevEdgeQnt: Int = difference / edgeLength
    if (difference == square) prevEdgeQnt += 1
    val middle: Int = Math.floor(edgeLength  / 2.0).toInt()

    val distToMiddleOfEdge = Math.abs(n  - (square + edgeLength * prevEdgeQnt - prevEdgeQnt + middle))

    println("Distance to the middle is: " + middle + distToMiddleOfEdge)

    /**
     * PART TWO
     * The answer was part of a oeis.org Integer series. Found it there with no code.
     */

}