package main.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
    Represents the paddle which deflects the ball and moves with the players mouse.
 */

public class Paddle {

    // Constants

    public static final int Y = Breakout.HEIGHT - 100;
    public static final int ALTWIDTHDURATION = 5;

    // Fields

    private double x;
    private int width, height, initialWidth;  // initialWidth is start width of new instance of Paddle; used to change paddle to initial size, if changed
    private long altWidthStartTime;           // altWidthStartTime is the time when the paddle's width is changed
    private boolean isAltWidth;
    private double targetX;                   // x-coordinate used for tweening of paddle

    // Constructor

    public Paddle(int width, int height) {

        x = (Breakout.WIDTH - width) / 2;

        this.width = width;
        initialWidth = width;
        isAltWidth = false;

        this.height = height;

    }

    // Methods

    public void update() {  // Update method for game loop

        if ((System.nanoTime() - altWidthStartTime) / 1000000000 > ALTWIDTHDURATION && isAltWidth) {

            width = initialWidth;
            isAltWidth = false;

        }

        x += (targetX - x) * 0.4;  // Tweening of paddle movement; 0.4 is an arbitrary number

    }

    public void draw(Graphics2D g) {  // Draw method for game loop

        g.setColor(Color.DARK_GRAY);
        g.fillRect((int)x, Y, width, height);

        if (isAltWidth) {

            g.setColor(Color.WHITE);
            g.setFont(new Font("Abyssinica SIL", Font.BOLD, 18));
            int stringWidth = g.getFontMetrics().stringWidth("Shrinking in " + ((ALTWIDTHDURATION + 1) - (System.nanoTime() - altWidthStartTime) / 1000000000) + "!");
            g.drawString("Shrinking in " + ((ALTWIDTHDURATION + 1) - (System.nanoTime() - altWidthStartTime) / 1000000000) + "!", (int)(x + (width - stringWidth) / 2), Y + 18);
            stringWidth = g.getFontMetrics().stringWidth("WIDE PADDLE POWER-UP ACTIVE!");
            g.setColor(Color.ORANGE);
            g.drawString("WIDE PADDLE POWER-UP ACTIVE!", (int)((Breakout.WIDTH - stringWidth)/2.0), 20);

        }

    }

    public void mouseMoved(int mouseX) {

        targetX = mouseX - (width / 2);

        if (targetX > Breakout.WIDTH - width) {  // Prevents paddle from moving off right edge of screen

            targetX = Breakout.WIDTH - width;

        }

        if (targetX < 0) {  // Prevents paddle from moving off left edge of screen

            targetX = 0;

        }

    }

    public Rectangle getBound() {  // Produces rectangle representing boundary of the paddle for collision detection

        return new Rectangle((int)x, Y, width, height);

    }

    public int getWidth() { return width; }

    public void setAltWidth(int width) {

        this.width = width;
        isAltWidth = true;
        setWidthTimer();

    }

    public void setWidthTimer() {  // Set start of width timer

        altWidthStartTime = System.nanoTime();

    }

}
