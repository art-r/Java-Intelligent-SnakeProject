package logik;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JPanel {
    //the window properties
    private final int WINDOW_HEIGHT = 750;
    private final int WINDOW_WIDTH = 1300;
    private final int WINDOW_SIZE = WINDOW_HEIGHT * WINDOW_WIDTH;

    //we divide the window into boxes
    //the size of a box is 50 * 50
    private final int BOXLENGTH = 50;
    private final int BOX_SIZE = BOXLENGTH * BOXLENGTH;
    private final int NUMBER_OF_BOXES = WINDOW_SIZE / BOX_SIZE;

    //Variables to save the coordinates from the apple
    private int appleX;
    private int appleY;

    //Variables to save the coordinates and the color of the snake
    private ArrayList<Integer> snakeBodyPartX;
    private ArrayList<Integer> snakeBodyPartY;
    private Color snakeCurrentColor;

    //empty constructor
    public Window() { }

    //set the appleCoordinates (this is done by the game manager by calling the apples's generateApple function!)
    public void setAppleCoordinates(int appleX, int appleY) {
        this.appleX = appleX;
        this.appleY = appleY;
    }

    //set the snake coordinates information
    public void setSnakeCoordinates(ArrayList<Integer> snakeBodyPartX, ArrayList<Integer> snakeBodyPartY) {
        this.snakeBodyPartX = snakeBodyPartX;
        this.snakeBodyPartY = snakeBodyPartY;
    }

    //set the snake color information
    public void setSnakeColor(Color snakeColor) {
        this.snakeCurrentColor = snakeColor;
    }

    //default getter and setter
    public int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public int getBOX_SIZE() {
        return BOX_SIZE;
    }

    public int getBOXLENGTH() {
        return BOXLENGTH;
    }

    //function to draw the apple
    public void drawApple(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, BOXLENGTH, BOXLENGTH);
    }

    //function to draw the snake
    public void drawSnake(Graphics g) {
        //iterate over the snakes parts
        for (int bodyPart = 0; bodyPart < snakeBodyPartX.size(); bodyPart++) {
            //the color of the head
            if (bodyPart == 0) {
                g.setColor(snakeCurrentColor);
            } else {
                //the color of the body (a different green color)
                g.setColor(new Color(45, 160, 0));
            }
            //draw the parts with the appropriate color
            g.fillRect(snakeBodyPartX.get(bodyPart), snakeBodyPartY.get(bodyPart), BOXLENGTH, BOXLENGTH);
        }
    }


    //draw the current score on top of the window
    public void drawCurrentScore(Graphics g, int applesEaten) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Current Score: " + applesEaten, (WINDOW_WIDTH - metrics.stringWidth("Current Score: " + applesEaten)) / 2, g.getFont().getSize());
    }


    //draw the game over screen
    public void drawGameOver(Graphics g, int applesEaten) {
        //draw the reached score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Reached Score: " + applesEaten, (WINDOW_WIDTH - metrics1.stringWidth("Reached Score: " + applesEaten)) / 2, g.getFont().getSize());

        //draw the game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WINDOW_WIDTH - metrics2.stringWidth("Game Over")) / 2, WINDOW_HEIGHT / 2);
    }
}
