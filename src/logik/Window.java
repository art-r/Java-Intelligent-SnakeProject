package logik;

public class Window  {
    private int windowHeight = 750;
    private int windowWidth = 1300;
    private int windowSize = windowHeight * windowWidth;

    private int boxLength = 50;
    private int boxSize = boxLength * boxLength;

    private int numberOfBoxes = windowSize / boxSize;

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getNumberOfBoxes() {
        return numberOfBoxes;
    }

    /*GamePanel() {

    }*/
}
