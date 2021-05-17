package ai;

public class RobotV2 extends RobotMaster{
    private int framerate;

    public RobotV2(int framerate) {
        super(framerate);
    }

    @Override
    public void moveRobot(){
        //get the newest coordinates of the snake
        snakeX = snake.getBodypartX();
        snakeY = snake.getBodypartY();

        //get the current direction of the snake
        snakeCurrentDirection = snake.getCurrentDirection();

        //get the newest apple coordinates
        appleX = apple.getxCoordinate();
        appleY = apple.getyCoordinate();

        //move towards the apple
        //first match the x coordinate
        if (snakeX.get(0) > appleX){
            if (snakeCurrentDirection.equals("Right")){
                game.robotMoveSnake("Down");
            }
            game.robotMoveSnake("Left");

        } else if (snakeX.get(0) < appleX){
            if (snakeCurrentDirection.equals("Left")){
                game.robotMoveSnake("Down");
            }
            game.robotMoveSnake("Right");

        } else { //if (snakeX.get(0) == appleX)
            //then match the y coordinate
            if (snakeY.get(0) < appleY) {
                if (snakeCurrentDirection.equals("Up")){
                    game.robotMoveSnake("Right");
                }
                game.robotMoveSnake("Down");

            } else if (snakeY.get(0) > appleY) {
                if (snakeCurrentDirection.equals("Down")){
                    game.robotMoveSnake("Right");
                }
                game.robotMoveSnake("Up");
            }
        }


    }


}
