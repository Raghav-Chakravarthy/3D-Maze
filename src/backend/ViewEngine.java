package backend;
import frontend.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import frontend.ChamberView;

public class ViewEngine{
    private JFrame frame;
    private JPanel mainPanel, currentPanel, introDisplay, chamberDisplay, mapDisplay, endDisplay;
    private String gameView;

    public ViewEngine(){
        setup();
    }

    public void setup(){
        frame = new JFrame();
        gameView = "mainview";
        //introDisplay = new MenuView();
        mainPanel.setLayout(null);
		mainPanel.setSize(frame.getSize());
        //mainPanel.add(introDisplay);
		frame.add(mainPanel);
		frame.setLayout(null);
		frame.setVisible(true);
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
        mainPanel.remove(introDisplay);
        mainPanel.add(chamberDisplay);
    } 

    private void chamberViewToMapView(){
        mainPanel.remove(chamberDisplay);
        mainPanel.add(mapDisplay);
    }

    private void mapViewToChamberView(){
        mainPanel.remove(mapDisplay);
        mainPanel.add(chamberDisplay);
    }

    private void chamberViewToEndView(){
        endDisplay = new EndView();
        mainPanel.remove(chamberDisplay);
        mainPanel.add(endDisplay);
    }

    private void endViewToClose(){
        frame.dispose();
    }
}