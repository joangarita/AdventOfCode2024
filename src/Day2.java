import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Day2 {

    public static void main(String[] args) throws IOException {
        List<List<Integer>> testData = Utils.readIntegersFromCsv("resources/day2Test.csv");
        List<List<Integer>> data = Utils.readIntegersFromCsv("resources/day2.csv");

        System.out.println("Test data:");
        System.out.println("Safe reports: " + countSafeReports(testData));
        System.out.println("Safe reports with dampener: " + countSafeReportsWithDampener(testData));

        System.out.println("Input data:");
        System.out.println("Safe reports: " + countSafeReports(data));
        System.out.println("Safe reports with dampener: " + countSafeReportsWithDampener(data));


    }

    private static Long countSafeReports(List<List<Integer>> data) {
        return data.stream()
                .filter(Day2::isReportSafe)
                .count();
    }

    private static boolean isReportSafe(List<Integer> r) {
        if (r.get(0).equals(r.get(1))) {
            return false;
        }
        Predicate<List<Integer>> predicate;
        if (r.get(0) < r.get(1)) {
            //Increasing
            predicate = v -> (v.get(0) > v.get(1));
        } else {
            //Decreasing
            predicate = v -> (v.get(0) < v.get(1));
        }

        for (int i = 1; i < r.size(); i++) {
            int previousValue = r.get(i - 1);
            int currValue = r.get(i);
            //Rule 1
            if (predicate.test(List.of(previousValue, currValue))) {
                return false;
            }

            //Rule 2
            int difference = Math.abs(previousValue - currValue);
            if (difference < 1 || difference > 3) {
                return false;
            }

        }
        return true;
    }

    private static Long countSafeReportsWithDampener(List<List<Integer>> data) {
        return data.stream()
                .filter(Day2::isReportSafeWithDampener)
                .count();
    }

    private static boolean isReportSafeWithDampener(List<Integer> report) {
//        System.out.println("test safe with dampening : " + report);
        if(isReportSafe(report)) {
            return true;
        } else {
            List<List<Integer>> possibilities = new ArrayList<>();
            for (int i = 0; i < report.size(); i++) {
                List<Integer> partialReport = new ArrayList<>(report);
                partialReport.remove(i);
                possibilities.add(partialReport);
            }
//            possibilities.forEach(p -> System.out.println("Poss -> " + p + " -> " + isReportSafe(p)));
            return possibilities.stream()
                    .anyMatch(Day2::isReportSafe);
        }
    }
}
