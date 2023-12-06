fun main() {
    fun part1(input: List<String>): Int {
        val grid: Array<Array<String>> = Array(input.size) {
            Array(input.first().length) {
                ""
            }
        }

        input.forEachIndexed { x, line ->
            line.toList().forEachIndexed { y, character ->
                grid[x][y] = character.toString()
            }
        }

        val digits = getDigits(input)

        return digits.filter {
            hasAdjecentSymbol(grid, it.line, it.range)
        }.sumOf {
            it.value.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val grid: Array<Array<Digit?>> = Array(input.size) {
            Array(input.first().length) {
                null
            }
        }

        val digits = getDigits(input)

        digits.forEach { digit ->
            for (y in digit.range) {
                grid[digit.line][y] = digit
            }
        }

        val stars = getStars(input)

        val result = stars.sumOf {  star ->
            if (star.x == 0) return@sumOf 0
            if (star.x == grid.size) return@sumOf 0

            val start = if (star.y > 0) star.y - 1 else 0
            val end = if (star.y < grid.first().size) star.y + 1 else star.y

            val adjecents =
                (start..end).map { y ->
                    grid[star.x - 1][y]
                } +
                listOf(grid[star.x][start], grid[star.x][end]) +
                (start..end).map { y ->
                    grid[star.x + 1][y]
                }

            val filteredAdjecents = adjecents.filterNotNull().toSet().toList()

            if (filteredAdjecents.size == 2) (filteredAdjecents.first().value.toInt() * filteredAdjecents.last().value.toInt()) else 0
        }

        return result
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

data class Digit(
    val line: Int,
    val value: String,
    val range: IntRange
)

data class Star(
    val x: Int,
    val y: Int
)

fun getDigits(input: List<String>): List<Digit> = input.flatMapIndexed { index, line ->
    """([0-9]+)""".toRegex().findAll(line).map {
        Digit(index, it.value, it.range)
    }
}

fun getStars(input: List<String>): List<Star> = input.flatMapIndexed { index, line ->
    """(\*)""".toRegex().findAll(line).map {
        Star(index, it.range.first)
    }
}

fun hasAdjecentSymbol(grid: Array<Array<String>>, line: Int, range: IntRange): Boolean {
    val start = if (range.first > 0) range.first -1 else 0
    val end = if (range.last +1 < grid.first().size) range.last +1 else range.last

    val above = if (line > 0) {
        (start..end).map {
            grid[line-1][it]
        }
    } else {
        emptyList()
    }

    val nextTo = listOf(start, end).mapNotNull {
        if (it != 0) grid[line][it] else null
    }

    val below = if (line+1 < grid.size) {
        (start..end).map {
            grid[line+1][it]
        }
    } else {
        emptyList()
    }

    val adjecents = above + nextTo + below

    adjecents.forEach {
        if (it != "." && !it.toCharArray().first().isDigit()) {
            return true
        }
    }
    return false
}
