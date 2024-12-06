import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day5 {

    public static void main(String[] args) throws IOException {

        String path = "resources/day5.txt";

        List<List<Integer>> instructions = new ArrayList<>();
        List<int[]> rules = new ArrayList<>();

        extractRulesAndInstructions(path, rules, instructions);

        Integer p1 = sumMiddleFromValidInstructions(instructions, rules);
        Integer p2 = sumMiddleInvalidInstructionFixed(instructions, rules);

        System.out.println("Part1: " + p1);
        System.out.println("Part2: " + p2);
    }

    private static Integer sumMiddleFromValidInstructions(List<List<Integer>> instructions, List<int[]> rules) {
        return instructions.stream()
                .filter(i -> isInstructionValid(filterRules(rules, i), i))
                .map(Day5::findMiddleValue)
                .reduce(0, Integer::sum);
    }

    private static Integer findMiddleValue(List<Integer> i) {
        return i.get(i.size() / 2);
    }

    private static boolean isInstructionValid(List<int[]> filteredRules, List<Integer> instruction) {
        return filteredRules.stream()
                .allMatch(r -> instruction.indexOf(r[0]) < instruction.indexOf(r[1]));
    }

    private static List<int[]> filterRules(List<int[]> rules, List<Integer> instruction) {
        return rules.stream()
                .filter(r -> instruction.contains(r[0]) && instruction.contains(r[1]))
                .toList();
    }

    private static Integer sumMiddleInvalidInstructionFixed(List<List<Integer>> instructions, List<int[]> rules) {
        return instructions.stream()
                .filter(i -> !isInstructionValid(filterRules(rules, i), i))
                .map(i -> fixInstruction(i, filterRules(rules, i)))
                .map(Day5::findMiddleValue)
                .reduce(0, Integer::sum);

    }

    private static List<Integer> fixInstruction(List<Integer> instruction, List<int[]> filteredRules) {

        List<Integer> combinedRule = new ArrayList<>();

        List<int[]> sortedRules = filteredRules.stream()
                .sorted(Comparator.comparing(r -> r[0]))
                .toList();
        for(int[] rule : sortedRules){
            combinedRule = processRule(rule, combinedRule);
        }


        verifyCombinedRule(combinedRule, filteredRules);

        return combinedRule;


    }


    private static List<Integer> processRule(int[] rule, List<Integer> combinedRule) {
        Optional<Integer> indexLeft = findIndex(rule[0], combinedRule);
        Optional<Integer> indexRight = findIndex(rule[1], combinedRule);

        if (indexLeft.isEmpty() && indexRight.isEmpty()) {
            combinedRule.add(rule[0]);
            combinedRule.add(rule[1]);
        } else {
            if (indexLeft.isPresent() && indexRight.isPresent()) {
                if (indexLeft.get() > indexRight.get()) {
                    combinedRule = moveLeftBeforeRight(indexLeft.get(), indexRight.get(), combinedRule);
                }

            } else {
                if(indexLeft.isPresent()){
                    combinedRule.add(indexLeft.get() + 1, rule[1]);
                }
                if(indexRight.isPresent()){
                    combinedRule.add(indexRight.get(), rule[0]);
                }
            }

        }
        return combinedRule;
    }

    private static void verifyCombinedRule(List<Integer> combinedRule, List<int[]> filteredRules) {
        boolean allRulesPass = filteredRules.stream()
                .allMatch(rule -> combinedRule.indexOf(rule[0]) < combinedRule.indexOf(rule[1]));

        if(!allRulesPass){
            System.out.println("Combined doesn't satisfy all tests");
            System.out.println(combinedRule);
            System.out.println(combinedRule.size());
            System.out.println("filtered rules:  " + filteredRules.size());
        }
    }

    private static List<Integer>  moveLeftBeforeRight(Integer indexLeft, Integer indexRight, List<Integer> combinedRule) {

        List<Integer> head = new ArrayList<>(combinedRule.subList(0, indexRight));
        List<Integer> middle = new ArrayList<>(combinedRule.subList(indexRight + 1, indexLeft));
        List<Integer> tail = new ArrayList<>(combinedRule.subList(indexLeft + 1, combinedRule.size()));
        head.add(combinedRule.get(indexLeft));
        head.add(combinedRule.get(indexRight));
        head.addAll(middle);
        head.addAll(tail);
        return head;

    }

    private static Optional<Integer> findIndex(int value, List<Integer> combinedRule) {
        int indexOf = combinedRule.indexOf(value);
        if(indexOf == -1){
            return Optional.empty();
        } else {
            return Optional.of(indexOf);
        }
    }


    private static void extractRulesAndInstructions(String path, List<int[]> rules, List<List<Integer>> instructions) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean readingRules = true;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    readingRules = false;
                    continue;
                }
                if (readingRules) {
                    String[] split = line.split("\\|");
                    rules.add(new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])});
                } else {
                    String[] split = line.split(",");

                    instructions.add(Arrays.stream(split).map(Integer::parseInt).toList());
                }
            }
        }
    }
}
