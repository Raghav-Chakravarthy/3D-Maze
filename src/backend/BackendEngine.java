import frontend.ChamberView;
import maze.Maze;
import utils.Direction;
import maze.Chamber;
import maze.MazeGenerator;

package backend;

public class BackendEngine {

    private ViewEngine viewEngine;
    private int currentMoves = 0, direction = Direction.EAST, minMoves, finalScore;
    private Maze gameMaze;
    private Chamber currentChamber, solutionChamber;
    private String gameMode, difficulty;

    public BackendEngine(){
        viewEngine = new ViewEngine();
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

    public String getDifficulty(){
        return this.difficulty;
    }

    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
        MazeGenerator m = new MazeGenerator(difficulty);
        this.gameMaze = m.getMaze();
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    public void move(int direction){
        currentMoves += 1;
        currentChamber = currentChamber.getAdjacentChamber(direction);
    }

    public void changeView(String newView){
        if(newView.equals("chamberview")){
            if(viewEngine.getGameView().equals("mainview")){
                this.viewEngine.setChamberView(new ChamberView(this.gameMaze.getRootChamber(), this));
                this.viewEngine.changeView("chamberview");
            } else if(viewEngine.getGameView().equals("mapview")){
                this.viewEngine.changeView("chamberview");
            }
        } else if(newView.equals("mapview")){
            if(viewEngine.getGameView().equals("chamberview")){
                this.viewEngine.setMapView(new MapView(this.gameMaze.getLevel(this.currentChamber.getCoordinates().getLevel()), this));
                this.viewEngine.changeView("mapview");
            }
        } else if(newView.equals("endview")){
            if(viewEngine.getGameView().equals("chamberview")){
                this.viewEngine.changeView("endview");
            }
        } else if(newView.equals("close")){
            if(viewEngine.getGameView().equals("endview")){
                this.viewEngine.changeView("close");
            }
        } 
    }
}
