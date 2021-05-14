package logik;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private int appleCounter = 0;
    private String currentDirection = "Right";
    private Color currentColor = Color.green;
    private ArrayList<Integer> bodypartX = new ArrayList<>();
    private ArrayList<Integer> bodypartY = new ArrayList<>();

    private int windowBoxSize;

    public Snake(int windowBoxSize){
        this.windowBoxSize = windowBoxSize;
    }

    public int getAppleCounter() {
        return appleCounter;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public ArrayList<Integer> getBodypartX() {
        return bodypartX;
    }

    public ArrayList<Integer> getBodypartY() {
        return bodypartY;
    }

    public void setCurrentColor(Color EntercurrentColor) {
        currentColor = EntercurrentColor;
    }

    public void setCurrentDirection(String EntercurrentDirection) {
        currentDirection = EntercurrentDirection;
    }

    public void eatApple() {
        appleCounter++;
        bodypartX.add(0);
        bodypartY.add(0);
    }


    public void move(int x, int y) {
        //move Body in Direction of Head
        for (int bodyPart = bodypartX.size(); bodyPart > 0; bodyPart--) {
            bodypartX.set(bodyPart, bodypartX.get(bodyPart - 1));
            bodypartY.set(bodyPart, bodypartY.get(bodyPart - 1));
        }

        //move the head in the selected direction
        switch (currentDirection) {
            case "Up":
                bodypartY.set(0, (bodypartY.get(0)-windowBoxSize));
                break;
            case "Down":
                bodypartY.set(0, (bodypartY.get(0)+windowBoxSize));
                break;
            case "Left":
                bodypartX.set(0, (bodypartX.get(0)-windowBoxSize));
                break;
            case "Right":
                bodypartX.set(0, (bodypartX.get(0)+windowBoxSize));
                break;
        }
    }
}

