package ai;

import logik.GameWindow;
import logik.Snake;
import logik.Window;

import java.util.ArrayList;


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

    public void runRobot(){
        //create the necessary objects
        game = new GameWindow();
        snake = game.getSnakeObject();
        window = game.getWindowObject();
        
        game.runClassicGame();
        //run the movement code of the robot as long as the game is running
        while (game.gameisRunning()) {
            moveRobot();
        }
    }

    public abstract void moveRobot();
    //override this function in the robot class and put the strategy of the robot in here
}
