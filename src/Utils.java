import utils.Coordinate;

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
        System.out.println("*******************");
        System.out.println("\n");
        for (char[] line : arr) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    public static char[][] copyGrid(char[][] grid) {
        char[][] copy = new char[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        return copy;
    }
    public static boolean inBounds(int i, int size) {
        return i >= 0 && i < size;
    }
    public static boolean inBounds(Coordinate coordinate, int xSize, int ySize) {
        return (coordinate.x >= 0 && coordinate.x < xSize) &&
                (coordinate.y >= 0 && coordinate.y < ySize) ;
    }



}
