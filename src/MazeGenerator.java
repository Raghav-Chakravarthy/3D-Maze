public class MazeGenerator {
    private String difficulty;
    private Maze generatedMaze;
    public MazeGenerator(String difficulty){
        this.difficulty = difficulty;
        if (difficulty.equals("easy")){
            easy();
        }
        else if (difficulty.equals("medium")){
            medium();
        }
        else{
            hard();
        }
    }
    public void easy(){

    }
    public void medium(){

    }
    public void hard(){

    }
    public Maze getMaze(){
        return generatedMaze;
    }
}
