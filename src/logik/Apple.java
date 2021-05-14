package logik;

import java.awt.*;
import java.util.Random;

public class Apple {
    private int xCoordinate;
    private int yCoordinate;
    private final Random RANDOM_NUMBER_GENERATOR = new Random();

    // these variables are information from the window class (passed through the manager)
    private int windowHeight;
    private int windowBoxSize;

    public Apple(int windowHeight, int windowBoxSize) {
        this.windowHeight = windowHeight;
        this.windowBoxSize = windowBoxSize;
    }

    public void generateNewApple() {
        xCoordinate = RANDOM_NUMBER_GENERATOR.nextInt((windowHeight/windowBoxSize)) * windowBoxSize;
        yCoordinate = RANDOM_NUMBER_GENERATOR.nextInt((windowHeight/windowBoxSize)) * windowBoxSize;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }
}
