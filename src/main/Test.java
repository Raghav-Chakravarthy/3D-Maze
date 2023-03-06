package main;

import java.awt.Dimension;

import javax.swing.JFrame;

import backend.BackendEngine;
import frontend.ChamberView;
import maze.Maze;
import maze.MazeGenerator;


public class Test {
    public static void main(String[] args) {
        MazeGenerator generator = new MazeGenerator("easy");
        Maze maze = generator.getMaze();

        BackendEngine backendEngine = new BackendEngine();
        backendEngine.setChamber(maze.getSolutionChamber());
        ChamberView chamberView = new ChamberView(maze.getSolutionChamber(), backendEngine);
        chamberView.setPreferredSize(new Dimension(720,720));
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Test");
        frame.setContentPane(chamberView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
