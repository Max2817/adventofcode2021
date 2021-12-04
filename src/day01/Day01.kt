package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val size = input.map { it.toInt() }
            .zipWithNext().count { (first, second) ->
                second > first
            }
        return size
    }

    fun part2(input: List<String>): Int {
        val size = input.asSequence()
            .map { it.toInt() }
            .windowed(3)
            .map { it.sum()
            }
            .zipWithNext()
            .count { (first, second) ->
                second > first
            }
        return size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01/Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("day01/Day01")
    println(part1(input))
    println(part2(input))
}
