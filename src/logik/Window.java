package logik;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JPanel {
    //TODO:
    //we want to make the sizing of the window not dynamic and not static!
    private int windowHeight = 750;
    private int windowWidth = 1300;
    private int windowSize = windowHeight * windowWidth;

    private final int boxLength = 50;
    private int boxSize = boxLength * boxLength;

    private int numberOfBoxes = windowSize / boxSize;

    //These variables are information from the snake
    private int appleX;
    private int appleY;

    //These variables are information from the apple
    private ArrayList<Integer> snakeBodyPartX;
    private ArrayList<Integer> snakeBodyPartY;
    private Color snakeCurrentColor;

    //we need an empty constructor to first create only a window (the information about the snake etc. will be passed later on!
    public Window(){ }

    //set the appleCoordinates
    public void setAppleCoordinates(int appleX, int appleY){
        this.appleX = appleX;
        this.appleY = appleY;
    }

    //set the snake coordinates
    public void setSnakeCoordinates(ArrayList<Integer> snakeBodyPartX, ArrayList<Integer> snakeBodyPartY){
        this.snakeBodyPartX = snakeBodyPartX;
        this.snakeBodyPartY = snakeBodyPartY;
    }

    //set the snake color
    public void setSnakeColor(Color snakeColor){
        this.snakeCurrentColor = snakeColor;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getBoxSize() {
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

    public void drawApple(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, boxLength, boxLength);
    }

    public void drawSnake(Graphics g) {
        for (int i = 0; i < snakeBodyPartX.size(); i++) {
            if (i == 0) { //Kopf evtl. andere Farbe
                g.setColor(snakeCurrentColor);
            } else {
                g.setColor(snakeCurrentColor);
            }
            g.fillRect(snakeBodyPartX.get(i), snakeBodyPartY.get(i), boxLength, boxSize);
        }
    }
}
