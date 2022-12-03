package com.jseidemann.adventofcode.day3

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Day3Pt2 {

    val characterValues = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day3/adventofcode_puzzle3_input.txt"))

        var i = 0
        var total = 0
        while (i < inputStrings.size) {
            total += findCommonItemValue(inputStrings[i], inputStrings[i + 1], inputStrings[i + 2])
            i += 3
        }

        println("The total is $total")
    }

    private fun findCommonItemValue(bag1: String, bag2: String, bag3: String): Int {
        for (i in characterValues.indices) {
            if (bag1.indexOf(characterValues[i]) != -1 && bag2.indexOf(characterValues[i]) != -1 && bag3.indexOf(characterValues[i]) != -1) {
                return 1 + i // Index of character is 1 lower than its value
            }
        }
        throw RuntimeException("Woopsy")
    }
}