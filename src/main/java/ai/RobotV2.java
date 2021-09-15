package ai;

import logik.Apple;
import logik.GameManager;
import logik.Snake;
import logik.Window;


/*
This Robot works the following way:
1. Move towards the apple by first matching the x and then the y-coordinate of the apple
note that the snake can not do a 180 degree turn-around and thus will sometimes have to first move in another direction first!
*/


public class RobotV2 extends RobotMaster{

    public RobotV2(Snake snake, Window window, Apple apple, GameManager gameManager) {
        super(snake, window, apple, gameManager);
    }


    @Override
    public void moveRobot(){
        //get the newest coordinates from the snake
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
                gamemanager.robotMoveSnake("Down");
            }
            gamemanager.robotMoveSnake("Left");

        } else if (snakeX.get(0) < appleX){
            if (snakeCurrentDirection.equals("Left")){
                gamemanager.robotMoveSnake("Down");
            }
            gamemanager.robotMoveSnake("Right");

        } else { //this equals: if (snakeX.get(0) == appleX) -> x-coordinates match
            //match the y coordinates
            if (snakeY.get(0) < appleY) {
                if (snakeCurrentDirection.equals("Up")){
                    gamemanager.robotMoveSnake("Right");
                }
                gamemanager.robotMoveSnake("Down");

            } else if (snakeY.get(0) > appleY) {
                if (snakeCurrentDirection.equals("Down")){
                    gamemanager.robotMoveSnake("Right");
                }
                gamemanager.robotMoveSnake("Up");
            }
        }
    }
}
