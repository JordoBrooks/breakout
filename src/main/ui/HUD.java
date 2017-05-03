package main.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

/*
    Displays information on screen during game, such as point total.
 */

public class HUD implements Observer {

    // Fields

    private int score;

    // Constructor

    public HUD() {

        score = 0;

    }

    // Methods

    public void draw(Graphics2D g) {  // Draw method for game loop

        g.setFont(new Font("Abyssinica SIL", Font.BOLD, 15));
        g.setColor(Color.RED);
        g.drawString("SCORE: " + score, 20, 20);

    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Observer updated");
        score += 50;
    }
}
