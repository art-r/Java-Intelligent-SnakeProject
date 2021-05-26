package gui;

import logik.GameWindow;
import logik.NoValidSettingsFileException;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SettingsGUI {
    private JFrame frame; //the window frame
    private JPanel Settings; //the settings panel

    //the radio buttons for the color
    private JRadioButton normalColorsRadioButton;
    private JRadioButton rainbowColorsRadioButton;

    //the difficulty slider and the current difficulty text
    private JSlider difficultySlider;
    private JLabel difficultyText;

    //the gamemode radio buttons
    private JRadioButton robotVersion1RadioButton;
    private JRadioButton robotVersion2RadioButton;
    private JRadioButton robotVersion3RadioButton;
    private JRadioButton robotVersion4RadioButton;
    private JRadioButton classicSelfRadioButton;

    //the start button
    private JButton startGameButton;

    //the variables that we need to set the settings
    private boolean rainbowColor; //if this is set to false we know that the player wants normal colors
    private int framerate; //the framerate of the game
    private String gamemode; //the current gamemode
    private int difficultySliderValue; //the difficulty slider value

    //the path for the settings.csv file that holds the settings from the last run
    private final Path SETTINGS_FILE = Paths.get("src/logik/Settings.csv");

    //the other variables/fields that are only placeholders
    private JTextField colorText;
    private JTextField difficultyTextField;
    private JTextField gametypeTextField;

    //constructor containing all the listeners
    public SettingsGUI() {
        frame = new JFrame("Settings");
        frame.setContentPane(this.Settings); //create new frame with this panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //default operation on close
        frame.setResizable(false); //window must not be resizable!
        frame.pack(); //layout of the window must be sized accordingly to the computers screen
        frame.setVisible(true); //show the window (we want to see our game actually ^^
        frame.setLocationRelativeTo(null); //window should be in the center of the screen
        openWindow(); //call the open window function to add all the listeners
    }

    //function to add all the listeners to the window
    private void openWindow() {
        //check for an existing settings file (see function below)
        checkForSettingsFile();

        //listener for the color radio buttons
        normalColorsRadioButton.addActionListener(e -> rainbowColor=false);
        rainbowColorsRadioButton.addActionListener(e -> rainbowColor=true);

        //listener for the difficulty slider
        difficultySlider.addChangeListener(e -> {
            difficultySliderValue = difficultySlider.getValue();
            setFramerateDifficulty(difficultySliderValue);
        });

        //listener for the game mode selection radio buttons
        robotVersion1RadioButton.addActionListener(e -> gamemode = "RobotV1");
        robotVersion2RadioButton.addActionListener(e -> gamemode = "RobotV2");
        robotVersion3RadioButton.addActionListener(e -> gamemode = "RobotV3");
        robotVersion4RadioButton.addActionListener(e -> gamemode = "RobotV4");
        classicSelfRadioButton.addActionListener(e -> gamemode = "Classic");

        //listener for the start button
        startGameButton.addActionListener(e -> {
            //create an object of the game and run it
            GameWindow snakeGame = new GameWindow(framerate, gamemode, rainbowColor);
            snakeGame.runGame();

            //close this window
            frame.dispose();

            //save the current settings by calling the write function
            writeSettings();
        });
    }

    //function to check for an existing settings file
    private void checkForSettingsFile(){
        //check if a settings.csv file exists and read it if it exists
        //if it does not exist create it and set the default values
        try {
            if (Files.exists(SETTINGS_FILE)) {
                //if a settings file exists...
                //...call the function that reads the settings.csv file and applies it
                try {
                    readSettings();
                } catch(NoValidSettingsFileException e) {
                    //if the settings file is not a valid settings file, fall back to the default values and ignore it
                    //set the default values and display the default values in the gui elements
                    normalColorsRadioButton.doClick();
                    difficultySlider.setValue(1);
                    classicSelfRadioButton.doClick();
                }
            } else {
                //if the settings file does not exist create it
                Files.createFile(SETTINGS_FILE);
                //set the default values and display the default values in the gui elements
                normalColorsRadioButton.doClick();
                difficultySlider.setValue(1);
                classicSelfRadioButton.doClick();
            }
        } catch (IOException e) {
            System.out.println("IO Exception!");
            e.printStackTrace();
        }
    }

    //function to set the framerate of the game and display the according difficulty in the gui
    private void setFramerateDifficulty(int sliderValue){
        switch (sliderValue) {
            case 0 -> {
                difficultyText.setText("Easy");
                framerate = 200;
            }
            case 1 -> {
                difficultyText.setText("Normal");
                framerate = 150;
            }
            case 2 -> {
                difficultyText.setText("Hard");
                framerate = 60;
            }
            case 3 -> {
                difficultyText.setText("Boss Mode");
                framerate = 30;
            }
            case 4 -> {
                difficultyText.setText("Unplayable");
                framerate = 5;
            }
        }
    }

    //function to read the settings
    //this function throws our own exception -> this will make it easier for us to see that the settings.csv file did not contain valid input!
    private void readSettings() throws NoValidSettingsFileException{
        try {
            //read in the settings file and split the lines
            BufferedReader settingsReader = Files.newBufferedReader(SETTINGS_FILE);
            settingsReader.readLine(); //we only need the second line!
            String secondLine = settingsReader.readLine(); //we only need the second line!
            String[] settings = secondLine.split(";");

            //set the values that were used last time
            rainbowColor = Boolean.parseBoolean(settings[0]);
            gamemode = settings[2];

            //framerate will be set through the setFramerateDifficulty function!
            setFramerateDifficulty(Integer.parseInt(settings[1]));
            //also set the gui accordingly
            difficultySlider.setValue(Integer.parseInt(settings[1]));

            //set the other gui elements accordingly
            //if the coloring is set to rainbow mode
            if(rainbowColor){
                rainbowColorsRadioButton.setSelected(true);
            } else {
                normalColorsRadioButton.setSelected(true);
            }

            //set the gamemode to the same one as last time
            switch (gamemode) {
                case "RobotV1" -> robotVersion1RadioButton.setSelected(true);
                case "RobotV2" -> robotVersion2RadioButton.setSelected(true);
                case "RobotV3" -> robotVersion3RadioButton.setSelected(true);
                case "RobotV4" -> robotVersion4RadioButton.setSelected(true);
                default -> classicSelfRadioButton.setSelected(true);
            }

            //close the reader
            settingsReader.close();

        } catch (Exception e) {
            //in case of an exception throw our own exception so that we know that the settings file was corrupt
           throw new NoValidSettingsFileException();
        }
    }

    //function to write the settings
    private void writeSettings(){
        try {
            //write the current settings to the file (splitted by ";")
            BufferedWriter settingsWriter = Files.newBufferedWriter(SETTINGS_FILE, StandardOpenOption.WRITE);
            String settingsLine = rainbowColor + ";" + difficultySliderValue + ";" + gamemode;
            settingsWriter.write("rainbow coloring mode (boolean); difficulty slider value (int); gamemode (string)\n");
            settingsWriter.write(settingsLine);
            settingsWriter.close();

        } catch (IOException e) {
            //this time want to throw the default exception as an exception here means the writer had problems!
            System.out.println("IO Exception!");
            e.printStackTrace();
        }
    }
}
