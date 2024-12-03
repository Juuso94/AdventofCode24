import java.io.File
import kotlin.math.abs

class Day1Class(override val dataFile: File): SolutionInterface {

    override fun solveFirst(): String {
        val values = parseFile()

        return calculateDistance(values).toString()
    }

    override fun solveSecond(): String {
        val values = parseFile()

        return calculateSimilarity(values).toString()
    }

    private fun calculateDistance(values:  Pair<List<Int>, List<Int>>): Int {
        return  values.first.zip(values.second)
            .map { valuePair -> abs(valuePair.first - valuePair.second) }.reduce { acc, i -> acc + i }
    }
    private fun calculateSimilarity(values:  Pair<List<Int>, List<Int>>): Int {
        val valueMap = HashMap<Int, Int> ()

        for (value in values.first) {
            valueMap[value] = 0
        }
        values.second.forEach { value -> if(valueMap.containsKey(value)){ valueMap[value] = valueMap[value]!! + 1} }

        return valueMap.map { key -> (key.key * key.value) }.reduce { acc, i ->  acc + i}
    }

    private fun parseFile() : Pair<List<Int>, List<Int>> {
        val data = dataFile.readLines().map {
                line -> line.split("   ")
        }
        val list1 = data.map { list -> list[0].toInt() }.sorted()
        val list2 = data.map { list -> list[1].toInt() }.sorted()

        return Pair(list1, list2)

    }
}
