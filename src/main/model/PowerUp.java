package main.model;

import java.awt.*;

/**
    Represents a power up block.
 */

public abstract class PowerUp {

    // Fields

    protected int x;
    protected int y;
    protected int dy;
    protected int width;
    protected int height;
    protected int type;
    protected boolean wasUsed;

    // Constructor

    public PowerUp(int width, int x, int y, int height) {
        this.width = width;
        dy = (int)(Math.random() * 3 + 1);
        wasUsed = false;
        this.x = x;
        this.y = y;
        this.height = height;
    }

    // Methods

    public abstract void draw(Graphics2D g);  // Draw method for game loop

    public void update() {  // Update method for game loop

        y += dy;

        if (y > Breakout.HEIGHT) {

            wasUsed = true;

        }

    }

    public Rectangle getBound() {  // Produces rectangle representing boundary of power up for collision detection

        return new Rectangle(x, y, width, height);

    }

    public abstract int getType();

    public boolean getWasUsed() { return wasUsed; }

    public void setWasUsed(boolean wasUsed) { this.wasUsed = wasUsed; }

}
