package com.jseidemann.adventofcode.day5

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.function.Consumer

object Day5Pt1 {

    val columns = listOf(1, 5, 9, 13, 17, 21, 25, 29, 33)
    val stacks = listOf(ArrayDeque<Char>(), ArrayDeque<Char>(), ArrayDeque<Char>(), ArrayDeque<Char>(), ArrayDeque<Char>(), ArrayDeque<Char>(), ArrayDeque<Char>(), ArrayDeque<Char>(), ArrayDeque<Char>())

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day5/adventofcode_puzzle5_input.txt"))

        buildStacks(inputStrings)
        val instructions = inputStrings.subList(10, inputStrings.size)

        for (instruction in instructions) {
            val quantity = Integer.valueOf(instruction.substring(instruction.indexOf(" ") + 1, instruction.indexOf(" from")))
            val from = Integer.valueOf(instruction.substring(instruction.indexOf("m ") + 2, instruction.indexOf(" to ")))
            val to = Integer.valueOf(instruction.substring(instruction.indexOf("o ") + 2, instruction.length))

//            moveTheStuff(quantity, from, to)
            moveTheStuffPart2(quantity, from, to)
        }

        stacks.forEach(Consumer { println(it.pop()) })
    }

    private fun moveTheStuff(quantity: Int, from: Int, to: Int) {
        var count = 0
        while (count < quantity) {
            try {
                val poppedLetter = stacks[from - 1].pop()
                stacks[to - 1].push(poppedLetter)
            } catch (e: NoSuchElementException) {
                break
            }
            count++
        }
    }

    private fun moveTheStuffPart2(quantity: Int, from: Int, to: Int) {
        var count = 0
        val poppedLetters = mutableListOf<Char>()
        while (count < quantity) {
            try {
                poppedLetters.add(stacks[from - 1].pop())
            } catch (e: NoSuchElementException) {
                break
            }
            count++
        }

        poppedLetters.reversed().stream()
                .forEach { stacks[to - 1].push(it) }

    }

    private fun buildStacks(inputStrings: MutableList<String>) {
        var i = 8
        while (i >= 0) {
            val chars = inputStrings[i].toCharArray()

            for (column in columns) {
                if (chars.size < column) break
                if (chars[column] != ' ') {
                    stacks[columns.indexOf(column)].push(chars[column])
                }
            }
            i--
        }
    }
}