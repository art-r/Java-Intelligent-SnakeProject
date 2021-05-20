package ai;

import logik.Apple;
import logik.GameManager;
import logik.Snake;
import logik.Window;

/*
This Robot works the following way:
1. Move towards the apple (but only if the movements dont kill the snake)
2. determine if the current direction is save or if it will kill the snake
2.1 if the current direction is unsafe check the possible moves
2.2 first check if it can go to the right/to the bottom or else do the opposite
2.3 make the move and then go to 1. again in the next round
*/


public class RobotV3 extends RobotMaster {
    private int snakeHeadX;
    private int snakeHeadY;
    private int potentialNewHeadX;
    private int potentialNewHeadY;
    private boolean pathClear;


    //these values are needed for checking the path
    private int snakePartX;
    private int snakePartY;

    public RobotV3(Snake snake, Window window, Apple apple, GameManager gameManager) {
        super(snake, window, apple, gameManager);
    }


    private boolean pathIsClear() {
        pathClear = true;
        if ((potentialNewHeadX < 0) || (potentialNewHeadX == windowWidth)) {
            return pathClear = false;
        }

        if ((potentialNewHeadY < 0) || (potentialNewHeadY == windowHeight)) {
            return pathClear = false;
        }

        for (int bodyPart = (snakeX.size() - 1); bodyPart > 0; bodyPart--) {
            snakePartX = snakeX.get(bodyPart);
            snakePartY = snakeY.get(bodyPart);
            if ((potentialNewHeadX == snakePartX) && (potentialNewHeadY == snakePartY)) {
                return pathClear = false;
            }
        }
        return pathClear;
    }

    protected void moveTowardsApple() {
        //move towards the apple
        //first match the x coordinate (but dont move if the direction change would kill the snake)
        //also dont do this if the movement is impossible as it would be 180 degree switch of movement
        if ((snakeX.get(0) > appleX) && (!snakeCurrentDirection.equals("Right"))) {
            potentialNewHeadX = (snakeHeadX - windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            if (pathIsClear()) {
                gamemanager.robotMoveSnake("Left");
            }

        } else if ((snakeX.get(0) < appleX) && (!snakeCurrentDirection.equals("Left"))) {
            potentialNewHeadX = (snakeHeadX + windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            if (pathIsClear()) {
                gamemanager.robotMoveSnake("Right");
            }

        } else { //if (snakeX.get(0) == appleX)
            //then match the y coordinate (but dont move if the direction change would kill the snake)
            if ((snakeY.get(0) < appleY) && (!snakeCurrentDirection.equals("Up"))) {
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY + windowBlockSize);
                if (pathIsClear()) {
                    gamemanager.robotMoveSnake("Down");
                }

            } else if ((snakeY.get(0) > appleY) && (!snakeCurrentDirection.equals("Down"))) {
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY - windowBlockSize);
                if (pathIsClear()) {
                    gamemanager.robotMoveSnake("Up");
                }
            }
        }
    }


    private void checkLeftAndRight() {
        if (!pathIsClear()) {
            //check if the snake should either move right or left now
            potentialNewHeadX = (snakeHeadX + windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            if (pathIsClear()) {
                gamemanager.robotMoveSnake("Right");
            } else {
                potentialNewHeadX = (snakeHeadX - windowBlockSize);
                potentialNewHeadY = snakeHeadY;
                if (pathIsClear()) {
                    gamemanager.robotMoveSnake("Left");
                }
            }
        } else {
            moveTowardsApple();
        }
    }

    private void checkUpAndDown() {
        if (!pathIsClear()) {
            //if the path is not clear check if the snake should either move down or up
            potentialNewHeadX = snakeHeadX;
            potentialNewHeadY = (snakeHeadY + windowBlockSize);

            if (pathIsClear()) {
                gamemanager.robotMoveSnake("Down");
            } else {
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY - windowBlockSize);
                if(pathIsClear()) {
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
        if (snakeCurrentDirection.equals("Left")) {
            potentialNewHeadX = (snakeHeadX - windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            //if the snake moves left and its way is blocked by itself or a border...
            //...check if it can avoid the collision by moving up or down (see function checkUpAndDown)
            checkUpAndDown();

        } else if (snakeCurrentDirection.equals("Right")) {
            potentialNewHeadX = (snakeHeadX + windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            //if the snake moves right and its way is blocked by itself or a border...
            //...check if it can avoid the collision by moving up or down (see function checkUpAndDown)
            checkUpAndDown();
        } else if (snakeCurrentDirection.equals("Down")) {
            potentialNewHeadX = snakeHeadX;
            potentialNewHeadY = (snakeHeadY + windowBlockSize);
            //if the snake moves down and its way is blocked by itself or a border...
            //...check if it can avoid the collision by moving right or left (see function checkLeftAndRight)
            checkLeftAndRight();
        } else if (snakeCurrentDirection.equals("Up")) {
            potentialNewHeadX = snakeHeadX;
            potentialNewHeadY = (snakeHeadY - windowBlockSize);
            //if the snake moves up and its way is blocked by itself or a border...
            //...check if it can avoid the collision by moving right or left (see function checkLeftAndRight)
            checkLeftAndRight();
        }
    }
}
