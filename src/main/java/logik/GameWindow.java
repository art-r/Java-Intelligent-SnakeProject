package logik;

import javax.swing.*;

//the game window function that brings everything together
public class GameWindow extends JFrame {
    //a variable to contain the game manager
    private GameManager gameManager;
    private String gametype;
    private String gameTitle;

    //the constructor must set the framerate and pass it to the gamemanager
    public GameWindow(int framerate, String gameType, boolean randomSnakeColor){
        gameManager = new GameManager(framerate, gameType, randomSnakeColor);
        this.gametype = gameType;
    }

    //the start function for the game
    //it adds the game manager to the window, sets the title and the default properties
    public void runGame(){
        this.add(gameManager);

        //set the title (and also include which version of the game is executed)
        switch (gametype) {
            case "RobotV1", "RobotV2", "RobotV3", "RobotV4" -> gameTitle = gametype + " is running the game";
            default -> gameTitle = "Classic Mode";
        }

        this.setTitle("Snake Game - by ASCSF (" + gameTitle + ")");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //default operation on close
        this.setResizable(false); //window must not be resizable!
        this.pack(); //layout of the window must be sized accordingly to the computers screen
        this.setVisible(true); //show the window (we want to see our game actually ^^
        this.setLocationRelativeTo(null); //window should be in the center of the screen
    }
}
