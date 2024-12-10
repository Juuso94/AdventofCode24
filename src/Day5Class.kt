import java.io.File

class Day5Class(override val dataFile: File): SolutionInterface {

    override fun solveFirst(): String {
        val data = parseFile()
        val ruleMap = data.first
        val pageLists = data.second
        val filteredLists = pageLists.filter { list ->
            checkList(ruleMap, list)
        }
        return filteredLists.map { list ->
            list.elementAt(list.size/2)
        }.reduce { acc, i ->  acc + i}.toString()
    }

    override fun solveSecond(): String {
        val data = parseFile()
        val ruleMap = data.first
        val pageLists = data.second
        println(ruleMap)
        val filteredLists = pageLists.filter { list ->
            ! checkList(ruleMap, list)
        }
        val fixedLists = filteredLists.map { fixList(ruleMap, it) }
        return fixedLists.map { list ->
            list.elementAt(list.size/2)
        }.reduce { acc, i ->  acc + i}.toString()
    }

    fun fixList(ruleMap:Map<Int, Set<Int>>, checkList:List<Int>): List<Int> {

        var fixedList = mutableListOf<Int>()

        for (i in checkList.indices) {
            val element = checkList[i]
            val values = ruleMap[element] ?: setOf()
            val prevElements = fixedList
            if(prevElements.any { it in values }) {

                val some = prevElements.indexOfFirst { it in values }
                fixedList.add(some, element)
            }
            else fixedList.add(element)
        }
        return fixedList

    }

    fun checkList(ruleMap:Map<Int, Set<Int>>, checkList:List<Int>): Boolean {

        for (i in checkList.indices) {
            val element = checkList[i]
            val values = ruleMap[element] ?: setOf()
            val prevElements = checkList.subList(0, i + 1)
            if(prevElements.any { it in values }) return false
        }
        return true
    }

    private fun parseFile(): Pair<Map<Int, Set<Int>>, List<List<Int>>>{
        val lines = dataFile.readLines()

        val breakPoint = lines.indexOf("")

        val ruleMap = lines.slice(0..< breakPoint).map { rule ->
            val rulePair = rule.split("|")
            Pair(rulePair[0].toInt(), rulePair[1].toInt())
        }
        .groupBy({it.first}, {it.second})
        .mapValues { (_, v) -> v.toSet() }

        val pageNumberLists = lines.slice(breakPoint+1..< lines.size).map {
            list -> list.split(",").map { it.toInt() }
        }
        return Pair (ruleMap, pageNumberLists)
    }
}