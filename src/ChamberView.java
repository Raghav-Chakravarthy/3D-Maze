import javax.swing.*;
import rendering.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChamberView extends JPanel {
    private BackendEngine backendEngine;
    private Camera camera;
    private BufferedImage frameImage = new BufferedImage(720,720,BufferedImage.TYPE_INT_RGB);
    private BufferedImage headerImage = new BufferedImage(720,120,BufferedImage.TYPE_INT_ARGB);
    //TODO: everything...
    public ChamberView(/*Chamber chamber, */BackendEngine backendEngine){
        this.backendEngine = backendEngine;
    }
    private void setChamber(/*Chamber chamber*/){

    }
    private void moveForward(){

    }
    private void moveUp(){

    }
    private void moveDown(){

    }
    private void turnLeft(){

    }
    private void turnRight(){

    }
    private void drawArrows(BufferedImage image){

    }
    public void paintComponent(Graphics g){
        //render the current scene stuff
        //Header.drawHeader(headerImage)
        drawArrows(frameImage);
        g.drawImage(frameImage,0,0,null);
        g.drawImage(headerImage,0,0,null);
    }
}
