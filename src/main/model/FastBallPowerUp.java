package main.model;

/*
    Represents a power up that makes the ball go faster.
 */

import java.awt.*;

public class FastBallPowerUp extends PowerUp {

    // Constants

    public static final int type = 5;
    public static final Color colour = Color.PINK;

    // Constructor

    public FastBallPowerUp(int x, int y, int width, int height) {

        super(width, x, y, height);

    }

    // Methods

    @Override
    public void draw(Graphics2D g) {

        g.setColor(colour);
        g.fillRect(x, y, width, height);

    }

    @Override
    public int getType() { return type; }

}
