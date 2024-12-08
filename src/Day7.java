import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day7 {

    public static void main(String[] args) throws IOException {
        String path = "resources/day7.txt";
        List<Operation> operations = readOperations(path);


        Long calibrationResult = operations.stream()
                .filter(Day7::isValid)
                .map(o -> o.result)
                .reduce(0L, Long::sum);

        System.out.println("Part 1: " + calibrationResult);

    }

    private static boolean isValid(Operation operation) {
        List<char[]> possibleOperators = generateOperatorPossible(operation.operands.size() - 1);
        return possibleOperators.stream()
                .anyMatch(operators -> evaluateEquation(operation, operators));
    }

    private static List<char[]> generateOperatorPossible(int size) {
        List<Character> operators = List.of('+', '*', '|');
        Double pow = Math.pow(operators.size(), size);
        int totalPossible = pow.intValue();
        List<char[]> results = new ArrayList<>();
        for (int i = 0; i < totalPossible; i++) {
            String binaryStr = leftPad(asBase3(i), size, "0");
            binaryStr = binaryStr.replace("0", "+");
            binaryStr = binaryStr.replace("1", "*");
            binaryStr = binaryStr.replace("2", "|");
            results.add(binaryStr.toCharArray());
        }

        return results;
    }

    public static String asBase3(int num) {
        long ret = 0, factor = 1;
        while (num > 0) {
            ret += num % 3 * factor;
            num /= 3;
            factor *= 10;
        }
        return String.valueOf(ret);
    }

    private static String leftPad(String binaryString, int size, String character) {
        if (binaryString.length() < size) {
            String padding = character.repeat(size - binaryString.length());
            return padding.concat(binaryString);
        } else {
            return binaryString;
        }
    }

    private static boolean evaluateEquation(Operation operation, char[] operators) {
        List<Integer> operands = operation.operands;
        long partialResult = evaluatePartial(operands.get(0), operators[0], operands.get(1));
        int operatorIndex = 1;
        for (int i = 2; i < operands.size(); i++) {
            partialResult = evaluatePartial(partialResult, operators[operatorIndex], operands.get(i));
            operatorIndex++;
        }
        return operation.result == partialResult;
    }

    private static long evaluatePartial(long operand1, Character operator, long operand2) {
        return switch (operator) {
            case '+' -> operand1 + operand2;
            case '*' -> operand1 * operand2;
            case '|' -> Long.parseLong(String.valueOf(operand1).concat(String.valueOf(operand2)));
            default -> throw new RuntimeException("Invalid operator");
        };

    }


    public static List<Operation> readOperations(String path) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.map(line -> {
                        List<String> parts = Arrays.asList(line.split(":"));
                        List<Integer> operands = Arrays.asList(parts.get(1).trim().split("\\s")).stream()
                                .map(Integer::parseInt)
                                .toList();


                        return new Operation(Long.parseLong(parts.get(0)), operands);
                    })
                    .toList();
        }
    }

    public static class Operation {
        long result;
        List<Integer> operands;

        public Operation(long result, List<Integer> operands) {
            this.result = result;
            this.operands = operands;
        }
    }

}
