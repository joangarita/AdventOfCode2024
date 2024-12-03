import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Utils {

    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> readCsvFile(String path) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                    .toList();
        }
    }

    private static List<List<Integer>> transformToInt(List<List<String>> data) {
        return data.stream()
                .map(l -> l.stream().map(Integer::valueOf).toList())
                .toList();
    }

    public static List<List<Integer>> readIntegersFromCsv(String path) throws IOException {
        return transformToInt(readCsvFile(path));
    }

    public static String extractFullTxt(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            lines.forEach(sb::append);
        }
        return sb.toString();
    }

}
