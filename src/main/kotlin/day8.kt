fun main(args: Array<String>) {

    val lines = readInputFromFile("day8input.txt").split("\n")
    val expressions = mutableListOf<Expression>()
    val registers = mutableMapOf<String, Int>()
    var allTimeMax = 0

    lines.forEach { line ->
        run {
            val elements = line.split(" ")
            registers.put(elements[0], 0)
            expressions.add(Expression(firstOp = elements[0],
                    opSym = transformOperation(elements[1]),
                    secOp = elements[2].toInt(),
                    firstCondOp = elements[4],
                    condSym = transformCondition(elements[5]),
                    secondCondOp = elements[6].toInt()))
        }
    }

    expressions.forEach { expr ->
        run {
            with(expr) {
                if (condSym.applyCondition(registers[firstCondOp]!!, secondCondOp)) {
                    registers.put(firstOp, opSym.applyOperation(registers[firstOp]!!, secOp))
                    if (registers[firstOp]!! > allTimeMax) allTimeMax = registers[firstOp]!!
                }
            }
        }
    }

    val maxValue = registers.values.max()
    val maxValueRegister = registers.filter { it.value == maxValue }

    println("The max value in any register is $maxValue in register ${maxValueRegister.keys}")
    println("The all time max is $allTimeMax")
}

enum class OPERATION {
    INC {
        override fun applyOperation(firstOp: Int, secOp: Int): Int = firstOp + secOp
    },
    DEC {
        override fun applyOperation(firstOp: Int, secOp: Int): Int = firstOp - secOp
    };

    abstract fun applyOperation(firstOp: Int, secOp: Int): Int

}

enum class CONDITION {

    EQUALS {
        override fun applyCondition(leftOp: Int, rightOp: Int): Boolean = leftOp == rightOp
    },
    DIFFERENT {
        override fun applyCondition(leftOp: Int, rightOp: Int): Boolean = leftOp != rightOp
    },
    GREATER_THAN {
        override fun applyCondition(leftOp: Int, rightOp: Int): Boolean = leftOp > rightOp
    },
    LESS_THAN {
        override fun applyCondition(leftOp: Int, rightOp: Int): Boolean = leftOp < rightOp
    },
    EQUAL_OR_GREATER_THAN {
        override fun applyCondition(leftOp: Int, rightOp: Int): Boolean = leftOp >= rightOp
    },
    EQUAL_OR_LESS_THAN {
        override fun applyCondition(leftOp: Int, rightOp: Int): Boolean = leftOp <= rightOp
    };

    abstract fun applyCondition(leftOp: Int, rightOp: Int): Boolean
}

fun transformCondition(cond: String): CONDITION {
    return when (cond) {
        "==" -> CONDITION.EQUALS
        "!=" -> CONDITION.DIFFERENT
        ">" -> CONDITION.GREATER_THAN
        "<" -> CONDITION.LESS_THAN
        ">=" -> CONDITION.EQUAL_OR_GREATER_THAN
        else -> CONDITION.EQUAL_OR_LESS_THAN
    }
}

fun transformOperation(op: String): OPERATION {
    return when (op) {
        "inc" -> OPERATION.INC
        else -> OPERATION.DEC
    }
}

data class Expression(val firstOp: String, val opSym: OPERATION, val secOp: Int, val firstCondOp: String, val condSym: CONDITION, val secondCondOp: Int)