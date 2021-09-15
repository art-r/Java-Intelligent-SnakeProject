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

//this robot is a direct extension of robot version 3 as it uses a lot of the same core...
//...functions and only overwrites the check functions!
public class RobotV4 extends RobotV3 {

    //these variables are needed for the collision check loops
    private boolean noCollision; // value to check if the possible path contains collisions
    private int noCollisionCounter; //value to check how many blocks the potential path contains (without any collisions)

    //constructor
    public RobotV4(Snake snake, Window window, Apple apple, GameManager gameManager) {
        super(snake, window, apple, gameManager);
    }



    //function to determine if the snake should go to the left or to the right as a collision avoidance move
    //this overwrites the function from robot version 3
    //most importantly this function to has no preference but instead checks which avoidance movement is better!
    @Override
    protected void checkLeftAndRight(int potentialNewHeadX, int potentialNewHeadY) {
        //this function determines which of the two moves (left or right) has more free blocks
        noCollision = true;
        noCollisionCounter = 1;
        boolean noCollisionRight;
        boolean noCollisionLeft;

        //if the current path is not clear
        if(!pathIsClear(potentialNewHeadX, potentialNewHeadY)) {
            //set the potential new head
            potentialNewHeadY = snakeHeadY;
            while (noCollision) {
                //do this until theres a obstacle in one of the possible ways (to the left or to the right)

                //set the new position according to the possible move (left and right)
                potentialNewHeadX = (snakeHeadX - (windowBlockSize * noCollisionCounter));
                //the variables that are needed to check potential movements of the snake in each direction
                //from the head of the snake to the right
                int potentialNewHeadXR = (snakeHeadX + (windowBlockSize) * noCollisionCounter);

                //check if theres a collision on the new block
                noCollisionRight = pathIsClear(potentialNewHeadXR, potentialNewHeadY);
                noCollisionLeft = pathIsClear(potentialNewHeadX, potentialNewHeadY);

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


    //this overwrites the function from robot version 3
    //most importantly this function to has no preference but instead checks which avoidance movement is better!
    @Override
    protected void checkUpAndDown(int potentialNewHeadX, int potentialNewHeadY) {
        //this is the same as in checkLeftAndRight for the possible moves "up or down"
        //=> see checkLeftAndRight for further explanation therefore! <=
        noCollision = true;
        noCollisionCounter = 1;
        boolean noCollisionUp;
        boolean noCollisionDown;

        if(!pathIsClear(potentialNewHeadX, potentialNewHeadY)) {
            potentialNewHeadX = snakeHeadX;

            while (noCollision) {
                potentialNewHeadY = (snakeHeadY - (windowBlockSize * noCollisionCounter));
                int potentialNewHeadYU = (snakeHeadY + (windowBlockSize * noCollisionCounter));

                noCollisionUp = pathIsClear(potentialNewHeadX, potentialNewHeadY);
                noCollisionDown = pathIsClear(potentialNewHeadX, potentialNewHeadYU);

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
            //the move towards apple function is exactly the same as in robot version 3!
            moveTowardsApple();
        }
    }

    //moveRobot function (we have the same code here as in robot version 3!)
    @Override
    public void moveRobot() {
        super.moveRobot();
    }
}
