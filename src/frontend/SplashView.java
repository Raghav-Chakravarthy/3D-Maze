package frontend;
import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class SplashView extends JPanel {
    BackendEngine backendEngine;
    BufferedImage drawImage = new BufferedImage(720,720,BufferedImage.TYPE_INT_ARGB);
    ImageIcon logo = new ImageIcon("assets"+ File.separator+"art"+ File.separator+"splash.png");

    public SplashView(BackendEngine backendEngine){
        this.setPreferredSize(new Dimension(720,720));
        this.backendEngine = backendEngine;
        fadeIn();
    }

    private void fadeIn(){
        final Timer frameTimer = new Timer(1000/60,null);
        frameTimer.addActionListener(new ActionListener() {
            double opacityRemaining = 1;
            Graphics2D g2d = (Graphics2D) drawImage.getGraphics();
            double lastTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentTime = System.nanoTime();
                opacityRemaining-=(currentTime-lastTime)/2000000000;
                if(opacityRemaining<=0){
                    frameTimer.stop();
                    opacityRemaining = 0;
                    g2d.clearRect(0,0,720,720);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
                    g2d.drawImage(logo.getImage(),0,0,null);
                    repaint();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    backendEngine.changeView("mainview");
                }
                g2d.clearRect(0,0,720,720);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (1-opacityRemaining)));
                g2d.drawImage(logo.getImage(),0,0,null);
                repaint();
                lastTime = currentTime;
            }
        });
        frameTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0,0,720,720);
        g.drawImage(drawImage,0,0,null);
    }
}
