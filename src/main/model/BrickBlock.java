package main.model;

import java.awt.Color;
import java.awt.Graphics2D;

/*
    Represents a smaller piece of a block when it bursts.
 */

public class BrickBlock {

    // Fields

    private double x, y, dx, dy, gravity;
    private Color color;
    private Bricks bricks;
    private int size;

    // Constructor

    public BrickBlock(double brickx, double bricky, Bricks bricks) {

        this.bricks = bricks;

        x = brickx + this.bricks.getBrickWidth() / 2;
        y = bricky + this.bricks.getBrickHeight() / 2;

        dx = (Math.random() * 30 - 15);
        dy = (Math.random() * 30 - 15);

        size = (int)(Math.random() * 15 + 2);

        color = Color.BLUE;

        gravity = 0.8;

    }

    // Methods

    public void update() {  // Update method for game loop

        x += dx;
        y += dy;
        dy += gravity;

    }

    public void draw(Graphics2D g) {  // Draw method for game loop

        g.setColor(color);
        g.fillRect((int)x, (int)y, size, size);

    }

}
