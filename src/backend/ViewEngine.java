package backend;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import frontend.*;

public class ViewEngine{
    private JFrame frame;
    private JPanel mainPanel, introDisplay, chamberDisplay, mapDisplay, endDisplay;
    private String gameView;
    private BackendEngine backend;
    
    public ViewEngine(BackendEngine backend){
        this.backend = backend;
        setup();
    }

    public void setup(){
        frame = new JFrame();
        mainPanel = new JPanel();
        gameView = "mainview";
		mainPanel.setPreferredSize(new Dimension(720,720));
        introDisplay = new MenuView(backend);
        introDisplay.setSize(720,720);
        frame.setContentPane(introDisplay);
        frame.pack();
        introDisplay.requestFocusInWindow();
		frame.setLayout(null);
        frame.setTitle("3D Maze");
        frame.pack();
		frame.setVisible(true);
        frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setChamberView(ChamberView chamberView){
        this.chamberDisplay = chamberView;
    }

    public void setMapView(MapView mapView){
        this.mapDisplay = mapView;
    }

    public JPanel getChamberView(){
        return this.chamberDisplay;
    }

    public void changeView(String newView){
        if(newView.equals("chamberview")){
            if(gameView.equals("mainview")){
                mainViewToChamberView();
            } else if(gameView.equals("mapview")){
                mapViewToChamberView();
            }
        } else if(newView.equals("mapview")){
            if(gameView.equals("chamberview")){
                chamberViewToMapView();
            }

        } else if(newView.equals("endview")){
            if(gameView.equals("chamberview")){
                chamberViewToEndView();
            }

        } else if(newView.equals("close")){
            if(gameView.equals("endview")){
                endViewToClose();
            }
        } 
    }

    public String getGameView(){
        return gameView;
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
        frame.dispose();
    }
}