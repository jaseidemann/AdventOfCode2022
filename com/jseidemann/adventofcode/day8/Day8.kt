package com.jseidemann.adventofcode.day8

import java.io.IOException
import java.lang.Integer.valueOf
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object Day8 {

    var treeGrid: MutableList<List<Int>> = ArrayList()

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day8/adventofcode_puzzle8_input.txt"))
        for (line in inputStrings) {
            val integers: List<Int> = line.toMutableList().map(Char::toString).map(::valueOf)
            treeGrid.add(integers)
        }

//        part1();
        part2()
    }

    private fun part1() {
        var visibleTrees = 0
        for (rowIndex in 0 until treeGrid.size) {
            for (columnIndex in 0 until treeGrid[rowIndex].size) {
                val itVisible = isItVisible(rowIndex, columnIndex)
                visibleTrees += itVisible
            }
        }

        println("The answer is $visibleTrees")
    }

    private fun part2() {
        var mostVisibleTrees = 0
        for (rowIndex in 0 until treeGrid.size) {
            for (columnIndex in 0 until treeGrid[rowIndex].size) {
                val visibleTreesFromTree = getVisibleTreesFromTree(rowIndex, columnIndex)
                if (visibleTreesFromTree > mostVisibleTrees) {
                    mostVisibleTrees = visibleTreesFromTree
                }
            }
        }
        println("The answer is $mostVisibleTrees")
    }

    private fun getVisibleTreesFromTree(rowIndex: Int, columnIndex: Int): Int {
        val treeHeight = treeGrid[rowIndex][columnIndex]
        val row = treeGrid[rowIndex];
        val column = treeGrid.stream().map { it[columnIndex] }.collect(Collectors.toList())

        var visibleTreeCount = 1;
        if (columnIndex != 0) {
            val treesToTheLeft = row.subList(0, columnIndex).reversed()
            visibleTreeCount *= getVisibleTreesInLine(treesToTheLeft, treeHeight)
        }

        if (columnIndex != row.lastIndex) {
            val treesToTheRight = row.subList(columnIndex + 1, row.size)
            visibleTreeCount *= getVisibleTreesInLine(treesToTheRight, treeHeight)
        }

        if (rowIndex != 0) {
            val treesAbove = column.subList(0, rowIndex).reversed()
            visibleTreeCount *= getVisibleTreesInLine(treesAbove, treeHeight)
        }

        if (rowIndex != column.lastIndex) {
            val treesBelow = column.subList(rowIndex + 1, column.size)
            visibleTreeCount *= getVisibleTreesInLine(treesBelow, treeHeight)
        }

        return visibleTreeCount
    }

    private fun getVisibleTreesInLine(trees: List<Int>, treeHeight: Int): Int {
        var count = 0
        for (tree in trees) {
            if (tree >= treeHeight) {
                count++
                break
            }
            count++
        }

        return count
    }


    private fun isItVisible(rowIndex: Int, columnIndex: Int): Int {
        // If its in the first row/col or last row/col it is visible
        if (rowIndex == 0 || columnIndex == 0) {
            return 1
        }
        if (rowIndex == treeGrid.lastIndex || columnIndex == treeGrid[0].lastIndex ) return 1

        val row = treeGrid[rowIndex];
        val column = treeGrid.stream().map { it[columnIndex] }.collect(Collectors.toList())

        val largestTreeHeightFromLeft = row.subList(0, columnIndex).maxOrNull() ?: -1
        val largestTreeHeightFromRight = row.subList(columnIndex + 1, row.size).maxOrNull() ?: -1
        val largestTreeHeightFromTop = column.subList(0, rowIndex).maxOrNull() ?: -1
        val largestTreeHeightFromBottom = column.subList(rowIndex + 1, column.size).maxOrNull() ?: -1

        val tree = treeGrid[rowIndex][columnIndex]
        if (
                tree > largestTreeHeightFromRight ||
                tree > largestTreeHeightFromLeft ||
                tree > largestTreeHeightFromTop ||
                tree > largestTreeHeightFromBottom
        ) {
            return 1
        }
        return 0;
    }
}