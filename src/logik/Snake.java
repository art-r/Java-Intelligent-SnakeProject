package logik;

import java.awt.*;

public class Snake {
    private int bodyPartCounter = 5;
    private int appleCounter = 0;
    private String currentDirection = "Right";
    private Color currentColor = Color.green;

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void eatApple() {
        appleCounter++;
        bodyPartCounter++;
    }



    public int move(int x, int y) {
        switch (currentDirection) {
            case "Up":
                window.setY[0] = window.setY[0] - window.getBlocksize; //TO-DO: getter- und setter-Funktionen von Window
                break;
            case "Down":
                window.setY[0] = window.setY[0] + window.getBlocksize;
                break;
            case "Left":
                window.setX[0] = window.setX[0] - window.getBlocksize;
                break;
            case "Right":
                window.setX[0] = window.setX[0] + window.getBlocksize;
                break;
        }
        return 0;
    }
}

