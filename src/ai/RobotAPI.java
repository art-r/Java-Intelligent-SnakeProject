package ai;

import logik.Apple;
import logik.Snake;
import logik.Window;

//Interface that makes sure we have all the necessary robot api functions
//The robots will need to be able to access these objects and read their variables (through the appropriate getter functions)
public interface RobotAPI {
    public Snake getSnakeObject();

    public Apple getAppleObject();

    public Window getWindowObject();

    public boolean gameisRunning();

    public void robotMoveSnake(String newDirection);
}
