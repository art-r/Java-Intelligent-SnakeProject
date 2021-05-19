package ai;

public class RobotV3 extends RobotMaster {
    private int snakeHeadX;
    private int snakeHeadY;
    private int potentialNewHeadX;
    private int potentialNewHeadY;
    private boolean pathClear;

    public RobotV3(int framerate) {
        super(framerate);
    }


    private boolean pathIsClear() {
        pathClear = true;
        for (int bodyPart = (snakeX.size() - 1); bodyPart > 0; bodyPart--) {
            if ((potentialNewHeadX == snakeX.get(bodyPart)) && (potentialNewHeadY == snakeY.get(bodyPart))) {
                pathClear = false;
                break;
            }
        }
        if ((potentialNewHeadX < 0) || (potentialNewHeadX == windowWidth)) {
            pathClear = false;
        }
        if ((potentialNewHeadY < 0 ) || (potentialNewHeadY == windowHeight)) {
            pathClear = false;
        }
        return pathClear;
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

        //move towards the apple
        //first match the x coordinate (but dont move if the direction change would kill the snake)
        //also dont do this if the movement is impossible as it would be 180 degree switch of movement
        if ((snakeX.get(0) > appleX) && (!snakeCurrentDirection.equals("Right"))) {
            potentialNewHeadX = (snakeHeadX - windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            if (pathIsClear()) {
                game.robotMoveSnake("Left");
            }

        } else if ((snakeX.get(0) < appleX) && (!snakeCurrentDirection.equals("Left"))) {
            potentialNewHeadX = (snakeHeadX + windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            if (pathIsClear()) {
                game.robotMoveSnake("Right");
            }

        } else { //if (snakeX.get(0) == appleX)
            //then match the y coordinate (but dont move if the direction change would kill the snake)
            if ((snakeY.get(0) < appleY) && (!snakeCurrentDirection.equals("Up"))) {
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY + windowBlockSize);
                if (pathIsClear()) {
                    game.robotMoveSnake("Down");
                }

            } else if ((snakeY.get(0) > appleY) && (!snakeCurrentDirection.equals("Down"))) {
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY - windowBlockSize);
                if (pathIsClear()) {
                    game.robotMoveSnake("Up");
                }
            }
        }

        //now check if the current direction will kill the snake if it continues

        if (snakeCurrentDirection == "Left") {
            potentialNewHeadX = (snakeHeadX - windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            //if the snake moves left and its way is blocked by itself or a border
            if (!pathIsClear()) {
                //check if the snake should either move down or up now
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY + windowBlockSize);
                if (pathIsClear()) {
                    game.robotMoveSnake("Down");
                } else {
                    game.robotMoveSnake("Up");
                }
            }
        } else if (snakeCurrentDirection == "Right") {
            potentialNewHeadX = (snakeHeadX + windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            //if the snake moves right and its way is blocked by itself or a border
            if (!pathIsClear()) {
                //check if the snake should either move down or up now
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY + windowBlockSize);
                if (pathIsClear()) {
                    game.robotMoveSnake("Down");
                } else {
                    game.robotMoveSnake("Up");
                }
            }
        } else if (snakeCurrentDirection == "Down") {
            potentialNewHeadX = snakeHeadX;
            potentialNewHeadY = (snakeHeadY + windowBlockSize);
            //if the snake moves down and its way is blocked by itself or a border
            if (!pathIsClear()) {
                //check if the snake should either move right or left now
                potentialNewHeadX = (snakeHeadX + windowBlockSize);
                potentialNewHeadY = snakeHeadY;
                if (pathIsClear()) {
                    game.robotMoveSnake("Right");
                } else {
                    game.robotMoveSnake("Left");
                }
            }
        } else if (snakeCurrentDirection == "Up") {
            potentialNewHeadX = snakeHeadX;
            potentialNewHeadY = (snakeHeadY - windowBlockSize);
            //if the snake moves up and its way is blocked by itself or a border
            if (!pathIsClear()) {
                //check if the snake should either move right or left now
                potentialNewHeadX = (snakeHeadX + windowBlockSize);
                potentialNewHeadY = snakeHeadY;
                if (pathIsClear()) {
                    game.robotMoveSnake("Right");
                } else {
                    game.robotMoveSnake("Left");
                }
            }
        }
    }
}
