package ai;

import logik.GameWindow;
import logik.Snake;
import logik.Window;

import java.util.ArrayList;


//abstract robot class that provides all the important variables to the various robot classes
//that way the robot classes dont need implement the same redundant code again and again
public abstract class RobotMaster {
    protected static GameWindow game;
    protected static Snake snake;
    protected static Window window;
    protected static ArrayList<Integer> snakeX;
    protected static ArrayList<Integer> snakeY;
    protected static int windowHeight;
    protected static int windowWidth;
    protected static int windowBlockSize;
    protected static String snakeCurrentDirection;

    private int framerate = 50;

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
