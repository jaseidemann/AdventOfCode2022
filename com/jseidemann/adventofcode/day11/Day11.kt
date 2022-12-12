package com.jseidemann.adventofcode.day11

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object Day11 {

    var monkies: MutableList<Monkey> = ArrayList()

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings: List<String> = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day11/adventofcode_puzzle11_input.txt"))

        var i = 0
        while (i < inputStrings.size) {
            monkies.add(Monkey(inputStrings.subList(i, i + 6)))
            i += 7
        }

    }

    class Monkey(input: List<String>) {
        var items: MutableList<Item>
        var op: Operation
        var testValue: Int

        init {
            val itemsAsStrings: List<String> = input[1].split(":")[1].replace(" ", "").split(",")
            this.items = itemsAsStrings.stream().map(Integer::valueOf).map(::Item).collect(Collectors.toList())
            this.op = Operation.getByLine(input[2])
            this.testValue = Integer.valueOf(input[3].substring(input[3].indexOf("by ") + 3, input[3].length))
            println("Created Monkey ${items.map(Item::value)}, ${op}, $testValue")
        }
    }

    class Item(var value: Int)

    abstract class Operation {

        abstract fun investigate(item: Item)

        companion object {
            fun getByLine(input: String): Operation {
                return if (input.contains("*") && !input.matches(Regex(".*\\d.*"))) {
                    SquareOperation()
                } else if (input.contains("*")) {
                    MultiplyOperation(Integer.valueOf(input.substring(input.indexOf("*") + 2, input.length)))
                } else {
                    AddOperation(Integer.valueOf(input.substring(input.indexOf("+") + 2, input.length)))
                }
            }
        }
    }

    class MultiplyOperation(val value: Int) : Operation() {
        override fun investigate(item: Item) {
            item.value = this.value * item.value
        }
    }

    class AddOperation(val value: Int) : Operation() {
        override fun investigate(item: Item) {
            item.value = this.value + item.value
        }
    }

    class SquareOperation : Operation() {
        override fun investigate(item: Item) {
            item.value = item.value * item.value
        }
    }
}