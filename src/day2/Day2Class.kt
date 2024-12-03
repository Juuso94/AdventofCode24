package day2

import SolutionInterface
import java.io.File
import kotlin.math.abs

class Day2Class(override val dataFile: File): SolutionInterface {

    override fun solveFirst(): String {
        val values = parseFile()
        val solution = calculateSafeLevels(values, false)

        return solution.toString()
    }

    override fun solveSecond(): String {
        val values = parseFile()
        val solution = calculateSafeLevels(values, true)

        return solution.toString()
    }

    private fun parseFile() : List<List<Int>> {
        val data = dataFile.readLines().map {
                line -> line.split(" ").map { value -> value.toInt() }
        }

        return data
    }


    private fun calculateSafeLevels(data: List<List<Int>>, errorFlag: Boolean): Int {

        var counter = 0
        data.forEach { valueList ->
            if (checkList(valueList)){
                counter++
            }
            else if (errorFlag) {
                for (i in valueList.indices) {
                    val mutableList = valueList.toMutableList()
                    mutableList.removeAt(i)
                    if(checkList(mutableList)) {
                        counter++
                        break
                    }
                }
            }

        }
        return counter
    }

    private fun checkList(valueList: List<Int>): Boolean {
        var loopFlag = true

        for (i in valueList.indices) {
            if (i == 0) {
                continue
            }
            val level = valueList[i - 1] - valueList[i]
            if (i == 1) {
                if (level > 0) {
                    loopFlag = true
                } else if (level < 0) {
                    loopFlag = false
                }
            }
            if (!(loopFlag && level > 0) && !(!loopFlag && level < 0)) {
                return false

            }
            if (abs(level) !in 1..3) {
                return false
            }

        }
        return true
    }

}