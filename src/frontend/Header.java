package frontend;
import java.awt.*;
import java.awt.image.BufferedImage;
import maze.Coordinate;

public class Header{
    public static void drawHeader(BufferedImage image, int currentMoves, Coordinate coordinate, int direction){
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setBackground(new Color(0,0,0,0));
        graphics.clearRect(0,0,720,120);
        //TODO: Draw the header
        graphics.setColor(new Color(255,255,255,126));
        graphics.fillRect(0,0,720,120);
        graphics.setColor(new Color(0,0,0,255));
        graphics.drawString("Level: "+coordinate.getLevel()+" | Row: "+coordinate.getRow()+" | Column: "+coordinate.getColumn(),0,40);
        graphics.drawString("Moves Made: "+currentMoves, 240,40);
        graphics.drawString("Direction: "+direction, 480,40);
    }
}