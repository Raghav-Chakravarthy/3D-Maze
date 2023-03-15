package frontEnd;
//import backend.BackendEngine;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
public class MenuView extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
	private boolean tutorial = false;
	private int slideCounter = 0;
	private String [] imageSlides = new String[]{"slide1.png", "slide2.png", "slide3.png", "slide4.png", "slide5.png", "slide6.png", "slide7.png", "slide8.png", "slide9.png", "slide10.png"};
	private Image display;
//	private final String path = "assets" + File.separator + "art" + File.separator;
	private final String path = "images\\";
	private boolean mouseHoveredEasy;
	private boolean mouseHoveredMedium;
	private boolean mouseHoveredHard;
	private boolean mouseHoveredTutorial;
	private boolean mouseHovered2;
	private boolean mouseHovered3;
	private int pNum = ((int)(Math.random()*14)+1);
	private int x = 510;
	private int y = 200;
	private Timer size = new Timer(550,this);
	private boolean scale = false;
	private Image phrase = new ImageIcon(path+"phrasem"+ pNum +".png").getImage();
//	private BackendEngine backendEngine;
//	public MenuView(BackendEngine backendEngine) {
	public MenuView() {
		size.start();
		this.setPreferredSize(new Dimension(720,720));
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
//		this.backendEngine = backendEngine;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(scale)) {
			phrase = new ImageIcon(path+"phrase"+ pNum +".png").getImage();
			x = 490;
			y = 180;
			scale = true;
			this.repaint();
		} else if(scale) {
			phrase = new ImageIcon(path+"phrasem"+ pNum +".png").getImage();
			x = 510;
			y = 200;
			scale = false;
			this.repaint();
		}
	}
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
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
			}else {
				display = new ImageIcon(path+"menu.png").getImage();
				g.drawImage(display,0,0,null);
			}
			g.drawImage(phrase,x,y,null);
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
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!tutorial) {
			if(e.getX()>=135 && e.getX()<=582 && e.getY()>=303 && e.getY()<=365) {
//				backendEngine.setDifficulty("easy");
//				backendEngine.changeView("chamberview");
			} else if(e.getX()>=135 && e.getX()<=582 && e.getY()>=390 && e.getY()<=455) {
//				backendEngine.setDifficulty("medium");
//				backendEngine.changeView("chamberview");
			} else if(e.getX()>=135 && e.getX()<=582 && e.getY()>=480 && e.getY()<=545) {
//				backendEngine.setDifficulty("hard");
//				backendEngine.changeView("chamberview");
			} else if(e.getX()>=135 && e.getX()<=366 && e.getY()>=570 && e.getY()<=630) {
				tutorial = true;
			}
			this.repaint();
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
			}
			this.repaint();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
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
			this.repaint();
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
			this.repaint();
		}
	}
}






