import java.util.*;
public class MazeGenerator {
    private Maze generatedMaze;
    private boolean[][][] visited;
    private ArrayList<ArrayList<ArrayList<ArrayList<Coordinate>>>> connections;
    private ArrayList<Character> directions;
    public MazeGenerator(String difficulty){
        generatedMaze = new Maze(difficulty);
        connections = new ArrayList<>();
        directions = new ArrayList<Character>();
        directions.add('N');
        directions.add('E');
        directions.add('S');
        directions.add('W');
        directions.add('T');
        directions.add('B');
        if (difficulty.equals("easy")){
            visited = new boolean[4][4][4];
            for (int i = 0; i < 4; i++){
                connections.add(new ArrayList<>());
                for (int j = 0; j < 4; j++){
                    connections.get(i).add(new ArrayList<>());
                    for (int k = 0; k < 4; k++){
                        connections.get(i).get(j).add(new ArrayList<>());
                    }
                }
            }
            easy(new Coordinate(0, 0, 0));
        }
        else if (difficulty.equals("medium")){
            visited = new boolean[5][5][5];
            for (int i = 0; i < 5; i++){
                connections.add(new ArrayList<>());
                for (int j = 0; j < 5; j++){
                    connections.get(i).add(new ArrayList<>());
                    for (int k = 0; k < 5; k++){
                        connections.get(i).get(j).add(new ArrayList<>());
                    }
                }
            }
            medium(new Coordinate(0, 0, 0));
        }
        else{
            visited = new boolean[6][6][6];
            for (int i = 0; i < 5; i++){
                connections.add(new ArrayList<>());
                for (int j = 0; j < 5; j++){
                    connections.get(i).add(new ArrayList<>());
                    for (int k = 0; k < 5; k++){
                        connections.get(i).get(j).add(new ArrayList<>());
                    }
                }
            }
            hard(new Coordinate(0, 0, 0));
        }
    }
    public void easy(Coordinate coord){

    }
    public void medium(Coordinate coord){

    }
    public void hard(Coordinate coord){
        visited[coord.getLevel()][coord.getRow()][coord.getColumn()] = true;
        Collections.shuffle(directions);
        for (int i = 0; i < 6; i++){
            if (directions.get(i) == 'N'){
                if (coord.getRow() - 1 >= 0 &&!visited[coord.getLevel()][coord.getRow() - 1][coord.getColumn()]){
                    connections.get(coord.getLevel()).get(coord.getRow()).get(coord.getColumn()).add(
                            new Coordinate(coord.getLevel(), coord.getRow() - 1, coord.getColumn())
                    );
                    hard(new Coordinate(coord.getLevel(), coord.getRow() - 1, coord.getColumn()));
                }
            }
            if (directions.get(i) == 'E'){
                if (coord.getColumn() + 1 < 6 &&!visited[coord.getLevel()][coord.getRow()][coord.getColumn() + 1]){
                    connections.get(coord.getLevel()).get(coord.getRow()).get(coord.getColumn()).add(
                            new Coordinate(coord.getLevel(), coord.getRow(), coord.getColumn() + 1)
                    );
                    hard(new Coordinate(coord.getLevel(), coord.getRow(), coord.getColumn() + 1));
                }
            }
            if (directions.get(i) == 'S'){
                if (coord.getRow() + 1 < 6 &&!visited[coord.getLevel()][coord.getRow() + 1][coord.getColumn()]){
                    connections.get(coord.getLevel()).get(coord.getRow()).get(coord.getColumn()).add(
                            new Coordinate(coord.getLevel(), coord.getRow() + 1, coord.getColumn())
                    );
                    hard(new Coordinate(coord.getLevel(), coord.getRow() + 1, coord.getColumn()));
                }
            }
            if (directions.get(i) == 'W'){
                if (coord.getColumn() - 1 >= 0 &&!visited[coord.getLevel()][coord.getRow()][coord.getColumn() - 1]){
                    connections.get(coord.getLevel()).get(coord.getRow()).get(coord.getColumn()).add(
                            new Coordinate(coord.getLevel(), coord.getRow(), coord.getColumn() - 1)
                    );
                    hard(new Coordinate(coord.getLevel(), coord.getRow(), coord.getColumn() - 1));
                }
            }
            if (directions.get(i) == 'T'){
                if (coord.getLevel() - 1 >= 0 &&!visited[coord.getLevel() - 1][coord.getRow()][coord.getColumn()]){
                    connections.get(coord.getLevel()).get(coord.getRow()).get(coord.getColumn()).add(
                            new Coordinate(coord.getLevel() - 1, coord.getRow(), coord.getColumn())
                    );
                    hard(new Coordinate(coord.getLevel() - 1, coord.getRow(), coord.getColumn()));
                }
            }
            if (directions.get(i) == 'B'){
                if (coord.getLevel() + 1 < 6 && !visited[coord.getLevel() + 1][coord.getRow()][coord.getColumn()]){
                    connections.get(coord.getLevel()).get(coord.getRow()).get(coord.getColumn()).add(
                            new Coordinate(coord.getLevel() + 1, coord.getRow(), coord.getColumn())
                    );
                    hard(new Coordinate(coord.getLevel() + 1, coord.getRow(), coord.getColumn()));
                }
            }
        }
    }
    public Maze getMaze(){
        return generatedMaze;
    }
}
