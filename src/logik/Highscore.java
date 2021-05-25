package logik;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Highscore {
    private String currentGametype;
    private final Path HIGHSCORE_FILE = Paths.get("src/logik/Highscore.csv");
    private BufferedWriter highscoreWriter;
    private BufferedReader highscoreReader;
    private String highscoreLine;
    private ArrayList<String> highscoreTempSaver = new ArrayList<>();
    private String[] splittedHighscoreLine;
    private final String[] GAMETYPES = {"Classic", "RobotV1", "RobotV2", "RobotV3", "RobotV4"};

    //empty construcotr
    public Highscore() {
        checkForHighscoreFile();
    }

    //function to set the current gametype
    public void setCurrentGametype(String gametype) {
        this.currentGametype = gametype;
    }

    //function to write the new highscore
    public void writeHighscore(int score) {
        //first check if the current score is higher then the previous scores
        if (getHighscoreValues(currentGametype).stream().anyMatch(a -> a < score)) {
            //if this is greater than 0 this means that the current score is better than the previous ones
            try {
                //first save the whole file by reading its content and saving every line to an array
                highscoreReader = Files.newBufferedReader(HIGHSCORE_FILE);
                highscoreLine = highscoreReader.readLine();
                while (!(highscoreLine == null)) {
                    //dont read in empty lines or lines that accidently only contain 0 (this is an unresolved bug)!
                    if (!(highscoreLine.equals("")) && !(highscoreLine.equals("0"))) {
                        highscoreTempSaver.add(highscoreLine);
                    }
                    highscoreLine = highscoreReader.readLine();
                }
                highscoreReader.close();

                //now check which line we have to replace
                //we start at the 3rd line as the first 3 lines are always only information text
                for (int lineCounter = 3; lineCounter < highscoreTempSaver.size(); lineCounter++) {
                    splittedHighscoreLine = highscoreTempSaver.get(lineCounter).split(";");
                    if ((splittedHighscoreLine[0].equals(currentGametype)) && (Integer.parseInt(splittedHighscoreLine[1]) <= score)) {
                        //if the gametypes match and the highscore entry is smaller than the current score we need to replace that line
                        //we do this by removing the old line and putting the new score at its place
                        highscoreTempSaver.remove(lineCounter);
                        highscoreTempSaver.add(lineCounter, (currentGametype + ";" + score));

                        //then we need to break as we dont want to replace the other 2 highscore values as well
                        //this could happen if the 1st highscore is replaced (the other ones would be smaller as well and thus replaced)
                        //thats why we need to break here
                        break;
                    }
                }

                //now write all of the lines again to the file (but now with the modified highscore values)
                highscoreWriter = Files.newBufferedWriter(HIGHSCORE_FILE, StandardOpenOption.WRITE);
                for (int lineCounter = 0; lineCounter < highscoreTempSaver.size(); lineCounter++) {
                    highscoreWriter.write(highscoreTempSaver.get(lineCounter) + "\n");
                }
                highscoreWriter.close();

            } catch (IOException e) {
                System.out.println("IO Exception!");
                e.printStackTrace();
            }
        }

    }


    //function to read the highscore file (for the specified robot -> this has to be public for other gui elements!)
    //it will return an array containing the highscores for the specified gametype
    public ArrayList<Integer> getHighscoreValues(String gametype) {
        ArrayList<Integer> highscores = new ArrayList<>(3);
        try {
            highscoreReader = Files.newBufferedReader(HIGHSCORE_FILE);
            //we dont need line 1-3 as they are only text
            for (int i = 0; i < 3; i++) {
                highscoreLine = highscoreReader.readLine();
            }

            //now read until the specified gametype has been found
            while (!(highscoreLine == null)) {
                highscoreLine = highscoreReader.readLine();
                splittedHighscoreLine = highscoreLine.split(";");
                //if we have found the highscore entries for our specified gametype
                if (splittedHighscoreLine[0].equals(gametype)) {
                    highscores.add(Integer.parseInt(splittedHighscoreLine[1]));
                    //we always have 3 entries per gametype but we already read in the 1st one
                    for (int counter = 0; counter < 2; counter++) {
                        highscoreLine = highscoreReader.readLine();
                        splittedHighscoreLine = highscoreLine.split(";");
                        highscores.add(Integer.parseInt(splittedHighscoreLine[1]));
                    }
                    //now after saving all of the highscore values break the while loop
                    break;
                }
            }
            highscoreReader.close();

        } catch (IOException e) {
            System.out.println("IO Exception!");
            e.printStackTrace();
        }
        //return the higscore values
        //they will be (0,0,0) if the gametype has not been played yet!
        return highscores;
    }

    //function to check if the highscore file exists
    //this function will also create the highscore file if it does not yet exist
    private void checkForHighscoreFile() {
        try {
            if (!Files.exists(HIGHSCORE_FILE)) {
                Files.createFile(HIGHSCORE_FILE);

                highscoreWriter = Files.newBufferedWriter(HIGHSCORE_FILE, StandardOpenOption.WRITE);
                highscoreWriter.write("This is the snake game's highscore file\n");
                highscoreWriter.write("it will hold the 3 best values for each of the 5 gametypes\n");
                highscoreWriter.write("gametype; highscore\n");
                //1st - 3rd line: text
                //4th - 6th line: classic mode
                //7th - 9th line: robotV1
                //10th - 12th line: robotV2
                //13th - 15th line: robotV3
                //16th - 18th line: robotV4
                for (String gametype : GAMETYPES) {
                    //each gametype always holds 3 highscore entries
                    for (int gameTypeMultiplier = 0; gameTypeMultiplier < 3; gameTypeMultiplier++) {
                        if ((gametype.equals("RobotV4")) && (gameTypeMultiplier == 2)) {
                            //dont write a new line if this is the last line
                            highscoreWriter.write(gametype + ";" + "000");
                        } else {
                            //a default line will need a break at the end
                            highscoreWriter.write(gametype + ";" + "000\n");
                        }
                    }
                }
                highscoreWriter.close();
            }
        } catch (IOException e) {
            System.out.println("IO Exception!");
            e.printStackTrace();
        }
    }
}
