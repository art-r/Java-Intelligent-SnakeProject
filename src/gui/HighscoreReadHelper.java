package gui;

import logik.HighscoreV2;

import java.util.ArrayList;

public class HighscoreReadHelper {
    HighscoreV2 highscoreManager = new HighscoreV2();

    public ArrayList<Integer> getHighscoreValues(String gametype){
        return highscoreManager.getHighscoreValues(gametype);
    }
}
