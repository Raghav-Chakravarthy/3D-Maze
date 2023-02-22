package frontend;

import maze.*;
import rendering.*;
import utils.*;
import backend.*;

import javax.swing.*;
import rendering.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;;


public class ChamberView extends JPanel {
    private BackendEngine backendEngine;
    private Camera camera = new Camera();
    private Scene scene;
    private BufferedImage frameImage = new BufferedImage(720,720,BufferedImage.TYPE_INT_RGB);
    private BufferedImage headerImage = new BufferedImage(720,120,BufferedImage.TYPE_INT_ARGB);
    //TODO: everything...
    public ChamberView(Chamber chamber, BackendEngine backendEngine){
        this.backendEngine = backendEngine;
        scene = new Scene(new Chamber[]{chamber});
        camera.setPosition(new Vector3(0,0,-2));
        this.repaint();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }
        });
    }


    private void moveForward(){
        //renders the new scene
        scene = new Scene(new Chamber[]{backendEngine.getChamber(),backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection())});
        double distanceRemaining = 2;
        double lastTime = System.nanoTime();
        //rendering loop
        while(distanceRemaining>0){
            double currentTime = System.nanoTime();
            //how far to move off of time since last frame, idk how to VSync but whatever
            double distanceMoved = (currentTime-lastTime)/500000000;
            distanceRemaining-=distanceMoved;
            //move
            if(backendEngine.getDirection()==Direction.NORTH){
                camera.translate(new Vector3(0,0,(float) distanceMoved));
            }else if(backendEngine.getDirection()==Direction.SOUTH){
                camera.translate(new Vector3(0,0,(float)(distanceMoved * -1)));
            }else if(backendEngine.getDirection()==Direction.EAST){
                camera.translate(new Vector3((float)distanceMoved,0,0));
            }else if(backendEngine.getDirection()==Direction.WEST){
                camera.translate(new Vector3((float)(distanceMoved*-1),0,0));
            }
            repaint();
            lastTime=currentTime;
        }
        //resets to a situation where there's only one scene
        backendEngine.move(backendEngine.getDirection());
        scene = new Scene(new Chamber[]{backendEngine.getChamber()});
        if(backendEngine.getDirection()==Direction.NORTH){
            camera.setPosition(new Vector3(0,0,-1));
        }else if(backendEngine.getDirection()==Direction.SOUTH){
            camera.setPosition(new Vector3(0,0,1));
        }else if(backendEngine.getDirection()==Direction.EAST){
            camera.setPosition(new Vector3(-1,0,0));
        }else if(backendEngine.getDirection()==Direction.WEST){
            camera.setPosition(new Vector3(1,0,0));
        }
        repaint();
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
        Graphics g = image.getGraphics();
        boolean drawForward=false,drawUp=false,drawDown=false;
        if(backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection())!=null){
            //forward arrow
            g.drawPolygon(new int[]{360, 375, 345},new int[]{360, (int) (360-(15*Math.sin(Math.PI/3))), (int) (360-(15*Math.sin(Math.PI/3)))},3);
        }
        if(backendEngine.getChamber().getAdjacentChamber(Direction.UP)!=null){
            //up arrow
        }
        if(backendEngine.getChamber().getAdjacentChamber(Direction.DOWN)!=null){
            //down arrow
        }
        //right and left arrows
    }
    public void paintComponent(Graphics g){
        rendering.Renderer.renderTo(scene, camera, frameImage);
        Header.drawHeader(headerImage,backendEngine.getMoves(),backendEngine.getChamber().getCoordinates(),backendEngine.getDirection());
        drawArrows(frameImage);
        g.drawImage(frameImage,0,0,null);
        g.drawImage(headerImage,0,0,null);
    }
}
