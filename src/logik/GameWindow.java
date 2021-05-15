package logik;

import ai.RobotAPI;

import javax.swing.*;

//the game window function that brings everything together
public class GameWindow extends JFrame implements RobotAPI {
    //a variable to contain the game manager
    private GameManager gameManager;

    //the constructor must set the framerate and pass it to the gamemanager
    public GameWindow(int framerate){
        gameManager = new GameManager(framerate);
    }

    //the start function for the game
    //it adds the game manager to the window, sets the title and the default properties
    public void runClassicGame(){
        this.add(gameManager);
        this.setTitle("Snake Game - by ASCSF"); //title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //default operation on close
        this.setResizable(false); //window must not be resizable!
        this.pack(); //layout of the window must be sized accordingly to the computers screen
        this.setVisible(true); //show the window (we want to see our game actually ^^^
        this.setLocationRelativeTo(null); //window should be in the center of the screen
    }

    //function to get the gamemanager
    public GameManager getGameManager() {
        return gameManager;
    }

    //implementation of the robot api interface |---start--->
    @Override
    public Snake getSnakeObject(){
        return gameManager.getSnakeObject();
    }

    @Override
    public Apple getAppleObject(){
        return gameManager.getAppleObject();
    }

    @Override
    public Window getWindowObject(){
        return gameManager.getWindowObject();
    }

    @Override
    public boolean gameisRunning(){
        return gameManager.gameisRunning();
    }

    @Override
    public void robotMoveSnake(String newDirection){
        gameManager.robotMoveSnake(newDirection);
    }
    //<---end---|
}
