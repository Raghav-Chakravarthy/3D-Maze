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
    private Camera camera = new Camera(new Vector3(-2,0,0),0, 90);
    private Scene scene;
    private boolean moving;
    private BufferedImage frameImage = new BufferedImage(360,360,BufferedImage.TYPE_INT_RGB);
    private BufferedImage arrowImage = new BufferedImage(720,720,BufferedImage.TYPE_INT_ARGB);
    private BufferedImage headerImage = new BufferedImage(720,30,BufferedImage.TYPE_INT_ARGB);
    //TODO: everything...
    public ChamberView(Chamber chamber, final BackendEngine backendEngine){
        this.setFocusable(true);
        this.backendEngine = backendEngine;
        camera.setNearPlane(1F);
        scene = new Scene(new Chamber[]{chamber});
        this.repaint();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!moving){
                    if((e.getKeyCode()==KeyEvent.VK_SPACE)&&(backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection())!=null)){
                        moveForward();
                    } else if ((e.getKeyCode()==KeyEvent.VK_UP)&&(backendEngine.getChamber().getAdjacentChamber(Direction.UP)!=null)) {
                        moveUp();
                    } else if ((e.getKeyCode()==KeyEvent.VK_DOWN)&&(backendEngine.getChamber().getAdjacentChamber(Direction.DOWN)!=null)) {
                        moveDown();
                    } else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
                        turnLeft();
                    } else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
                        turnRight();
                    }
                }
            }
        });
        centerChamber();
        //camera.translate(new Vector3(0,2,0));
    }
    private void moveForward(){
        //renders the new scene
        moving = true;
        scene = new Scene(new Chamber[]{backendEngine.getChamber(), backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection())});
        //rendering loop
        final Timer frameTimer = new Timer(1000 / 60, null);
        frameTimer.addActionListener(new ActionListener() {
            double distanceRemaining = 3;
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                double distanceMoved = (currentTime- lastTime)/330000000;
                distanceRemaining -= distanceMoved;
                //move

                if(distanceRemaining<=0){
                    frameTimer.stop();
                    backendEngine.move(backendEngine.getDirection());
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

        //resets to a situation where there's only one moveFor
    }
    private void centerChamber(){
        scene = new Scene(new Chamber[]{backendEngine.getChamber()});
        if(backendEngine.getDirection()==Direction.NORTH){
            camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(0,0,-2)));
            camera.setRotation(0,90);
        }else if(backendEngine.getDirection()==Direction.SOUTH){
            camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(0,0,2)));
            camera.setRotation(0,270);
        }else if(backendEngine.getDirection()==Direction.EAST){
            camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(-2,0,0)));
            camera.setRotation(0,0);
        }else if(backendEngine.getDirection()==Direction.WEST){
            camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(2,0,0)));
            camera.setRotation(0,180);
        }
        repaint();
        moving = false;
    }
    private void moveUp(){
        moving = true;
        //renders the new scene
        scene = new Scene(new Chamber[]{backendEngine.getChamber(), backendEngine.getChamber().getAdjacentChamber(Direction.UP)});
        //rendering loop
        final Timer frameTimerForward = new Timer(1000/60, null);
        final Timer frameTimerUp = new Timer(1000/60, null);
        final Timer frameTimerBackward = new Timer(1000/60,null);
        frameTimerForward.addActionListener(new ActionListener() {
            double distanceRemaining = 1;
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                double distanceMoved = (currentTime- lastTime)/250000000;
                distanceRemaining -= distanceMoved;
                //move

                if(distanceRemaining<=0){
                    frameTimerForward.stop();
                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(0,0,-1)));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(0,0,1)));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(-1,0,0)));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(1,0,0)));
                    }
                    frameTimerUp.start();
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
        frameTimerUp.addActionListener(new ActionListener() {
            double distanceRemaining = 2;
            double lastTime = -1;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastTime==-1){
                    lastTime=System.nanoTime();
                }
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                double distanceMoved = (currentTime- lastTime)/250000000;
                distanceRemaining -= distanceMoved;
                //move
                System.out.println(distanceRemaining);
                if(distanceRemaining<=0){
                    frameTimerUp.stop();
                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(0,2,-1)));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(0,2,1)));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(-1,2,0)));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(1,2,0)));
                    }
                    frameTimerBackward.start();
                }else{
                    camera.translate(new Vector3(0,(float) distanceMoved,0));
                    repaint();
                    lastTime = currentTime;
                }

            }
        });
        frameTimerBackward.addActionListener(new ActionListener() {
            double distanceRemaining = 1;
            double lastTime = -1;
            @Override

            public void actionPerformed(ActionEvent e) {
                if (lastTime==-1){
                    lastTime=System.nanoTime();
                }
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                double distanceMoved = (currentTime- lastTime)/250000000;
                distanceRemaining -= distanceMoved;
                //move

                if(distanceRemaining<=0){
                    frameTimerBackward.stop();
                    backendEngine.move(Direction.UP);
                    centerChamber();
                    moving=false;
                }else{
                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.translate(new Vector3(0,0,(float) distanceMoved*-1));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.translate(new Vector3(0,0,(float)distanceMoved));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,0));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.translate(new Vector3((float)distanceMoved,0,0));
                    }
                    repaint();
                    lastTime = currentTime;
                }

            }
        });
        frameTimerForward.start();

        //resets to a situation where there's only one moveFor
    }
    private void moveDown(){
        moving = true;
        //renders the new scene
        scene = new Scene(new Chamber[]{backendEngine.getChamber(), backendEngine.getChamber().getAdjacentChamber(Direction.DOWN)});
        //rendering loop
        final Timer frameTimerForward = new Timer(1000/60, null);
        final Timer frameTimerDown = new Timer(1000/60, null);
        final Timer frameTimerBackward = new Timer(1000/60,null);
        frameTimerForward.addActionListener(new ActionListener() {
            double distanceRemaining = 1;
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                double distanceMoved = (currentTime- lastTime)/250000000;
                distanceRemaining -= distanceMoved;
                //move

                if(distanceRemaining<=0){
                    frameTimerForward.stop();
                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(0,0,-1)));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(0,0,1)));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(-1,0,0)));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(1,0,0)));
                    }
                    frameTimerDown.start();
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
        frameTimerDown.addActionListener(new ActionListener() {
            double distanceRemaining = 2;
            double lastTime = -1;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastTime==-1){
                    lastTime=System.nanoTime();
                }
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                double distanceMoved = (currentTime- lastTime)/250000000;
                distanceRemaining -= distanceMoved;
                //move
                System.out.println(distanceRemaining);
                if(distanceRemaining<=0){
                    frameTimerDown.stop();
                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(0,-2,-1)));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(0,-2,1)));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(-1,-2,0)));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.setPosition(new Vector3(backendEngine.getChamber().getCoordinates()).add(new Vector3(1,-2,0)));
                    }
                    frameTimerBackward.start();
                }else{
                    camera.translate(new Vector3(0,(float) distanceMoved*-1,0));
                    repaint();
                    lastTime = currentTime;
                }

            }
        });
        frameTimerBackward.addActionListener(new ActionListener() {
            double distanceRemaining = 1;
            double lastTime = -1;
            @Override

            public void actionPerformed(ActionEvent e) {
                if (lastTime==-1){
                    lastTime=System.nanoTime();
                }
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                double distanceMoved = (currentTime- lastTime)/250000000;
                distanceRemaining -= distanceMoved;
                //move

                if(distanceRemaining<=0){
                    frameTimerBackward.stop();
                    backendEngine.move(Direction.DOWN);
                    centerChamber();
                    moving=false;
                }else{
                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.translate(new Vector3(0,0,(float) distanceMoved*-1));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.translate(new Vector3(0,0,(float)distanceMoved));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,0));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.translate(new Vector3((float)distanceMoved,0,0));
                    }
                    repaint();
                    lastTime = currentTime;
                }

            }
        });
        frameTimerForward.start();

        //resets to a situation where there's only one moveFor
    }
    private void turnLeft(){
        moving = true;
        final Timer frameTimer = new Timer(1000 / 60, null);
        frameTimer.addActionListener(new ActionListener() {
            double angleRemaining = 90;
            double distanceRemaining = 2;
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                double distanceMoved = (currentTime- lastTime)/500000000;
                double angleMoved = (currentTime - lastTime)*90/1000000000;

                distanceRemaining -= distanceMoved;
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
                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.translate(new Vector3((float)distanceMoved,0,(float) distanceMoved));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,(float)distanceMoved*-1));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.translate(new Vector3((float)distanceMoved,0,(float)distanceMoved*-1));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,(float)distanceMoved));
                    }

                    camera.setRotation((float) camera.getPitch(), (float) (camera.getYaw()+angleMoved));
                    repaint();
                    lastTime = currentTime;
                }
            }
        });
        frameTimer.start();
    }
    private void turnRight(){
        moving = true;
        final Timer frameTimer = new Timer(1000 / 60, null);
        frameTimer.addActionListener(new ActionListener() {
            double angleRemaining = 90;
            double distanceRemaining = 2;
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();
                //how far to move off of time since last frame, idk how to VSync but whatever

                double distanceMoved = (currentTime- lastTime)/500000000;
                double angleMoved = (currentTime - lastTime)*90/1000000000;

                distanceRemaining -= distanceMoved;
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

                    if(backendEngine.getDirection()==Direction.NORTH){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,(float) distanceMoved));
                    }else if(backendEngine.getDirection()==Direction.SOUTH){
                        camera.translate(new Vector3((float)distanceMoved,0,(float)distanceMoved*-1));
                    }else if(backendEngine.getDirection()==Direction.EAST){
                        camera.translate(new Vector3((float)distanceMoved,0,(float)distanceMoved));
                    }else if(backendEngine.getDirection()==Direction.WEST){
                        camera.translate(new Vector3((float)distanceMoved*-1,0,(float)distanceMoved*-1));
                    }


                    camera.setRotation((float) 0, (float) (camera.getYaw()-angleMoved));
                    repaint();
                    lastTime = currentTime;
                }
            }
        });
        frameTimer.start();
    }
    private void drawArrows(BufferedImage image){
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setBackground(new Color(0,0,0,0));
        g.clearRect(0,0,720,720);
        g.setColor(Color.RED);
        if(!moving){
            if(backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection())!=null){
                //forward arrow
                g.fillPolygon(new int[]{360, 405, 315},new int[]{450, (int) (450+(20*Math.sin(Math.PI/3))), (int) (450+(20*Math.sin(Math.PI/3)))},3);
                g.fillPolygon(new int[]{375,345,330,390},new int[]{(int) (450+(20*Math.sin(Math.PI/3))),(int) (450+(20*Math.sin(Math.PI/3))),500,500},4);
            }
            if(backendEngine.getChamber().getAdjacentChamber(Direction.UP)!=null){
                //up arrow
                g.fillPolygon(new int[]{360,395,325},new int[]{80,(int) (80+(45*Math.sin(Math.PI/3))),(int) (80+(45*Math.sin(Math.PI/3)))},3);
                g.fillPolygon(new int[]{377,343,343,377},new int[]{(int) (80+(45*Math.sin(Math.PI/3))),(int) (80+(45*Math.sin(Math.PI/3))),170,170},4);
            }
            if(backendEngine.getChamber().getAdjacentChamber(Direction.DOWN)!=null){
                //down arrow
                g.fillPolygon(new int[]{360,395,325},new int[]{720-80,(int) (720-(80+(45*Math.sin(Math.PI/3)))),(int) (720-(80+(45*Math.sin(Math.PI/3))))},3);
                g.fillPolygon(new int[]{377,343,343,377},new int[]{(int) (720-(80+(45*Math.sin(Math.PI/3)))),(int) (720-(80+(45*Math.sin(Math.PI/3)))),720-170,720-170},4);
            }
            //right and left arrows

        }
    }
    public void paintComponent(Graphics g){
        rendering.Renderer.renderTo(scene, camera, frameImage);
        Header.drawHeader(headerImage,backendEngine.getMoves(),backendEngine.getChamber().getCoordinates(),backendEngine.getDirection());
        drawArrows(arrowImage);
        g.drawImage(frameImage,0,0,720,720,null);
        g.drawImage(arrowImage,0,0,null);
        g.drawImage(headerImage,0,0,null);
    }
}
