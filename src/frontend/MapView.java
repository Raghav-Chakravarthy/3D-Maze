package frontend;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

import backend.*;
import maze.*;
import utils.*;

public class MapView extends JPanel {
    private final static int xBound1 = 175;
    //left edge right edge
    private final static int yBound1 = 175;
    private final static int mapSize = 540;
    private static int mapDim = 0;
    //number of chambers per side
    private static int edgeLength = 0;
    //Horizontal pixelage
    private boolean mouseHoverUp, mouseHoverDown, mouseHoverChamber;
    private int levelLabel;
    private BufferedImage headerImage = new BufferedImage(720,30,BufferedImage.TYPE_INT_ARGB);
    private Chamber[][] level;
    private BackendEngine backend;

    public MapView(final BackendEngine backend) {
        this.setPreferredSize(new Dimension(720,720));
        this.setFocusable(true);
        this.backend = backend;
        this.level = level;
        if (backend.getDifficulty().equals("easy")){
            mapDim = 4;
        }else if (backend.getDifficulty().equals("medium")){
            mapDim = 5;
        }else if (backend.getDifficulty().equals("hard")){
            mapDim = 6;
        }
        edgeLength = mapSize/mapDim;
        levelLabel = backend.getChamber().getCoordinates().getLevel();
        level = backend.getLevel(levelLabel);
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (e.getX() >= 78 && e.getX() <= 132 && e.getY() >= 366 && e.getY() <= 450) {
                    mouseHoverUp = true;
                } else if (e.getX() >= 18 && e.getX() <= 72 && e.getY() >= 366 && e.getY() <= 450) {
                    mouseHoverDown = true;
                } else if (e.getX() >= 20 && e.getX() <= 130 && e.getY() >= 560 && e.getY() <= 670) {
                    mouseHoverChamber = true;
                } else {
                    mouseHoverUp = false;
                    mouseHoverDown = false;
                    mouseHoverChamber = false;
                }
                repaint();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getX() >= 20 && e.getX() <= 130 && e.getY() >= 560 && e.getY() <= 670) {
                    openChamberView();
                }else if(e.getX() >= 18 && e.getX() <= 72 && e.getY() >= 366 && e.getY() <= 450 && levelLabel<mapDim-1){
                    levelLabel+=1;
                    level = backend.getLevel(levelLabel);
                    repaint();
                }else if(e.getX() >= 78 && e.getX() <= 132 && e.getY() >= 366 && e.getY() <= 450 && levelLabel>0){
                    levelLabel-=1;
                    level = backend.getLevel(levelLabel);
                    repaint();
                }
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode()==KeyEvent.VK_UP)&&(levelLabel>0)) {
                    levelLabel-=1;
                    level = backend.getLevel(levelLabel);
                    repaint();
                } else if ((e.getKeyCode()==KeyEvent.VK_DOWN)&&(levelLabel<mapDim-1)) {
                    levelLabel+=1;
                    level = backend.getLevel(levelLabel);
                    repaint();
                } else if (e.getKeyCode()== KeyEvent.VK_M){
                    backend.changeView("chamberview");
                }
            }
        });

    }

    private void openChamberView() {
        backend.changeView("chamberview");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.setBackground(new Color(255,255,255,255));
        g2D.clearRect(0,0,720,720);

        g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"lvl.png").getImage(), 15, 250, null);
        g.setFont(new Font("OCR A Extended", Font.BOLD, 53));
        g.drawString(Integer.toString(levelLabel+1), 60, 340);
        //added this to check in case mouse doesn't move but image is displayed

        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouseLocation,this);
        if (mouseLocation.getX() >= 78 && mouseLocation.getX() <= 132 && mouseLocation.getY() >= 366 && mouseLocation.getY() <= 450) {
            mouseHoverUp = true;
            mouseHoverDown = false;
            mouseHoverChamber = false;
        } else if (mouseLocation.getX() >= 18 && mouseLocation.getX() <= 72 && mouseLocation.getY() >= 366 && mouseLocation.getY() <= 450) {
            mouseHoverDown = true;
            mouseHoverUp = false;
            mouseHoverChamber = false;
        } else if (mouseLocation.getX() >= 20 && mouseLocation.getX() <= 130 && mouseLocation.getY() >= 560 && mouseLocation.getY() <= 670) {
            mouseHoverChamber = true;
            mouseHoverUp = false;
            mouseHoverDown = false;
        } else {
            mouseHoverUp = false;
            mouseHoverDown = false;
            mouseHoverChamber = false;
        }

        if (mouseHoverUp && levelLabel != 0) {
            g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"upbuttonOutlined.png").getImage(), 58, 360, null);
        } else if(levelLabel == 0) {
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"upbutton.png").getImage(), 58, 360, null);
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        } else {
            g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"upbutton.png").getImage(), 58, 360, null);
        }
        if (mouseHoverDown && levelLabel != mapDim-1) {
            g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"downbuttonOutlined.png").getImage(), 0, 360, null);
        } else if(levelLabel == mapDim-1) {
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"downbutton.png").getImage(), 0, 360, null);
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        } else {
            g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"downbutton.png").getImage(), 0, 360, null);
        }
        if (mouseHoverChamber) {
            g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"chamberViewButtonOutlined.png").getImage(), 23, 563, null);
        } else {
            g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"chamberViewButton.png").getImage(), 25, 565, null);
        }
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(11));
        for (int x = 0;  x< mapDim; x++) {
            for (int y = 0; y < mapDim; y++) {
                if (!level[y][x].getVisited()) {
                    g2D.fillRect(xBound1+(x*edgeLength),yBound1+(y*edgeLength),edgeLength,edgeLength);
                }
                g2D.drawLine(xBound1 + (x * edgeLength), yBound1 + (y * edgeLength), xBound1 + ((x + 1) * edgeLength), yBound1 + (y * edgeLength));
                g2D.drawLine(xBound1 + (x * edgeLength), yBound1 + ((y + 1) * edgeLength), xBound1 + ((x + 1) * edgeLength), yBound1 + ((y + 1) * edgeLength));
                g2D.drawLine(xBound1 + ((x + 1) * edgeLength), yBound1 + (y * edgeLength), xBound1 + ((x + 1) * edgeLength), yBound1 + ((y + 1) * edgeLength));
                g2D.drawLine(xBound1 + (x * edgeLength), yBound1 + (y * edgeLength), xBound1 + (x * edgeLength), yBound1 + ((y + 1) * edgeLength));
            }
        }
        g2D.setColor(Color.white);
        for (int x = 0;  x< mapDim; x++) {
            for (int y = 0; y < mapDim; y++) {
                if (level[y][x].getVisited()) {
                    if (level[y][x].getAdjacentChamber(Direction.NORTH) != null) {
                        g2D.drawLine(xBound1+(x*edgeLength)+(edgeLength/4),yBound1+(y*edgeLength),xBound1+((x+1)*edgeLength)-(edgeLength/4),yBound1+(y*edgeLength));
                    }
                    if (level[y][x].getAdjacentChamber(Direction.SOUTH) != null) {
                        g2D.drawLine(xBound1+(x*edgeLength)+(edgeLength/4),yBound1+((y+1)*edgeLength),xBound1+((x+1)*edgeLength)-(edgeLength/4),yBound1+((y+1)*edgeLength));
                    }
                    if (level[y][x].getAdjacentChamber(Direction.EAST) != null) {
                        g2D.drawLine(xBound1+((x+1)*edgeLength),yBound1+(y*edgeLength)+(edgeLength/4),xBound1+((x+1)*edgeLength),yBound1+((y+1)*edgeLength)-(edgeLength/4));
                    }
                    if (level[y][x].getAdjacentChamber(Direction.WEST) != null) {
                        g2D.drawLine(xBound1+(x*edgeLength),yBound1+(y*edgeLength)+(edgeLength/4),xBound1+(x*edgeLength),yBound1+((y+1)*edgeLength)-(edgeLength/4));
                    }

                    if ((level[y][x].getAdjacentChamber(Direction.UP) != null)&&(level[y][x].getAdjacentChamber(Direction.DOWN) != null)) {
                        g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"hatch.png").getImage(),xBound1+(x*edgeLength),yBound1+(y*edgeLength),(int)(((double)edgeLength/384)*252),edgeLength,null);
                        g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"trapdoor.png").getImage(),xBound1+((x+1)*edgeLength)-(int)(((double)edgeLength/384)*252),yBound1+(y*edgeLength),(int)(((double)edgeLength/384)*252),edgeLength,null);
                    } else if (level[y][x].getAdjacentChamber(Direction.UP) != null) {
                        g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"hatch.png").getImage(),xBound1+(x*edgeLength),yBound1+(y*edgeLength),(int)(((double)edgeLength/384)*252),edgeLength,null);
                    } else if (level[y][x].getAdjacentChamber(Direction.DOWN) != null){
                        g.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"trapdoor.png").getImage(),xBound1+((x+1)*edgeLength)-(int)(((double)edgeLength/384)*252),yBound1+(y*edgeLength),(int)(((double)edgeLength/384)*252),edgeLength,null);
                    }
                }
                if(level[y][x]==backend.getChamber()){
                    drawCharacter(g,x,y);
                }
            }
        }


        Header.drawHeader(headerImage,backend.getMoves(),backend.getChamber().getCoordinates(),backend.getDirection());
        g.drawImage(headerImage,0,0,null);
    }

    private void drawCharacter(Graphics g, int x, int y) {
        BufferedImage iconRotator = new BufferedImage(208,208,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = iconRotator.createGraphics();
        int originx = 0; int originy = 0;
        if (backend.getDirection() == Direction.NORTH) {
            //no rotation since its north
        } else if (backend.getDirection() == Direction.SOUTH) {
            g2d.rotate(Math.PI);
            originx = -208;
            originy = -208;
        } else if (backend.getDirection() == Direction.EAST) {
            g2d.rotate(Math.PI/2);
            originy = -208;
        } else if (backend.getDirection() == Direction.WEST) {
            g2d.rotate(Math.PI*3/2);
            originx = -208;
        }
        g2d.drawImage(new ImageIcon("assets"+ File.separator+"art"+ File.separator+"playericon.png").getImage(),originx,originy,null);
        g.drawImage(iconRotator,xBound1+(x*edgeLength),yBound1+(y*edgeLength),edgeLength,edgeLength,null);
    }
}
