package main.ui;

import main.model.*;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    BreakoutMouseListener mouseListener;
    private int mouseX;
    private enum STATE { GAME, MENU }
    private STATE state;
    private Menu menu;

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

        mouseListener = new BreakoutMouseListener();
        addMouseMotionListener(mouseListener);
        addMouseListener(mouseListener);

        image = new BufferedImage(Breakout.WIDTH, Breakout.HEIGHT, BufferedImage.TYPE_INT_RGB);

        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        state = STATE.MENU;

        menu = new Menu(this);

        isRunning = true;

    }

    // Methods

    public void playGame() {  // Game loop

        while (isRunning) {

            if (state == STATE.MENU) {

                menu.render(g);
                repaint();

            } else if (state == STATE.GAME) {

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

    }

    public void checkCollisions() {

        Rectangle ballBound = ball.getBound();
        Rectangle paddleBound = paddle.getBound();

        for (int i = 0; i < powerUps.size(); i++) {

            Rectangle powerUpBound = powerUps.get(i).getBound();

            if (paddleBound.intersects(powerUpBound)) {

                if (powerUps.get(i).getType() == WidePaddlePowerUp.type && !powerUps.get(i).getWasUsed()) {

                    paddle.setAltWidth(paddle.getWidth() * 2);

                    powerUps.get(i).setWasUsed(true);

                } else if (powerUps.get(i).getType() == FastBallPowerUp.type && !powerUps.get(i).getWasUsed()) {

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

        BRICKCHECKLOOP:
        for (int row = 0; row < bricks.getMapArray().length; row++) {

            for (int col = 0; col < bricks.getMapArray()[0].length; col++) {

                if (bricks.getMapArray()[row][col] > 0) {

                    int brickX = col * bricks.getBrickWidth() + bricks.HOR_PAD;
                    int brickY = row * bricks.getBrickHeight() + bricks.VERT_PAD;
                    int brickWidth = bricks.getBrickWidth();
                    int brickHeight = bricks.getBrickHeight();

                    Rectangle brickBound = new Rectangle(brickX, brickY, brickWidth, brickHeight);

                    if (ballBound.intersects(brickBound)) {

                        int brickVal = bricks.getMapArray()[row][col];

                        switch (brickVal) {
                            case 1:
                                bursts.add(new BrickBurst(brickX, brickY, bricks));
                                bricks.brickHit(row, col);
                                break;

                            case WidePaddlePowerUp.type:
                                powerUps.add(new WidePaddlePowerUp(brickX, brickY, brickWidth, brickHeight));
                                bricks.setBrick(row, col, 0);
                                break;

                            case FastBallPowerUp.type:
                                powerUps.add(new FastBallPowerUp(brickX, brickY, brickWidth, brickHeight));
                                bricks.setBrick(row, col, 0);
                                break;

                            default:
                                bricks.brickHit(row, col);
                                break;

                        }

                        ball.reverseDY();

                        hud.addScore(50);

                        break BRICKCHECKLOOP;

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

    private class BreakoutMouseListener implements MouseMotionListener, MouseListener {

        @Override
        public void mouseDragged(MouseEvent arg0) { /* Unused */ }

        @Override
        public void mouseMoved(MouseEvent e) {

            if (state == STATE.GAME) {
                mouseX = e.getX();
                paddle.mouseMoved(e.getX());
            } else if (state == STATE.MENU) {

            }

        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

//            private static final int BUTTON_WIDTH = Breakout.WIDTH/6;
//            private static final int BUTTON_HEIGHT = Breakout.HEIGHT/10;
//            private static final int BUTTON_X = (int)((Breakout.WIDTH - BUTTON_WIDTH)/2.0);
//            private static final int START_BUTTON_Y = (int)(Breakout.HEIGHT*(3.0/5.0));
//            private static final int QUIT_BUTTON_Y = (int)(Breakout.HEIGHT*(4.0/5.0));

            System.out.println("Mouse clicked");

            if (state == STATE.MENU) {

                int x = mouseEvent.getX();
                int y = mouseEvent.getY();

                if (x >= Menu.BUTTON_X && x <= Menu.BUTTON_X  + Menu.BUTTON_WIDTH && y >= Menu.START_BUTTON_Y && y <= Menu.START_BUTTON_Y + Menu.BUTTON_HEIGHT) {  // Clicked start button
                    state = STATE.GAME;
                    System.out.println(state);
                } else if (x >= Menu.BUTTON_X && x <= Menu.BUTTON_X  + Menu.BUTTON_WIDTH && y >= Menu.QUIT_BUTTON_Y && y <= Menu.QUIT_BUTTON_Y + Menu.BUTTON_HEIGHT) {  // Clicked quit button
                    System.exit(0);
                }

            }

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

}
