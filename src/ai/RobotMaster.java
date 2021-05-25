package ai;

import logik.*;

import java.util.ArrayList;


//abstract robot class that provides all the important variables to the various robot classes
//that way the robot classes dont need to implement the same code again and again
public abstract class RobotMaster {
    //the variables for the objects (as we need to be able to access them from the robots...
    //...they need to be set to protected instead of private
    protected GameManager gamemanager;
    protected Snake snake;
    protected Window window;
    protected Apple apple;

    //variables to save the coordinates of the apple and the snakes body
    protected int appleX;
    protected int appleY;
    protected ArrayList<Integer> snakeX;
    protected ArrayList<Integer> snakeY;

    //variables to save the window dimensions
    protected int windowHeight;
    protected int windowWidth;
    protected int windowBlockSize;

    //variable to save the snake's current direction
    protected String snakeCurrentDirection;


    //the constructor with the objects that each robot needs
    public RobotMaster(Snake snake, Window window, Apple apple, GameManager game){
        this.snake = snake;
        this.window = window;
        this.apple = apple;
        this.gamemanager = game;
        windowHeight = window.getWINDOW_HEIGHT();
        windowWidth = window.getWINDOW_WIDTH();
        windowBlockSize = window.getBOXLENGTH();
    }


    //override this function in each robot class and put the strategy of the robot in here
    public abstract void moveRobot();
}
