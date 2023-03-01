package maze;

import maze.Chamber;
import maze.Coordinate;
public class Maze {
    private int mazeSize;
    private Chamber[][][] chambers;
    private int moves;
    private Chamber rootChamber;
    private Chamber solutionChamber;
    public Maze(String difficulty){
        if (difficulty.equals("easy")){
            chambers = new Chamber[4][4][4];
            mazeSize=4;
        }
        else if (difficulty.equals("medium")){
            chambers = new Chamber[5][5][5];
            mazeSize=5;
        }
        else{
            chambers = new Chamber[6][6][6];
            mazeSize=6;
        }
    }
    public Chamber getChamberAt(Coordinate coord){
        return chambers[coord.getLevel()][coord.getRow()][coord.getColumn()];
    }
    public Chamber[][] getLevel(int z){
        return chambers[z];
    }
    public void setRootChamber(Chamber c) {
    	rootChamber = c;
    }
    public void setSolutionChamber(Chamber c) {
    	solutionChamber = c;
    }
    public Chamber getRootChamber(){
        return getChamberAt(new Coordinate(0,0,0));
    }
    public Chamber getSolutionChamber(){
        return getChamberAt(new Coordinate(mazeSize-1, mazeSize-1, mazeSize-1));
    }
    public void setMoves(int moves) {
    	this.moves = moves;
    }
    public int getMoves(){
        return moves;
    }
    public void setChamber(Coordinate coord, Chamber chamber){
        if(coord.equals(new Coordinate(mazeSize-1, mazeSize-1, mazeSize-1)))
            chamber.setLastDoor(true);
        
        chambers[coord.getLevel()][coord.getRow()][coord.getColumn()] = chamber;
    }
}