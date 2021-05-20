package ai;

import logik.Apple;
import logik.GameManager;
import logik.Snake;
import logik.Window;

public class RobotV4 extends RobotMaster {
    private int snakeHeadX;
    private int snakeHeadY;
    private int potentialNewHeadX;
    private int potentialNewHeadXR;
    private int potentialNewHeadY;
    private int potentialNewHeadYU;
    private boolean pathClear;
    private boolean noCollision;
    private int noCollisionCounter;

    //these values are needed for checking the path
    private int snakePartX;
    private int snakePartY;

    public RobotV4(Snake snake, Window window, Apple apple, GameManager gameManager) {
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

    private void moveTowardsApple() {
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

    private boolean checkDirection(int directionX, int directionY){
        if ((directionX < 0) || (directionX == windowWidth)) {
            return false;
        }

        if ((directionY < 0) || (directionY == windowHeight)) {
            return false;
        }

        for (int bodyPart = (snakeX.size() - 1); bodyPart > 0; bodyPart--) {
            snakePartX = snakeX.get(bodyPart);
            snakePartY = snakeY.get(bodyPart);
            if ((directionX == snakePartX) && (directionY == snakePartY)) {
                return false;
            }
        }
        return true;
    }

    private void checkLeftAndRight() {
        noCollision = true;
        noCollisionCounter = 1;
        boolean noCollisionRight;
        boolean noCollisionLeft;
        if(!pathIsClear()) {
            while (noCollision) {
                potentialNewHeadX = (snakeHeadX - (windowBlockSize * noCollisionCounter));
                potentialNewHeadXR = (snakeHeadX + (windowBlockSize) * noCollisionCounter);
                potentialNewHeadY = snakeHeadY;

                noCollisionRight = checkDirection(potentialNewHeadXR, potentialNewHeadY);
                noCollisionLeft = checkDirection(potentialNewHeadX, potentialNewHeadY);

                if ((!noCollisionLeft) && noCollisionRight) {
                    gamemanager.robotMoveSnake("Right");
                    break;
                } else if ((!noCollisionRight) && noCollisionLeft) {
                    gamemanager.robotMoveSnake("Left");
                    break;
                } else if (noCollisionCounter == (windowWidth / windowBlockSize)) {
                    gamemanager.robotMoveSnake("Right");
                    break;
                }
                noCollisionCounter++;
            }
        } else {
            moveTowardsApple();
        }
    }

    private void checkUpAndDown() {
        noCollision = true;
        noCollisionCounter = 1;
        boolean noCollisionUp;
        boolean noCollisionDown;

        if(!pathIsClear()) {

            while (noCollision) {
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY - (windowBlockSize * noCollisionCounter));
                potentialNewHeadYU = (snakeHeadY + (windowBlockSize * noCollisionCounter));

                noCollisionUp = checkDirection(potentialNewHeadX, potentialNewHeadY);
                noCollisionDown = checkDirection(potentialNewHeadX, potentialNewHeadYU);

                if ((!noCollisionUp) && noCollisionDown) {
                    gamemanager.robotMoveSnake("Down");
                    break;
                } else if ((!noCollisionDown) && noCollisionUp) {
                    gamemanager.robotMoveSnake("Up");
                    break;
                } else if (noCollisionCounter == (windowHeight / windowBlockSize)) {
                    gamemanager.robotMoveSnake("Down");
                    break;
                }
                noCollisionCounter++;
            }
        } else {
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
