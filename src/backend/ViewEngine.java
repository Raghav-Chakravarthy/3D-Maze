package backend;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import frontend.*;

public class ViewEngine{
    private JFrame frame;
    private JPanel splashDisplay, mainDisplay, chamberDisplay, mapDisplay, endDisplay;
    private String gameView;
    private BackendEngine backend;
    
    public ViewEngine(BackendEngine backend){
        this.backend = backend;
        setup();
    }

    public void setup(){
        frame = new JFrame();
        gameView = "splashview";
        splashDisplay = new SplashView(backend);
        frame.setContentPane(splashDisplay);
        frame.pack();
		frame.setLayout(null);
        frame.setTitle("3D Maze");
        frame.pack();
		frame.setVisible(true);
        frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setMainView(MenuView menuView){
        this.mainDisplay = menuView;
    }

    public void setChamberView(ChamberView chamberView){
        this.chamberDisplay = chamberView;
    }

    public void setMapView(MapView mapView){
        this.mapDisplay = mapView;
    }

    public void changeView(String newView){
        if(newView.equals("mainview")){
            gameView = "mainview";
            splashViewToMainView();
        } else if(newView.equals("chamberview")){
            if(gameView.equals("mainview")){
                gameView = "chamberview";
                mainViewToChamberView();
            } else if(gameView.equals("mapview")){
                gameView = "chamberview";
                mapViewToChamberView();
            }
        } else if(newView.equals("mapview")){
            gameView = "mapview";
            chamberViewToMapView();
        } else if(newView.equals("endview")){
            gameView = "endview";
            chamberViewToEndView();
        } else if(newView.equals("close")){
            endViewToClose();
        } 
    }

    public String getGameView(){
        return gameView;
    }

    private void splashViewToMainView(){
        frame.setContentPane(mainDisplay);
        frame.pack();
        mainDisplay.requestFocusInWindow();
    }
    private void mainViewToChamberView(){
        frame.setContentPane(chamberDisplay);
        frame.pack();
        chamberDisplay.requestFocusInWindow();
    } 

    private void chamberViewToMapView(){
        frame.setContentPane(mapDisplay);
        frame.pack();
        mapDisplay.requestFocusInWindow();
    }

    private void mapViewToChamberView(){
        frame.setContentPane(chamberDisplay);
        frame.pack();
        chamberDisplay.requestFocusInWindow();
    }

    private void chamberViewToEndView(){
        endDisplay = new EndView(backend.getScore(), backend);
        frame.setContentPane(endDisplay);
        frame.pack();
        endDisplay.requestFocusInWindow();
    }

    private void endViewToClose(){
        frame.setVisible(false);
        frame.dispose();
        System.exit(0);
    }
}