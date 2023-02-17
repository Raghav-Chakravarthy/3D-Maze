import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Menu extends JPanel{
	private boolean tutorial = false;
	private int slideCounter = 0;
	private Image [] imageSlides;
	private Image display;
	private final String path = "images\\";
	private JFrame frame;
	private JPanel intro;
	public Menu() {
		//this is a testing constructor
		frame = new JFrame("3D Maze");
		frame.addMouseListener(new clicks());
		frame.setSize(720,720);
		intro = new draw();
		frame.setContentPane(intro);
		frame.setVisible(true);
	}
	private class draw extends JPanel{
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			if(!tutorial) {
				display = new ImageIcon(path+"amenu.png").getImage();
				g.drawImage(display,0,0,null);
			} else if(tutorial) {
//				display = new ImageIcon(path+imageSlides[slideCounter]).getImage();
//				g.drawImage(display,0,0,null);
				if(slideCounter==0) {
					g.drawImage(new ImageIcon(path+"tutorialStart.png").getImage(),95,240,null);
				} else if(slideCounter<8) {
					g.drawImage(new ImageIcon(path+"continue.png").getImage(),490,630,null);
				} else if(slideCounter==8) {
					g.drawImage(new ImageIcon(path+"home.png").getImage(),490,630,null);
				}
			}
		}
	}
	private class clicks implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(!tutorial) {
				if(e.getX()>=140 && e.getX()<=585 && e.getY()>=335 && e.getY()<=394) {
					//calls setDifficulty("Easy")
					//calls changeView("chamberview")
				} else if(e.getX()>=140 && e.getX()<=585 && e.getY()>=422 && e.getY()<=482) {
					//calls setDifficulty("Medium")
					//calls changeView("chamberview")
				} else if(e.getX()>=140 && e.getX()<=585 && e.getY()>=511 && e.getY()<=570) {
					//calls setDifficulty("Hard")
					//calls changeView("chamberview")
				} else if(e.getX()>=140 && e.getX()<=368 && e.getY()>=600 && e.getY()<=658) {
					tutorial = true;
				}
				intro.repaint();
			} else {
				if(slideCounter<8){
					if(slideCounter==0 && e.getX()>=485 && e.getX()<=609 && e.getY()>=384 && e.getY()<=410) {
						slideCounter++;
					} else if(e.getX()>=490 && e.getX()<=695 && e.getY()>=630 && e.getY()<=690) {
						slideCounter++;
					}
				} else if (e.getX()>=495 && e.getX()<=694 && e.getY()>=658 && e.getY()<=691 && slideCounter==8) {
					slideCounter=0;
					tutorial = false;
				}
				intro.repaint();		
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
	}
	//test run
	private static void runGUI() {
	  	JFrame.setDefaultLookAndFeelDecorated(true);
	  	Menu drive = new Menu();
	}
	public static void main(String[] args) {
	    /* Methods that create and show a GUI should be 
	       run from an event-dispatching thread */
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	runGUI();
	        }
	    });
	}

}
