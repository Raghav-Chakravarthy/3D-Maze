package main;


import java.awt.Color;
import java.awt.image.BufferedImage;


import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import backend.BackendEngine;
import frontend.*;
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
            null, TextureManager.main.getTexture("art0").getImage(),null,null,null, null
        }));


        Chamber chamber2 = new Chamber();
        chamber2.setCoordinates(new Coordinate(0,1,0));
        chamber2.setWallColor(Color.green);
        chamber.setChambers(new Chamber[] {
            null, null, chamber2, null, null, null
        });
        chamber2.setChambers(new Chamber[]{
               null,null,null,chamber,null,null
        });
        chamber2.setWallArt(new ImageWallArt(new BufferedImage[] {
                null, null, TextureManager.main.getTexture("art2").getImage(), null, null, null
        }));
        BackendEngine backendEngine = new BackendEngine();
        backendEngine.setChamber(chamber);
        ChamberView chamberView = new ChamberView(chamber, backendEngine);
        chamberView.setPreferredSize(new Dimension(720,720));
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Test");
        JPanel contentPane = new JPanel();
        contentPane.add(chamberView);
        frame.setContentPane(chamberView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        System.out.println("Test complete");

    }
}
