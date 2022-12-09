package com.jseidemann.adventofcode.day9

import java.io.IOException
import java.lang.Integer.valueOf
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

object Day9pt2 {

    val tailPositions: MutableList<Pair<Int, Int>> = ArrayList()
    val rope: MutableList<Knot> = ArrayList()

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day9/adventofcode_puzzle9_input.txt"))

        createRope()

        val commands = inputStrings.map(::Command)

        for (command in commands) {
            for (count in 0 until command.quantity) {
                // Apply direction to head
                command.applyCommand(rope[0])

                // Adjust Next Knot if necessary
                for (i in 1 until rope.size) {
                    rope[i].adjustPosition(rope[i - 1])
                }

                val tail = rope.lastOrNull()!!

                // Save tail position if new
                if (!tailPositions.contains(tail.position)) {
                    tailPositions.add(tail.position)
                }
            }
        }

        print("Answer is ${tailPositions.size}")


    }

    private fun createRope() {
        for (i in 0 until 10) {
            rope.add(Knot())
        }
    }


    internal class Command(input: String) {
        val direction: String = input.substring(0, 1)
        val quantity: Int = valueOf(input.substring(input.indexOf(' ') + 1, input.length))

        fun applyCommand(knot: Knot) {
            knot.previousPosition = knot.position
            knot.position =
                    when (direction) {
                        "U" -> Pair(knot.position.first, knot.position.second + 1)
                        "D" -> Pair(knot.position.first, knot.position.second - 1)
                        "L" -> Pair(knot.position.first - 1, knot.position.second)
                        "R" -> Pair(knot.position.first + 1, knot.position.second)
                        else -> throw RuntimeException()
                    }
            print("Head to ${knot.position}. ")
        }
    }

    class Knot {
        var position: Pair<Int, Int> = Pair(0, 0)
        var previousPosition: Pair<Int, Int> = Pair(0, 0)

        fun adjustPosition(knotAhead: Knot) {
            val rowDiff = abs(knotAhead.position.first - this.position.first)
            val columnDiff = abs(knotAhead.position.second - this.position.second)
            if (rowDiff > 1 && columnDiff > 1) {
                this.previousPosition = this.position
                val newRowIndex = this.position.first + ((knotAhead.position.first - this.position.first) / 2)
                val newColumnIndex = this.position.second + ((knotAhead.position.second - this.position.second) / 2)
                this.position = Pair(newRowIndex, newColumnIndex)
            } else if (rowDiff > 1 && columnDiff == 1) {
                this.previousPosition = this.position
                val newRowIndex = this.position.first + ((knotAhead.position.first - this.position.first) / 2)
                val newColumnIndex = this.position.second + ((knotAhead.position.second - this.position.second))
                this.position = Pair(newRowIndex, newColumnIndex)
            } else if (rowDiff > 1 && columnDiff == 0) {
                this.previousPosition = this.position
                val newRowIndex = this.position.first + ((knotAhead.position.first - this.position.first) / 2)
                this.position = Pair(newRowIndex, this.position.second)
            } else if (columnDiff > 1 && rowDiff == 1) {
                this.previousPosition = this.position
                val newColumnIndex = this.position.second + ((knotAhead.position.second - this.position.second) / 2)
                val newRowIndex = this.position.first + ((knotAhead.position.first - this.position.first))
                this.position = Pair(newRowIndex, newColumnIndex)
            } else if (columnDiff > 1 && rowDiff == 0) {
                this.previousPosition = this.position
                val newColumnIndex = this.position.second + ((knotAhead.position.second - this.position.second) / 2)
                this.position = Pair(this.position.first, newColumnIndex)
            }

            if ((rowDiff > 1 || columnDiff > 1) && rope.indexOf(this) == rope.lastIndex) println("Tail to $position")
        }
    }
}