package maze;

import rendering.Vector3;

public class Coordinate {
    private int row;
    private int column;
    private int level;
    
    public Coordinate(int level, int row, int column) {
        this.level = level;
        this.row = row;
        this.column = column;
    }
    
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public int getLevel() {
        return level;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public boolean equals(Object other) {
        if(other instanceof Coordinate) {
            Coordinate c = (Coordinate) other;
            if(c.getRow() == row && c.getColumn() == column && c.getLevel() == level) return true;
            else return false;
        } else {
            return false;
        }
    }
}
