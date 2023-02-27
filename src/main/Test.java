package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import backend.BackendEngine;
import frontend.ChamberView;
import maze.Chamber;
import maze.Coordinate;
import rendering.ColorUtils;
import rendering.ImageWallArt;
import utils.TextureManager;

public class Test {
    public static void main(String[] args) {
        Chamber chamber = new Chamber();
        chamber.setCoordinates(new Coordinate(0, 0, 0));
        chamber.setWallColor(ColorUtils.randomChamberColor());
        chamber.setWallArt(ImageWallArt.generateWallArtFor(chamber, 3));

        Chamber chamber2 = new Chamber();
        chamber2.setCoordinates(new Coordinate(1,0,0));
        chamber2.setWallColor(ColorUtils.randomChamberColor());
        Chamber chamber3 = new Chamber();
        chamber3.setCoordinates(new Coordinate(0,0,1));
        chamber3.setWallColor(ColorUtils.randomChamberColor());
        chamber.setChambers(new Chamber[] {
            null, null, chamber3, null, null, chamber2
        });
        chamber3.setChambers(new Chamber[]{
                null,null,null,chamber,null,null
        });
        chamber3.setWallArt(ImageWallArt.generateWallArtFor(chamber3, 2));
        chamber2.setChambers(new Chamber[]{
               null,null,null,null,chamber,null
        });
        chamber2.setWallArt(ImageWallArt.generateWallArtFor(chamber2, 2));
        BackendEngine backendEngine = new BackendEngine();
        backendEngine.setChamber(chamber);
        ChamberView chamberView = new ChamberView(chamber, backendEngine);
        chamberView.setPreferredSize(new Dimension(720,720));
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Test");
        frame.setContentPane(chamberView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
