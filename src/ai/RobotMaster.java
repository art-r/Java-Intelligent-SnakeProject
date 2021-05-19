package ai;

import logik.Apple;
import logik.GameWindow;
import logik.Snake;
import logik.Window;

import java.util.ArrayList;


//abstract robot class that provides all the important variables to the various robot classes
//that way the robot classes dont need implement the same redundant code again and again
public abstract class RobotMaster {
    protected GameWindow game;
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

    private int framerate;

    //the constructor with the framerate has to always exist
    public RobotMaster(int framerate){
        this.framerate = framerate;
    }

    //the main function to start each robot
    public void runRobot(){
        //create the necessary objects
        game = new GameWindow(framerate);
        snake = game.getSnakeObject();
        window = game.getWindowObject();
        apple = game.getAppleObject();

        windowHeight = window.getWINDOW_HEIGHT();
        windowWidth = window.getWINDOW_WIDTH();
        windowBlockSize = window.getBOXLENGTH();

        //start the 'classic' game (the robots build upon it)
        game.runClassicGame();
        //run the movement code of the robot as long as the game is running
        while (game.gameisRunning()) {
            moveRobot();
        }
    }

    //override this function in each robot class and put the strategy of the robot in here
    public abstract void moveRobot();
}
