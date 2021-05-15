package logik;

import ai.RobotAPI;

import javax.swing.*;

public class GameWindow extends JFrame implements RobotAPI {
    private GameManager gameManager = new GameManager();

    public GameWindow(){
        this.add(gameManager);
        this.setTitle("Snake Game - by ASCSF");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

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
}
