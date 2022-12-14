package com.jseidemann.adventofcode.day12

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Day12pt2 {

    private val charValues: List<Char> = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')

    private const val MAX_HEIGHT_DIFF = 1;

    private var nodes: MutableList<Node> = ArrayList()
    private var startingNodes: MutableList<Node> = ArrayList()
    private var aNodeShortestPaths: HashMap<Node, Int> = HashMap()
    private var endNode: Node? = null

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings: List<String> =
            Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day12/adventofcode_puzzle12_input.txt"))

        createNodesList(inputStrings)

        for (node in startingNodes) {
            val currentPath: MutableList<Node> = mutableListOf(node)
            calculateNeighborDistances(currentPath, 1, node.neighbors)

            val shortestHopCount = endNode!!.distanceFromS
            aNodeShortestPaths[node] = shortestHopCount
            println("Shortest path for aNode at ${node.coordinates} is $shortestHopCount hops")
        }

        println("Shortest shortest path is ${aNodeShortestPaths.entries.stream().min(Comparator.comparingInt{ it.value }).get().value}")
    }

    private fun calculateNeighborDistances(currentPath: MutableList<Node>, currentHops: Int, neighbors: MutableList<Node>) {
        for (me in neighbors) {
            if (currentHops < me.distanceFromS) {
                me.distanceFromS = currentHops
                me.shortestPathToMe = currentPath
                if (me != endNode) {
                    val pathWithMe = ArrayList(currentPath)
                    pathWithMe.add(me)
                    calculateNeighborDistances(pathWithMe, currentHops + 1, me.neighbors)
                }
            } else {
                continue;
            }
        }
    }

    private fun createNodesList(inputStrings: List<String>) {
        for (y in 1 until inputStrings.size + 1) {
            val charArray = inputStrings[y - 1].toCharArray()
            for (x in 1 until charArray.size + 1) {
                val charArrayIndex = x - 1
                val node = Node(Pair(x, y), getHeight(charArray[charArrayIndex]))

                if (charArray[charArrayIndex] == 'S' || charArray[charArrayIndex] == 'a') {
                    node.distanceFromS = 0
                    startingNodes.add(node)
                }
                else if (charArray[charArrayIndex] == 'E') endNode = node

                // Create neighbors
                if ((x + 1) <= charArray.lastIndex) {
                    val neighborNode = nodes.stream().filter { it.coordinates == Pair(x + 1, y) }.findFirst()
                    if (neighborNode.isPresent && node.height + MAX_HEIGHT_DIFF >= neighborNode.get().height) node.neighbors.add(neighborNode.get())
                    if (neighborNode.isPresent && neighborNode.get().height + MAX_HEIGHT_DIFF >= node.height) neighborNode.get().neighbors.add(node)
                }

                if ((x - 1) >= 1) {
                    val neighborNode = nodes.stream().filter { it.coordinates == Pair(x - 1, y) }.findFirst()
                    if (neighborNode.isPresent && node.height + MAX_HEIGHT_DIFF >= neighborNode.get().height) node.neighbors.add(neighborNode.get())
                    if (neighborNode.isPresent && neighborNode.get().height + MAX_HEIGHT_DIFF >= node.height) neighborNode.get().neighbors.add(node)
                }

                if ((y + 1) <= inputStrings.lastIndex) {
                    val neighborNode = nodes.stream().filter { it.coordinates == Pair(x, y + 1) }.findFirst()
                    if (neighborNode.isPresent && node.height + MAX_HEIGHT_DIFF >= neighborNode.get().height) node.neighbors.add(neighborNode.get())
                    if (neighborNode.isPresent && neighborNode.get().height + MAX_HEIGHT_DIFF >= node.height) neighborNode.get().neighbors.add(node)
                }

                if ((y - 1) >= 1) {
                    val neighborNode = nodes.stream().filter { it.coordinates == Pair(x, y - 1) }.findFirst()
                    if (neighborNode.isPresent && node.height + MAX_HEIGHT_DIFF >= neighborNode.get().height) node.neighbors.add(neighborNode.get())
                    if (neighborNode.isPresent && neighborNode.get().height + MAX_HEIGHT_DIFF >= node.height) neighborNode.get().neighbors.add(node)
                }
                nodes.add(node)
            }
        }
    }

    internal fun getHeight(c: Char): Int {
        return when (c) {
            'S' -> charValues.indexOf('a')
            'E' -> charValues.indexOf('z')
            else -> charValues.indexOf(c)
        }
    }

    internal class Node(val coordinates: Pair<Int, Int>, val height: Int) {
        var distanceFromS = Int.MAX_VALUE
        var shortestPathToMe: MutableList<Node> = ArrayList();
        var neighbors: MutableList<Node> = ArrayList()
    }
}