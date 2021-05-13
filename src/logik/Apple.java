package logik;

import java.awt.*;
import java.util.Random;

public class Apple {
    private static int xCoordinate;
    private static int yCoordinate;
    private static final Random randomNumberGenerator = new Random();

    public static void generateNewApple(int screenWidth, int screenHeight, int unitSize) {
        xCoordinate = randomNumberGenerator.nextInt((Window.getWindowHeight()/Window.getBoxSize())) * Window.getBoxSize();
        yCoordinate = randomNumberGenerator.nextInt((Window.getWindowHeight()/Window.getBoxSize())) * Window.getBoxSize();
    }

    public static int getxCoordinate() {
        return xCoordinate;
    }

    public static int getyCoordinate() {
        return yCoordinate;
    }
}
