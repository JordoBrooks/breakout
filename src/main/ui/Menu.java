package main.ui;

import main.model.Breakout;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
     The main menu displayed when game starts
 */

public class Menu {

    public static final double BUTTON_WIDTH = Breakout.WIDTH/6.0;
    public static final double BUTTON_HEIGHT = Breakout.HEIGHT/10.0;
    public static final double BUTTON_X = ((Breakout.WIDTH - BUTTON_WIDTH)/2.0);
    public static final double START_BUTTON_Y = (Breakout.HEIGHT*(2.5/5.0));
    public static final double QUIT_BUTTON_Y = (Breakout.HEIGHT*(3.5/5.0));

    private Color textColour;
    private Font titleFont;
    private Font optionsFont;
    private String title;
    private RoundRectangle2D startButton;
    private RoundRectangle2D quitButton;
    private boolean buttonHighlighted;

    public Menu(GamePanel panel) {

        textColour = Color.BLUE;
        titleFont = new Font("Abyssinica SIL", Font.BOLD, 50);
        title = "BREAKOUT!";
        optionsFont = new Font("Abyssinica SIL", Font.BOLD, 20);
        buttonHighlighted = false;


    }

    public void render(Graphics2D g) {

        g.setBackground(Color.WHITE);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Breakout.WIDTH, Breakout.HEIGHT);

        g.setColor(Color.BLACK);
        startButton = new RoundRectangle2D.Double(BUTTON_X, START_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, 50, 50);
        quitButton = new RoundRectangle2D.Double(BUTTON_X, QUIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, 50, 50);
        g.draw(startButton);
        g.draw(quitButton);

        g.setFont(titleFont);
        g.setColor(textColour);
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (Breakout.WIDTH - titleWidth)/2, Breakout.HEIGHT/3);

        g.setFont(optionsFont);
        int startWidth = g.getFontMetrics().stringWidth("START");
        int startHeight = g.getFontMetrics().getHeight();
        g.drawString("START", (int)(BUTTON_X + ((BUTTON_WIDTH - startWidth)/2)), (int)(START_BUTTON_Y + ((BUTTON_HEIGHT - startHeight)/2) + startHeight) - 5);
        int quitWidth = g.getFontMetrics().stringWidth("QUIT");
        int quitHeight = g.getFontMetrics().getHeight();
        g.drawString("QUIT", (int)(BUTTON_X + ((BUTTON_WIDTH - quitWidth)/2)), (int)(QUIT_BUTTON_Y + ((BUTTON_HEIGHT - quitHeight)/2) + quitHeight) - 5);
    }

    public void mouseMoved(int mouseX, int mouseY, Graphics2D g) {
        if (mouseX >= Menu.BUTTON_X && mouseX <= Menu.BUTTON_X  + Menu.BUTTON_WIDTH && mouseY >= Menu.START_BUTTON_Y && mouseY <= Menu.START_BUTTON_Y + Menu.BUTTON_HEIGHT) { // mouse in startButton
            if (!buttonHighlighted) {
                g.clearRect((int)BUTTON_X, (int)START_BUTTON_Y, (int)BUTTON_WIDTH, (int)BUTTON_HEIGHT);
                g.setColor(Color.lightGray);
                g.fill(startButton);
                g.setColor(Color.BLACK);
                g.draw(startButton);
                g.setColor(Color.BLUE);
                int startWidth = g.getFontMetrics().stringWidth("START");
                int startHeight = g.getFontMetrics().getHeight();
                g.drawString("START", (int)(BUTTON_X + ((BUTTON_WIDTH - startWidth)/2)), (int)(START_BUTTON_Y + ((BUTTON_HEIGHT - startHeight)/2) + startHeight) - 5);
                buttonHighlighted = true;
            }
        } else if (!(mouseX >= Menu.BUTTON_X && mouseX <= Menu.BUTTON_X  + Menu.BUTTON_WIDTH && mouseY >= Menu.START_BUTTON_Y && mouseY <= Menu.START_BUTTON_Y + Menu.BUTTON_HEIGHT) && !(mouseX >= Menu.BUTTON_X && mouseX <= Menu.BUTTON_X  + Menu.BUTTON_WIDTH && mouseY >= Menu.QUIT_BUTTON_Y && mouseY <= Menu.QUIT_BUTTON_Y + Menu.BUTTON_HEIGHT)) { // mouse not in startButton or quitButton
            if (buttonHighlighted) {
                g.clearRect((int)BUTTON_X, (int)START_BUTTON_Y, (int)BUTTON_WIDTH, (int)BUTTON_HEIGHT);
                g.setColor(Color.WHITE);
                g.fill(startButton);
                g.setColor(Color.BLACK);
                g.draw(startButton);
                g.setColor(Color.BLUE);
                int startWidth = g.getFontMetrics().stringWidth("START");
                int startHeight = g.getFontMetrics().getHeight();
                g.drawString("START", (int)(BUTTON_X + ((BUTTON_WIDTH - startWidth)/2)), (int)(START_BUTTON_Y + ((BUTTON_HEIGHT - startHeight)/2) + startHeight) - 5);
                buttonHighlighted = false;

                g.clearRect((int)BUTTON_X, (int)QUIT_BUTTON_Y, (int)BUTTON_WIDTH, (int)BUTTON_HEIGHT);
                g.setColor(Color.WHITE);
                g.fill(quitButton);
                g.setColor(Color.BLACK);
                g.draw(quitButton);
                g.setColor(Color.BLUE);
                int quitWidth = g.getFontMetrics().stringWidth("QUIT");
                int quitHeight = g.getFontMetrics().getHeight();
                g.drawString("QUIT", (int)(BUTTON_X + ((BUTTON_WIDTH - quitWidth)/2)), (int)(QUIT_BUTTON_Y + ((BUTTON_HEIGHT - quitHeight)/2) + quitHeight) - 5);
            }
        } else if (mouseX >= Menu.BUTTON_X && mouseX <= Menu.BUTTON_X  + Menu.BUTTON_WIDTH && mouseY >= Menu.QUIT_BUTTON_Y && mouseY <= Menu.QUIT_BUTTON_Y + Menu.BUTTON_HEIGHT) { // mouse in quitButton
            if (!buttonHighlighted) {
                g.clearRect((int) BUTTON_X, (int) QUIT_BUTTON_Y, (int) BUTTON_WIDTH, (int) BUTTON_HEIGHT);
                g.setColor(Color.lightGray);
                g.fill(quitButton);
                g.setColor(Color.BLACK);
                g.draw(quitButton);
                g.setColor(Color.BLUE);
                int quitWidth = g.getFontMetrics().stringWidth("QUIT");
                int quitHeight = g.getFontMetrics().getHeight();
                g.drawString("QUIT", (int) (BUTTON_X + ((BUTTON_WIDTH - quitWidth) / 2)), (int) (QUIT_BUTTON_Y + ((BUTTON_HEIGHT - quitHeight) / 2) + quitHeight) - 5);
                buttonHighlighted = true;
            }
        }

    }

}
