package main.model;

import java.awt.Graphics2D;
import java.util.ArrayList;

/*
    Represents a block that has burst from being hit by the ball.
 */

public class BrickBurst {

    // Fields

    private ArrayList<BrickBlock> blocks;
    private int x, y;
    private Bricks bricks;
    private boolean isActive;
    private long startTime;

    // Constructor

    public BrickBurst(int x, int y, Bricks bricks) {

        this.x = x;
        this.y = y;
        this.bricks = bricks;
        isActive = true;
        startTime = System.nanoTime();
        blocks = new ArrayList<BrickBlock>();

        initBlocks();

    }

    // Methods

    private void initBlocks() {

        int randNum = (int) (Math.random() * 20 + 5);

        for (int i = 0; i < randNum; i++) {

            blocks.add(new BrickBlock(x, y, bricks));

        }

    }

    public void update() {  // Update method for game loop

        if (isActive) {

            for (BrickBlock bp : blocks) {

                bp.update();

            }

            if ((System.nanoTime() - startTime) / 1000000000 > 0.1) {

                isActive = false;

            }

        }

    }

    public void draw(Graphics2D g) {  // Update method for game loop

        for (BrickBlock bp : blocks) {

            bp.draw(g);

        }

    }

    public boolean getIsActive() { return isActive; }

}
