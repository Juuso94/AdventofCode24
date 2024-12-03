import java.io.File


class Day3Class(override val dataFile: File): SolutionInterface {

    private val mulRegex = """mul\(\d+,\d+\)""".toRegex()
    private val doRegex = """do\(\)""".toRegex()
    private val dontRegex = """don't\(\)""".toRegex()

    private fun readFileToString(): String {
        return dataFile.readText()
    }
    private fun parseRegex(matchingString: String): Int {
        return matchingString.subSequence(4,matchingString.length - 1).split(',').map { value ->
            value.toInt()}.reduce { acc, i ->  acc * i}
    }

    private fun findSolution(assigmentString: String): String {
        val regexMatch = mulRegex.findAll(assigmentString)

        return regexMatch.map {
                stringMatch -> parseRegex(stringMatch.value)
        }.reduce { acc, i ->  acc + i}.toString()
    }

    private fun findSolution(assigmentString: String, unallowedSegments: MutableList<Pair<Int, Int>>): String {
        val regexMatch = mulRegex.findAll(assigmentString)

        return regexMatch.map {
            stringMatch -> parseRegex(stringMatch.value)
        }.reduce { acc, i ->  acc + i}.toString()
    }

    override fun solveFirst(): String {

        val assignmentString = readFileToString()
        return findSolution(assignmentString)
    }

    override fun solveSecond(): String {

        var assignmentString = readFileToString()
        val unAllowedSegments: MutableList<Pair<Int, Int>> = mutableListOf()

        val doMatches = doRegex.findAll(assignmentString)
        val dontMatches = dontRegex.findAll(assignmentString)

        var stringBreakPoint = 0

        dontMatches.forEach { dontMatch ->
            val startIndex = dontMatch.range.first
            if (startIndex > stringBreakPoint){
                for (doMatch in doMatches) {
                    val endIndex = doMatch.range.last
                    if(endIndex > startIndex) {
                        unAllowedSegments.add(Pair(startIndex, endIndex))
                        stringBreakPoint = endIndex
                        break
                    }
                }
            }
        }
        val lastDontMatch = dontMatches.first { match -> match.range.first >  doMatches.last().range.first}

        unAllowedSegments.add(Pair(lastDontMatch.range.first, assignmentString.length))

        unAllowedSegments.asReversed().forEach{
            segment ->
            assignmentString = assignmentString.removeRange(segment.first, segment.second)
        }
        return findSolution(assignmentString)
    }
}