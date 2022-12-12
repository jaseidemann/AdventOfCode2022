package com.jseidemann.adventofcode.day11

import java.io.IOException
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object Day11pt2 {

    var monkeys: MutableList<Monkey> = ArrayList()

    var modFactor = BigInteger.ONE

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings: List<String> =
            Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day11/adventofcode_puzzle11_input.txt"))

        // Create initial Array of Monkeys
        for (i in 0 until 8) {
            monkeys.add(Monkey())
        }

        // Initialize each monkey's properties
        var inputLinesIndex = 0
        var monkeyIndex = 0
        while (inputLinesIndex < inputStrings.size) {
            monkeys[monkeyIndex].initialize(inputStrings.subList(inputLinesIndex, inputLinesIndex + 6))
            modFactor *= monkeys[monkeyIndex].testValue!!
            inputLinesIndex += 7
            monkeyIndex++
        }

        println("Mod Factor is $modFactor")
        // Perform 20 rounds
        for (i in 0 until 10000) {
            monkeys.forEach(Monkey::inspectItems)
        }

        // Print item counts
        println("~~~ Counts ~~~")
        monkeys.forEach { println(it.countedItems) }

        val counts = monkeys
            .map(Monkey::countedItems)
            .sorted().reversed()
            .subList(0, 2)
        println("Answer is ${counts[0].times(counts[1])}")
    }

    class Monkey {
        var items: MutableList<Item> = ArrayList()
        var op: Operation? = null
        var testValue: BigInteger? = null
        var ifTrueMonkey: Monkey? = null
        var ifFalseMonkey: Monkey? = null
        var countedItems = BigInteger.ZERO

        fun initialize(input: List<String>) {
            val itemsAsStrings: List<String> = input[1].split(":")[1].replace(" ", "").split(",")
            this.items = itemsAsStrings.stream().map(String::toBigInteger).map(::Item).collect(Collectors.toList())
            this.op = Operation.getByLine(input[2])
            this.testValue = input[3].substring(input[3].indexOf("by ") + 3, input[3].length).toBigInteger()
            this.ifTrueMonkey =
                monkeys[Integer.valueOf(input[4].substring(input[4].indexOf("monkey ") + 7, input[4].length))]
            this.ifFalseMonkey =
                monkeys[Integer.valueOf(input[5].substring(input[5].indexOf("monkey ") + 7, input[5].length))]
        }

        fun inspectItems() {
            val iterator = items.iterator()
            while (iterator.hasNext()) {
                countedItems++

                val item = iterator.next()

                // Adjust Value
                op!!.investigate(item)
                item.value = item.value.mod(modFactor)

                // Test Item
                if (item.value.mod(testValue) == BigInteger.ZERO) {
                    iterator.remove()
                    ifTrueMonkey!!.items.add(item)
                } else {
                    iterator.remove()
                    ifFalseMonkey!!.items.add(item)
                }
            }
        }
    }
}