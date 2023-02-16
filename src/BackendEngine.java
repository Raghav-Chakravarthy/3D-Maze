public class BackendEngine {
    private static ViewEngine viewEngine;
    private static BackendEngine backend;

    public BackendEngine(){
        viewEngine = new ViewEngine();
    }
    public static void main(String[] args){
        backend = new BackendEngine();
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
