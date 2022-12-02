package com.jseidemann.adventofcode.day2

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Day2Pt2 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day2/adventofcode_puzzle2_input.txt"))

        //@formatter:off
        val total = inputStrings.stream()
                .map(::Round)
                .mapToInt(Round::calculateScore)
                .sum()
        //@formatter:on
        println("I got $total points")
    }

    internal class Round(input: String) {
        var oppPlay: OppPlay = OppPlay(input.substring(0, 1))
        var result: Result = Result.valueOf(input.substring(2, 3))
        fun calculateScore(): Int = result.value + oppPlay.calculateMyPlay(result).value

    }

    internal class OppPlay(var oppPlay: String) {
        fun calculateMyPlay(result: Result?): MyPlay {
            return when (oppPlay) {
                "A" -> {
                    when (result) {
                        Result.X -> MyPlay.C
                        Result.Y -> MyPlay.A
                        Result.Z -> MyPlay.B
                        else -> throw RuntimeException()
                    }
                }
                "B" -> {
                    when (result) {
                        Result.X -> MyPlay.A
                        Result.Y -> MyPlay.B
                        Result.Z -> MyPlay.C
                        else -> throw RuntimeException()
                    }
                }
                else -> {
                    when (result) {
                        Result.X -> MyPlay.B
                        Result.Y -> MyPlay.C
                        Result.Z -> MyPlay.A
                        else -> throw RuntimeException()
                    }
                }
            }
        }
    }

    internal enum class Result(var value: Int) {
        X(0), Y(3), Z(6);
    }

    internal enum class MyPlay(var value: Int) {
        A(1), B(2), C(3);
    }
}