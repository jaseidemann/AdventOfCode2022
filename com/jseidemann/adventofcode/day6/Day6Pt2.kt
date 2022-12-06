package com.jseidemann.adventofcode.day6

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

object Day6Pt2 {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val allLines = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day6/adventofcode_puzzle6_input.txt"))
        val firstLine = allLines[0]

        val charArray: List<String> = firstLine.toList().map(Char::toString)

        var i = 14
        while (i <= charArray.size) {
            val subList = charArray.subList(i - 14, i)
            println("Testing $subList")
            if (subList.stream().distinct().toList().size == 14) {
                println("Answer is ${i}") // Not i + 1 because String.subList( start , end ) is exclusive on the end.
                break
            }
            i++
        }


    }
}