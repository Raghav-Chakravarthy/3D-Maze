import java.awt.Color;
public class Chamber {
    private Color wallColor;
    private boolean visited;
    private Chamber[] adjacentChambers;
    private WallArt wallArt;
    private Coordinate coordinates;
    public Chamber() {

    }
    public WallArt getWallArt() {

    }
    public void setWallArt(WallArt art) {
        wallArt = art;
    }
    public boolean getVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public Chamber getAdjacentChamber(int index) {
        return adjacentChambers[index];
    }
    public Chamber setAdjacentChamber(int index, Chamber c) {
        adjacentChambers[index] = c;
    }
    public Chamber[] getChambers() {
        return adjacentChambers;
    }
    public void setChambers (Chamber[] chambers) {
        adjacentChambers = chambers;
    }
}