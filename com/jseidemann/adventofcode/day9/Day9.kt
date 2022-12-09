package com.jseidemann.adventofcode.day9

import java.io.IOException
import java.lang.Integer.valueOf
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

object Day9 {

    val tailPositions: MutableList<Pair<Int, Int>> = ArrayList()
    var headPosition = Pair(0, 0)
    var tailPosition = Pair(0, 0)

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day9/adventofcode_puzzle9_input.txt"))

        val commands = inputStrings.map(::Command)

        for (command in commands) {
            for (count in 0 until command.quantity) {
                val oldHeadPosition = headPosition
                // Apply direction to head
                headPosition = command.applyCommand(headPosition)
                print("Head to $headPosition. ")

                // Adjust Tail if necessary
                val needToMoveRow = abs(headPosition.first - tailPosition.first) > 1
                val needToMoveColumn = abs(headPosition.second - tailPosition.second) > 1
                if (needToMoveRow || needToMoveColumn) {
                    /**
                     * For every position of head and tail,
                     *
                     * When the head moves, the tail is either
                     *  1. Close enough and therefore doesn't move
                     *  2. Or it takes on the old position of the head
                     */
                    tailPosition = oldHeadPosition
                    println("Tail to ${tailPosition}")
                }


                // Save tail position if new
                if (!tailPositions.contains(tailPosition)) {
                    tailPositions.add(tailPosition)
                }
            }
        }

        print("Answer is ${tailPositions.size}")


    }


    internal class Command(input: String) {
        val direction: String = input.substring(0, 1)
        val quantity: Int = valueOf(input.substring(input.indexOf(' ') + 1, input.length))

        fun applyCommand(pair: Pair<Int, Int>): Pair<Int, Int> {
            return when (direction) {
                "U" -> Pair(pair.first, pair.second + 1)
                "D" -> Pair(pair.first, pair.second - 1)
                "L" -> Pair(pair.first - 1, pair.second)
                "R" -> Pair(pair.first + 1, pair.second)
                else -> throw RuntimeException()
            }
        }
    }
}