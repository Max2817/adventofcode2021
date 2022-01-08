package day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var position = Position(x = 0, y = 0, aim = 0)
        return input
            .map { it.split(" ") }
            .groupBy({ it[0] }, { it[1].toInt() })
            .map { Pair(it.key, it.value.sum()) }
            .fold(position) { _, (command, value) ->
                position = when {
                    command.equals(Direction.DOWN.name, true) -> position.copy(y = position.y + value)
                    command.equals(Direction.UP.name, true) -> position.copy(y = position.y - value)
                    else -> position.copy(x = position.x + value)
                }
                position
            }.let { it.x * it.y }
    }

    fun part2(input: List<String>): Int {
        var position = Position(0, 0, 0)
        return input.map { line ->
            val array = line.split(" ")
            array[0] to array[1].toInt()
        }
            .fold(position) { _, (command, value) ->
                position = when {
                    command.equals(Direction.DOWN.name, true) -> position.copy(aim = position.aim + value)
                    command.equals(Direction.UP.name, true) -> position.copy(aim = position.aim - value)
                    else -> {
                        val positionY = if (position.aim > 0) {
                            position.y + (value * position.aim)
                        } else {
                            position.y
                        }
                        position.copy(x = position.x + value, y = positionY)
                    }
                }
                position
            }.let { it.x * it.y }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("day02/Day02")
    println(part1(input))
    println(part2(input))
}

data class Position(val x: Int, val y: Int, val aim: Int)

enum class Direction {
    DOWN, UP
}

