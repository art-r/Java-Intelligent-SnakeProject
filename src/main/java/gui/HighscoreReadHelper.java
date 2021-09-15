package gui;

import logik.Highscore;

import java.util.ArrayList;

//class that helps the start gui to get the highscore values (as in the beginning theres no full highscore manager implemented)
//possible game-types:
//RobotV1, RobotV2, RobotV3, RobotV4 or Classic
public class HighscoreReadHelper {
    Highscore highscoreManager = new Highscore();
    private ArrayList<Integer> tempHighscore;

    public ArrayList<Integer> getHighscoreValues(String gametype){
        return highscoreManager.getHighscoreValues(gametype);
    }
}
