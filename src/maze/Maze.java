package maze;
public class Maze {
    private int mazeSize;
    private Chamber[][][] chambers;
    private int moves;
    private Chamber rootChamber;
    private Chamber solutionChamber;
    public Maze(String difficulty){
        if (difficulty.equals("easy")){
            chambers = new Chamber[4][4][4];
        }
        else if (difficulty.equals("medium")){
            chambers = new Chamber[5][5][5];
        }
        else{
            chambers = new Chamber[6][6][6];
        }
    }
    public Chamber getChamberAt(Coordinate coord){
       return null;
    }
    public Chamber[][] getLevel(int z){
        return null;
    }
    public Chamber getRootChamber(){
        return rootChamber;
    }
    public Chamber getSolutionChamber(){
        return solutionChamber;
    }
    public int getMoves(){
        return moves;
    }
    public Chamber setChamber(Coordinate coord){
        return null;
    }
}
