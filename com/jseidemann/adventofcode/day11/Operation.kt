package com.jseidemann.adventofcode.day11

import java.math.BigInteger

abstract class Operation {

    abstract fun investigate(item: Item)

    companion object {
        fun getByLine(input: String): Operation {
            return if (input.contains("*") && !input.matches(Regex(".*\\d.*"))) {
                SquareOperation()
            } else if (input.contains("*")) {
                MultiplyOperation(input.substring(input.indexOf("*") + 2, input.length).toBigInteger())
            } else {
                AddOperation(input.substring(input.indexOf("+") + 2, input.length).toBigInteger())
            }
        }
    }
}

class MultiplyOperation(val value: BigInteger) : Operation() {
    override fun investigate(item: Item) {
        item.value = this.value * item.value
    }
}

class AddOperation(val value: BigInteger) : Operation() {
    override fun investigate(item: Item) {
        item.value = this.value + item.value
    }
}

class SquareOperation : Operation() {
    override fun investigate(item: Item) {
        item.value = item.value * item.value
    }
}