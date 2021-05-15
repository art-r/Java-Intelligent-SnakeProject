package ai;

import logik.Apple;
import logik.GameWindow;
import logik.Snake;
import logik.Window;

import java.awt.*;
import java.util.ArrayList;

//Current Highscore is 'only' 185 apples at framerate of 10
public class SimpleRobot extends RobotMaster{
    @Override
    public void moveRobot() {
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
            game.robotMoveSnake("Up");
        }

        //If it is going up and has reached the (top - 2 blocks) and is not on the right border of the window
        else if (((snakeY.get(0) == (0 + (windowBlockSize))) && snakeCurrentDirection.equals("Up")) && (!(snakeX.get(0) == 0))) {
            game.robotMoveSnake("Left");
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
}
