package frontend;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import backend.*;
import maze.*;
import utils.*;

public class MapView extends JPanel {

    private final static int edge1 = 180, edge2 = 660;
    private final static int Inc4 = (edge2 - edge1) / 4, Inc5 = (edge2 - edge1) / 5, Inc6 = (edge2 - edge1) / 6;
    private final static int value4 = Inc4 + edge1, value5 = Inc5 + edge1, value6 = Inc6 + edge1;
    private final static int doorLength4 = Inc4 / 4, doorLength5 = Inc5 / 4, doorLength6 = Inc6 / 4;
    private final static int[] fourValues = {edge1, value4, value4 + Inc4, value4 + Inc4 * 2, edge2};
    private final static int[] fiveValues = {edge1, value5, value5 + Inc5, value5 + Inc5 * 2, value5 + Inc5 * 3, edge2};
    private final static int[] sixValues = {edge1, value6, value6 + Inc6, value6 + Inc6 * 2, value6 + Inc6 * 3, value6 + Inc6 * 4, edge2};
    private boolean mouseHoverUp, mouseHoverDown, mouseHoverChamber;
    private int levelLabel;
    private JFrame frame;
    private Image display;

    private Chamber chamber;
    private Chamber[][] level;
    private BackendEngine backend;

    public MapView(Chamber[][] level, BackendEngine backend) {
        this.setPreferredSize(new Dimension(720,720));
        this.setFocusable(true);
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (e.getX() >= 80 && e.getX() <= 132 && e.getY() >= 360 && e.getY() <= 445) {
                    mouseHoverUp = true;
                } else if (e.getX() >= 20 && e.getX() <= 72 && e.getY() >= 360 && e.getY() <= 445) {
                    mouseHoverDown = true;
                } else if (e.getX() >= 0 && e.getX() <= 122 && e.getY() >= 565 && e.getY() <= 687) {
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
                if (e.getX() >= 0 && e.getX() <= 122 && e.getY() >= 565 && e.getY() <= 687) {
                    openChamberView();
                }
            }
        });
        this.backend = backend;
        this.level = level;
    }

    private void openChamberView() {
        backend.changeView("chamberView");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        display = new ImageIcon("MapImages/lvl.png").getImage();
        g.drawImage(display, 15, 250, null);

        display = new ImageIcon("MapImages/chamberViewButton.png").getImage();
        g.drawImage(display, 0, 565, null);

        display = new ImageIcon("MapImages/upbutton.png").getImage();
        g.drawImage(display, 58, 360, null);

        display = new ImageIcon("MapImages/downbutton.png").getImage();
        g.drawImage(display, 0, 360, null);

        display = new ImageIcon("MapImages/PlayerCharacterEast.png").getImage();
        g.drawImage(display, fourValues[0], edge1, null);

        levelLabel = backend.getChamber().getCoordinates().getLevel();
        g.setFont(new Font("OCR A Extended", Font.BOLD, 53));
        g.drawString(Integer.toString(levelLabel), 60, 340);

        if (mouseHoverUp == true) {
            display = new ImageIcon("MapImages/upbuttonOutlined.png").getImage();
            g.drawImage(display, 58, 360, null);
        } else if (mouseHoverDown == true) {
            display = new ImageIcon("MapImages/downbuttonOutlined.png").getImage();
            g.drawImage(display, 0, 360, null);
        } else if (mouseHoverChamber == true) {
            display = new ImageIcon("MapImages/chamberViewButtonOutlined.png").getImage();
            g.drawImage(display, 0, 565, null);
        }

        if (backend.getDifficulty().equals("easy")) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    chamber = level[i][j];
                    if (backend.getChamber().getCoordinates().getRow() == i && backend.getChamber().getCoordinates().getColumn() == j) {
                        if (backend.getDirection() == 0) {
                            display = new ImageIcon("MapImages/PlayerCharacterNorth.png").getImage();
                            g.drawImage(display, fourValues[i], fourValues[j], null);
                        } else if (backend.getDirection() == 1) {
                            display = new ImageIcon("MapImages/PlayerCharacterSouth.png").getImage();
                            g.drawImage(display, fourValues[i], fourValues[j], null);
                        } else if (backend.getDirection() == 2) {
                            display = new ImageIcon("MapImages/PlayerCharacterEast.png").getImage();
                            g.drawImage(display, fourValues[i], fourValues[j], null);
                        } else if (backend.getDirection() == 3) {
                            display = new ImageIcon("MapImages/PlayerCharacterWest.png").getImage();
                            g.drawImage(display, fourValues[i], fourValues[j], null);
                        }
                    }
                    if (chamber.getVisited() == false) {
                        g.fillRect(fourValues[i], fourValues[j], Inc4, Inc4);
                    } else {
                        if (chamber.getAdjacentChamber(0) != null) {
                            g.drawLine(fourValues[i], fourValues[j], fourValues[i] - (doorLength4 * 2), fourValues[j]);
                            g.drawLine(fourValues[i] - doorLength4, fourValues[j], fourValues[i + 1], fourValues[j]);
                        } else if (chamber.getAdjacentChamber(1) != null) {
                            g.drawLine(fourValues[i], fourValues[j], fourValues[i] - (doorLength4 * 2), fourValues[j]);
                            g.drawLine(fourValues[i] - doorLength4, fourValues[j], fourValues[i + 1], fourValues[j]);
                        } else if (chamber.getAdjacentChamber(2) != null) {
                            g.drawLine(fourValues[i], fourValues[j], fourValues[i], fourValues[j] - (doorLength4 * 2));
                            g.drawLine(fourValues[i], fourValues[j] - doorLength4, fourValues[i], fourValues[j + 1]);
                        } else if (chamber.getAdjacentChamber(3) != null) {
                            g.drawLine(fourValues[i], fourValues[j], fourValues[i], fourValues[j] - (doorLength4 * 2));
                            g.drawLine(fourValues[i], fourValues[j] - doorLength4, fourValues[i], fourValues[j + 1]);
                        } else if (chamber.getAdjacentChamber(4) != null) {
                            if (chamber.getAdjacentChamber(5) != null) {
                                display = new ImageIcon("MapImages/BothHT.png").getImage();
                                g.drawImage(display, fourValues[i], fourValues[j], null);
                            } else {
                                display = new ImageIcon("MapImages/Hatch.png").getImage();
                                g.drawImage(display, fourValues[i], fourValues[j], null);
                            }
                        } else if (chamber.getAdjacentChamber(5) != null) {
                            display = new ImageIcon("MapImages/Trapdoor.png").getImage();
                            g.drawImage(display, fourValues[i], fourValues[j], null);
                        }
                    }
                }
            }
        } else if (backend.getDifficulty().equals("medium")) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    chamber = level[i][j];
                    if (backend.getChamber().getCoordinates().getRow() == i && backend.getChamber().getCoordinates().getColumn() == j) {
                        if (backend.getDirection() == 0) {
                            display = new ImageIcon("MapImages/PlayerCharacterNorth.png").getImage();
                            g.drawImage(display, fiveValues[i], fiveValues[j], null);
                        } else if (backend.getDirection() == 1) {
                            display = new ImageIcon("MapImages/PlayerCharacterSouth.png").getImage();
                            g.drawImage(display, fiveValues[i], fiveValues[j], null);
                        } else if (backend.getDirection() == 2) {
                            display = new ImageIcon("MapImages/PlayerCharacterEast.png").getImage();
                            g.drawImage(display, fiveValues[i], fiveValues[j], null);
                        } else if (backend.getDirection() == 3) {
                            display = new ImageIcon("MapImages/PlayerCharacterWest.png").getImage();
                            g.drawImage(display, fiveValues[i], fiveValues[j], null);
                        }
                    }
                    if (chamber.getVisited() == false) {
                        g.fillRect(fiveValues[i], fiveValues[j], Inc5, Inc5);
                    } else {
                        for (int a = 0; a < 6; a++) {
                            if (chamber.getAdjacentChamber(0) != null) {
                                g.drawLine(fiveValues[i], fiveValues[j], fiveValues[i] - (doorLength5 * 2), fiveValues[j]);
                                g.drawLine(fiveValues[i] - doorLength5, fiveValues[j], fiveValues[i + 1], fiveValues[j]);
                            } else if (chamber.getAdjacentChamber(1) != null) {
                                g.drawLine(fiveValues[i], fiveValues[j], fiveValues[i] - (doorLength5 * 2), fiveValues[j]);
                                g.drawLine(fiveValues[i] - doorLength5, fiveValues[j], fiveValues[i + 1], fiveValues[j]);
                            } else if (chamber.getAdjacentChamber(2) != null) {
                                g.drawLine(fiveValues[i], fiveValues[j], fiveValues[i], fiveValues[j] - (doorLength5 * 2));
                                g.drawLine(fiveValues[i], fiveValues[j] - doorLength5, fiveValues[i], fiveValues[j + 1]);
                            } else if (chamber.getAdjacentChamber(3) != null) {
                                g.drawLine(fiveValues[i], fiveValues[j], fiveValues[i], fiveValues[j] - (doorLength5 * 2));
                                g.drawLine(fiveValues[i], fiveValues[j] - doorLength5, fiveValues[i], fiveValues[j + 1]);
                            } else if (chamber.getAdjacentChamber(4) != null) {
                                if (chamber.getAdjacentChamber(5) != null) {
                                    display = new ImageIcon("MapImages/BothHT.png").getImage();
                                    g.drawImage(display, fiveValues[i], fiveValues[j], null);
                                } else {
                                    display = new ImageIcon("MapImages/Hatch.png").getImage();
                                    g.drawImage(display, fiveValues[i], fiveValues[j], null);
                                }
                            } else if (chamber.getAdjacentChamber(6) != null) {
                                display = new ImageIcon("MapImages/Trapdoor.png").getImage();
                                g.drawImage(display, fiveValues[i], fiveValues[j], null);
                            }
                        }

                    }
                }
            }
        } else if (backend.getDifficulty().equals("hard")) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    chamber = level[i][j];
                    if (backend.getChamber().getCoordinates().getRow() == i && backend.getChamber().getCoordinates().getColumn() == j) {
                        if (backend.getDirection() == 0) {
                            display = new ImageIcon("MapImages/PlayerCharacterNorth.png").getImage();
                            g.drawImage(display, sixValues[i], sixValues[j], null);
                        } else if (backend.getDirection() == 1) {
                            display = new ImageIcon("MapImages/PlayerCharacterSouth.png").getImage();
                            g.drawImage(display, sixValues[i], sixValues[j], null);
                        } else if (backend.getDirection() == 2) {
                            display = new ImageIcon("MapImages/PlayerCharacterEast.png").getImage();
                            g.drawImage(display, sixValues[i], sixValues[j], null);
                        } else if (backend.getDirection() == 3) {
                            display = new ImageIcon("MapImages/PlayerCharacterWest.png").getImage();
                            g.drawImage(display, sixValues[i], sixValues[j], null);
                        }
                    }
                    if (chamber.getVisited() == false) {
                        g.fillRect(sixValues[i], sixValues[j], Inc6, Inc6);
                    } else {
                        for (int a = 0; a < 6; a++) {
                            if (chamber.getAdjacentChamber(0) != null) {
                                g.drawLine(sixValues[i], sixValues[j], sixValues[i] - (doorLength6 * 2), sixValues[j]);
                                g.drawLine(sixValues[i] - doorLength6, sixValues[j], sixValues[i + 1], sixValues[j]);
                            } else if (chamber.getAdjacentChamber(1) != null) {
                                g.drawLine(sixValues[i], sixValues[j], sixValues[i] - (doorLength6 * 2), sixValues[j]);
                                g.drawLine(sixValues[i] - doorLength6, sixValues[j], sixValues[i + 1], sixValues[j]);
                            } else if (chamber.getAdjacentChamber(2) != null) {
                                g.drawLine(sixValues[i], sixValues[j], sixValues[i], sixValues[j] - (doorLength6 * 2));
                                g.drawLine(sixValues[i], sixValues[j] - doorLength6, sixValues[i], sixValues[j + 1]);
                            } else if (chamber.getAdjacentChamber(3) != null) {
                                g.drawLine(sixValues[i], sixValues[j], sixValues[i], sixValues[j] - (doorLength6 * 2));
                                g.drawLine(sixValues[i], sixValues[j] - doorLength6, sixValues[i], sixValues[j + 1]);
                            } else if (chamber.getAdjacentChamber(4) != null) {
                                if (chamber.getAdjacentChamber(5) != null) {
                                    display = new ImageIcon("MapImages/BothHT.png").getImage();
                                    g.drawImage(display, sixValues[i], sixValues[j], null);
                                } else {
                                    display = new ImageIcon("MapImages/Hatch.png").getImage();
                                    g.drawImage(display, sixValues[i], sixValues[j], null);
                                }
                            } else if (chamber.getAdjacentChamber(5) != null) {
                                display = new ImageIcon("MapImages/Trapdoor.png").getImage();
                                g.drawImage(display, sixValues[i], sixValues[j], null);
                            }
                        }

                    }
                }
            }
        }
    }
}
