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
2.2 determine which possible move is best
2.3 make the move and then go to 1. again in the next round
*/


public class RobotV4 extends RobotMaster {
    //the variables that are needed to check the location of the snake
    private int snakeHeadX;
    private int snakeHeadY;
    private int snakePartX;
    private int snakePartY;

    //the variables that are needed to check potential movements of the snake in each direction
    private int potentialNewHeadX; //from the head of the snake to the left
    private int potentialNewHeadXR; //from the head of the snake to the right
    private int potentialNewHeadY; //from the head of the snake to the lower block
    private int potentialNewHeadYU; //from the head of the snake to upper block
    private boolean noCollision; // value to check if the possible path contains collisions
    private int noCollisionCounter; //value to check how many blocks the potential path contains (without any collisions)
    private boolean pathClear; //value to save if the path the snake is taking is clear

    //constructor
    public RobotV4(Snake snake, Window window, Apple apple, GameManager gameManager) {
        super(snake, window, apple, gameManager);
    }

    //function to check if the path of the snake is clear
    //this works by setting potentialNewHeadX/Y before calling this function
    //(potentialNewHeadX/Y contains the new position of the snakes head if it continues to move its way)
    private boolean pathIsClear() {
        //check if the snake is going to hit either the left or right border of the window
        if ((potentialNewHeadX < 0) || (potentialNewHeadX == windowWidth)) {
            return pathClear = false;
        }

        //check if the snake is going to hit either the upper or lower border of the window
        if ((potentialNewHeadY < 0) || (potentialNewHeadY == windowHeight)) {
            return pathClear = false;
        }

        //check if the snake is going to hit itself
        for (int bodyPart = (snakeX.size() - 1); bodyPart > 0; bodyPart--) {
            snakePartX = snakeX.get(bodyPart);
            snakePartY = snakeY.get(bodyPart);
            if ((potentialNewHeadX == snakePartX) && (potentialNewHeadY == snakePartY)) {
                return pathClear = false;
            }
        }

        //if none of these cases are valid the path is clear for the snake!
        return pathClear = true;
    }

    //function to move the snake towards the apple
    private void moveTowardsApple() {
        //move towards the apple
        //first match the x coordinate (but dont move if the change of direction would kill the snake)
        //also dont do this if the movement is impossible (as it would be 180 degree switch of movement)
        if ((snakeX.get(0) > appleX) && (!snakeCurrentDirection.equals("Right"))) {
            //if the snake is more 'right' than the apple
            potentialNewHeadX = (snakeHeadX - windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            if (pathIsClear()) {
                gamemanager.robotMoveSnake("Left");
            }

        } else if ((snakeX.get(0) < appleX) && (!snakeCurrentDirection.equals("Left"))) {
            //if the snake is more 'left' than the apple
            potentialNewHeadX = (snakeHeadX + windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            if (pathIsClear()) {
                gamemanager.robotMoveSnake("Right");
            }

        } else { //if (snakeX.get(0) == appleX) --> the x coordinates match
            //now match the y coordinates (but dont move if the direction change would kill the snake or is impossible)
            if ((snakeY.get(0) < appleY) && (!snakeCurrentDirection.equals("Up"))) {
                //if the snake is above the apple
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY + windowBlockSize);
                if (pathIsClear()) {
                    gamemanager.robotMoveSnake("Down");
                }

            } else if ((snakeY.get(0) > appleY) && (!snakeCurrentDirection.equals("Down"))) {
                //if the snake is below the apple
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY - windowBlockSize);
                if (pathIsClear()) {
                    gamemanager.robotMoveSnake("Up");
                }
            }
        }
    }

    //function to check a potential new direction
    //the function takes two values (x & y coordinate) that are to check
    private boolean checkDirection(int directionX, int directionY){
        //if the new position would result in a crash into the left or right border
        if ((directionX < 0) || (directionX == windowWidth)) {
            return false;
        }

        //if the new position would result in a crash into the upper or lower border
        if ((directionY < 0) || (directionY == windowHeight)) {
            return false;
        }

        //if the new position would result in the snaking crashing into itself
        for (int bodyPart = (snakeX.size() - 1); bodyPart > 0; bodyPart--) {
            snakePartX = snakeX.get(bodyPart);
            snakePartY = snakeY.get(bodyPart);
            if ((directionX == snakePartX) && (directionY == snakePartY)) {
                return false;
            }
        }
        //if all of these cases are not valid the new position is save!
        return true;
    }

    //function to determine if the snake should go to the left or to the right as a collision avoidance move
    private void checkLeftAndRight() {
        //this function determines which of the two moves (left or right) has more free blocks
        noCollision = true;
        noCollisionCounter = 1;
        boolean noCollisionRight;
        boolean noCollisionLeft;
        if(!pathIsClear()) {
            //if the current path is not clear

            potentialNewHeadY = snakeHeadY;
            while (noCollision) {
                //do this until theres a obstacle in one of the possible ways (to the left or to the right)

                //set the new position according to the possible move (left and right)
                potentialNewHeadX = (snakeHeadX - (windowBlockSize * noCollisionCounter));
                potentialNewHeadXR = (snakeHeadX + (windowBlockSize) * noCollisionCounter);

                //check if theres a collision on the new block
                noCollisionRight = checkDirection(potentialNewHeadXR, potentialNewHeadY);
                noCollisionLeft = checkDirection(potentialNewHeadX, potentialNewHeadY);

                //if one of the two possible moves has a collision in its way before the other one...
                //...take the move that still has no collision in its way
                if ((!noCollisionLeft) && noCollisionRight) {
                    gamemanager.robotMoveSnake("Right");
                    break;
                } else if ((!noCollisionRight) && noCollisionLeft) {
                    gamemanager.robotMoveSnake("Left");
                    break;
                } //if both directions dont have an obstacle in it for an equal amount of blocks go to the right
                else if (noCollisionCounter == (windowWidth / windowBlockSize)) {
                    gamemanager.robotMoveSnake("Right");
                    break;
                }
                noCollisionCounter++;
            }
        } else {
            //if theres no collision in the direction of the snake move towards the apple
            moveTowardsApple();
        }
    }

    private void checkUpAndDown() {
        //this is the same as in checkLeftAndRight for the possible moves up or down
        noCollision = true;
        noCollisionCounter = 1;
        boolean noCollisionUp;
        boolean noCollisionDown;

        if(!pathIsClear()) {
            potentialNewHeadX = snakeHeadX;

            while (noCollision) {

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

    //moveRobotfunciton
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
