fun main(args: Array<String>) {

    val programs = "abcdefghijklmnop".split("").toMutableList()
    programs.removeAt(0)
    programs.removeAt(programs.size - 1)
    val repeatedSequence = mutableListOf(programs.joinToString(""))
    val moves = readInputFromFile("day16input.txt").split(",").toMutableList()

    fun swap(abIndex: Pair<Int, Int>) {
        val temp = programs[abIndex.first]
        programs[abIndex.first] = programs[abIndex.second]
        programs[abIndex.second] = temp
    }

    fun moveFactory(moveString: String) {
        when (moveString[0].toString()) {
            "s" -> {
                val dim = moveString.substring(1).toInt()
                programs.addAll(0, programs.subList(programs.size - dim, programs.size))
                repeat(dim) { programs.removeAt(programs.size - 1) }
            }
            "x" -> swap(moveString.substring(1).split("/").map(String::toInt).zipWithNext().first())
            "p" -> swap(moveString.substring(1).split("/").map(programs::indexOf).zipWithNext().first())
        }
    }

    for (i in 0..1000000000) {
        moves.forEach(::moveFactory)
        val currentString = programs.joinToString("")
        if (i == 0) println("PART ONE: $currentString")
        if (repeatedSequence.contains(currentString)) {
            println("PART TWO: ${repeatedSequence[1000000000 % (i + 1)]}")
            break
        } else repeatedSequence.add(currentString)
    }
}