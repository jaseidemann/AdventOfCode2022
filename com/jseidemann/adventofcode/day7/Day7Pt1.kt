package com.jseidemann.adventofcode.day7

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Day7Pt1 {

    val systemDir: Dir = Dir("/", 0, null)
    val allDirs: MutableList<Dir> = mutableListOf(systemDir)
    const val MAX_SIZE = 70000000
    const val SIZE_NEEDED = 30000000

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputStrings = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day7/adventofcode_puzzle7_input.txt"))

        buildFileSystem(inputStrings)

        // Part 1
//        val sumOfDirs = allDirs.stream().mapToInt(Dir::calculateSize).filter { it <= 100000 }.sum()
//        println("Answer is $sumOfDirs")

        // Part 2
        val sizeToClear = (systemDir.calculateSize() + SIZE_NEEDED) - MAX_SIZE
        val sizeOfDirToClear = allDirs.stream().mapToInt(Dir::calculateSize).filter { it >= sizeToClear }.sorted().findFirst().asInt
        println("Answer is $sizeOfDirToClear")

    }

    private fun buildFileSystem(inputStrings: MutableList<String>) {
        var currentDir: Dir = systemDir
        for (line in inputStrings) {
            if (line.startsWith("$")) {

                val command = line.substring(2, line.length)
                if (getCommand(command) == "cd") {
                    currentDir = getNewDir(line, currentDir)
                }

            } else if (line.startsWith("dir")) {
                val newDir = Dir(getArgument(line), 0, currentDir)
                allDirs.add(newDir)
                currentDir.entries.add(newDir)
            } else {
                val fileSize = Integer.valueOf(line.substring(0, line.indexOf(" ")))
                val fileName = line.substring(line.indexOf(" "), line.length)
                currentDir.entries.add(Entry(fileName, fileSize, currentDir))
            }
        }
    }

    private fun getNewDir(line: String, currentDir: Dir): Dir {
        return when (getArgument(line)) {
            "/" -> systemDir
            ".." -> currentDir.parent!!
            else -> currentDir.entries.stream().filter { it.name == getArgument(line) }.findFirst().orElseThrow(::RuntimeException) as Dir
        }
    }

    private fun getArgument(line: String): String {
        return line.substring(line.lastIndexOf(" ") + 1, line.length)
    }

    private fun getCommand(line: String): String {
        return line.substring(0, 2)
    }

    class Dir(name: String, size: Int, parent: Dir?) : Entry(name, size, parent) {
        var entries: MutableList<Entry> = ArrayList()

        override fun calculateSize(): Int {
            return entries.stream().mapToInt(Entry::calculateSize).sum()
        }

    }

    open class Entry(var name: String, var size: Int, var parent: Dir?) {
        open fun calculateSize(): Int {
            return size
        }
    }
}