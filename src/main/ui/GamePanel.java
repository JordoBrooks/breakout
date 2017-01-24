package main.ui;

import main.model.*;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
    The panel in which the game is rendered
 */

public class GamePanel extends JPanel {

    // Fields

    private boolean isRunning;
    private BufferedImage image;
    private Graphics2D g;
    PaddleMouseMotionListener mouseListener;
    private int mouseX;

    // Entities

    private Ball ball;
    private Paddle paddle;
    private Bricks bricks;
    private HUD hud;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<BrickBurst> bursts;

    // Constructor

    public GamePanel() {

        ball = new Ball();

        paddle = new Paddle(100, 20);

        bricks = new Bricks(8, 10, 3);

        hud = new HUD();

        powerUps = new ArrayList<PowerUp>();

        bursts = new ArrayList<BrickBurst>();

        mouseX = 0;

        mouseListener = new PaddleMouseMotionListener();
        addMouseMotionListener(mouseListener);

        image = new BufferedImage(Breakout.WIDTH, Breakout.HEIGHT, BufferedImage.TYPE_INT_RGB);

        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        isRunning = true;

    }

    // Methods

    public void playGame() {  // Game loop

        while (isRunning) {

            update();

            draw();

            repaint();

            try {

                Thread.sleep(10);

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }

    public void checkCollisions() {

        Rectangle ballBound = ball.getBound();
        Rectangle paddleBound = paddle.getBound();

        for (int i = 0; i < powerUps.size(); i++) {

            Rectangle powerUpBound = powerUps.get(i).getBound();

            if (paddleBound.intersects(powerUpBound)) {

                if (powerUps.get(i).getType() == PowerUp.WIDEPADDLE && !powerUps.get(i).getWasUsed()) {

                    paddle.setAltWidth(paddle.getWidth() * 2);

                    powerUps.get(i).setWasUsed(true);

                } else if (powerUps.get(i).getType() == PowerUp.FASTBALL && !powerUps.get(i).getWasUsed()) {

                    ball.speedUp();

                    powerUps.get(i).setWasUsed(true);

                }

            }

        }

        if (ballBound.intersects(paddleBound)) {

            ball.moveAbovePaddle(paddle);

            ball.reverseDY();

            if (ball.hitLeft(mouseX, paddle)) {

                ball.redirectLeft();

            }

            if (ball.hitRight(mouseX, paddle)) {

                ball.redirectRight();

            }

        }

        BRKCHKLOOP:
        for (int row = 0; row < bricks.getMapArray().length; row++) {

            for (int col = 0; col < bricks.getMapArray()[0].length; col++) {

                if (bricks.getMapArray()[row][col] > 0) {

                    int brickx = col * bricks.getBrickWidth() + bricks.HOR_PAD;
                    int bricky = row * bricks.getBrickHeight() + bricks.VERT_PAD;
                    int brickWidth = bricks.getBrickWidth();
                    int brickHeight = bricks.getBrickHeight();

                    Rectangle brickBound = new Rectangle(brickx, bricky, brickWidth, brickHeight);

                    if (ballBound.intersects(brickBound)) {

                        if (bricks.getMapArray()[row][col] == 1) {

                            bursts.add(new BrickBurst(brickx, bricky, bricks));

                        }

                        if (bricks.getMapArray()[row][col] == PowerUp.WIDEPADDLE) {

                            powerUps.add(new PowerUp(brickx, bricky, bricks.getMapArray()[row][col], brickWidth, brickHeight));

                            bricks.setBrick(row, col, 0);

                        } else if (bricks.getMapArray()[row][col] == PowerUp.FASTBALL) {

                            powerUps.add(new PowerUp(brickx, bricky, bricks.getMapArray()[row][col], brickWidth, brickHeight));

                            bricks.setBrick(row, col, 0);

                        } else {

                            bricks.brickHit(row, col);

                        }

                        ball.reverseDY();

                        hud.addScore(50);

                        break BRKCHKLOOP;

                    }
                }
            }
        }

    }

    public void update() {  // Update method for game loop

        checkCollisions();

        ball.update();
        paddle.update();

        for (PowerUp pu : powerUps) {

            pu.update();

        }

        for (int i = 0; i < bursts.size(); i++) {

            bursts.get(i).update();

            if (!bursts.get(i).getIsActive()) {

                bursts.remove(i);

            }

        }

    }

    public void draw() {  // Draw method for game loop

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Breakout.WIDTH, Breakout.HEIGHT);

        bricks.draw(g);
        ball.draw(g);
        paddle.draw(g);
        hud.draw(g);
        drawPowerUps();

        if (bricks.isThereAWinner()) {

            drawWin();

            isRunning = false;

        }

        if (ball.isThereALoser()) {

            drawLose();

            isRunning = false;

        }

        for (BrickBurst bb : bursts) {

            bb.draw(g);

        }

    }

    public void drawWin() {

        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 50));
        g.drawString("Winner!", 200, 200);

    }

    public void drawLose() {

        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 50));
        g.drawString("Loser!", 200, 200);

    }

    public void drawPowerUps() {

        for (PowerUp pu : powerUps) {

            if (!pu.getWasUsed()) {

                pu.draw(g);

            }

        }

    }

    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, 0, 0, Breakout.WIDTH, Breakout.HEIGHT, null);
        g2.dispose();

    }

    private class PaddleMouseMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent arg0) { /* Unused */ }

        @Override
        public void mouseMoved(MouseEvent e) {

            mouseX = e.getX();
            paddle.mouseMoved(e.getX());

        }

    }

}
