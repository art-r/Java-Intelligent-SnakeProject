package ai;


//Interface that makes sure the robots can operate correctly
//The robots will need to be able to move the snake and therefore access the moverobots function
public interface RobotAPI {
    //function to move the snake
    public void robotMoveSnake(String newDirection);
}
