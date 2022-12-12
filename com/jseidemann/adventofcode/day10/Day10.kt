package com.jseidemann.adventofcode.day10

import java.io.IOException
import java.lang.Integer.valueOf
import java.nio.file.Files
import java.nio.file.Paths

object Day10 {

    val cycleStates: MutableList<CycleState> = ArrayList()

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings: List<String> = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day10/adventofcode_puzzle10_input.txt"))



        inputStrings.stream()
                .map(Command::fromString)
                .forEach { it.execute(cycleStates) }

        var answer = 0
        for (i in 1 until cycleStates.size) {
            if ((i - 20) % 40 == 0) {
                println("XXX Cycle State #$i is (${cycleStates[i - 1].start}, ${cycleStates[i - 1].end}))")
                answer += (i * cycleStates[i - 1].start)
            } else {
                println("Cycle State #$i is (${cycleStates[i - 1].start}, ${cycleStates[i - 1].end})")
            }
        }

        println("Answer is $answer")
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