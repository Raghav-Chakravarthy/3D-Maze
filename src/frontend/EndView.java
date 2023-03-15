package frontend;

import backend.BackendEngine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class EndView extends JPanel implements MouseListener, MouseMotionListener{

	//variables
	private int playerScore;
	private int[] newTopTen;
	private boolean mouseHovered;
	private JPanel panel;
	private final BackendEngine game;
	private float endOpacity = 1F;

	private BufferedImage screenRender = new BufferedImage(720,720,BufferedImage.TYPE_INT_ARGB);



	public EndView(int score, BackendEngine engine) {
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(720,720));
		//	actual code for the constructor:
		playerScore = score;
		updateLeaderboard(playerScore);
		panel = new PaintEndView();
		game = engine;
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		this.add(panel);
		panel.setPreferredSize(getPreferredSize());
		Graphics g = screenRender.getGraphics();
		Image bgImage = readImage("assets" + File.separator + "art" + File.separator + "endbg.jpg");
		Image bgScaled = bgImage.getScaledInstance(1151, 720, Image.SCALE_DEFAULT);
		g.drawImage(bgScaled,-215,0,null);
		Image titleImage = readImage("assets" + File.separator + "art" + File.separator +"endtitle.png");
		Image titleScaled = titleImage.getScaledInstance(680, 177, Image.SCALE_DEFAULT);
		g.drawImage(titleScaled,20,10,null);

		//	Displays the player's score (called multiple times to create black outline around white text)
		String displayScore; //assumes the highest playerScore possible is 4 digits (max score = 9999)
		displayScore = adjustScore(playerScore);
		g.setColor(Color.BLACK);
		g.setFont(new Font("OCR A Extended", Font.BOLD, 40));
		g.drawString("Your Score: " + displayScore, 168, 203);
		g.drawString("Your Score: " + displayScore, 168, 205);
		g.drawString("Your Score: " + displayScore, 168, 207);
		g.drawString("Your Score: " + displayScore, 170, 203);
		g.drawString("Your Score: " + displayScore, 170, 205);
		g.drawString("Your Score: " + displayScore, 170, 207);
		g.drawString("Your Score: " + displayScore, 172, 203);
		g.drawString("Your Score: " + displayScore, 172, 205);
		g.drawString("Your Score: " + displayScore, 172, 207);
		g.setColor(Color.WHITE);
		g.drawString("Your Score: " + displayScore, 170, 205);

		//	creates the transparent black background for the leaderboard
		g.setColor(new Color(0,0,0,150));
		g.fillRect(80,230,560,350);

		//	creates the image that has numbers 1-10 for the leaderboard
		Image leaderImage = readImage("assets" + File.separator  + "art" + File.separator +"endleaderboard.png");
		Image leaderScaled = leaderImage.getScaledInstance(300, 317, Image.SCALE_DEFAULT);
		g.drawImage(leaderScaled,115,245,null);
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("OCR A Extended", Font.BOLD, 40));

		//	reads from the newly altered file and displays the scores on the leaderboard (your score is yellow if on leaderboard)
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("assets" + File.separator + "3D-Maze-Scores.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scanner.useDelimiter(",");
		boolean yellowed = false;
		yellowed = drawLBScore(Integer.parseInt(scanner.next()), 175, 283, yellowed, g) ;
		yellowed = drawLBScore(Integer.parseInt(scanner.next()), 175, 351, yellowed, g) ;
		yellowed = drawLBScore(Integer.parseInt(scanner.next()), 175, 418, yellowed, g) ;
		yellowed = drawLBScore(Integer.parseInt(scanner.next()), 175, 486, yellowed, g) ;
		yellowed = drawLBScore(Integer.parseInt(scanner.next()), 175, 552, yellowed, g) ;
		yellowed = drawLBScore(Integer.parseInt(scanner.next()), 430, 283, yellowed, g) ;
		yellowed = drawLBScore(Integer.parseInt(scanner.next()), 430, 351, yellowed, g) ;
		yellowed = drawLBScore(Integer.parseInt(scanner.next()), 430, 418, yellowed, g) ;
		yellowed = drawLBScore(Integer.parseInt(scanner.next()), 430, 486, yellowed, g) ;
		yellowed = drawLBScore(Integer.parseInt(scanner.next()), 430, 552, yellowed, g) ;
		scanner.close();
		endFade();
		/*
		//	close button
		JButton closeBtn = new JButton("Exit");
		closeBtn.setBounds(160,600,400,60);
		panel.add(closeBtn);
		
		
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.changeView("close");
			}
		}); */
		
	}

	//	method to read the csv file and edit it if necessary
	private void updateLeaderboard(int score) {
		int place = 0;
		int[] topTen = new int[10];
		newTopTen = new int[10];
		Scanner scan = null;

		//	read the csv file and make it an array
		try {
			scan = new Scanner(new File("assets"+ File.separator + "3D-Maze-Scores.csv"));
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			File newCSV = new File("assets"+ File.separator + "3D-Maze-Scores.csv");
			FileWriter f = null;
			try {
				newCSV.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			try {
				f = new FileWriter(newCSV);
				System.out.println(newCSV);
				f.append((CharSequence)"0,0,0,0,0,0,0,0,0,0");
				f.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Does not add all place-holder scores");
				//e1.printStackTrace();
			}
			try {
				scan = new Scanner(new File("assets"+ File.separator + "3D-Maze-Scores.csv"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("Does not make scanner");
				e1.printStackTrace();
			}
		}
		scan.useDelimiter(",");
		for(int l=0; l<10; l++) {
			topTen[place] = Integer.parseInt(scan.next());
			place++;
		}
		scan.close();

		//	find where score goes on leaderboard and adjust array as necessary
		if(score>=topTen[9]) { //if score in top 10
			int index = 0;
			int temp = 0;
			boolean isDone = false;
			do {
				if((score>=topTen[index])&&(!isDone)) {
					temp = topTen[index];
					newTopTen[index] = score;
					isDone = true;
				} else if(!isDone) {
					newTopTen[index]=topTen[index];
				} else {
					newTopTen[index] = topTen[index-1];
				}
				index++;
			} while(index<10);
		} else {  //if not in top 10
			for(int i=0; i<10; i++) {
				newTopTen[i]=topTen[i];
			}
		}

		//	copy correct array back to csv
		CharSequence tempSeq;
		String tempStr;
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File("assets" + File.separator + "3D-Maze-Scores.csv"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			for (int i=0; i<10; i++) {
				tempStr = ""+newTopTen[i];
				tempSeq = (CharSequence)tempStr;
				fw.append(tempSeq);
				if(i<9)
					fw.append(",");
			}
			fw.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
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
					endOpacity = 0;
					repaint();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						throw new RuntimeException(ex);
					}
				}
				endOpacity= (float) (opacityRemaining);
				repaint();
				lastTime = currentTime;
			}
		});
		frameTimer.start();
	}

	//paint component method to do all the painting required
	private class PaintEndView extends JPanel {

		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			g.drawImage(screenRender,0,0,null);
			//end button
			Image endButton;
			if(mouseHovered) {
				endButton = readImage("assets" + File.separator + "art" + File.separator + "closeOutlined.png");
				g.drawImage(endButton,208,598,null);
			}else{
				endButton = readImage("assets" + File.separator + "art" + File.separator + "close.png");
				g.drawImage(endButton,210,600,null);
			}
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,endOpacity));
			g2d.fillRect(0,0,720,720);
		}
	}

	private Image readImage(String imgStr) {
		try {
			return ImageIO.read(new File(imgStr));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private String adjustScore(int theScore) {
		String scoreToReturn;
		if(theScore<10) {
			scoreToReturn = "000" + theScore;
		} else if(theScore<100) {
			scoreToReturn = "00" + theScore;
		} else if(theScore<1000) {
			scoreToReturn = "0" + theScore;
		} else {
			scoreToReturn = "" + theScore;
		}
		return scoreToReturn;
	}

	//	method to display number on leaderboard (simplify code above)
	private boolean drawLBScore(int score, int x, int y, boolean yellowed, Graphics g) {
		boolean yello = yellowed;
		if((score==playerScore)&&(!yello)) {
			g.setColor(Color.YELLOW);
			yello = true;
		}
		g.drawString("= "+adjustScore(score), x, y);
		g.setColor(Color.LIGHT_GRAY);
		return yello;
	}

	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getX()>=210 && e.getX()<=510 && e.getY()>=600 && e.getY()<=660)
			mouseHovered=true;
		else
			mouseHovered=false;
		panel.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getX()>=210 && e.getX()<=510 && e.getY()>=600 && e.getY()<=660)
			game.changeView("close");
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
