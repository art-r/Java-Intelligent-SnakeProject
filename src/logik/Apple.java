package logik;

import java.util.Random;

public class Apple {
    private int xCoordinate;
    private int yCoordinate;
    private final Random randomNumberGenerator = new Random();

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void generateNewApple(int screenWidth, int screenHeight, int unitSize) {
        xCoordinate = randomNumberGenerator.nextInt((screenWidth/unitSize)) * unitSize;
        yCoordinate = randomNumberGenerator.nextInt((screenHeight/unitSize)) * unitSize;

        //TODO:
        //Call Apple paint method in Window class with these generated values!!
    }
}
