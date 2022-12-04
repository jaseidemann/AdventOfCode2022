package com.jseidemann.adventofcode.day4

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Day4Pt1 {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day4/adventofcode_puzzle4_input.txt"))

        val sum = inputStrings.stream()
                .map(::ElfPair)
//                .mapToInt(ElfPair::isFullyContained) // Part 1
                .mapToInt(ElfPair::hasOverlap) // Part 2
                .sum()

        println("The total number of pairings that overlap is $sum")
    }

    internal class ElfPair(input: String) {
        var firstElf: IntRange
        var secondElf: IntRange

        init {
            val firstElfRangeString = input.substring(0, input.indexOf(","))
            val secondElfRangeString = input.substring(input.indexOf(",") + 1, input.length)

            firstElf = IntRange(Integer.valueOf(firstElfRangeString.substring(0, firstElfRangeString.indexOf("-"))), Integer.valueOf(firstElfRangeString.substring(firstElfRangeString.indexOf("-") + 1, firstElfRangeString.length)))
            secondElf = IntRange(Integer.valueOf(secondElfRangeString.substring(0, secondElfRangeString.indexOf("-"))), Integer.valueOf(secondElfRangeString.substring(secondElfRangeString.indexOf("-") + 1, secondElfRangeString.length)))
        }

        // Part 1
        fun isFullyContained(): Int {
            return if (firstElf.contains(secondElf.first) && firstElf.contains(secondElf.last)) {
                1
            } else if (secondElf.contains(firstElf.first) && secondElf.contains(firstElf.last)) {
                1
            } else {
                0
            }
        }

        // Part 2
        fun hasOverlap(): Int {
            return when {
                firstElf.contains(secondElf.first) -> 1
                secondElf.contains(firstElf.first) -> 1
                else -> 0
            }
        }
    }
}