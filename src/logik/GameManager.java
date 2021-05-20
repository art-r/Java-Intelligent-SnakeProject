package logik;

import ai.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;


public class GameManager extends JPanel implements ActionListener, RobotAPI {
    //create the necessary objects
    private Window window = new Window();
    private Snake snake = new Snake(window.getBOXLENGTH(), 6);
    private Apple apple = new Apple(window.getWINDOW_HEIGHT(), window.getWINDOW_WIDTH(), window.getBOXLENGTH(), window.getNUMBER_OF_BOXES());
    private RobotMaster robot;
    private boolean robotIsControlling = true;

    //this is needed for the game to work (see explanation later on!)
    private boolean isRunning = false;
    private Timer swingActionEventTimer;

    //these variables are needed to block double input for the movement of the snake...
    //...because calling the movement method twice will otherwise cause the snake to die!
    private boolean movementIsBlocked = false;
    private LinkedList<String> movementSaver = new LinkedList<>();

    //the varialbes for the coordinates and the current direction of the snake
    private ArrayList<Integer> snakeBodyPartsX;
    private ArrayList<Integer> snakeBodyPartsY;
    private String currentDirection;
    private String newDirection = snake.getCurrentDirection();

    //we need these variables later when checking if the snake has hit itself
    private int headX;
    private int headY;
    private int bodyX;
    private int bodyY;

    //the 'framerate' / speed of the game
    private int framerate;

    //constructor
    public GameManager(int framerate, String gameType) {
        //set the framerate
        this.framerate = framerate;

        switch(gameType) {
            case "RobotV1":
                robot = new SimpleRobot(snake, window, apple , this);
                break;
            case "RobotV2":
                robot = new RobotV2(snake, window, apple, this);
                break;
            case "RobotV3":
                robot = new RobotV3(snake, window, apple, this);
                break;
            case "RobotV4":
                robot = new RobotV4(snake, window, apple, this);
                break;
            default:
                robotIsControlling = false;
                break;
        }

        //set the color of the snake
        window.setSnakeColor(snake.getCurrentColor());

        //set the window dimensions, background and properties
        this.setPreferredSize(new Dimension(window.getWINDOW_WIDTH(), window.getWINDOW_HEIGHT()));
        this.setBackground(Color.black);
        this.setFocusable(true);

        //add the custom key listener (see bottom!)
        this.addKeyListener(new ControlKeyChecker());

        //call the start game function
        startGameFunction();
    }

    //the start game function
    public void startGameFunction() {
        //set is running to true (later on we check this to see if the snake has already died)
        isRunning = true;
        //the timer is important for coordinating everything (basically the refresh rate of drawing everything etc.!)
        //the time basically fires an action event with the framerate delay between
        //--> this causes the actionPerformed function to run (see at bottom)!
        swingActionEventTimer = new Timer(framerate, this);
        //start the timer
        swingActionEventTimer.start();
        //generate a new apple
        apple.generateNewApple(snakeBodyPartsX, snakeBodyPartsY);
        //the next steps (paintComponent funciton and actionPerformed function) are called automatically!
    }

    // the running function that will be called each time the swing timer fires (it fires at the specified framerate)
    @Override
    public void actionPerformed(ActionEvent e) {
        //only do this if the game is still running
        if (isRunning) {
            if (robotIsControlling) {
                robot.moveRobot();
            }
            //first check if all queued movements have been executed and if not execute it (we call with "null"...
            //...as we dont want to add another direction and only want to make the queue empty!)
            if (!movementSaver.isEmpty()) {
                setSnakeDirection("null");
            }
            snake.move();
            //after the snake has moved, movement is no longer blocked
            movementIsBlocked = false;
            checkForApple();
            checkGameOver();
        }
        //repaint the window
        repaint();
    }

    //this function is part of JComponent and is called automatically when calling the repaint() method in the actionPerformed method!
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //call the drawElements function
        drawElements(g);
    }

    //draw elements function (draws snake & apple)
    private void drawElements(Graphics g) {
        //only draw these parts if the game is running!
        if (isRunning) {
            //the apple
            window.setAppleCoordinates(apple.getxCoordinate(), apple.getyCoordinate());
            window.drawApple(g);


            //the snake
            snakeBodyPartsX = snake.getBodypartX();
            snakeBodyPartsY = snake.getBodypartY();
            window.setSnakeCoordinates(snakeBodyPartsX, snakeBodyPartsY);
            window.drawSnake(g);

            //the current score
            window.drawCurrentScore(g, snake.getAppleCounter());
        }
        //if the game is not running this means that we should print a game over or 'game succeeded' sign!
        else {
            try {
                Thread.sleep(1000);
                if (snakeBodyPartsX.size() == window.getNUMBER_OF_BOXES()) {
                    window.drawGameWon(g, snake.getAppleCounter());
                } else {
                    window.drawGameOver(g, snake.getAppleCounter());
                }
            } catch (InterruptedException e) {
                if (snakeBodyPartsX.size() == window.getNUMBER_OF_BOXES()) {
                    window.drawGameWon(g, snake.getAppleCounter());
                } else {
                    window.drawGameOver(g, snake.getAppleCounter());
                }
            }
        }
    }

    //function to check if the snake has 'eaten' an apple
    private void checkForApple() {
        //Check if the snake and apple coordinates match
        snakeBodyPartsX = snake.getBodypartX();
        snakeBodyPartsY = snake.getBodypartY();
        //if the head of the snake (index 0) matches the coordinates of the apple the snake has eaten the apple
        if ((snakeBodyPartsX.get(0) == apple.getxCoordinate()) && (snakeBodyPartsY.get(0) == apple.getyCoordinate())) {
            snake.eatApple();
            apple.generateNewApple(snakeBodyPartsX, snakeBodyPartsY);
        }
    }

    //check if the snake should die
    private void checkGameOver() {
        snakeBodyPartsX = snake.getBodypartX();
        snakeBodyPartsY = snake.getBodypartY();
        //check if the snake has reached a border or has hit itself ==> game over!
        //this will be the case in the following scenarios:

        //snake head x coordinate less than 0 -> snake has hit the left border
        if (snakeBodyPartsX.get(0) < 0) {
            isRunning = false;
        }

        //snake head x coordinate equal the window width -> snake has hit the right border
        else if (snakeBodyPartsX.get(0) == window.getWINDOW_WIDTH()) {
            isRunning = false;
        }

        //snake head y coordinate less than 0 -> snake has hit the upper border
        else if (snakeBodyPartsY.get(0) < 0) {
            isRunning = false;
        }

        //snake head y coordinate equals the window height -> snake has hit the bottom border
        else if (snakeBodyPartsY.get(0) == window.getWINDOW_HEIGHT()) {
            isRunning = false;
        }

        //now check if the snake has hit itself
        //we check for every bodyPart coordinates if they match the coordinates of the head
        // --> in that case the snake has hit itself
        headX = snakeBodyPartsX.get(0);
        headY = snakeBodyPartsY.get(0);
        for (int bodyPart = (snakeBodyPartsX.size() - 1); bodyPart > 0; bodyPart--) {
            bodyX = snakeBodyPartsX.get(bodyPart);
            bodyY = snakeBodyPartsY.get(bodyPart);
            if ((headX == bodyX) && (headY == bodyY)) {
                isRunning = false;
                break;
            }
        }

        //check if the game has been won
        if (snakeBodyPartsX.size() == window.getNUMBER_OF_BOXES()) {
            isRunning = false;
        }

        //if the game is no longer running stop the swing timer
        if (!isRunning) {
            swingActionEventTimer.stop();
        }
    }

    //function to set the new snake direction (called by either the robot or by the key-events)
    private void setSnakeDirection(String newDirection) {
        //first of all we need to check that the new direction is not the same as the current direction...
        //...as in this case we dont want to save this new command!
        this.currentDirection = snake.getCurrentDirection();
        if (!(this.currentDirection.equals(newDirection))) {

            //if the snake has not yet executed the previous movement we need to add the new command to a queue...
            //...and then dont do anything as otherwise we would change the direction of the snake before it has actually moved...
            //...causing the snake to die immediately
            if (movementIsBlocked) {
                //only save the newDirection value if it has been called by either a key or by a robot
                //if the setSnakeDirection function is called automatically the new direction value will be "null"!
                if (!(newDirection.equals("null"))) {
                    movementSaver.add(newDirection);
                }
            }
            //if the movement is not blocked
            else {
                //first check if there are still movements to execute
                if (!movementSaver.isEmpty()) {
                    //only save the newDirection value if it has been called by either a key or by a robot
                    //if the setSnakeDirection function is called automatically the new direction value will be "null"!
                    if (!(newDirection.equals("null"))) {
                        movementSaver.add(newDirection);
                    }
                    newDirection = movementSaver.pop();
                }
                //if the queue is already empty and the movement is not blocked the new direction will be executed directly

                //now block movement again as we are executing the next movement command
                //movement will be unblocked as soon as the snake has moved (see function actionPerformed above)
                movementIsBlocked = true;
                //get the current direction as we need to check for some certain unlogic movements
                //(the snake cant move 'backwards' into itself!

                switch (newDirection) {
                    case "Left":
                        if (!currentDirection.equals("Right")) {
                            snake.setCurrentDirection("Left");
                        }
                        break;
                    case "Right":
                        if (!currentDirection.equals("Left")) {
                            snake.setCurrentDirection("Right");
                        }
                        break;
                    case "Up":
                        if (!currentDirection.equals("Down")) {
                            snake.setCurrentDirection("Up");
                        }
                        break;
                    case "Down":
                        if (!currentDirection.equals("Up")) {
                            snake.setCurrentDirection("Down");
                        }
                        break;
                }
            }
        }
    }

    //here we implement the robot api functions (see interface RobotAPI in package ai for explanations)
    //Implement RobotAPI |---BEGIN---->
    @Override
    public void robotMoveSnake(String newDirectionCommand) {
        //first set the current direction again as it is needed in the setSnakeDirection function
        this.currentDirection = snake.getCurrentDirection();
        //we dont pass the newDirection directly to the function for debugging purposes!
        this.newDirection = newDirectionCommand;
        setSnakeDirection(newDirection);
    }
    //Implement RobotAPI <---END----|


    //the class that checks which key has been pressed and then sets the direction of the snake respectively
    public class ControlKeyChecker extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            currentDirection = snake.getCurrentDirection();

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    setSnakeDirection("Left");
                    break;
                case KeyEvent.VK_RIGHT:
                    setSnakeDirection("Right");
                    break;
                case KeyEvent.VK_UP:
                    setSnakeDirection("Up");
                    break;
                case KeyEvent.VK_DOWN:
                    setSnakeDirection("Down");
                    break;
            }
        }
    }
}
