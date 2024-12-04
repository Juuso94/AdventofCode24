import java.io.File

class Day4Class(override val dataFile: File): SolutionInterface {

    private val xmasRegex = """XMAS""".toRegex()
    private val samxRegex = """SAMX""".toRegex()

    override fun solveFirst(): String {

        var matchCounter = 0
        val testableChars = parseFile()

        // Find horizontal matches of xmas strings
        testableChars.forEach {
            charArray ->
            matchCounter += findMatches(String(charArray))
        }

        // Find vertical matches of xmas strings
        for (i in testableChars.indices) {
            val horizontalChars = testableChars.map { array -> array[i] }.toCharArray()
            matchCounter += findMatches(String(horizontalChars))
        }
        matchCounter += findMatchesHorizontal(testableChars)

        matchCounter += findMatchesVertical(testableChars)

        return matchCounter.toString()
    }

    override fun solveSecond(): String {
        var matchCounter = 0
        val testableChars = parseFile()
        var i = 0


        while(i < testableChars.size - 2) {
            var j = 0
            while (j < testableChars[0].size - 2) {
                val slidingArray = testableChars.slice(i..i+2).map { row ->
                    row.slice(j..j + 2)
                    }
                if(checkSquare(slidingArray)) matchCounter++
                j++
                }
            i++
        }
        return matchCounter.toString()
    }


    private fun parseFile(): List<CharArray> {
        val data = dataFile.readLines().map {
            line -> line.toCharArray()
        }
        return data
    }

    private fun findMatchesVertical(testableChars: List<CharArray>): Int {
        // Start from 1 since the first row is checked horizontally
        var i = 1
        var matchCounter = 0

        while ( i < testableChars.size) {

            var verticalIndex = i
            var horizontalIndex = testableChars[0].size - 1
            var charsToLeft = ""
            // Find crossing strings to left
            while ( verticalIndex < testableChars.size && horizontalIndex >= 0) {
                charsToLeft += testableChars[verticalIndex][horizontalIndex]
                verticalIndex++
                horizontalIndex--
            }

            // Find crossing strings to right
            verticalIndex = i
            horizontalIndex = 0
            var charsToRight = ""
            while( verticalIndex < testableChars.size && horizontalIndex < testableChars[0].size) {
                charsToRight += testableChars[verticalIndex][horizontalIndex]
                verticalIndex++
                horizontalIndex++
            }

            matchCounter += findMatches(charsToLeft)
            matchCounter += findMatches(charsToRight)
            i++
        }

        return matchCounter
    }

    private fun findMatchesHorizontal(testableChars: List<CharArray>): Int {
        var j = testableChars[0].size - 1
        var matchCounter = 0
        while ( j >= 0) {
            var verticalIndex = 0
            var horizontalIndex = j
            var charsToLeft = ""
            // Find crossing strings to left
            while ( verticalIndex < testableChars.size && horizontalIndex >= 0) {
                charsToLeft += testableChars[verticalIndex][horizontalIndex]
                verticalIndex++
                horizontalIndex--
            }

            // Find crossing strings to right
            verticalIndex = 0
            horizontalIndex = j
            var charsToRight = ""
            while( verticalIndex < testableChars.size && horizontalIndex < testableChars[0].size) {
                charsToRight += testableChars[verticalIndex][horizontalIndex]
                verticalIndex++
                horizontalIndex++
            }

            matchCounter += findMatches(charsToLeft)
            matchCounter += findMatches(charsToRight)
            j--
        }
        return matchCounter
    }

    private fun checkSquare(square: List<List<Char>>): Boolean{
        val string1 = square[0][0].toString() + square[1][1].toString() + square[2][2].toString()
        val string2 = square[0][2].toString() + square[1][1].toString() + square[2][0].toString()

        return (string1 == "MAS" && string2 == "MAS" || string1 == "SAM" && string2 == "SAM"
                || string1 == "MAS" && string2 =="SAM" || string1 == "SAM" && string2 == "MAS")
    }

    private fun findMatches(chars: String): Int {
        var counter = 0
        var i = 0
        while (i < chars.length - 3) {
            val matchableString = chars.substring(i, i + 4)
            if (matchableString == "XMAS") counter ++
            if (matchableString == "SAMX") counter++
            i++
        }
        return counter
    }
}