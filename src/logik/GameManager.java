package logik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameManager extends JPanel implements ActionListener {
    //create the necessary objects
    private Window window = new Window();
    private Snake snake = new Snake(window.getBOXLENGTH(), 6);
    private Apple apple = new Apple(window.getWindowHeight(), window.getBOXLENGTH());

    private boolean isRunning = false;
    private Timer swingActionEventTimer;
    private Random randomGenerator;

    private ArrayList<Integer> snakeBodyPartsX;
    private ArrayList<Integer> snakeBodyPartsY;
    private String currentDirection;

    //TODO:
    //make framerate variable
    private int framerate = 150;

    //constructor
    public GameManager() {
        window.setSnakeColor(snake.getCurrentColor());

        this.setPreferredSize(new Dimension(window.getWindowWidth(),window.getWindowHeight()));
        this.setBackground(Color.black);
        this.setFocusable(true);

        randomGenerator = new Random();
        this.addKeyListener(new ControlKeyChecker());
        startGameFunction();
    }

    public void startGameFunction() {
        isRunning = true;
        swingActionEventTimer = new Timer(framerate, this);
        swingActionEventTimer.start();
        apple.generateNewApple();
    }

    @Override
    public void paintComponent(Graphics g) { //Logik
        super.paintComponent(g);
        drawElements(g);
    }

    public void drawElements(Graphics g) {
        //only draw these parts if the game is running!
        if (isRunning) {
            //the apple
            window.setAppleCoordinates(apple.getxCoordinate(), apple.getyCoordinate());
            window.drawApple(g);

            //the snake body parts
            snakeBodyPartsX = snake.getBodypartX();
            snakeBodyPartsY = snake.getBodypartY();

            window.setSnakeCoordinates(snakeBodyPartsX, snakeBodyPartsY);
            window.drawSnake(g);

            //TODO: Print the current highscore at the top of the window (implement this in the window class as well!!)
            window.drawCurrentScore(g, snake.getAppleCounter());
        }
        //if the game is not running this means that we should print a game over sign!
        else {
            window.drawGameOver(g, snake.getAppleCounter());
        }
    }


    public void checkForApple() {
        //Check if the snake and apple coordinates match
        snakeBodyPartsX = snake.getBodypartX();
        snakeBodyPartsY = snake.getBodypartY();
        //if the head of the snake (index 0) matches the coordinates of the apple the snake has eaten the apple
        if ((snakeBodyPartsX.get(0) == apple.getxCoordinate()) && (snakeBodyPartsY.get(0) == apple.getyCoordinate())) {
            snake.eatApple();
            apple.generateNewApple();
        }
    }

    public void checkGameOver() {
        snakeBodyPartsX = snake.getBodypartX();
        snakeBodyPartsY = snake.getBodypartY();
        //check if the snake has reached a border or has hit itself ==> game over!
        //this will be the case in the following scenarios:
        //snake head x coordinate less than 0 -> has hit the left border
        if (snakeBodyPartsX.get(0) < 0) {
            isRunning = false;
        }
        //snake head x coordinate greater than window width -> has hit the right border
        else if (snakeBodyPartsX.get(0) == window.getWindowWidth() || snakeBodyPartsX.get(0) > window.getWindowWidth()) {
            isRunning = false;
        }
        //snake head y coordinate less than 0 -> has hit the upper border
        else if (snakeBodyPartsY.get(0) < 0) {
            isRunning = false;
        }
        //snake head y coordinate greater than window height -> has hit the bottom border
        else if (snakeBodyPartsY.get(0) == window.getWindowHeight() || snakeBodyPartsY.get(0) > window.getWindowHeight()) {
            isRunning = false;
        }

        //now check if it has hit itself
        //we check for every bodyPart coordinates if they match the coordinates of the head --> in that case the snake has hit itself
        for (int bodyPart = (snakeBodyPartsX.size()-1); bodyPart > 0; bodyPart--) {
            if ((snakeBodyPartsX.get(0) == snakeBodyPartsX.get(bodyPart)) && (snakeBodyPartsY.get(0) == snakeBodyPartsY.get(bodyPart))){
                isRunning = false;
            }
        }

        //if the game is no longer running stop the swing timer
        if (!isRunning) {
            swingActionEventTimer.stop();
        }
    }


    // the running function that will be called each time the swing timer fires (it fires at the specified framerate)
    @Override
    public void actionPerformed(ActionEvent e) {
        //only do this if the game is still running
        if (isRunning) {
            snake.move();
            checkForApple();
            checkGameOver();
        }
        //repaint the window
        repaint();
    }

    //the function that check which key has been pressed and then sets the direction of the snake respectively
    public class ControlKeyChecker extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            currentDirection = snake.getCurrentDirection();

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (!currentDirection.equals("Right")) {
                        snake.setCurrentDirection("Left");
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!currentDirection.equals("Left")) {
                        snake.setCurrentDirection("Right");
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (!currentDirection.equals("Down")) {
                        snake.setCurrentDirection("Up");
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!currentDirection.equals("Up")) {
                        snake.setCurrentDirection("Down");
                    }
                    break;
            }
        }

    }
}
