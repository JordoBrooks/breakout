package main.model;

import main.ui.GamePanel;

import javax.swing.*;

/*
    Represents the main window in which the Breakout game is played
 */

public class Breakout {

    // Constants

    public static final int WIDTH = 640, HEIGHT = 480;

    // Fields

    GamePanel panel;

    // Constructor

    public Breakout() {

        panel = new GamePanel();

        JFrame frame = new JFrame("Breakout");
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        panel.playGame();

    }

    public static void main(String[] args) {

        new Breakout();

    }

}
