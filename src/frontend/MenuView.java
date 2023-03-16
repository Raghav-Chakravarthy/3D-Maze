package frontend;

import backend.BackendEngine;
import rendering.Vector3;
import utils.Direction;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

import javax.swing.*;

public class MenuView extends JPanel{
	private boolean tutorial = false;
	private int slideCounter = 0;
	private String [] imageSlides = new String[]{"slide1.png", "slide2.png", "slide3.png", "slide4.png", "slide5.png", "slide6.png", "slide7.png", "slide8.png", "slide9.png", "slide10.png"};
	private Image display;
	private final String path = "assets" + File.separator + "art" + File.separator;
	private boolean mouseHoveredEasy, mouseHoveredMedium,mouseHoveredHard,mouseHoveredTutorial,mouseHovered2,mouseHovered3;
	private BackendEngine backendEngine;
	private Random random = new Random();
	private Image phrase = new ImageIcon(path+"phrase"+(random.nextInt(14)+1)+".png").getImage();
	private float phraseScale = 1.5F;
	Timer phraseTimer = new Timer(1000/60,null);
	public MenuView(final BackendEngine backendEngine) {
		this.setPreferredSize(new Dimension(720,720));
		this.setFocusable(true);
		this.backendEngine = backendEngine;
		phrasePulse();
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!tutorial) {
					if(e.getX()>=135 && e.getX()<=582 && e.getY()>=303 && e.getY()<=365) {
						backendEngine.setDifficulty("easy");
						backendEngine.changeView("chamberview");
						phraseTimer.stop();
					} else if(e.getX()>=135 && e.getX()<=582 && e.getY()>=390 && e.getY()<=455) {
						backendEngine.setDifficulty("medium");
						backendEngine.changeView("chamberview");
						phraseTimer.stop();
					} else if(e.getX()>=135 && e.getX()<=582 && e.getY()>=480 && e.getY()<=545) {
						backendEngine.setDifficulty("hard");
						backendEngine.changeView("chamberview");
						phraseTimer.stop();
					} else if(e.getX()>=135 && e.getX()<=366 && e.getY()>=570 && e.getY()<=630) {
						tutorial = true;
						phraseTimer.stop();
					}
					repaint();
				} else {
					if(slideCounter<9){
						if(slideCounter==0 && e.getX()>=470 && e.getX()<=609 && e.getY()>=352 && e.getY()<=382) {
							slideCounter++;
						} else if(e.getX()>=490 && e.getX()<=690 && e.getY()>=630 && e.getY()<=665) {
							slideCounter++;
						}
					} else if (e.getX()>=490 && e.getX()<=690 && e.getY()>=631 && e.getY()<=663 && slideCounter==9) {
						slideCounter=0;
						tutorial = false;
						phrasePulse();
					}
					repaint();
				}
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(!tutorial) {
					if(e.getX()>=132 && e.getX()<=582 && e.getY()>=303 && e.getY()<=365) {
						mouseHoveredEasy = true;
					} else if(e.getX()>=135 && e.getX()<=582 && e.getY()>=390 && e.getY()<=455) {
						mouseHoveredMedium = true;
					} else if(e.getX()>=135 && e.getX()<=582 && e.getY()>=480 && e.getY()<=545) {
						mouseHoveredHard = true;
					} else if(e.getX()>=135 && e.getX()<=366 && e.getY()>=570 && e.getY()<=630) {
						mouseHoveredTutorial = true;
					} else {
						mouseHoveredEasy = false;
						mouseHoveredMedium = false;
						mouseHoveredHard = false;
						mouseHoveredTutorial = false;
					}
					repaint();
				} else {
					if(slideCounter<9){
						if(slideCounter==0 && e.getX()>=470 && e.getX()<=609 && e.getY()>=352 && e.getY()<=382) {
							mouseHovered2 = true;
						} else if(e.getX()>=490 && e.getX()<=690 && e.getY()>=630 && e.getY()<=665) {
							mouseHovered3 = true;
						} else {
							mouseHovered2 = false;
							mouseHovered3 = false;
						}
					} else if (e.getX()>=490 && e.getX()<=690 && e.getY()>=631 && e.getY()<=663 && slideCounter==9) {
						mouseHovered3=true;
					} else {
						mouseHovered3 = false;
					}
					repaint();
				}
			}
		});
	}
	private void phrasePulse(){
		phraseTimer = new Timer(1000/60,null);
		phraseTimer.addActionListener(new ActionListener() {
			double lastTime = System.nanoTime();
			boolean growing = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				double currentTime = System.nanoTime();

				double scaleMoved = (currentTime- lastTime)/1500000000;
				if(growing){
					phraseScale += scaleMoved;
				}else{
					phraseScale -= scaleMoved;
				}
				if(phraseScale<1){
					growing = true;
					phraseScale = 1;
				}else if(phraseScale>1.5){
					growing = false;
					phraseScale = 1.5F;
				}
				repaint();
				lastTime = currentTime;
			}
		});
		phraseTimer.start();
	}

	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		display = new ImageIcon(path+"menu.png").getImage();
		g.drawImage(display,0,0,null);
		if(!tutorial) {
			if(mouseHoveredEasy) {
				display = new ImageIcon(path+"amenuEasyOutlined.png").getImage();
				g.drawImage(display,0,0,null);
			}else if(mouseHoveredMedium) {
				display = new ImageIcon(path+"amenuMediumOutlined.png").getImage();
				g.drawImage(display,0,0,null);
			}else if(mouseHoveredHard) {
				display = new ImageIcon(path+"amenuHardOutlined.png").getImage();
				g.drawImage(display,0,0,null);
			}else if(mouseHoveredTutorial) {
				display = new ImageIcon(path+"amenuTutorialOutlined.png").getImage();
				g.drawImage(display,0,0,null);
			}
			g.drawImage(phrase, (int) (600-(100*phraseScale)), (int) (260-(75*phraseScale)), (int) (200*phraseScale), (int) (150*phraseScale),null);
		} else if(tutorial) {
			display = new ImageIcon(path+imageSlides[slideCounter]).getImage();
			g.drawImage(display,0,0,null);
			if(slideCounter==0) {
				if(mouseHovered2)
					g.drawImage(new ImageIcon(path+"tutorialStartOutlined.png").getImage(),95,240,null);
				else
					g.drawImage(new ImageIcon(path+"tutorialStart.png").getImage(),95,240,null);
			} else if(slideCounter<9) {
				if(mouseHovered3)
					g.drawImage(new ImageIcon(path+"continueOutlined.png").getImage(),488,628,null);
				else
					g.drawImage(new ImageIcon(path+"continue.png").getImage(),490,630,null);
			} else if(slideCounter==9) {
				mouseHoveredEasy = false;
				mouseHoveredMedium = false;
				mouseHoveredHard = false;
				mouseHoveredTutorial = false;
				if(mouseHovered3)
					g.drawImage(new ImageIcon(path+"homeOutlined.png").getImage(),488,628,null);
				else
					g.drawImage(new ImageIcon(path+"home.png").getImage(),490,630,null);
			}
		}
	}
}




