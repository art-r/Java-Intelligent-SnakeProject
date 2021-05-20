package ai;

import logik.*;

import java.util.ArrayList;


//abstract robot class that provides all the important variables to the various robot classes
//that way the robot classes dont need implement the same redundant code again and again
public abstract class RobotMaster {
    protected GameManager gamemanager;
    protected Snake snake;
    protected Window window;
    protected Apple apple;
    protected int appleX;
    protected int appleY;
    protected ArrayList<Integer> snakeX;
    protected ArrayList<Integer> snakeY;
    protected int windowHeight;
    protected int windowWidth;
    protected int windowBlockSize;
    protected String snakeCurrentDirection;


    //the constructor with the framerate has to always exist
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
