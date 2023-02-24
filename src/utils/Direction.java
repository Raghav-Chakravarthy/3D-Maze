package utils;

public class Direction {
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;
    public static final int UP = 4;
    public static final int DOWN = 5;

    public static String toString(int dir) {
        if(dir == NORTH)
            return "North";
        else if(dir == SOUTH)
            return "South";
        else if(dir == EAST)
            return "East";
        else if(dir == WEST)
            return "West";
        else if(dir == UP)
            return "Up";
        else if(dir == DOWN)
            return "Down";
        else
            return "Unknown Direction";
    }
}
