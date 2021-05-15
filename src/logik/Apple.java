package logik;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Apple {
    private int xCoordinate;
    private int yCoordinate;
    private final Random RANDOM_NUMBER_GENERATOR = new Random();
    private boolean newAppleCheck;

    // these variables are information from the window class (passed through the manager)
    private int windowHeight;
    private int windowBoxSize;

    public Apple(int windowHeight, int windowBoxSize) {
        this.windowHeight = windowHeight;
        this.windowBoxSize = windowBoxSize;
    }

    public void generateNewApple(ArrayList<Integer> forbiddenX, ArrayList<Integer> forbiddenY) {
        newAppleCheck = true;
        while (newAppleCheck) {
            xCoordinate = RANDOM_NUMBER_GENERATOR.nextInt((int) (windowHeight / windowBoxSize)) * windowBoxSize;
            yCoordinate = RANDOM_NUMBER_GENERATOR.nextInt((int) (windowHeight / windowBoxSize)) * windowBoxSize;

            //TODO: this is not a correct implementation (we need to check x & y both)
            if (forbiddenX != null) {
                if ((forbiddenX.stream().filter(s -> s == xCoordinate).count()) == 0) {
                    if (forbiddenY != null) {
                        if ((forbiddenY.stream().filter(s -> s == yCoordinate).count()) == 0) {
                            newAppleCheck = false;
                        }
                    } else {
                        newAppleCheck = false;
                    }
                }
            } else {
                newAppleCheck = false;
            }
        }
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }
}
