import javax.swing.JFrame;
import javax.swing.JPanel;
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

    private void mainViewToChamberView(){
        mainPanel.remove(introDisplay);
        //chamberView = new ChamberView();
        //mainPanel.add(chamberView);
    } 

    private void chamberViewToMapView(){
        //mainPanel.remove(chamberView);
        //mapView = new MapView();
        //mainPanel.add(mapView);
    }

    private void mapViewToChamberView(){
        //mainPanel.remove(mapView);
        //mainPanel.add(chamberView);
    }

    private void chamberViewToEndView(){
        //endView = new EndView();
        //mainPanel.remove(chamberView);
        //mainPanel.add(endView);
    }

    private void endViewToClose(){
        frame.dispose();
    }
}