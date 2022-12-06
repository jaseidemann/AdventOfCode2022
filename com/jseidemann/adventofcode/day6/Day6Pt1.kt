package com.jseidemann.adventofcode.day6

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Day6Pt1 {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val charArray = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day6/adventofcode_puzzle6_input.txt"))[0].toCharArray()

        var i = 4
        while (i < charArray.size) {
            val first = charArray[i - 3]
            val second = charArray[i - 2]
            val third = charArray[i - 1]
            val fourth = charArray[i]

            if (!doTheyMatch(first, second, third, fourth)) {
                println("Answer is ${i + 1} and elements are $first , $second , $third , $fourth")
                break
            }
            i++
        }


    }

    fun doTheyMatch(first: Char, second: Char, third: Char, fourth: Char): Boolean {
        if (first == second || first == third || first == fourth) return true
        if (second == third || second == fourth) return true
        if (third == fourth) return true
        return false
    }
}