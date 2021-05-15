package ai;

import logik.Apple;
import logik.GameWindow;
import logik.Snake;
import logik.Window;

import java.util.ArrayList;

//TODO: Not yet perfect (current Highscore is 'only' 185 apples at framerate of 10
//Possible approach: replace thread.sleep for two actions as it is not really reliable especially for very fast movements!
public class SimpleRobot {
    private static GameWindow game;
    private static Snake snake;
    private static Window window;
    private static ArrayList<Integer> snakeX;
    private static ArrayList<Integer> snakeY;
    private static int windowHeight;
    private static int windowWidth;
    private static int windowBlockSize;
    private static String snakeCurrentDirection;
    private static int framerate;
    private static int waitTime;

    public static void moveRobot() {
        snakeX = snake.getBodypartX();
        snakeY = snake.getBodypartY();
        windowHeight = window.getWindowHeight();
        windowWidth = window.getWindowWidth();
        windowBlockSize = window.getBOXLENGTH();
        snakeCurrentDirection = snake.getCurrentDirection();

        //First go down if the snakes head has reached the right border
        if ((snakeX.get(0) == (windowWidth - windowBlockSize)) && snakeCurrentDirection.equals("Right")) {
            game.robotMoveSnake("Down");
        }

        //If it is going down and has reached the bottom and is not on the right border of the window
        else if (((snakeY.get(0) == (windowHeight - windowBlockSize)) && snakeCurrentDirection.equals("Down")) && (!(snakeX.get(0) == 0))) {
            game.robotMoveSnake("Left");
            /*try {
                Thread.sleep(waitTime); //TODO: Replace this with something reliable!
            } catch (InterruptedException e){ }*/
            game.robotMoveSnake("Up");
        }

        //If it is going up and has reached the (top - 2 blocks) and is not on the right border of the window
        else if (((snakeY.get(0) == (0 + (windowBlockSize))) && snakeCurrentDirection.equals("Up")) && (!(snakeX.get(0) == 0))) {
            game.robotMoveSnake("Left");
            /*try {
                Thread.sleep(waitTime); //TODO: Replace this with something reliable!
            } catch (InterruptedException e){ }*/
            game.robotMoveSnake("Down");
        }

        //If it has reached the left side
        else if ((snakeX.get(0) == 0) && snakeCurrentDirection.equals("Left")) {
            game.robotMoveSnake("Up");
        }

        //If it has reached the top
        else if (((snakeY.get(0) == 0) && snakeCurrentDirection.equals("Up")) && (snakeX.get(0) == 0)) {
            game.robotMoveSnake("Right");
        }

    }

    public static void main(String[] args) {
        game = new GameWindow();
        snake = game.getSnakeObject();
        window = game.getWindowObject();

        framerate = game.getGameManager().getFramerate();
        if (50 <= framerate){
            waitTime = framerate + 40;
        }
        else {
            waitTime = framerate + (framerate/2);
        }

        //only keep the robot running as long as the game is running!
        while (game.gameisRunning()) {
            moveRobot();
        }


    }

}
