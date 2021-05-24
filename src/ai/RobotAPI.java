package ai;

import logik.Apple;
import logik.Snake;
import logik.Window;

//Interface that makes sure the robots can operate correctly
//The robots will need to be able to access these objects and read their variables (through the appropriate getter functions)
public interface RobotAPI {
    //function to move the snake
    public void robotMoveSnake(String newDirection);
}
