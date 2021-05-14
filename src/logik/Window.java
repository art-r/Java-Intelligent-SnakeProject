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

    private final int BOXLENGTH = 50;
    private int boxSize = BOXLENGTH * BOXLENGTH;

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

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getBoxSize() {
        return boxSize;
    }

    public int getBOXLENGTH() {
        return BOXLENGTH;
    }

    public void drawApple(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, BOXLENGTH, BOXLENGTH);
    }

    public void drawSnake(Graphics g) {
        for (int bodyPart = 0; bodyPart < snakeBodyPartX.size(); bodyPart++) {
            if (bodyPart == 0) {
                g.setColor(snakeCurrentColor);
            } else {
                g.setColor(new Color(45, 160, 0));
            }
            g.fillRect(snakeBodyPartX.get(bodyPart), snakeBodyPartY.get(bodyPart), BOXLENGTH, BOXLENGTH);
        }
    }

    public void drawGameOver(Graphics g){
        //TODO
    }

    public void drawCurrentScore(Graphics g){
        //TODO
    }
}
