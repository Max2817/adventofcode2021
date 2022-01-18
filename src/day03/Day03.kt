package day03

import readInput
import java.util.function.Predicate

fun main() {
    fun part1(input: List<String>): Int {
        val gamma = input.map {
            it.chunked(1)
                .mapIndexed { index, value ->
                    index to value.toInt()
                }
        }
            .flatten()
            .groupBy { it.first }
            .mapValues { (_, value) ->
                if (value.count { it.second == 1 } > value.size / 2) {
                    1
                } else {
                    0
                }
            }.values
        val gammaDecimal = gamma.joinToString("").toInt(2)
        val epsilon = gamma.map { (it + 1) % 2 }
        val epsilonDecimal = epsilon.joinToString("").toInt(2)
        return gammaDecimal * epsilonDecimal
    }

    fun removeWithValueAtIndex(input: MutableList<String>, index: Int, value: String) {
        remove(input) { it[index].toString() == value }
    }

    fun filterList(index: Int, input: MutableList<String>, lifeSupport: LifeSupport): String {
        return if (input.size == 1) {
            input[0]
        } else {
            val oneBitCount = input.map { it[index].toString() }.count { it == "1" }
            val halfSize = input.size.toFloat() / 2f
            if (oneBitCount.toFloat() < halfSize ) {
                removeWithValueAtIndex(input, index, if(lifeSupport == LifeSupport.OXYGEN) "1" else "0")
            } else if (oneBitCount.toFloat() >= halfSize) {
                removeWithValueAtIndex(input, index, if(lifeSupport == LifeSupport.OXYGEN) "0" else "1")
            }
            filterList(index + 1, input, lifeSupport)
        }
    }

    fun part2(input: List<String>): Int {
        val o2Bit = filterList(0, input.toMutableList(), LifeSupport.OXYGEN)
        val co2Bit = filterList(0, input.toMutableList(), LifeSupport.CO2)
        return o2Bit.toInt(2) * co2Bit.toInt(2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}

enum class LifeSupport {
    OXYGEN, CO2
}

fun <T> remove(list: MutableList<T>, predicate: Predicate<T>) {
    list.filter { predicate.test(it) }.forEach { list.remove(it) }
}

