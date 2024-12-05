import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {

    public static void main(String[] args) throws IOException {
        String path = "resources/day4.txt";


        char[][] txtArr = Utils.readFile(path);


        int xmasCount = couuntXmas(txtArr);
        System.out.println("Total XMAS: " + xmasCount);

        int xmasP2Count = countXmasP2(txtArr);
        System.out.println("Total XMAS P2: " + xmasP2Count);
    }

    private static int couuntXmas(char[][] txtArr) {
        int totalCount = 0;
        int lineCount = 0;
        int columnCount = 0;
        int diag1Count = 0;
        int diag2Count = 0;

        //Line count

        for (char[] line : txtArr) {
            String lineStr = String.valueOf(line);

            lineCount += countMatches(lineStr);
        }
//        System.out.println("Total in lines: " + lineCount);
        totalCount += lineCount;

        //Column search
        for (int i = 0; i < txtArr[0].length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < txtArr.length; j++) {

                sb.append(txtArr[j][i]);
            }
            String columnStr = sb.toString();

            columnCount += countMatches(columnStr);
        }

        totalCount += columnCount;
//        System.out.println("Total in columns: " + columnCount);

        //Diag Y = X search
        int lineSize = txtArr[0].length;
        int columnSize = txtArr.length;
        for (int k = 0; k < lineSize; k++) {
            int i = 0;
            int j = k;
            StringBuilder sb = new StringBuilder();
            while (inBounds(i, lineSize) && inBounds(j, columnSize)) {
                sb.append(txtArr[i][j]);
                i++;
                j--;
            }
            String diag = sb.toString();
//            System.out.println("Diag -> " + diag);
            diag1Count += countMatches(diag);
        }

        for (int k = 1; k < lineSize; k++) {
            int i = lineSize - 1;
            int j = k;
            StringBuilder sb = new StringBuilder();
            while (inBounds(i, lineSize) && inBounds(j, columnSize)) {
                sb.append(txtArr[i][j]);
                i--;
                j++;
            }
            String diag = sb.toString();
//            System.out.println("Diag -> " + diag);
            diag1Count += countMatches(diag);
        }

        totalCount += diag1Count;
//        System.out.println("Total in Diag y=x: " + diag1Count);


        //Diag y = -x search

        for (int k = 0; k < lineSize; k++) {
            int i = 0;
            int j = k;
            StringBuilder sb = new StringBuilder();
            while (inBounds(i, lineSize) && inBounds(j, columnSize)) {
                sb.append(txtArr[i][j]);
                i++;
                j++;
            }
            String diag = sb.toString();
//            System.out.println("Diag -> " + diag);
            diag2Count += countMatches(diag);
        }

        for (int k = lineSize - 2; k >= 0; k--) {
            int i = lineSize - 1;
            int j = k;
            StringBuilder sb = new StringBuilder();
            while (inBounds(i, lineSize) && inBounds(j, columnSize)) {
                sb.append(txtArr[i][j]);
                i--;
                j--;
            }
            String diag = sb.toString();
//            System.out.println("Diag -> " + diag);
            diag2Count += countMatches(diag);
        }

        totalCount += diag2Count;
//        System.out.println("Total in Diag y=-x: " + diag2Count);


        return totalCount;
    }

    private static int countXmasP2(char[][] txtArr) {

        int count = 0;

        int lineSize = txtArr[0].length;
        int columnSize = txtArr.length;
        for (int i = 0; i < lineSize - 2; i++) {
            for (int j = 0; j < columnSize - 2; j++) {
                char[][] chunk = new char[3][3];

                chunk[0] = Arrays.copyOfRange(txtArr[i], j, j + 3);
                chunk[1] = Arrays.copyOfRange(txtArr[i + 1], j, j + 3);
                chunk[2] = Arrays.copyOfRange(txtArr[i + 2], j, j + 3);
                if (isValidChunk(chunk)) {
                    count++;
                }
//                Utils.print2DArr(chunk);
            }
//            System.out.println("*********");
        }

        return count;
    }

    private static boolean isValidChunk(char[][] chunk) {
        if (chunk[1][1] != 'A') {
            return false;
        }
        char topLeft = chunk[0][0];
        char topRight = chunk[0][2];
        char bottomLeft = chunk[2][0];
        char bottomRight = chunk[2][2];
        if (
                ((topLeft == 'M' && bottomRight == 'S') || (topLeft == 'S' && bottomRight == 'M')) &&
                        ((topRight == 'M' && bottomLeft == 'S') || (topRight == 'S' && bottomLeft == 'M'))) {
            return true;
        }


        return false;
    }

    private static boolean inBounds(int i, int size) {
        return i >= 0 && i < size;
    }

    private static int countMatches(String str) {
        Pattern pattern = Pattern.compile("XMAS");
        Pattern pattern2 = Pattern.compile("SAMX");
        int count = 0;
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            count++;
        }
        Matcher matcher2 = pattern2.matcher(str);
        while (matcher2.find()) {
            count++;
        }
        return count;
    }
}
