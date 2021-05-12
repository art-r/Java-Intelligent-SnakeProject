package logik;

import java.util.Random;

public class Apple {
    private int xCoordinate;
    private int yCoordinate;
    private final Random randomNumberGenerator = new Random();

    public void generateNewApple(int screenWidth, int screenHeight, int unitSize) {
        //TODO:
        // get screenWidth, screemHeight and unitSize from window function and not from parameters!!
        Window.getWindowHeight
        xCoordinate = randomNumberGenerator.nextInt((Window./unitSize)) * unitSize;
        yCoordinate = randomNumberGenerator.nextInt((screenHeight/unitSize)) * unitSize;

        //TODO:
        //Call Apple paint method in Window class with these generated values!!
    }
}
