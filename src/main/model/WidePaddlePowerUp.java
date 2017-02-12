package main.model;

/*
    Represents a power up that makes the paddle wider.
 */

import java.awt.*;

public class WidePaddlePowerUp extends PowerUp {

    // Constants

    public static final int type = 4;
    public static final Color colour = Color.MAGENTA;

    // Constructor

    public WidePaddlePowerUp(int x, int y, int width, int height) {

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
