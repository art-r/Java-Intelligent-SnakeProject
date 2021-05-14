package logik;

import java.awt.*;
import java.util.Random;

public class Apple {
    private int xCoordinate;
    private int yCoordinate;
    private final Random randomNumberGenerator = new Random();

    // these variables are information from the window class (passed through the manager)
    private int windowHeight;
    private int windowBoxSize;

    public Apple(int windowHeight, int windowBoxSize) {
        this.windowHeight = windowHeight;
        this.windowBoxSize = windowBoxSize;
    }

    public void generateNewApple(int screenWidth, int screenHeight, int unitSize) {
        xCoordinate = randomNumberGenerator.nextInt((windowHeight/windowBoxSize)) * windowBoxSize;
        yCoordinate = randomNumberGenerator.nextInt((windowHeight/windowBoxSize)) * windowBoxSize;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }
}
