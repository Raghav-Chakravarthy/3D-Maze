package main;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import backend.BackendEngine;
import frontend.*;
import maze.Chamber;
import maze.Coordinate;
import rendering.Camera;
import rendering.Renderer;
import rendering.Scene;
import rendering.Vector3;

public class Test {
    public static void main(String[] args) {
        Chamber chamber = new Chamber();
        chamber.setCoordinates(new Coordinate(0, 0, 0));
        chamber.setWallColor(Color.ORANGE);
        chamber.setChambers(new Chamber[] {
            new Chamber(), new Chamber(), null, new Chamber(), null, new Chamber()
        });
        BackendEngine backendEngine = new BackendEngine();
       
        ChamberView chamberView = new ChamberView(chamber, backendEngine);
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Test");
        frame.setContentPane(chamberView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
