package logik;

public class GameManager {
    //create the necessary objects
    private Window window = new Window();
    private Snake snake = new Snake(window.getBoxSize());
    private Apple apple = new Apple(window.getWindowHeight(), window.getBoxSize());



}
