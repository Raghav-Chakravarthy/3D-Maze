package frontend;

import maze.*;
import rendering.*;
import utils.*;
import backend.*;

import javax.swing.*;
import rendering.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;;


public class ChamberView extends JPanel {
    private BackendEngine backendEngine;
    private Camera camera = new Camera(new Vector3(0,0,0),0, 90);
    private Scene scene;
    private BufferedImage frameImage = new BufferedImage(360,360,BufferedImage.TYPE_INT_RGB);
    private BufferedImage headerImage = new BufferedImage(720,120,BufferedImage.TYPE_INT_ARGB);
    //TODO: everything...
    public ChamberView(Chamber chamber, BackendEngine backendEngine){
        this.setFocusable(true);
        this.backendEngine = backendEngine;
        camera.setNearPlane(0.33F);
        scene = new Scene(new Chamber[]{chamber});
        this.repaint();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                turnLeft();
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    moveForward();
                } else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
                    turnLeft();
                } else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
                    turnRight();
                }
            }
        });

        centerChamber();

        //camera.translate(new Vector3(0,2,0));
    }


    private void moveForward(){
        //renders the new scene
       
        scene = new Scene(new Chamber[]{backendEngine.getChamber(), backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection())});
        
        System.out.println(backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection()));
        //rendering loop
        final Timer frameTimer = new Timer(1000 / 60, null);
        frameTimer.addActionListener(new ActionListener() {
            double distanceRemaining = 2;
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                double distanceMoved = (currentTime- lastTime)/500000000;
                distanceRemaining -= distanceMoved;
                //move

                if(distanceRemaining<=0){
                    frameTimer.stop();
                    centerChamber();
                }else{
                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.translate(new Vector3(0,0,(float) distanceMoved));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.translate(new Vector3(0,0,(float)distanceMoved*-1));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.translate(new Vector3((float)distanceMoved,0,0));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,0));
                    }
                    repaint();
                    lastTime = currentTime;
                }

            }
        });
        frameTimer.start();
        backendEngine.move(backendEngine.getDirection());

        //resets to a situation where there's only one moveFor
    }
    private void centerChamber(){
        scene = new Scene(new Chamber[]{backendEngine.getChamber()});
        if(backendEngine.getDirection()==Direction.NORTH){
            camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()));
            camera.setRotation(0,90);
        }else if(backendEngine.getDirection()==Direction.SOUTH){
            camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()));
            camera.setRotation(0,270);
        }else if(backendEngine.getDirection()==Direction.EAST){
            camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()));
            camera.setRotation(0,0);
        }else if(backendEngine.getDirection()==Direction.WEST){
            camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()));
            camera.setRotation(0,180);
        }
        repaint();
    }
    private void moveUp(){

    }
    private void moveDown(){

    }
    private void turnLeft(){
        final Timer frameTimer = new Timer(1000 / 60, null);
        frameTimer.addActionListener(new ActionListener() {
            double angleRemaining = 90;
            //double xRemaining = 1; double yRemaining = 1;
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                //double distanceMoved = (currentTime- lastTime)/1000000000;
                double angleMoved = (currentTime - lastTime)*90/1000000000;

                //xRemaining -= distanceMoved;
                //yRemaining -= distanceMoved;
                angleRemaining -= angleMoved;
                //move
                if(angleRemaining<=0){
                    frameTimer.stop();
                    if(backendEngine.getDirection()==Direction.NORTH){
                        backendEngine.setDirection(Direction.WEST);
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        backendEngine.setDirection(Direction.EAST);
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        backendEngine.setDirection(Direction.NORTH);
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        backendEngine.setDirection(Direction.SOUTH);
                    }
                    centerChamber();
                }else{
                    /*
                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.translate(new Vector3((float)distanceMoved,0,(float) distanceMoved));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,(float)distanceMoved*-1));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.translate(new Vector3((float)distanceMoved,0,(float)distanceMoved*-1));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,(float)distanceMoved));
                    }

                     */
                    System.out.println(angleMoved);
                    camera.setRotation((float) camera.getPitch(), (float) (camera.getYaw()+angleMoved));
                    System.out.println(camera.getYaw());
                    repaint();
                    lastTime = currentTime;
                }
            }
        });
        frameTimer.start();
    }
    private void turnRight(){
        final Timer frameTimer = new Timer(1000 / 60, null);
        frameTimer.addActionListener(new ActionListener() {
            double angleRemaining = 90;
            //double xRemaining = 1; double yRemaining = 1;
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                //double distanceMoved = (currentTime- lastTime)/1000000000;
                double angleMoved = (currentTime - lastTime)*90/1000000000;

                //xRemaining -= distanceMoved;
                //yRemaining -= distanceMoved;
                angleRemaining -= angleMoved;
                //move

                if(angleRemaining<=0){
                    frameTimer.stop();
                    if(backendEngine.getDirection()==Direction.NORTH){
                        backendEngine.setDirection(Direction.EAST);
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        backendEngine.setDirection(Direction.WEST);
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        backendEngine.setDirection(Direction.SOUTH);
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        backendEngine.setDirection(Direction.NORTH);
                    }
                    centerChamber();
                }else{
                    /*
                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,(float) distanceMoved));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.translate(new Vector3((float)distanceMoved,0,(float)distanceMoved*-1));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.translate(new Vector3((float)distanceMoved,0,(float)distanceMoved));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,(float)distanceMoved*-1));
                    }

                     */
                    System.out.println(camera.getYaw());
                    camera.setRotation((float) 0, (float) (camera.getYaw()-angleMoved));
                    System.out.println(camera.getYaw());
                    repaint();
                    lastTime = currentTime;
                }
            }
        });
        frameTimer.start();
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
        //Header.drawHeader(headerImage,backendEngine.getMoves(),backendEngine.getChamber().getCoordinates(),backendEngine.getDirection());
        //drawArrows(frameImage);
        g.drawImage(frameImage,0,0,720,720,null);
        //g.drawImage(headerImage,0,0,null);
    }
}
