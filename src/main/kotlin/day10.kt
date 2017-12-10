import java.util.stream.IntStream
import kotlin.streams.toList

fun main(args: Array<String>) {

    val weights = listOf(106, 16, 254, 226, 55, 2, 1, 166, 177, 247, 93, 0, 255, 228, 60, 36)
//    val weights = listOf(3, 4, 1, 5)
    var seq = IntStream.range(0, 256).toList()
//    var seq = IntStream.range(0, 5).toList()
    val seqStart = 0
    val seqEnd = seq.size
    var currIndex = 0
    var skipSize = 0
//    0 3 2 1 4

    fun reverseSublist(startIndex: Int, endIndex: Int) {
        if (startIndex > endIndex) {
            // 4
            val reverseFirst = seq.subList(seqStart, endIndex + 1).reversed().toMutableList()
            val maintainMiddle = seq.subList(endIndex + 1, startIndex).toMutableList()
            val reverseLast = seq.subList(startIndex, seqEnd).reversed().toMutableList()
            if (startIndex == endIndex + 1) {
                reverseFirst.add(0, reverseLast.last())
                reverseFirst.addAll(reverseLast.subList(0, reverseLast.size - 1))
                reverseLast.clear()
                reverseLast.addAll(reverseFirst)
            } else {
                maintainMiddle.addAll(reverseFirst)
                reverseLast.addAll(maintainMiddle)
            }
            seq = reverseLast
        } else {
            when (startIndex) {
                0 -> {
                    // 2
                    val reverseFirst = seq.subList(seqStart, endIndex + 1).reversed().toMutableList()
                    val maintainMiddleToEnd = seq.subList(endIndex + 1, seqEnd)
                    reverseFirst.addAll(maintainMiddleToEnd)
                    seq = reverseFirst
                }
                else -> {
                    // 1
                    val maintainFirst = seq.subList(seqStart, startIndex).toMutableList()
                    val reverseMiddle = seq.subList(startIndex, endIndex + 1).reversed().toMutableList()
                    val maintainLast = seq.subList(endIndex + 1, seqEnd)
                    reverseMiddle.addAll(maintainLast)
                    maintainFirst.addAll(reverseMiddle)
                    seq = maintainFirst
                }
            }
        }
    }

    weights.forEach {
        println(seq)
        val endIndex = if (currIndex + it > seqEnd) seqEnd - it else currIndex + it - 1
        reverseSublist(currIndex, endIndex)
        currIndex = (currIndex + it + skipSize) % seqEnd
        skipSize++
    }
    println(seq)

}