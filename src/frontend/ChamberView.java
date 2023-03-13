package frontend;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;

import javax.swing.*;

import backend.BackendEngine;
import maze.Chamber;
import maze.OptimalSolver;
import rendering.Camera;
import rendering.Scene;
import rendering.Vector3;
import utils.Direction;
import utils.BoundingBox;


public class ChamberView extends JPanel {
    private BackendEngine backendEngine;
    private Camera camera = new Camera(new Vector3(-2,0,0),0, 90);
    private Scene scene;
    private boolean moving, mouseHoverMap;
    private float endOpacity = 0;
    private BufferedImage frameImage = new BufferedImage(360,360,BufferedImage.TYPE_INT_RGB);
    private BufferedImage arrowImage = new BufferedImage(720,720,BufferedImage.TYPE_INT_ARGB);
    private BufferedImage headerImage = new BufferedImage(720,30,BufferedImage.TYPE_INT_ARGB);
   
    private BoundingBox leftArrowBounds = new BoundingBox(21, 425+120, 157, 336+80);
    private BoundingBox rightArrowBounds = new BoundingBox(568, 425+120, 700, 336+80);
    private BoundingBox forwardArrowBounds = new BoundingBox(291, 520, 424, 430);
    private BoundingBox downArrowBounds = new BoundingBox(305, 662, 415, 531);
    private BoundingBox upArrowBounds = new BoundingBox(305, 190, 415, 63);

    //Auto-solve stuff
    private int currentMove = 0;
    private String solution;
    private boolean autoSolve = false; //IMPORTANT: Set to false when not testing!

    public ChamberView(Chamber chamber, final BackendEngine backendEngine){
        if(autoSolve)
            this.solution = new OptimalSolver(backendEngine.getGameMaze()).getSolution();

        this.setPreferredSize(new Dimension(720,720));
        this.setFocusable(true);
        this.backendEngine = backendEngine;
        camera.setNearPlane(1F);
        scene = new Scene(new Chamber[]{chamber});
        this.repaint();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (!moving) {
                    if (upArrowBounds.inBounds(x, y) && (backendEngine.getChamber().getAdjacentChamber(Direction.UP) != null)) {
                        moveUp();
                    } else if (downArrowBounds.inBounds(x, y) && (backendEngine.getChamber().getAdjacentChamber(Direction.DOWN) != null)) {
                        moveDown();
                    } else if (leftArrowBounds.inBounds(x, y)) {
                        turnLeft();
                    } else if (rightArrowBounds.inBounds(x, y)) {
                        turnRight();
                    } else if (forwardArrowBounds.inBounds(x, y)
                            && ((backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection()) != null) ||
                            (backendEngine.getChamber().isLastDoor() && backendEngine.getDirection() == Direction.SOUTH))) {
                        moveForward();
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!moving) {
                    if (e.getX() >= 20 && e.getX() <= 130 && e.getY() >= 560 && e.getY() <= 670) {
                        backendEngine.changeView("mapview");
                    }
                }
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(!moving){
                    boolean changed = false;
                    if (e.getX() >= 20 && e.getX() <= 130 && e.getY() >= 560 && e.getY() <= 670) {
                        if (!mouseHoverMap){
                            changed = true;
                        }
                        mouseHoverMap = true;
                    }else{
                        if (mouseHoverMap){
                            changed = true;
                        }
                        mouseHoverMap = false;
                    }
                    if(changed){
                        repaint();
                    }
                }
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!moving){
                    if((e.getKeyCode()==KeyEvent.VK_SPACE)&&((backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection())!=null)||((backendEngine.getChamber().isLastDoor())&&(backendEngine.getDirection()==Direction.SOUTH)))){
                        moveForward();
                    } else if ((e.getKeyCode()==KeyEvent.VK_UP)&&(backendEngine.getChamber().getAdjacentChamber(Direction.UP)!=null)) {
                        moveUp();
                    } else if ((e.getKeyCode()==KeyEvent.VK_DOWN)&&(backendEngine.getChamber().getAdjacentChamber(Direction.DOWN)!=null)) {
                        moveDown();
                    } else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
                        turnLeft();
                    } else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
                        turnRight();
                    } else if (e.getKeyCode()== KeyEvent.VK_M){
                        backendEngine.changeView("mapview");
                    }
                }
            }
        });
        centerChamber();
        moveEnded();
    }
    private void moveForward(){
        //renders the new scene
        moving = true;
        if(!((backendEngine.getChamber().isLastDoor())&&(backendEngine.getDirection()==Direction.SOUTH))){
            scene = new Scene(new Chamber[]{backendEngine.getChamber(), backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection())});
        }
        //rendering loop
        final Timer frameTimer = new Timer(1000 / 60, null);

        frameTimer.addActionListener(new ActionListener() {
            double distanceRemaining = 3;
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();

                double distanceMoved = (currentTime- lastTime)/330000000;
                distanceRemaining -= distanceMoved;
                //move

                if(distanceRemaining<=0){
                    frameTimer.stop();
                    if((backendEngine.getChamber().isLastDoor())&&(backendEngine.getDirection()==Direction.SOUTH)){
                        endFade();
                    }else{
                        backendEngine.move(backendEngine.getDirection());
                        centerChamber();
                    }
                    moveEnded();
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
                //System.out.println(distanceRemaining);
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
                    moveEnded();
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
                //System.out.println(distanceRemaining);
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
                    moveEnded();
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
                    moveEnded();
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
                    moveEnded();
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
    private void endFade(){
        final Timer frameTimer = new Timer(1000/60,null);
        frameTimer.addActionListener(new ActionListener() {
            double opacityRemaining = 1;
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();
                opacityRemaining-=(currentTime-lastTime)/500000000;
                if(opacityRemaining<=0){
                    frameTimer.stop();
                    opacityRemaining = 0;
                    endOpacity = 1;
                    paintComponent(getGraphics());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    backendEngine.changeView("endview");
                }
                endOpacity= (float) (1-opacityRemaining);
                repaint();
                lastTime = currentTime;
            }
        });
        frameTimer.start();
    }
    public void moveEnded() {
        if(autoSolve && currentMove < solution.length()) {
            char move = solution.charAt(currentMove);
            System.out.println("Autosolver: Move " + (currentMove+1) + " of " + solution.length());
            if(move == 'U') {
                moveUp();
                System.out.println("Autosolver: Moving Up");
                currentMove++;
            } else if(move == 'D') {
                moveDown();
                System.out.println("Autosolver: Moving Down");
                currentMove++;
            } else {
                if(Direction.toString(backendEngine.getDirection()).charAt(0) != move) {
                    System.out.println("Autosolver: Turning to " + move);
                    turnRight();
                } else {
                    System.out.println("Autosolver: Moving forward");
                    moveForward();
                    currentMove++;
                }
            }
           
        }
    }
    
    private void drawArrows(BufferedImage image){
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setBackground(new Color(0,0,0,0));
        g.clearRect(0,0,720,720);
        g.setColor(Color.RED);
        if(!moving){
            if((backendEngine.getChamber().getAdjacentChamber(backendEngine.getDirection())!=null)||((backendEngine.getChamber().isLastDoor())&&(backendEngine.getDirection()==Direction.SOUTH))){
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
            drawLeftArrow(g);
            drawRightArrow(g);

            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(mouseLocation,this);
            if (mouseLocation.getX() >= 20 && mouseLocation.getX() <= 130 && mouseLocation.getY() >= 560 && mouseLocation.getY() <= 670) {
                mouseHoverMap = true;
            }else{
                mouseHoverMap = false;
            }
            
            if (mouseHoverMap){
                g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"mapViewButtonOutlined.png").getImage(), 23, 563, null);
            }else{
                g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"mapViewButton.png").getImage(), 25, 565, null);
            }
            
        }
    }


    private void drawLeftArrow(Graphics2D g) {
        int x = 70;
        int y = 720/2+150;

        QuadCurve2D.Float curve = new QuadCurve2D.Float(x+50, y, x+51, y-50, x, y-50);
        g.setStroke(new BasicStroke(25));
        g.draw(curve);
        g.fillPolygon(new int[]{x-30, x+5, x+5},new int[]{y-50, y-25, y-75},3);
    }

    private void drawRightArrow(Graphics2D g) {
        int x = 720-70;
        int y = 720/2+150;

        QuadCurve2D.Float curve = new QuadCurve2D.Float(x-50, y, x-51, y-50, x, y-50);
        g.setStroke(new BasicStroke(25));
        g.draw(curve);
        g.fillPolygon(new int[]{x+30, x-5, x-5},new int[]{y-50, y-25, y-75},3);
    }

    public void paintComponent(Graphics g){
        rendering.Renderer.renderTo(scene, camera, frameImage);
        Header.drawHeader(headerImage,backendEngine.getMoves(),backendEngine.getChamber().getCoordinates(),backendEngine.getDirection());
        drawArrows(arrowImage);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g.drawImage(frameImage,0,0,720,720,null);
        g.drawImage(arrowImage,0,0,null);
        g.drawImage(headerImage,0,0,null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,endOpacity));
        g2d.fillRect(0,0,720,720);
    }
}
