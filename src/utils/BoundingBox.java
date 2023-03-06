package utils;

public class BoundingBox {
    //Top left
    private int x0;
    private int y0;
    //Bottom right
    private int x1;
    private int y1;

    public BoundingBox(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    public int x0() {
        return x0;
    }

    public int y0() {
        return y0;
    }

    public int x1() {
        return x1;
    }

    public int y1() {
        return y1;
    }

    public boolean inBounds(int x, int y) {
        return x >= x0 && x <= x1 && y <= y0 && y >= y1;
    }
}
