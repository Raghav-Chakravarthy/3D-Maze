package backend;

import frontend.ChamberView;
import frontend.MapView;
import frontend.MenuView;
import maze.Chamber;
import maze.Coordinate;
import maze.Maze;
import maze.MazeGenerator;
import maze.OptimalSolver;
import utils.Direction;

public class BackendEngine {

    private ViewEngine viewEngine;
    private int currentMoves = 0, direction = Direction.EAST;
    private Maze gameMaze;
    private Chamber currentChamber;
    private String difficulty;

    public BackendEngine(){
        viewEngine = new ViewEngine(this);
    }
    
    public static void main(String[] args){
        BackendEngine backend = new BackendEngine();
    }

    public int getMoves(){
        return currentMoves;
    }

    public int getDirection(){
        return direction;
    }

    public Chamber getChamber(){
        return currentChamber;
    }

    public Chamber getSolutionChamber(){
        return gameMaze.getSolutionChamber();
    }

    public int getScore(){
        return (int) (10000 * ((double) this.gameMaze.getMoves() / (double) this.getMoves()));
    }

    public void setChamber(Chamber chamber){
        currentChamber = chamber;
    }

    public String getDifficulty(){
        return this.difficulty;
    }

    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
        MazeGenerator m = new MazeGenerator(difficulty);
        this.gameMaze = m.getMaze();
        this.currentChamber = this.gameMaze.getChamberAt(new Coordinate(0,0,0));
        currentChamber.setVisited(true);
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    public void move(int direction){
        currentMoves += 1;
        currentChamber = currentChamber.getAdjacentChamber(direction);
        currentChamber.setVisited(true);
    }

    public Chamber[][] getLevel(int level){
        return this.gameMaze.getLevel(level);
    }

    public void changeView(String newView) {
        if(newView.equals("mainview")){
            this.viewEngine.setMainView(new MenuView(this));
            this.viewEngine.changeView("mainview");
        } else if(newView.equals("chamberview")){
            if(viewEngine.getGameView().equals("mainview")){
                this.viewEngine.setChamberView(new ChamberView(this.gameMaze.getChamberAt(new Coordinate(0,0,0)), this));
                this.viewEngine.changeView("chamberview");
            } else if(viewEngine.getGameView().equals("mapview")){
                this.viewEngine.changeView("chamberview");
            }
        } else if(newView.equals("mapview")){
            this.viewEngine.setMapView(new MapView(this));
            this.viewEngine.changeView("mapview");
        } else if(newView.equals("endview")){
            this.viewEngine.changeView("endview");
        } else if(newView.equals("close")){
            this.viewEngine.changeView("close");
        }
    }

    public Maze getGameMaze() {
        return gameMaze;
    }
}
