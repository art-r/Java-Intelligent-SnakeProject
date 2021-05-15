package ai;

//Current Highscore is 'only' 185 apples at framerate of 10
public class SimpleRobot extends RobotMaster{
    //==> default code is implemented in RobotMaster <==
    //see RobotMaster for all the variables that are usable!

    //constructor with the framerate
    public SimpleRobot(int framerate){
        super(framerate);
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
            game.robotMoveSnake("Down");
        }

        //Move Left and the up if the snake's head has reached the bottom of the screen
        //Note: This should not happen if the snake is at the bottom and also at the right side of the window!
        else if (((snakeY.get(0) == (windowHeight - windowBlockSize)) && snakeCurrentDirection.equals("Down")) && (!(snakeX.get(0) == 0))) {
            game.robotMoveSnake("Left");
            game.robotMoveSnake("Up");
        }

        //Move Left and Down if the snake has reached the second last block on the top of the window (the last one is reserved!)
        //Note: This should not happen if the snake is on the right side of the window!
        else if (((snakeY.get(0) == (0 + (windowBlockSize))) && snakeCurrentDirection.equals("Up")) && (!(snakeX.get(0) == 0))) {
            game.robotMoveSnake("Left");
            game.robotMoveSnake("Down");
        }

        //Move Up if the snake is at the right side of the screen
        else if ((snakeX.get(0) == 0) && snakeCurrentDirection.equals("Left")) {
            game.robotMoveSnake("Up");
        }

        //Move right if the snake is at the top of the screen and also at the right side of the screen
        else if (((snakeY.get(0) == 0) && snakeCurrentDirection.equals("Up")) && (snakeX.get(0) == 0)) {
            game.robotMoveSnake("Right");
        }
    }
}
