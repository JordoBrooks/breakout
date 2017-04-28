package main.model;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
    The game ball which bounces around the screen, colliding with the paddle and blocks.
 */

public class Ball {

    // Constants

    public static final int SPEEDUPDURATION = 3;

    // Fields

    private double x, y, dx, dy, resetDY;  // dx, dy are the change in ball x, y position per call to advancePosition()

    private int ballSize;

    private long speedUpStartTime;

    private boolean isSpedUp;

    // Constructor

    public Ball() {

        x = 200;
        y = 200;

        if (Math.random() > 0.5) {

            dx = 1;

        } else {

            dx = -1;

        }

        dy = 3;
        resetDY = dy;

        ballSize = 30;

    }

    // Methods

    public void update() {

        advancePosition();

        if ((System.nanoTime() - speedUpStartTime) / 1000000000 > SPEEDUPDURATION && isSpedUp) {

            dy = resetDY;
            isSpedUp = false;

        }

    }

    public void advancePosition() { // Advances x, y by dx, dy

        x += dx;
        y += dy;

        if (x < 0 || x > Breakout.WIDTH - ballSize) {  // Reverse direction if ball hits left or right walls

            dx = -dx;

        }

        if (y < 0 || y > Breakout.HEIGHT - ballSize) {  // Reverse direction if ball hits top or bottom walls
            
            reverseDY();

        }

    }

    public void redirectLeft() {

        dx -= 0.5;

    }

    public void redirectRight() {

        dx += 0.5;

    }

    public void reverseDY() {

        dy = -dy;
        resetDY = -resetDY;

    }

    public void moveAbovePaddle(Paddle paddle) {  // Moves ball to y coordinate just above paddle; done to avoid issue of ball getting stuck to paddle

        y = Paddle.Y - ballSize;

    }

    public void draw(Graphics2D g) {

        g.setColor(Color.DARK_GRAY);
        g.setStroke(new BasicStroke(4));
        g.drawOval((int)x, (int)y, ballSize, ballSize);

    }

    public Rectangle getBound() {  // Returns a rectangle representing the boundaries of the ball; intended for use in collision detection

        return new Rectangle((int)x, (int)y, ballSize, ballSize);

    }

    public boolean hitLeft(double mouseX, Paddle paddle) {  // Checks if ball has collided with left side of paddle

        return (x < mouseX - paddle.getWidth() * (1/6));

    }

    public boolean hitRight(double mouseX, Paddle paddle) {  // Checks if ball has collided with right side of paddle

        return (x > mouseX + paddle.getWidth() * (1/6));

    }

    public boolean isThereALoser() {  // Returns true (indicating game has been lost) if ball has descended beyond paddle

        boolean gameLost = false;

        if (y > Breakout.HEIGHT - ballSize * 2) {
            gameLost = true;
        }

        return gameLost;

    }

    public void speedUp() {

        speedUpStartTime = System.nanoTime();
        isSpedUp = true;
        dy = dy * 2;

    }

}
