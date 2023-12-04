fun main() {
    fun part1(input: List<String>): Int {
        val bag = listOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )

        return  input.sumOf { line ->
            val id = line.substringAfter("Game ").substringBefore(":")
            getRounds(line).forEach { round ->
                if (round.second.toInt() > (bag.find { it.first == round.first }?.second ?: 0))  {
                    return@sumOf 0
                }
            }
            id.toInt()
        }
    }

    fun part2(input: List<String>): Int  = input.sumOf { line ->
        getRounds(line).groupBy (
            keySelector = { it.first },
            valueTransform = { it.second.toInt() }
        ).map {
            it.key to it.value.max()
        }.map {
            it.second
        }.fold(1) { acc, i ->
            acc * i
        }.toInt()
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

fun getRounds(line: String) = line.substringAfter(": ")
    .split("; ")
    .flatMap {
        it.split(", ").map { round ->
            round.substringAfter(" ") to round.substringBefore(" ")
        }
    }
