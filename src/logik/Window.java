package logik;

import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {
    private static int windowHeight = 750;
    private static int windowWidth = 1300;
    private static int windowSize = windowHeight * windowWidth;

    private static int boxLength = 50;
    private static int boxSize = boxLength * boxLength;

    private static int numberOfBoxes = windowSize / boxSize;

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static int getBoxSize() {
        return boxSize;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        drawApple(g);
        drawSnake(g);
    }

    public static void drawApple(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(Apple.getxCoordinate(), Apple.getyCoordinate(), boxLength, boxLength);
    }

    public static void drawSnake(Graphics g) {
        for (int i = 0; i < Snake.getBodypartX().size(); i++) {
            if (i == 0) { //Kopf evtl. andere Farbe
                g.setColor(Snake.getCurrentColor());
            } else {
                g.setColor(Snake.getCurrentColor());
            }
            g.fillRect(Snake.getBodypartX().get(i), Snake.getBodypartY().get(i), boxLength, boxSize);
        }
    }
}
