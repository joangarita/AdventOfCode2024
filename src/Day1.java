import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {
    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) throws IOException {

        List<Integer> exampleL1 = List.of(3, 4, 2, 1, 3, 3);
        List<Integer> exampleL2 = List.of(4, 3, 5, 3, 9, 3);

        List<List<String>> records = readCsvFile("resources/day1.csv");
        // build to lists
        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();
        for (List<String> line : records) {
            firstList.add(Integer.valueOf(line.getFirst()));
            secondList.add(Integer.valueOf(line.getLast()));
        }
        //sort the lists in ascending order
        firstList.sort(Collections.reverseOrder());
        secondList.sort(Collections.reverseOrder());


        int total = 0;
        for (int i = 0; i < firstList.size(); i++) {
            int difference = Math.abs(firstList.get(i) - secondList.get(i));
            total += difference;
        }
        System.out.println("part 1: " + total);


        System.out.println("part2: " + similarityScore(firstList, secondList));


    }
    public static Long similarityScore(List<Integer> list1, List<Integer> list2 ){
        Map<Integer, Long> list2Map = list2.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return list1.stream()
                .map(number -> number * list2Map.getOrDefault(number, 0L))
                .reduce(0L, Long::sum);
    }

    public static List<List<String>> readCsvFile(String path) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                    .toList();
        }
    }
}
