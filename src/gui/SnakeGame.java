package gui;

import ai.RobotMaster;
import ai.RobotV2;
import ai.RobotV3;
import ai.SimpleRobot;
import logik.GameWindow;


//the main function to run the game
public class SnakeGame {
    public static void main(String[] args) {
        //create the game and pass along the framerate and the gametype (empty String = classic game, RobotV1, RobotV2, RobotV3)
        GameWindow snake = new GameWindow(100, "RobotV4", true);
        //Highscore RobotV4: 50
        //Highscore RobotV3: 21
        //Highscore RobotV2: 16
        //Highscore RobotV1: game-won

        //run the game
        snake.runGame();
    }
}
