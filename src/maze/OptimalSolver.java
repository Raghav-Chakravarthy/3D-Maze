package maze;

import java.util.LinkedList;
import java.util.Queue;
public class OptimalSolver {
	private Maze toSolve;
	private int moveCount;
	private String solution;
	public OptimalSolver (Maze maze) {
		toSolve = maze;
		moveCount = 0;
		solution = "";
		bfs();
	}
	public void bfs() {
		Queue<Chamber> queue = new LinkedList<Chamber>();
		int size = toSolve.getLevel(0).length;
		boolean[][][] visited = new boolean[size][size][size];
		//access key with indices, get value coordinate
		Coordinate[][][] prevNode = new Coordinate[size][size][size];
		prevNode[0][0][0] = null;
		visited[0][0][0] = true;
		queue.add(toSolve.getChamberAt(new Coordinate(0,0,0)));
		while (queue.size() > 0) {
			Chamber current = queue.poll();
			Coordinate coord = current.getCoordinates();
			if (coord.getLevel() == size-1 && coord.getRow() == size-1 && coord.getColumn() == size-1) {
				break;
			}
			for (Chamber c : current.getChambers()) {
				if (c != null) {
					int z,y,x;
					z = c.getCoordinates().getLevel();
					y = c.getCoordinates().getRow();
					x = c.getCoordinates().getColumn();
					if (!visited[z][y][x]) {
						//if not visited, add to queue, mark prev, and mark visited
						visited[z][y][x] = true;
						queue.add(c);
						prevNode[z][y][x] = coord;
					}
				}
			}
		}
		//run through prevNode values to find optimal path
		int z = size-1;
		int y = size-1;
		int x = size-1;
		String reverseSolution = "";
		while (prevNode[z][y][x] != null) {
			//add to solution string
			//System.out.println(z + " " + y + " " + x);
			Coordinate fromCoord = prevNode[z][y][x];
			// z,y,x -> destination coordinate
			// z1,y1,x1 -> from coordinate
			int z1 = fromCoord.getLevel();
			int y1 = fromCoord.getRow();
			int x1 = fromCoord.getColumn();
			//placeholder char
			char relationChar = '@';
			if (z1 > z) {
				relationChar = 'U';
			} else if (z1 < z) {
				relationChar = 'D';
			} else if (y1 > y) {
				relationChar = 'N';
			} else if (y1 < y) {
				relationChar = 'S';
			} else if (x1 > x) {
				relationChar = 'W';
			} else if (x1 < x) {
				relationChar = 'E';
			}
			reverseSolution += relationChar;
			z = z1;
			y = y1;
			x = x1;
		}
		for (int i = reverseSolution.length()-1; i >= 0; i--) {
			solution += reverseSolution.charAt(i);
		}
		moveCount = reverseSolution.length();
	}
	public String getSolution() {
		return solution;
	}
	public int getMoves() {
		return moveCount;
	}
	public static void main(String[] args) {
		Maze toSolve = new MazeGenerator("easy").getMaze();
		OptimalSolver test = new OptimalSolver(toSolve);
	}
}
