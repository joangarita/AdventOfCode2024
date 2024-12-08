import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day6 {


    public static void main(String[] args) throws IOException {
        String path = "resources/day6.txt";

        char[][] grid = Utils.readFile(path);
        int xSize = grid.length;
        int ySize = grid[0].length;

        char[][] firstTraversalGrid = copyGrid(grid);
        int posVisited = processRoute(firstTraversalGrid);

        System.out.println("Part 1: " +posVisited);

        int infiniteLoops = 0;

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if(List.of('-', '|', '+').contains(firstTraversalGrid[i][j]) && grid[i][j] == '.'){
                    char[][] copy = copyGrid(grid);
                    copy[i][j] = '#';
                    int count = processRoute(copy);
                    if(count == -1){
                        infiniteLoops++;
                    }
                }
            }
        }


        System.out.println("Part 2: " + infiniteLoops);

    }

    private static char[][] copyGrid(char[][] grid) {
        char[][] copy = new char[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        return copy;
    }

    private static int processRoute(char[][] grid) {
        int xSize = grid.length;
        int ySize = grid[0].length;

        List<Character> guardMarkers = List.of('<', '>', '^', 'v');

        Coordinate guardCoor = findGuard(guardMarkers, grid);
        char guardDirection = grid[guardCoor.x][guardCoor.y];
//        System.out.println("************");
//        Utils.print2DArr(grid);
        char existingMarker = '.';

        Coordinate nextStep = findDesiredNextStep(guardDirection, guardCoor);
        int movCount = 0;
        while (inBound(nextStep.x, nextStep.y, xSize, ySize)) {
            if(movCount > xSize*ySize*2){
                return -1;
            }
            int rotCount = 0;
            while (rotCount < 4) {
                if (grid[nextStep.x][nextStep.y] != '#') {

                    char newMarker = calculateMarker(existingMarker, guardDirection);
                    if(rotCount > 0){
                        newMarker = '+';
                    }

                    grid[guardCoor.x][guardCoor.y] = newMarker;
                    existingMarker = grid[nextStep.x][nextStep.y];
                    grid[nextStep.x][nextStep.y] = guardDirection;
                    guardCoor.update(nextStep);
                    nextStep = findDesiredNextStep(guardDirection, guardCoor);
                    movCount++;
                    break;
                }

//                System.out.println("************");
//                Utils.print2DArr(grid);
                rotateRightDesPos(guardCoor, nextStep);
                guardDirection = switch (guardDirection) {
                    case '>' -> 'v';
                    case '<' -> '^';
                    case '^' -> '>';
                    case 'v' -> '<';
                    default -> throw new RuntimeException("Invalid guard direction");
                };
                rotCount++;
            }

        }
        grid[guardCoor.x][guardCoor.y] = calculateMarker(existingMarker, guardDirection);

//        System.out.println("************");
//        Utils.print2DArr(grid);

        return calculatePosVisited(grid);
    }

    private static char calculateMarker(char existingMarker, char guardDirection) {
        if(existingMarker == '+'){
            return '+';
        }
        List<Character> yDirMarks = List.of('<', '>');
        char newMarker = yDirMarks.contains(guardDirection)?'-':'|';
        if(List.of('-', '|').contains(existingMarker) && existingMarker != newMarker){
            newMarker = '+';
        }
        return newMarker;
    }


    private static int calculatePosVisited(char[][] grid) {
        int couunt = 0;
        for (char[] line : grid) {
            for (char c : line) {
                if (List.of('-', '|', '+').contains(c)) {
                    couunt++;
                }
            }
        }
        return couunt;
    }

    private static Coordinate findDesiredNextStep(char guardDirection, Coordinate guardCoor) {
        return switch (guardDirection) {
            case '>' -> Coordinate.of(guardCoor.x, guardCoor.y + 1);
            case '<' -> Coordinate.of(guardCoor.x, guardCoor.y - 1);
            case '^' -> Coordinate.of(guardCoor.x - 1, guardCoor.y);
            case 'v' -> Coordinate.of(guardCoor.x + 1, guardCoor.y);
            default -> throw new RuntimeException("Invalid guard direction");
        };
    }

    private static Coordinate findGuard(List<Character> guardMarkers, char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (guardMarkers.contains(grid[i][j])) {
                    return Coordinate.of(i, j);
                }
            }
        }
        return Coordinate.of(-1, -1);
    }

    public static boolean inBound(int x, int y, int xSize, int ySize) {
        return ((x >= 0) && (x < xSize)) && ((y >= 0) && (y < ySize));
    }


    public static class Coordinate {
        int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public static Coordinate of(int x, int y) {
            return new Coordinate(x, y);
        }

        public void update(Coordinate c) {
            this.x = c.x;
            this.y = c.y;
        }

        public void update(int x, int y) {
            this.x = x;
            this.y = y;
        }


    }

    public static void rotateRightDesPos(Coordinate currPos, Coordinate desPos) {

        int yDiff = currPos.y - desPos.y;
        int xDiff = currPos.x - desPos.x;
        desPos.update(currPos.x - yDiff, currPos.y + xDiff);
    }
}
