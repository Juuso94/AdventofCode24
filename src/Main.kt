import java.io.File


fun openFile(fileLocation: String): File {
    return File(fileLocation)
}

fun createClassInstance(day: Int, dataFile: File): SolutionInterface {
    return when (day) {
        1 -> Day1Class(dataFile)
        2 -> Day2Class(dataFile)
        3 -> Day3Class(dataFile)
        4 -> Day4Class(dataFile)
        else -> throw IllegalArgumentException("Day number $day Class is not yet implemented")
    }
}

fun main(args: Array<String>) {
    try {
        val filePath = args[0]
        println(filePath)
        val file = openFile(filePath)
        val day = args[1].toInt()
        val classOfTheDay = createClassInstance(day, file)

        println(classOfTheDay.solveFirst())
        println(classOfTheDay.solveSecond())
    }
    catch (e: Exception) {
        println(e)
    }



}