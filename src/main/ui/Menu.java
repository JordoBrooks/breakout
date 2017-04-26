package main.ui;

import main.model.Breakout;

import java.awt.*;

/**
     The main menu displayed when game starts
 */

public class Menu {

    private static final int BUTTON_WIDTH = Breakout.WIDTH/6;
    private static final int BUTTON_HEIGHT = Breakout.HEIGHT/10;
    private static final int BUTTON_X = (int)((Breakout.WIDTH - BUTTON_WIDTH)/2.0);
    private static final int START_BUTTON_Y = (int)(Breakout.HEIGHT*(3.0/5.0));
    private static final int QUIT_BUTTON_Y = (int)(Breakout.HEIGHT*(4.0/5.0));

    private Color textColour;
    private Font titleFont;
    private Font optionsFont;
    private String title;
    private Rectangle startButton;
    private Rectangle quitButton;

    public Menu(GamePanel panel) {

        textColour = Color.WHITE;
        titleFont = new Font("Courier New", Font.BOLD, 50);
        title = "BREAKOUT";
        optionsFont = new Font("Courier New", Font.BOLD, 20);

        startButton = new Rectangle(BUTTON_X, START_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        quitButton = new Rectangle(BUTTON_X, QUIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);

    }

    public void render(Graphics2D g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Breakout.WIDTH, Breakout.HEIGHT);

        g.setFont(titleFont);
        g.setColor(textColour);
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString("BREAKOUT", (Breakout.WIDTH - titleWidth)/2, Breakout.HEIGHT/3);

        g.setFont(optionsFont);
        g.draw(startButton);
        int startWidth = g.getFontMetrics().stringWidth("START");
        int startHeight = g.getFontMetrics().getHeight();
        g.drawString("START", (BUTTON_X + ((BUTTON_WIDTH - startWidth)/2)), (START_BUTTON_Y + ((BUTTON_HEIGHT - startHeight)/2) + startHeight));
        g.draw(quitButton);
        int quitWidth = g.getFontMetrics().stringWidth("QUIT");
        int quitHeight = g.getFontMetrics().getHeight();
        g.drawString("QUIT", (BUTTON_X + ((BUTTON_WIDTH - quitWidth)/2)), (QUIT_BUTTON_Y + ((BUTTON_HEIGHT - quitHeight)/2) + quitHeight));
    }

}
