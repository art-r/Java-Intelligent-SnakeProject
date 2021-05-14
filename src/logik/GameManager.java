package logik;

public class GameManager {
    public static void main(String[] args) {
        //create the necessary objects
        Window window = new Window();
        Snake snake = new Snake(window.getBoxSize());
        Apple apple = new Apple(window.getWindowHeight(), window.getBoxSize());


    }
}
