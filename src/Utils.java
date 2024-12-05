import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Utils {

    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> readCsvFile(String path) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
                    .toList();
        }
    }

    public static char[][] readFile(String path) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            List<char[]> txtList = lines.map(String::toCharArray)
                    .toList();
            int columnCount = txtList.get(0).length;
            int rowCount = txtList.size();
            char[][] fileArr = new char[rowCount][columnCount];
            for (int i = 0; i < rowCount; i++) {
                System.arraycopy(txtList.get(i), 0, fileArr[i], 0, columnCount);
            }
            return fileArr;
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

    public static void print2DArr(char[][] arr) {
        for (char[] line : arr) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.print("\n");
        }
    }

}
