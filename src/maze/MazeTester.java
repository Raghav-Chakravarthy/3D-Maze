package maze;

import utils.Direction;

import java.awt.*;

public class MazeTester {
    private Maze testMaze;
    public MazeTester(Maze testMaze) {
        this.testMaze = testMaze;
    }
    private void runTests() {
        /*
        test it can be solved
        test all edges are bidirectional
        test end room only has one path
        test all room are included in maze
        test rootChamber and endChamber according to size

        all room colors are unique
        art amounts
         */
        generationTests();
        colorTests();
    }
    private void generationTests() {
        int passed = 0;
        final int tests = 5;
        //test whether bfs finds end node
        OptimalSolver solver = new OptimalSolver(testMaze);
        boolean solved = solver.bfs();
        if (solved) {
            passed++;
        }
        //test each chamber for bidirectional edges
        int n = testMaze.getSolutionChamber().getCoordinates().getLevel();
        boolean bidirectional = true;
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                for (int c = 0; c < n; c++) {
                    Chamber current = testMaze.getChamberAt(new Coordinate(i,k,c));
                    for (int index = 0; index < 6; index++) {
                        Chamber other = current.getChambers()[index];
                        if (other != null) {
                            int otherIndex;
                            if (index == Direction.NORTH) {
                                otherIndex = Direction.SOUTH;
                            } else if (index == Direction.SOUTH) {
                                otherIndex = Direction.NORTH;
                            } else if (index == Direction.EAST) {
                                otherIndex = Direction.WEST;
                            } else if (index == Direction.WEST) {
                                otherIndex = Direction.EAST;
                            } else if (index == Direction.UP) {
                                otherIndex = Direction.DOWN;
                            } else {
                                otherIndex = Direction.UP;
                            }
                            //chamber one's reference with index should match chamber's two otherIndex
                            if (other.getChambers()[otherIndex] == null) {
                                bidirectional = false;
                                break;
                            }
                            if (other.getChambers()[otherIndex].getCoordinates() != current.getCoordinates()) {
                                bidirectional = false;
                            }
                        }
                    }
                }
            }
        }
        if (bidirectional) {
            passed++;
        }
        //test end room only has one path
        int roomCount = 0;
        boolean onlyOnePath = false;
        for (Chamber other : testMaze.getSolutionChamber().getChambers()) {
            if (other != null) {
                roomCount++;
            }
        }
        if (roomCount == 1) {
            onlyOnePath = true;
            passed++;
        }
        //test all rooms are included
        boolean allIncluded = true;
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                for (int c = 0; c < n; c++) {
                    Chamber current = testMaze.getChamberAt(new Coordinate(i,k,c));
                    boolean hasConnection = false;
                    for (Chamber other : current.getChambers()) {
                        if (other != null) {
                            hasConnection = true;
                        }
                    }
                    if (!hasConnection) {
                        allIncluded = false;
                    }
                }
            }
        }
        if (allIncluded) {
            passed++;
        }
        //test root and sol chambers
        boolean rootAndSolution = true;
        Coordinate rootCoord = testMaze.getRootChamber().getCoordinates();
        if (rootCoord.getLevel() != 0 || rootCoord.getRow() != 0 || rootCoord.getColumn() != 0) {
            rootAndSolution = false;
        }
        Coordinate endCoord = testMaze.getSolutionChamber().getCoordinates();
        if (endCoord.getLevel() != n || endCoord.getRow() != n || endCoord.getColumn() != n) {
            rootAndSolution = false;
        }
        if (rootAndSolution) {
            passed++;
        }
        System.out.println("Generation tests: " + passed + "/" + tests);
        if (passed < tests) {
            String out = "Failed ";
            if (!solved) {
                out += "Solve test ";
            }
            if (!bidirectional) {
                out += "Bidirectional test ";
            }
            if (!onlyOnePath) {
                out += "Only One Path Test ";
            }
            if (!allIncluded) {
                out += "All included test";
            }
            if (!rootAndSolution) {
                out += "Root and Solution Chambers Test";
            }
            System.out.println(out);
        }
    }
    public void colorTests() {
        //unique colors and top half of rgb
        //unique art/ art amounts
        int passed = 0;
        final int tests = 2;
        int n = testMaze.getSolutionChamber().getCoordinates().getLevel();

        boolean brightColors = true;
        boolean noRepetition = true;
        boolean[][][] foundColors = new boolean[257][257][257];
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                for (int c = 0; c < n; c++) {
                    Color curr = testMaze.getChamberAt(new Coordinate(i,k,c)).getWallColor();
                    int[] rgb = {curr.getRed(),curr.getGreen(),curr.getBlue()};
                    if (foundColors[rgb[0]][rgb[1]][rgb[2]]) {
                        noRepetition = false;
                    }
                    foundColors[rgb[0]][rgb[1]][rgb[2]] = true;
                    for (int ind = 0; ind < 3; ind++) {
                        if (rgb[ind] < 128 || rgb[ind] > 256) {
                            brightColors = false;
                        }
                    }
                }
            }
        }
        if (brightColors) {
            passed++;
        }
        if (noRepetition) {
            passed++;
        }
        System.out.println("Color tests: " + passed + "/" + tests);
        String failMessage = "";
        if (!brightColors) {
            failMessage += "Colors not in top half of rgb, ";
        }
        if (!noRepetition) {
            failMessage += "Repeated wall colors";
        }
        System.out.println(failMessage);
    }
    public static void main(String[] args) {
        Maze toTest = new MazeGenerator("hard").getMaze();
        MazeTester tester = new MazeTester(toTest);
        tester.runTests();
    }
}
