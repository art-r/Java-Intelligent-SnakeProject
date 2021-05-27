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
    private final Window WINDOW = new Window();
    private final Snake SNAKE = new Snake(WINDOW.getBOXLENGTH(), 6);
    private final Apple APPLE = new Apple(WINDOW.getWINDOW_HEIGHT(), WINDOW.getWINDOW_WIDTH(), WINDOW.getBOXLENGTH());
    private RobotMaster robot;
    private final Highscore HIGHSCORE_HANDLER = new Highscore();

    //variables to check if a robot is running the game
    //this is set to true by default and only set to false if we are controlling the game manually
    private boolean robotIsControlling = true;

    //this is needed for the game to work (see explanation later on!)
    private boolean isRunning = false;
    private Timer swingActionEventTimer;

    //these variables are needed to block double input for the movement of the snake...
    //...because calling the movement method twice will otherwise cause the snake to die!
    private boolean movementIsBlocked = false;
    private LinkedList<String> movementSaver = new LinkedList<>();

    //the variables for the coordinates and the current direction of the snake
    private ArrayList<Integer> snakeBodyPartsX;
    private ArrayList<Integer> snakeBodyPartsY;
    private String currentDirection;
    private String newDirection = SNAKE.getCurrentDirection();

    //we need these variables later when checking if the snake has hit itself
    private int headX;
    private int headY;
    private int bodyX;
    private int bodyY;

    //the 'framerate' or speed of the game
    private int framerate;

    //constructor
    public GameManager(int framerate, String gameType, boolean randomSnakeColor) {
        //set the framerate
        this.framerate = framerate;

        switch (gameType) {
            case "RobotV1" -> robot = new SimpleRobot(SNAKE, WINDOW, APPLE, this);
            case "RobotV2" -> robot = new RobotV2(SNAKE, WINDOW, APPLE, this);
            case "RobotV3" -> robot = new RobotV3(SNAKE, WINDOW, APPLE, this);
            case "RobotV4" -> robot = new RobotV4(SNAKE, WINDOW, APPLE, this);
            default -> robotIsControlling = false;
        }
        //tell the highscore handler which gametype is running
        HIGHSCORE_HANDLER.setCurrentGametype(gameType);

        //tell the window if the snake color should be random
        WINDOW.setSnakeRandomColor(randomSnakeColor);

        //set the window dimensions, background and properties
        this.setPreferredSize(new Dimension(WINDOW.getWINDOW_WIDTH(), WINDOW.getWINDOW_HEIGHT()));
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
        APPLE.generateNewApple(snakeBodyPartsX, snakeBodyPartsY);
        //the next steps (paintComponent function and actionPerformed function) are called automatically!
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
            SNAKE.move();
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
            WINDOW.setAppleCoordinates(APPLE.getxCoordinate(), APPLE.getyCoordinate());
            WINDOW.drawApple(g);


            //the snake
            snakeBodyPartsX = SNAKE.getBodypartX();
            snakeBodyPartsY = SNAKE.getBodypartY();
            WINDOW.setSnakeCoordinates(snakeBodyPartsX, snakeBodyPartsY);
            WINDOW.drawSnake(g);

            //the current score
            WINDOW.drawCurrentScore(g, SNAKE.getAppleCounter());
        }
        //if the game is not running this means that we should print a game over or 'game succeeded' sign!
        else {
            try {
                Thread.sleep(2000);
                if (snakeBodyPartsX.size() == WINDOW.getNUMBER_OF_BOXES()) {
                    WINDOW.drawGameWon(g, SNAKE.getAppleCounter());
                } else {
                    WINDOW.drawGameOver(g, SNAKE.getAppleCounter());
                }
            } catch (InterruptedException e) {
                if (snakeBodyPartsX.size() == WINDOW.getNUMBER_OF_BOXES()) {
                    WINDOW.drawGameWon(g, SNAKE.getAppleCounter());
                } else {
                    WINDOW.drawGameOver(g, SNAKE.getAppleCounter());
                }
            }
        }
    }

    //function to check if the snake has 'eaten' an apple
    private void checkForApple() {
        //Check if the snake and apple coordinates match
        snakeBodyPartsX = SNAKE.getBodypartX();
        snakeBodyPartsY = SNAKE.getBodypartY();
        //if the head of the snake (index 0) matches the coordinates of the apple the snake has eaten the apple
        if ((snakeBodyPartsX.get(0) == APPLE.getxCoordinate()) && (snakeBodyPartsY.get(0) == APPLE.getyCoordinate())) {
            SNAKE.eatApple();
            APPLE.generateNewApple(snakeBodyPartsX, snakeBodyPartsY);
        }
    }

    //check if the snake should die
    private void checkGameOver() {
        snakeBodyPartsX = SNAKE.getBodypartX();
        snakeBodyPartsY = SNAKE.getBodypartY();
        //check if the snake has reached a border or has hit itself ==> game over!
        //this will be the case in the following scenarios:

        //snake head x coordinate less than 0 -> snake has hit the left border
        if (snakeBodyPartsX.get(0) < 0) {
            isRunning = false;
        }

        //snake head x coordinate equal the window width -> snake has hit the right border
        else if (snakeBodyPartsX.get(0) == WINDOW.getWINDOW_WIDTH()) {
            isRunning = false;
        }

        //snake head y coordinate less than 0 -> snake has hit the upper border
        else if (snakeBodyPartsY.get(0) < 0) {
            isRunning = false;
        }

        //snake head y coordinate equals the window height -> snake has hit the bottom border
        else if (snakeBodyPartsY.get(0) == WINDOW.getWINDOW_HEIGHT()) {
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
        if (snakeBodyPartsX.size() == WINDOW.getNUMBER_OF_BOXES()) {
            isRunning = false;
        }

        //if the game is no longer running stop the swing timer
        //also call the highscore handler with the current score
        if (!isRunning) {
            HIGHSCORE_HANDLER.writeHighscore(SNAKE.getAppleCounter());
            swingActionEventTimer.stop();
        }
    }

    //function to set the new snake direction (called by either the robot or by the key-events)
    private void setSnakeDirection(String newDirection) {
        //first of all we need to check that the new direction is not the same as the current direction...
        //...as in this case we dont want to save this new command!
        this.currentDirection = SNAKE.getCurrentDirection();
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
                //get the current direction as we need to check for some certain movements that are not possible
                //(the snake cant move 'backwards' into itself!

                switch (newDirection) {
                    case "Left":
                        if (!currentDirection.equals("Right")) {
                            SNAKE.setCurrentDirection("Left");
                        }
                        break;
                    case "Right":
                        if (!currentDirection.equals("Left")) {
                            SNAKE.setCurrentDirection("Right");
                        }
                        break;
                    case "Up":
                        if (!currentDirection.equals("Down")) {
                            SNAKE.setCurrentDirection("Up");
                        }
                        break;
                    case "Down":
                        if (!currentDirection.equals("Up")) {
                            SNAKE.setCurrentDirection("Down");
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
        this.currentDirection = SNAKE.getCurrentDirection();
        //we dont pass the newDirection directly to the function for debugging purposes!
        this.newDirection = newDirectionCommand;
        setSnakeDirection(newDirection);
    }
    //Implement RobotAPI <---END----|


    //the class that checks which key has been pressed and then sets the direction of the snake respectively
    public class ControlKeyChecker extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            currentDirection = SNAKE.getCurrentDirection();

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> setSnakeDirection("Left");
                case KeyEvent.VK_RIGHT -> setSnakeDirection("Right");
                case KeyEvent.VK_UP -> setSnakeDirection("Up");
                case KeyEvent.VK_DOWN -> setSnakeDirection("Down");
            }
        }
    }
}
