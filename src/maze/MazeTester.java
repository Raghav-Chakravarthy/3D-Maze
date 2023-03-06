package maze;

import utils.Direction;

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

        all room colors are unique
        art amounts
        test rootChamber and endChamber according to size
         */
        System.out.println("Maze generation tests:");
        generationTests();
    }
    private void generationTests() {
        int passed = 0;
        final int tests = 4;
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
        } else {
            System.out.println("room count from end is " + roomCount);
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
            System.out.println(out);
        }
    }
    public static void main(String[] args) {
        Maze toTest = new MazeGenerator("easy").getMaze();
        /*
        Chamber toModify = toTest.getSolutionChamber();
        Chamber[] adjacent = new Chamber[6];
        adjacent[0] = new Chamber();
        adjacent[1] = new Chamber();
        toModify.setChambers(adjacent);
        toTest.setChamber(new Coordinate(3,3,3), toModify);
         */
        MazeTester tester = new MazeTester(toTest);
        tester.runTests();
        OptimalSolver solution = new OptimalSolver(toTest);
        System.out.println(solution.getSolution());
    }
}
