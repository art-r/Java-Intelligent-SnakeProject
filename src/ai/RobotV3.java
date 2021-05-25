package ai;

import logik.Apple;
import logik.GameManager;
import logik.Snake;
import logik.Window;

/*
This Robot works the following way:
1. Move towards the apple (but only if the movements dont kill the snake)
2. determine if the current direction is safe or if it will kill the snake
2.1 if the current direction is unsafe check the possible moves, else goto 1. again
2.2 first check if it can go to the right/to the bottom or else do the opposite
2.3 make the move and then go to 1. again in the next round
*/


public class RobotV3 extends RobotMaster {
    protected int snakeHeadX;
    protected int snakeHeadY;


    public RobotV3(Snake snake, Window window, Apple apple, GameManager gameManager) {
        super(snake, window, apple, gameManager);
    }

    //function to check if the path is clear
    //takes the potential new head coordinates as its value
    protected boolean pathIsClear(int potentialNewHeadX, int potentialNewHeadY) {
        //if the potential new head is inside the right or left border this indicates that the snake will crash!
        if ((potentialNewHeadX < 0) || (potentialNewHeadX == windowWidth)) {
            return false;
        }

        //if the potential new head is inside the upper or lower border this indicates that the snake will crash!
        if ((potentialNewHeadY < 0) || (potentialNewHeadY == windowHeight)) {
            return false;
        }

        //if the potential new head is inside the snakes body this indicates that the snake will crash into itself!
        for (int bodyPart = (snakeX.size() - 1); bodyPart > 0; bodyPart--) {
            int snakePartX = snakeX.get(bodyPart);
            int snakePartY = snakeY.get(bodyPart);
            if ((potentialNewHeadX == snakePartX) && (potentialNewHeadY == snakePartY)) {
                return false;
            }
        }
        //if none of these cases applies the path is clear for the snake
        return true;
    }

    //function to always move towards the apple
    protected void moveTowardsApple() {
        //move towards the apple
        //first match the x coordinate (but dont move if the direction change would kill the snake)
        //also dont do this if the movement is impossible as it would be 180 degree switch of movement
        if ((snakeX.get(0) > appleX) && (!snakeCurrentDirection.equals("Right"))) {
            if (pathIsClear((snakeHeadX - windowBlockSize), snakeHeadY)) {
                gamemanager.robotMoveSnake("Left");
            }

        } else if ((snakeX.get(0) < appleX) && (!snakeCurrentDirection.equals("Left"))) {
            if (pathIsClear((snakeHeadX + windowBlockSize), snakeHeadY)) {
                gamemanager.robotMoveSnake("Right");
            }

        } else { //if (snakeX.get(0) == appleX)
            //then match the y coordinate (but dont move if the direction change would kill the snake)
            if ((snakeY.get(0) < appleY) && (!snakeCurrentDirection.equals("Up"))) {
                if (pathIsClear(snakeHeadX, (snakeHeadY + windowBlockSize))) {
                    gamemanager.robotMoveSnake("Down");
                }

            } else if ((snakeY.get(0) > appleY) && (!snakeCurrentDirection.equals("Down"))) {
                if (pathIsClear(snakeHeadX, (snakeHeadY - windowBlockSize))) {
                    gamemanager.robotMoveSnake("Up");
                }
            }
        }
    }


    protected void checkLeftAndRight(int potentialNewHeadX, int potentialNewHeadY) {
        if (!pathIsClear(potentialNewHeadX, potentialNewHeadY)) {
            //check if the snake should either move right or left now
            if (pathIsClear((snakeHeadX + windowBlockSize), snakeHeadY)) {
                gamemanager.robotMoveSnake("Right");
            } else {
                if (pathIsClear((snakeHeadX - windowBlockSize), snakeHeadY)) {
                    gamemanager.robotMoveSnake("Left");
                }
            }
        } else {
            moveTowardsApple();
        }
    }

    protected void checkUpAndDown(int potentialNewHeadX, int potentialNewHeadY) {
        if (!pathIsClear(potentialNewHeadX, potentialNewHeadY)) {
            //if the path is not clear check if the snake should either move down or up

            if (pathIsClear(snakeHeadX, (snakeHeadY + windowBlockSize))) {
                gamemanager.robotMoveSnake("Down");
            } else {
                if(pathIsClear(snakeHeadX, (snakeHeadY - windowBlockSize))) {
                    gamemanager.robotMoveSnake("Up");
                }
            }
        } else {
            //if the path is clear move towards the apple
            moveTowardsApple();
        }
    }


    @Override
    public void moveRobot() {
        //get the newest coordinates of the snake
        snakeX = snake.getBodypartX();
        snakeY = snake.getBodypartY();

        //set the head coordinates as we need them later
        snakeHeadX = snakeX.get(0);
        snakeHeadY = snakeY.get(0);

        //get the current direction of the snake
        snakeCurrentDirection = snake.getCurrentDirection();

        //get the newest apple coordinates
        appleX = apple.getxCoordinate();
        appleY = apple.getyCoordinate();


        //check if the current direction will kill the snake if it continues and then move towards the apple
        switch (snakeCurrentDirection) {

            //if the snake moves left and its way is blocked by itself or a border...
            //...check if it can avoid the collision by moving up or down (see function checkUpAndDown)
            case "Left" -> checkUpAndDown((snakeHeadX - windowBlockSize), snakeHeadY);

            //if the snake moves right and its way is blocked by itself or a border...
            //...check if it can avoid the collision by moving up or down (see function checkUpAndDown)
            case "Right" -> checkUpAndDown((snakeHeadX + windowBlockSize), snakeHeadY);

            //if the snake moves down and its way is blocked by itself or a border...
            //...check if it can avoid the collision by moving right or left (see function checkLeftAndRight)
            case "Down" -> checkLeftAndRight(snakeHeadX, (snakeHeadY + windowBlockSize));

            //if the snake moves up and its way is blocked by itself or a border...
            //...check if it can avoid the collision by moving right or left (see function checkLeftAndRight)
            case "Up" -> checkLeftAndRight(snakeHeadX, (snakeHeadY - windowBlockSize));

        }
    }
}
