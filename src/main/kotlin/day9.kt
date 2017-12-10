fun main(args: Array<String>) {

    var chars = readInputFromFile("day9input.txt").split("")

    chars = chars.subList(1, chars.size - 1)

    val opens = mutableListOf<Int>()
    var canceledGarbage = 0

    fun findResult() {

        var currLevelScore = 1
        var insideGarbage = false
        var nextIsInvalid = false

        // It would have been "better" if a normal for loop was used: that way I could've removed all the "nextIsInvalid = false",
        // cause it would've been enough to increment the "i" of the loop and just skip to the next next element

        chars.forEach {
            when (it) {
                "!" -> {
                    if (insideGarbage) nextIsInvalid = !nextIsInvalid
                }
                "{" -> {
                    if (!nextIsInvalid) {
                        if (!insideGarbage) opens.add(currLevelScore++)
                        else canceledGarbage++
                    }
                    nextIsInvalid = false
                }
                "}" -> {
                    if (!nextIsInvalid) {
                        if (!insideGarbage) currLevelScore--
                        else canceledGarbage++
                    }
                    nextIsInvalid = false
                }
                "<" -> {
                    if (!nextIsInvalid) {
                        if (!insideGarbage) insideGarbage = true
                        else canceledGarbage++
                    }
                    nextIsInvalid = false
                }
                ">" -> {
                    if (!nextIsInvalid) if (insideGarbage) insideGarbage = false
                    nextIsInvalid = false
                }
                else -> {
                    if (!nextIsInvalid && insideGarbage) canceledGarbage++
                    nextIsInvalid = false
                }
            }
        }
    }

    findResult()
    println("The total score is ${opens.sum()}")
    println("The total number of canceled items is $canceledGarbage")
}