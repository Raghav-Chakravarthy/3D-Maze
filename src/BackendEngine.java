public class BackendEngine {

    private ViewEngine viewEngine;
    private int currentMoves = 0, direction = Direction.EAST, minMoves, finalScore;
    private Maze gameMaze;
    private Chamber currentChamber, solutionChamber;
    private String gameMode;

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

    public void setDirection(int Direction){
        this.direction = Direction;
    }

    public void move(int direction){
        currentMoves += 1;
        currentChamber = currentChamber.getAdjacentChamber(direction);
    }

    // Create maze
    public void startMaze(String difficulty){
        //TODO: Do once generator done
    }

    public Chamber getChamber(){
        return currentChamber;
    }

    public void changeView(String newView){
        if(newView.equals("chamberview")){
            if(viewEngine.getGameView().equals("mainview")){
            } else if(viewEngine.getGameView().equals("mapview")){
            }
        } else if(newView.equals("mapview")){
            if(viewEngine.getGameView().equals("chamberview")){
            }
        } else if(newView.equals("endview")){
            if(viewEngine.getGameView().equals("chamberview")){
            }
        } else if(newView.equals("close")){
            if(viewEngine.getGameView().equals("endview")){
            }
        } 
    }
}
