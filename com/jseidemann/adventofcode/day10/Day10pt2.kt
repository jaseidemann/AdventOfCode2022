package com.jseidemann.adventofcode.day10

import java.io.IOException
import java.lang.Integer.valueOf
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

object Day10pt2 {

    val cycleStates: MutableList<CycleState> = ArrayList()

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings: List<String> = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day10/adventofcode_puzzle10_input.txt"))



        inputStrings.stream()
                .map(Command::fromString)
                .forEach { it.execute(cycleStates) }


        var fullString = StringBuilder("")
        for (i in 0 until 239) {
            var currentPosition = i % 40


            val spritePosition = cycleStates[i].start
            if (abs(spritePosition - currentPosition) <= 1) {
                fullString.append("#")
            } else {
                fullString.append(".")
            }
        }

        // I'm lazy
        println(fullString.substring(0, 39))
        println(fullString.substring(40, 79))
        println(fullString.substring(80, 119))
        println(fullString.substring(120, 159))
        println(fullString.substring(160, 199))
        println(fullString.substring(200, 239))
    }

    internal abstract class Command {

        open var CYCLES = 1

        abstract fun execute(cycles: MutableList<CycleState>)

        companion object {
            fun fromString(input: String): Command {
                return if (input.startsWith("noop")) {
                    NoopCommand()
                } else {
                    AddXCommand(input)
                }
            }
        }
    }

    internal class NoopCommand : Command() {

        init {
            super.CYCLES = 1
        }

        override fun execute(cycles: MutableList<CycleState>) {
            var startingValue = 1
            if (cycles.isNotEmpty()) {
                startingValue = cycles.last().end
            }

            cycles.add(CycleState(startingValue, startingValue))
        }

    }

    internal class AddXCommand(input: String) : Command() {

        var parameter: Int

        init {
            super.CYCLES = 2
            this.parameter = valueOf(input.split(" ")[1])
        }

        override fun execute(cycles: MutableList<CycleState>) {
            var startingValue = 1
            if (cycles.isNotEmpty()) {
                startingValue = cycles.last().end
            }

            cycles.addAll(listOf(CycleState(startingValue, startingValue), CycleState(startingValue, startingValue + parameter)))
        }

    }

    class CycleState(val start: Int, val end: Int)

}