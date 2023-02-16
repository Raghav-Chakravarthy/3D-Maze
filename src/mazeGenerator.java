import java.util.*;
public class MazeGenerator {
    private Maze generatedMaze;
    private boolean[][][] visited;
    private Coordinate[][][][] connections;
    public MazeGenerator(String difficulty){
        generatedMaze = new Maze(difficulty);
        if (difficulty.equals("easy")){
            connections = new Coordinate[4][4][4][6];
            visited = new boolean[4][4][4];
            easy(new Coordinate(0, 0, 0));
        }
        else if (difficulty.equals("medium")){
            connections = new Coordinate[5][5][5][6];
            visited = new boolean[5][5][5];
            medium(new Coordinate(0, 0, 0));
        }
        else{
            connections = new Coordinate[6][6][6][6];
            visited = new boolean[6][6][6];
            hard(new Coordinate(0, 0, 0));
        }
    }
    public void easy(Coordinate coord){

    }
    public void medium(Coordinate coord){

    }
    public void hard(Coordinate coord){
        visited[coord.getLevel()][coord.getRow()][coord.getColumn()] = true;
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                for (int k = -1; k <= 1; k++){
                    if (i == 0 && j == 0 && k == 0){
                        continue;
                    }
                    if (coord.getLevel() + i >= 0 && coord.getLevel() + i < 6 &&
                            coord.getRow() + j >= 0 && coord.getRow() + j < 6 &&
                            coord.getColumn() + k >= 0 && coord.getColumn() + k < 6){
                        if (!visited[coord.getLevel() + i][coord.getRow() + j][coord.getColumn() + k]){
                            //connections[coord.getLevel()][coord.getRow()][coord.getColumn()][i] = ne
                        }
                    }
                }
            }
        }
    }
    public Maze getMaze(){
        return generatedMaze;
    }
}
