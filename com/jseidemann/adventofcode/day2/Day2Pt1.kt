package com.jseidemann.adventofcode.day2

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Day2Pt1 {
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
        var myPlay: MyPlay = MyPlay.valueOf(input.substring(2, 3))

        fun calculateScore(): Int {
            println("I played " + myPlay + ". They played " + oppPlay.choice + ". So I got " + (myPlay.value + oppPlay.calculateWinner(myPlay)) + " points.")
            return myPlay.value + oppPlay.calculateWinner(myPlay)
        }

    }

    internal enum class MyPlay(var value: Int) {
        X(1), Y(2), Z(3);
    }

    internal class OppPlay(var choice: String) {
        fun calculateWinner(myPlay: MyPlay?): Int {
            return when (choice) {
                "A" -> {
                    when (myPlay) {
                        MyPlay.X -> 3
                        MyPlay.Y -> 6
                        MyPlay.Z -> 0
                        else -> throw RuntimeException()
                    }
                }
                "B" -> {
                    when (myPlay) {
                        MyPlay.X -> 0
                        MyPlay.Y -> 3
                        MyPlay.Z -> 6
                        else -> throw RuntimeException()
                    }
                }
                "C" -> {
                    when (myPlay) {
                        MyPlay.X -> 6
                        MyPlay.Y -> 0
                        MyPlay.Z -> 3
                        else -> throw RuntimeException()
                    }
                }
                else -> throw RuntimeException()
            }
        }
    }
}