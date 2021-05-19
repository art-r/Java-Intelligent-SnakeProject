package gui;

import ai.RobotMaster;
import ai.RobotV2;
import ai.RobotV3;
import ai.SimpleRobot;
import logik.GameWindow;


//the main function to run the game
public class SnakeGame {
    public static void main(String[] args) {
        //create a classic snake game (where the user has to play)
        GameWindow classicSnake = new GameWindow(150);
        //create a robot snake game (where the computer plays)
        //this is robotOne
        RobotMaster robotOne = new SimpleRobot(40);
        RobotMaster robotTwo = new RobotV2(150);
        RobotMaster robotThree = new RobotV3(150);
        //run the game

        //the classic game (uncomment to run the classic game)
        //classicSnake.runClassicGame();

        //the robot (uncomment to run the robot game)
        robotOne.runRobot();
        //robotTwo.runRobot();
        //robotThree.runRobot();
    }
}
