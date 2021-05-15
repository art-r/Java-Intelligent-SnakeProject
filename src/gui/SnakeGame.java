package gui;

import ai.RobotMaster;
import ai.SimpleRobot;
import logik.GameWindow;

public class SnakeGame {
    public static void main(String[] args) {
        GameWindow classicSnake = new GameWindow();
        RobotMaster robotOne = new SimpleRobot();

        //the classic game (uncomment to run the classic game)
        //classicSnake.runClassicGame();

        //the robot (uncomment to run the robot game)
        robotOne.runRobot();
    }
}
