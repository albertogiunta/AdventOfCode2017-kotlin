fun main(args: Array<String>) {

    val currentConf = mutableListOf(0, 5, 10, 0, 11, 14, 13, 4, 11, 8, 8, 7, 1, 4, 12, 11)
    val prevConf = mutableListOf<List<Int>>()
    var redistributionCycles = 0
    var loops = 0
    var wasOnceFound = false

    while (!prevConf.contains(currentConf)) {
        // increment counters for either PART ONE or PART TWO
        if (!wasOnceFound) redistributionCycles++ else loops++

        // adding current unseen configuration to chronology of configurations
        val tempList = mutableListOf<Int>()
        tempList.addAll(currentConf)
        prevConf.add(tempList)

        // taking current max and its index
        var nToRedistribute = currentConf.max() ?: 0
        var iOfMax = currentConf.indexOf(nToRedistribute)
        currentConf[iOfMax] = 0

        // redistributing max in a circular way
        while (nToRedistribute != 0) {
            if (iOfMax + 1 >= currentConf.size) iOfMax = -1
            iOfMax++
            currentConf[iOfMax]++
            nToRedistribute--
        }

        // clear the state for PART TWO in order to find how big is a loop before you get twice the same configuration
        if (!wasOnceFound && prevConf.contains(currentConf)) {
            prevConf.clear()
            wasOnceFound = true
        }
    }

    println("$redistributionCycles passages were needed to get a twin configuration for the first time")
    println("$loops iterations were needed to get that configuration again")
}

