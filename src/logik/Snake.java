package logik;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private static int appleCounter = 0;
    private static String currentDirection = "Right";
    private static Color currentColor = Color.green;
    private static ArrayList<Integer> bodypartX = new ArrayList<>();
    private static ArrayList<Integer> bodypartY = new ArrayList<>();

    public static int getAppleCounter() {
        return appleCounter;
    }

    public static Color getCurrentColor() {
        return currentColor;
    }

    public static ArrayList<Integer> getBodypartX() {
        return bodypartX;
    }

    public static ArrayList<Integer> getBodypartY() {
        return bodypartY;
    }

    public static void setCurrentColor(Color EntercurrentColor) {
        currentColor = EntercurrentColor;
    }

    public static void setCurrentDirection(String EntercurrentDirection) {
        currentDirection = EntercurrentDirection;
    }

    public static void eatApple() {
        appleCounter++;
        bodypartX.add(0);
        bodypartY.add(0);
    }


    public static void move(int x, int y) {
        //move Body in Direction of Head
        for (int bodyPart = bodypartX.size(); bodyPart > 0; bodyPart--) {
            bodypartX.set(bodyPart, bodypartX.get(bodyPart - 1));
            bodypartY.set(bodyPart, bodypartY.get(bodyPart - 1));
        }

        switch (currentDirection) {
            case "Up":
                bodypartY.set(0, bodypartY.get(0)-Window.getBoxSize());
                break;
            case "Down":
                bodypartY.set(0, bodypartY.get(0)+Window.getBoxSize());
                break;
            case "Left":
                bodypartX.set(0, bodypartX.get(0)-Window.getBoxSize());
                break;
            case "Right":
                bodypartX.set(0, bodypartX.get(0)+Window.getBoxSize());
                break;
        }
    }
}

