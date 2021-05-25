package logik;

import java.util.ArrayList;

public class Apple {
    private int xCoordinate;
    private int yCoordinate;

    //these variables are needed to check the new apple coordinates
    private boolean newAppleCheck;
    private int snakeX;
    private int snakeY;

    // these variables are information from the window class (passed through the manager)
    private int windowHeight;
    private int windowBoxSize;
    private int windowWidth;

    public Apple(int windowHeight, int windowWidth, int windowBoxSize) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.windowBoxSize = windowBoxSize;
    }

    //function to generate a new apple
    public void generateNewApple(ArrayList<Integer> forbiddenX, ArrayList<Integer> forbiddenY) {
        //only do this if the snake has not yet reached the maximum length!


        newAppleCheck = true;


        //generate a new apple and check if the new location of the apple is inside the snake (this is not allowed!)
        while (newAppleCheck) {
            //generate new coordinates for the apple

            xCoordinate = (int) (Math.random() * (windowWidth / windowBoxSize)) * windowBoxSize;
            yCoordinate = (int) (Math.random() * (windowHeight / windowBoxSize)) * windowBoxSize;

            //in the beginning theres no snake so we dont have to check anything!
            //==> as the x and y coordinates of the snake grow equally we only have to check x here!
            if (forbiddenX == null) {
                newAppleCheck = false;
            }
            //afterwords we will always have to check if the new apple coordinates are inside the snake
            //if this is the case we need to run this whole while statement again and generate new coordinates for the apple
            //it is important that we now check x & y!
            else {
                //we first assume that the new apple is okay and set the flag
                newAppleCheck = false;
                //now we check if our assumption is correct
                for (int checkCounter = 0; checkCounter < forbiddenX.size(); checkCounter++) {
                    snakeX = forbiddenX.get(checkCounter);
                    snakeY = forbiddenY.get(checkCounter);
                    if ((snakeX == xCoordinate) && (snakeY == yCoordinate)) {
                        //if the coordinates are not correct we need to reset the flag and also stop this loop
                        newAppleCheck = true;
                        break;
                    }
                }
            }
        }
    }

    //getter for the x coordinate of the apple
    public int getxCoordinate() {
        return xCoordinate;
    }

    //getter for the y coordinate of the apple
    public int getyCoordinate() {
        return yCoordinate;
    }
}
