import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public static void main(String[] args) throws IOException {

        String path = "resources/day3.txt";

        List<List<Integer>> validMuls = extractValidMuls(path);
        System.out.println("Result P1: " + calculate(validMuls));

        List<List<Integer>> validMulsDoes = extractValidMulsDoes(path);
        System.out.println("Result p2: " + calculate(validMulsDoes));

    }

    private static Integer calculate(List<List<Integer>> mulsList) {
        return mulsList.stream()
                .map(l -> l.get(0) * l.get(1))
                .reduce(0, Integer::sum);
    }

    private static List<List<Integer>> extractValidMuls(String path) throws IOException {

        String fullTxt = Utils.extractFullTxt(path);
        return extractMuls(fullTxt);
    }

    private static List<List<Integer>> extractMuls(String txt) {

        String regexPattern = "mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regexPattern);

        List<List<Integer>> validMuls = new ArrayList<>();
        Matcher matcher = pattern.matcher(txt);
        while (matcher.find()) {
            Integer num1 = Integer.valueOf(matcher.group(1));
            Integer num2 = Integer.valueOf(matcher.group(2));
            validMuls.add(List.of(num1, num2));

        }
        return validMuls;
    }

    private static List<List<Integer>> extractValidMulsDoes(String path) throws IOException {

        String fullTxt = Utils.extractFullTxt(path);
        return findValidMulsDoes(fullTxt);

    }


    private static List<List<Integer>> findValidMulsDoes(String txt) {

        String doRegex = "don't\\(\\).*?do\\(\\)";
        String doRegex2 = "don't\\(\\).*$";

        txt = txt.replaceAll(doRegex, "");
        txt = txt.replaceAll(doRegex2, "");

        return extractMuls(txt);
    }
}
