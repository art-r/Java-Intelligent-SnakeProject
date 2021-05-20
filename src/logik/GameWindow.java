package logik;

import ai.RobotAPI;

import javax.swing.*;

//the game window function that brings everything together
public class GameWindow extends JFrame {
    //a variable to contain the game manager
    private GameManager gameManager;

    //the constructor must set the framerate and pass it to the gamemanager
    public GameWindow(int framerate, String gameType){
        gameManager = new GameManager(framerate, gameType);
    }

    //the start function for the game
    //it adds the game manager to the window, sets the title and the default properties
    public void runGame(){
        this.add(gameManager);
        this.setTitle("Snake Game - by ASCSF"); //title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //default operation on close
        this.setResizable(false); //window must not be resizable!
        this.pack(); //layout of the window must be sized accordingly to the computers screen
        this.setVisible(true); //show the window (we want to see our game actually ^^^
        this.setLocationRelativeTo(null); //window should be in the center of the screen
    }
}
