package ai;

import logik.Apple;
import logik.GameManager;
import logik.Snake;
import logik.Window;


/*
This Robot works the following way:
1. Move to the right side of the window
2. Move downwards
3. Move to the left and the upwards
4. before reaching the very top of the possible blocks (on the 2nd last block) move left and down
5. do 2.-4. until reaching the left side of the window then move upwards
6. upon reaching the last possible block move to the right and then goto 1.
*/


public class SimpleRobot extends RobotMaster{
    //==> default code is implemented in RobotMaster <==
    //see RobotMaster for all the variables that are usable!


    public SimpleRobot(Snake snake, Window window, Apple apple, GameManager gameManager) {
        super(snake, window, apple, gameManager);
    }

    //the strategy of this robot:
    //take the longest possible way (but this will also be the safest at the same time!)
    @Override
    public void moveRobot() {
        //get the newest coordinates of the snake
        snakeX = snake.getBodypartX();
        snakeY = snake.getBodypartY();

        //get the current direction of the snake
        snakeCurrentDirection = snake.getCurrentDirection();

        //Move downwards if the snake's head has reached the right border
        if ((snakeX.get(0) == (windowWidth - windowBlockSize)) && snakeCurrentDirection.equals("Right")) {
            gamemanager.robotMoveSnake("Down");
        }

        //Move Left and the up if the snake's head has reached the bottom of the screen
        //Note: This should not happen if the snake is at the bottom and also at the right side of the window!
        else if (((snakeY.get(0) == (windowHeight - windowBlockSize)) && snakeCurrentDirection.equals("Down")) && (!(snakeX.get(0) == 0))) {
            gamemanager.robotMoveSnake("Left");
            gamemanager.robotMoveSnake("Up");
        }

        //Move Left and Down if the snake has reached the second last block on the top of the window (the last one is reserved!)
        //Note: This should not happen if the snake is on the right side of the window!
        else if (((snakeY.get(0) == (0 + (windowBlockSize))) && snakeCurrentDirection.equals("Up")) && (!(snakeX.get(0) == 0))) {
            gamemanager.robotMoveSnake("Left");
            gamemanager.robotMoveSnake("Down");
        }

        //Move Up if the snake is at the right side of the screen
        else if ((snakeX.get(0) == 0) && snakeCurrentDirection.equals("Left")) {
            gamemanager.robotMoveSnake("Up");
        }

        //Move right if the snake is at the top of the screen and also at the right side of the screen
        else if (((snakeY.get(0) == 0) && snakeCurrentDirection.equals("Up")) && (snakeX.get(0) == 0)) {
            gamemanager.robotMoveSnake("Right");
        }
    }
}
