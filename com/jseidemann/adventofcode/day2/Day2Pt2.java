package com.jseidemann.adventofcode.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2Pt2 {

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
        Result result;

        public Round(String input) {
            this.oppPlay = new OppPlay(input.substring(0, 1));
            this.result = Result.valueOf(input.substring(2, 3));
        }

        public Integer getScore() {
            return result.value + oppPlay.calculateMyPlay(result).value;
        }
    }

    static class OppPlay {
        String oppPlay;

        public OppPlay(String oppPlay) {
            this.oppPlay = oppPlay;
        }

        MyPlay calculateMyPlay(Result result) {
            if (oppPlay.equals("A")) {
                switch (result) {
                    case X: return MyPlay.C;
                    case Y: return MyPlay.A;
                    case Z: return MyPlay.B;
                    default: throw new RuntimeException();
                }
            } else if (oppPlay.equals("B")) {
                switch (result) {
                    case X: return MyPlay.A;
                    case Y: return MyPlay.B;
                    case Z: return MyPlay.C;
                    default: throw new RuntimeException();
                }
            } else {
                switch (result) {
                    case X: return MyPlay.B;
                    case Y: return MyPlay.C;
                    case Z: return MyPlay.A;
                    default: throw new RuntimeException();
                }
            }
        }
    }

    enum Result {
        X(0), Y(3), Z(6);

        Integer value;

        Result(Integer val) {
            this.value = val;
        }
    }

    enum MyPlay {
        A(1), B(2), C(3);

        Integer value;

        MyPlay(Integer val) {
            this.value = val;
        }
    }
}
