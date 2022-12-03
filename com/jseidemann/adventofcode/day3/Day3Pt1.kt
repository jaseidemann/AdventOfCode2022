package com.jseidemann.adventofcode.day3

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Day3Pt1 {

    val characterValues = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day3/adventofcode_puzzle3_input.txt"))

        val sum = inputStrings.stream()
                .map(::Rucksack)
                .mapToInt(this::findCommonItemValue)
                .sum()

        println("The total is $sum")
    }

    private fun findCommonItemValue(rucksack: Rucksack): Int {
        for (i in characterValues.indices) {
            val compartment1Index = rucksack.compartment1.indexOf(characterValues[i])
            val compartment2Index = rucksack.compartment2.indexOf(characterValues[i])
            if (compartment1Index != -1 && compartment2Index != -1) {
                return 1 + i // Index of character is 1 lower than its value
            }
        }
        throw RuntimeException("Woopsy")
    }

    internal class Rucksack(input: String) {
        val compartment1 = input.substring(0, input.length / 2)
        val compartment2 = input.substring(input.length / 2, input.length)
    }
}