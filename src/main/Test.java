package main;


import java.awt.Color;
import java.awt.image.BufferedImage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import maze.Chamber;
import maze.Coordinate;
import rendering.Camera;
import rendering.ImageWallArt;
import rendering.Renderer;
import rendering.Scene;
import rendering.Vector3;
import utils.TextureManager;

public class Test {
    public static void main(String[] args) {
        Chamber chamber = new Chamber();
        chamber.setCoordinates(new Coordinate(0, 0, 0));
        chamber.setWallColor(Color.ORANGE);
        chamber.setWallArt(new ImageWallArt(new BufferedImage[] {
            null, null, TextureManager.main.getTexture("art0").getImage(), null, null, null
        }));
        chamber.setChambers(new Chamber[] {
            new Chamber(), new Chamber(), null, new Chamber(), null, new Chamber()
        });

        Chamber chamber2 = new Chamber();
        chamber2.setCoordinates(new Coordinate(0, 1, 0));
        chamber2.setWallColor(Color.YELLOW);
        chamber2.setWallArt(new ImageWallArt(new BufferedImage[] {
            TextureManager.main.getTexture("art2").getImage(), null, null, null, null, null
        }));
        chamber2.setChambers(new Chamber[] {
            null, new Chamber(), null, new Chamber(), null, new Chamber()
        });
        Scene scene = new Scene(new Chamber[] {
            chamber,
            chamber2
        });

        Camera cam = new Camera();
        cam.setPosition(new Vector3(0,0,-1));
        cam.setNearPlane(0.7f);
        System.out.println(scene.getObjects());
        BufferedImage render = new BufferedImage(720/3,720/3,BufferedImage.TYPE_INT_RGB);

        

        JFrame frame = new JFrame("TEST");
        frame.setSize(new Dimension(720,720));
        frame.setContentPane(new JPanel() {
            public void paintComponent(Graphics g) {
                long t0 = System.currentTimeMillis();
                Renderer.renderTo(scene, cam, render);
                g.drawImage(render, 0, 0, getWidth(), getHeight(), null);
                int dt = (int) (System.currentTimeMillis()-t0);
                System.out.println(1000f/dt);
            }
        });

        Timer t = new Timer(1000/30, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cam.translate(new Vector3(0,0,0.01f));
                frame.repaint();
            }
            
        });
        t.start();

        frame.setVisible(true);
        /*
        File out = new File("out/test.png");
        try {
            ImageIO.write(render, "png", out);
        } catch(IOException e) {
            e.printStackTrace();
        }
        */
    }
}
