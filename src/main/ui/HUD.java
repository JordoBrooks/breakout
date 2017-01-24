package main.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

/*
    Displays information on screen during game, such as point total.
 */

public class HUD {

    // Fields

    private int score;

    // Constructor

    public HUD() {

        score = 0;

    }

    // Methods

    public void draw(Graphics2D g) {  // Draw method for game loop

        g.setFont(new Font("Courier New", Font.PLAIN, 14));
        g.setColor(Color.RED);
        g.drawString("Score: " + score, 20, 20);

    }

    public void addScore(int newScore) {

        score += newScore;

    }

}
