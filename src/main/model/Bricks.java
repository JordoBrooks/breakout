package main.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/*
    Represents the rows and columns of bricks
 */

public class Bricks {

    // Constants

    public final int HOR_PAD = 80, VERT_PAD = 50;

    // Fields

    private int[][] bricks;
    private int height, width;
    private int maxHitValue;

    // Constructor

    public Bricks(int row, int col, int maxHitValue) {

        width = (Breakout.WIDTH - 2 * HOR_PAD) / col;
        height = (Breakout.HEIGHT / 2 - VERT_PAD * 2) / row;
        this.maxHitValue = maxHitValue;

        initMap(row, col, maxHitValue);

    }

    // Methods

    public void initMap(int row, int col, int maxHitValue) {  // Initialize map with hit values (3 = 3 hits to break, 2 = 2 hits to break... 0 = brick broken)

        bricks = new int[row][col];

        for (int i = 0; i < bricks.length; i++) {

            for (int j = 0; j < bricks[0].length; j++) {

                int r = (int)(Math.random() * maxHitValue + 1);
                bricks[i][j] = r;

            }

        }

        bricks[7][2] = 5;
        bricks[4][4] = 4;
        bricks[2][8] = 4;

    }

    public void draw(Graphics2D g) {  // Draw method for game loop

        for(int row = 0; row < bricks.length; row++) {

            for(int col = 0; col < bricks[0].length; col++) {

                if (bricks[row][col] > 0) {

                    if (bricks[row][col] == 1) {

                        g.setColor(Color.BLUE);

                    }

                    if (bricks[row][col] == 2) {

                        g.setColor(Color.CYAN);

                    }

                    if (bricks[row][col] == 3) {

                        g.setColor(Color.DARK_GRAY);

                    }

                    if (bricks[row][col] == WidePaddlePowerUp.type) {

                        g.setColor(WidePaddlePowerUp.colour);

                    }

                    if (bricks[row][col] == FastBallPowerUp.type) {

                        g.setColor(FastBallPowerUp.colour);

                    }
                    g.fillRect(col * width + HOR_PAD, row * height + VERT_PAD, width, height);

                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.WHITE);
                    g.drawRect(col * width + HOR_PAD, row * height + VERT_PAD, width, height);

                }

            }

        }

    }

    public void brickHit(int row, int col) {  // Reduces hit value of brick by 1

        if (bricks[row][col] > 0) {

            bricks[row][col] -= 1;

        }

    }

    public void setBrick(int row, int col, int value) {

        bricks[row][col] = value;

    }

    public boolean isThereAWinner() {  // Returns true if all bricks have been broken

        boolean isWinner = false;

        int bricksRemaining = 0;

        for (int row = 0; row < bricks.length; row++) {

            for (int col = 0; col < bricks[0].length; col++) {

                bricksRemaining += bricks[row][col];

            }

        }

        if (bricksRemaining == 0) {

            isWinner = true;

        }

        return isWinner;

    }

    public int[][] getMapArray() { return bricks; }

    public int getBrickWidth() { return width; }

    public int getBrickHeight() { return height; }

    public int getMaxHitValue() { return maxHitValue; }

}
