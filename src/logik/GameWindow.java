package logik;

import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow(){
        this.add(new GameManager());
        this.setTitle("Snake Game - by ASCSF");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
