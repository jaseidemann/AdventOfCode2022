package com.jseidemann.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AdventOfCode {

    public static void main(String[] args) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get("adventofcode_puzzle1_input.txt"));

        inputStrings.

        List<Integer> calorieSums = new ArrayList<>();

        Integer currentCalorieSum = 0;
        for(String calorieLine: inputStrings) {
            if (calorieLine != null && !calorieLine.equals("")) {
                // Line from file is not null and not blank. Therefore contains a value. Add it to the running total for the current elf
                currentCalorieSum += Integer.parseInt(calorieLine);
            } else if (calorieLine != null) {
                // Line from file is still not null but is blank indicating the end of the counts for the current elf.
                // Add the sum to the list of elf totals and reset the current sum.
                calorieSums.add(currentCalorieSum);
                currentCalorieSum = 0;
            } else {
                // Null line. End of file.
                System.out.println("Null Line. Ending count");
                break;
            }
        }

        System.out.println("Finished Counting");

        List<Integer> calorieSumsDescending = calorieSums.stream().sorted(Collections.reverseOrder(Integer::compare)).collect(Collectors.toList());

        //@formatter:off
        StringBuilder builder = new StringBuilder("The Top 3 Elf Calorie totals are [ ")
                .append(calorieSumsDescending.get(0)).append(", ").append(calorieSumsDescending.get(1)).append(", ").append(calorieSumsDescending.get(2))
                .append("] for a total of ")
                .append(calorieSumsDescending.get(0) + calorieSumsDescending.get(1) + calorieSumsDescending.get(2));
        //@formatter:on

        System.out.println(builder);
    }
}
