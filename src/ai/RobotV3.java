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
        //first match the x coordinate
        if (snakeX.get(0) > appleX) {
            potentialNewHeadX = (snakeHeadX - windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            if (pathIsClear()) {
                if (snakeCurrentDirection.equals("Right")) {
                    game.robotMoveSnake("Down");
                }
                game.robotMoveSnake("Left");
            }

        } else if (snakeX.get(0) < appleX) {
            potentialNewHeadX = (snakeHeadX + windowBlockSize);
            potentialNewHeadY = snakeHeadY;
            if (pathIsClear()) {
                if (snakeCurrentDirection.equals("Left")) {
                    game.robotMoveSnake("Down");
                }
                game.robotMoveSnake("Right");
            }

        } else { //if (snakeX.get(0) == appleX)
            //then match the y coordinate
            if (snakeY.get(0) < appleY) {
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY + windowBlockSize);
                if (pathIsClear()) {
                    if (snakeCurrentDirection.equals("Up")) {
                        game.robotMoveSnake("Right");
                    }
                    game.robotMoveSnake("Down");
                }

            } else if (snakeY.get(0) > appleY) {
                potentialNewHeadX = snakeHeadX;
                potentialNewHeadY = (snakeHeadY - windowBlockSize);
                if (pathIsClear()) {
                    if (snakeCurrentDirection.equals("Down")) {
                        game.robotMoveSnake("Right");
                    }
                    game.robotMoveSnake("Up");
                }
            }
        }
    }
}
