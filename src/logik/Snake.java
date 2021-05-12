package logik;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private int appleCounter = 0;
    private String currentDirection = "Right";
    private Color currentColor = Color.green;
    private ArrayList<Integer> bodypartX = new ArrayList<>();
    private ArrayList<Integer> bodypartY = new ArrayList<>();

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void eatApple() {
        appleCounter++;
        bodypartX.add(0);
        bodypartY.add(0);
    }


    public int move(int x, int y) {
        //move Body in Direction of Head
        for (int bodyPart = bodypartX.size(); bodyPart > 0; bodyPart--) {
            bodypartX.set(bodyPart, bodypartX.get(bodyPart - 1));
            bodypartY.set(bodyPart, bodypartY.get(bodyPart - 1));
        }

        //TODO:
        //implement window.getBlocksize in window class

        switch (currentDirection) {
            case "Up":
                bodypartY.set(0, bodypartY.get(0)-window.getBlocksize);
                break;
            case "Down":
                bodypartY.set(0, bodypartY.get(0)+window.getBlocksize);
                break;
            case "Left":
                bodypartX.set(0, bodypartX.get(0)-window.getBlocksize);
                break;
            case "Right":
                bodypartX.set(0, bodypartX.get(0)+window.getBlocksize);
                break;
        }
        return 0;
    }
}

