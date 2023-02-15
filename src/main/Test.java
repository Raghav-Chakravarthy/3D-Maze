package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
       
        Scene scene = new Scene(new Chamber[] {
            chamber
        });
        
        Camera cam = new Camera();
        cam.setPosition(new Vector3(0,0,-2));
        System.out.println(scene.getObjects());
        BufferedImage render = new BufferedImage(720,720,BufferedImage.TYPE_INT_RGB);

        Renderer.renderTo(scene, cam, render);
        File out = new File("test.png");
        try {
            ImageIO.write(render, "png", out);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
