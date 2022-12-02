package com.jseidemann.adventofcode.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2Pt1 {

    public static void main(String[] args) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get("com/jseidemann/adventofcode/day2/adventofcode_puzzle2_input.txt"));

        //@formatter:off
        int total = inputStrings.stream()
                .map(Round::new)
                .mapToInt(Round::getScore)
                .sum();
        //@formatter:on

        System.out.println("I got " + total + " points");
    }

    static class Round {
        OppPlay oppPlay;
        MyPlay myPlay;

        public Round(String input) {
            this.oppPlay = new OppPlay(input.substring(0, 1));
            this.myPlay = MyPlay.valueOf(input.substring(2, 3));
        }

        public Integer getScore() {
            System.out.println("I played " + myPlay + ". They played " + oppPlay.choice + ". So I got " + (myPlay.value + oppPlay.calculateWinner(myPlay)) + " points.");
            return myPlay.value + oppPlay.calculateWinner(myPlay);
        }
    }

    enum MyPlay {
        X(1), Y(2), Z(3);

        Integer value;

        MyPlay(Integer val) {
            this.value = val;
        }
    }

    static class OppPlay {
        String choice;

        public OppPlay(String choice) {
            this.choice = choice;
        }

        Integer calculateWinner(MyPlay myPlay) {
            if (choice.equals("A")) {
                switch (myPlay) {
                    case X: return 3;
                    case Y: return 6;
                    case Z: return 0;
                    default: throw new RuntimeException();
                }
            } else if (choice.equals("B")) {
                switch (myPlay) {
                    case X: return 0;
                    case Y: return 3;
                    case Z: return 6;
                    default: throw new RuntimeException();
                }
            } else {
                switch (myPlay) {
                    case X: return 6;
                    case Y: return 0;
                    case Z: return 3;
                    default: throw new RuntimeException();
                }
            }
        }
    }
}
