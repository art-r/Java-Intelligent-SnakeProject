package logik;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private int appleCounter = 0;
    private String currentDirection = "Right"; // we start at the top left screen with a movement to the right
    private ArrayList<Integer> bodypartX = new ArrayList<>(); //array for the x-coordinates of the snake
    private ArrayList<Integer> bodypartY = new ArrayList<>(); //array for the y-coordinates of the snake

    private int windowBoxSize; //the size of the window boxes (they're virtual!)

    public Snake(int windowBoxSize, int startsize){
        this.windowBoxSize = windowBoxSize;

        //initialize the startsize of the snake
        for(int counter = 0; counter < startsize; counter++){
            bodypartX.add(0);
            bodypartY.add(0);
        }
    }

    //default getter and setter
    public int getAppleCounter() {
        return appleCounter;
    }

    public ArrayList<Integer> getBodypartX() {
        return bodypartX;
    }

    public ArrayList<Integer> getBodypartY() {
        return bodypartY;
    }

    public void setCurrentDirection(String newDirection) {
        this.currentDirection = newDirection;
    }

    public String getCurrentDirection() {
        return this.currentDirection;
    }

    //function to eat an apple => snake has to grow
    public void eatApple() {
        appleCounter++;
        bodypartX.add(bodypartX.get(bodypartX.size()-1));
        bodypartY.add(bodypartY.get(bodypartY.size()-1));
    }

    //function to move the snake
    public void move() {
        //move body in direction of head
        for (int bodyPart = (bodypartX.size()-1); bodyPart > 0; bodyPart--) {
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

