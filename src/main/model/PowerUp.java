package main.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
    Represents a power up contained within a brick.
 */

public class PowerUp {

    // Constants

    public static final int WIDEPADDLE = 4;
    public static final int FASTBALL = 5;
    public static final Color WIDECOLOR = Color.MAGENTA;
    public static final Color FASTCOLOR = Color.PINK;

    // Fields

    private int x, y, dy, type, width, height;
    private boolean wasUsed;
    //private boolean isOnScreen;
    private Color color;

    // Constructor

    public PowerUp(int x, int y, int type, int width, int height) {

        this.x = x;
        this.y = y;

        this.type = type;
        if (type == WIDEPADDLE) { color = WIDECOLOR; }
        if (type == FASTBALL) { color = FASTCOLOR; }

        this.width = width;
        this.height = height;

        dy = (int)(Math.random() * 3 + 1);

        wasUsed = false;

    }

    // Methods

    public void draw(Graphics2D g) {  // Draw method for game loop

        g.setColor(color);
        g.fillRect(x, y, width, height);

    }

    public void update() {  // Update method for game loop

        y += dy;

        if (y > Breakout.HEIGHT) {

            wasUsed = true;

        }

    }

    public Rectangle getBound() {  // Produces rectangle representing boundary of power up for collision detection

        return new Rectangle(x, y, width, height);

    }

    public int getType() { return type; }

    public boolean getWasUsed() { return wasUsed; }

    public void setWasUsed(boolean wasUsed) { this.wasUsed = wasUsed; }

}
