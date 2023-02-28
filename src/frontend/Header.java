package frontend;
import java.awt.*;
import java.awt.image.BufferedImage;
import maze.Coordinate;
import utils.Direction;

public class Header{
    public static void drawHeader(BufferedImage image, int currentMoves, Coordinate coordinate, int direction){
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setBackground(new Color(0,0,0,0));
        graphics.clearRect(0,0,720,30);
        //TODO: Draw the header
        graphics.setColor(new Color(255,255,255,126));
        graphics.fillRect(0,0,720,30);
        graphics.setColor(new Color(0,0,0,255));
        graphics.setFont(new Font("OCR A Extended", Font.PLAIN,20));
        graphics.drawString("Level: "+(coordinate.getLevel()+1)+" | Row: "+(coordinate.getRow()+1)+" | Column: "+(coordinate.getColumn()+1),0,22);
        graphics.drawString("Moves Made: "+currentMoves, (((580-graphics.getFontMetrics().stringWidth("Level: "+(coordinate.getLevel()+1)+" | Row: "+(coordinate.getRow()+1)+" | Column: "+(coordinate.getColumn()+1)))-graphics.getFontMetrics().stringWidth("Moves Made: "+currentMoves))/2)+graphics.getFontMetrics().stringWidth("Level: "+coordinate.getLevel()+" | Row: "+coordinate.getRow()+" | Column: "+coordinate.getColumn()),22);
        graphics.drawString("Direction: "+ Direction.toString(direction), 580,22);
    }
}