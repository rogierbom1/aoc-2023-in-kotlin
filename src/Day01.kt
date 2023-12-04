fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            val firstMatch = line.first { it.isDigit() }
            val lastMatch = line.last { it.isDigit() }
            "$firstMatch$lastMatch".toInt()
        }

    fun part2(input: List<String>): Int {
        val digits = listOf(
            "1" to "1",
            "2" to "2",
            "3" to "3",
            "4" to "4",
            "5" to "5",
            "6" to "6",
            "7" to "7",
            "8" to "8",
            "9" to "9",
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )

        val result = input.sumOf { line ->
            val firstMatch = digits.mapNotNull { digit ->
                if (line.indexOf(digit.first) > -1) {
                    line.indexOf(digit.first) to digit
                } else {
                    null
                }
            }.sortedBy { it.first }.firstOrNull()?.second?.second ?: "0"

            val lastMatch = digits.mapNotNull { digit ->
                if (line.lastIndexOf(digit.first) > -1) {
                    line.lastIndexOf(digit.first) to digit
                } else {
                    null
                }
            }.sortedBy { it.first }.reversed().firstOrNull()?.second?.second ?: ""

            "$firstMatch$lastMatch".toInt()
        }
        return result
    }

    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)
    part2(testInput).println()

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
