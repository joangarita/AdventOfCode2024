import utils.Coordinate;
import utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Day8 {
    public static void main(String[] args) throws IOException {
        String path = "resources/day8.txt";
        char[][] grid = Utils.readFile(path);
        Utils.print2DArr(grid);

        HashMap<Character, List<Coordinate>> antennas = findAllAntennas(grid);

        removeAntiNodes(grid);

        antennas.values().stream()
                .map(Day8::findPairs)
                .flatMap(Collection::stream)
                .forEach(p -> calculateResonances(p.left, p.right, grid));



        Utils.print2DArr(grid);

        int count = countResonances(grid);
        System.out.println("Part 2: " + count);
    }

    private static int countResonances(char[][] grid) {
        int count = 0;
        for (char[] line : grid) {
            for (char c : line) {
                if (c == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    private static void removeAntiNodes(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '#'){
                    grid[i][j] = '.';
                }
            }
        }
    }

    private static List<Pair<Coordinate, Coordinate>> findPairs(List<Coordinate> list) {
        int size = list.size();
        List<Pair<Coordinate, Coordinate>> result = new ArrayList<>();
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                if(i > j){
                    result.add(Pair.of(list.get(i), list.get(j)));
                }
            }
        }
        return result;
    }

    private static HashMap<Character, List<Coordinate>> findAllAntennas(char[][] grid) {
        HashMap<Character, List<Coordinate>> antennas = new HashMap<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                char c = grid[i][j];
                if(c != '.' ){
                    List<Coordinate> antennaList = antennas.getOrDefault(c, new ArrayList<>());
                    antennas.put(c, antennaList);
                    antennaList.add(Coordinate.of(i,j));
                }
            }
        }
        return antennas;
    }

    private static void calculateResonances(Coordinate a1, Coordinate a2, char[][] grid) {
        int xSize = grid.length;
        int ySize = grid[0].length;

        int xDelta = a1.x -a2.x;
        int yDelta = a1.y - a2.y;

        Coordinate antiNode;
        int factor = 1;
        antiNode = Coordinate.of(a1.x + xDelta*factor, a1.y + yDelta*factor);
        while(Utils.inBounds(antiNode, xSize, ySize)){
            grid[antiNode.x][antiNode.y] = '#';
            factor++;
            antiNode = Coordinate.of(a1.x + xDelta*factor, a1.y + yDelta*factor);
//            break; //Uncomment for Part 1
        }

        factor = 1;
        antiNode = Coordinate.of(a2.x - xDelta*factor, a2.y - yDelta*factor);
        while(Utils.inBounds(antiNode, xSize, ySize)){
            grid[antiNode.x][antiNode.y] = '#';
            factor++;
            antiNode = Coordinate.of(a2.x - xDelta*factor, a2.y - yDelta*factor);
//            break; //Uncomment for Part 1
        }

        //comment this for part 1
        grid[a1.x][a1.y] = '#';
        grid[a2.x][a2.y] = '#';

    }

}
