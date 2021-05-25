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
    private JFrame frame;
    private JTextField colorText;
    private JPanel Settings;
    private JRadioButton normalColorsRadioButton;
    private JRadioButton rainbowColorsRadioButton;
    private JTextField difficultyTextField;
    private JSlider difficultySlider;
    private JLabel difficultyText;
    private JButton startGameButton;
    private JTextField gametypeTextField;
    private JRadioButton robotVersion1RadioButton;
    private JRadioButton robotVersion2RadioButton;
    private JRadioButton robotVersion3RadioButton;
    private JRadioButton robotVersion4RadioButton;
    private JRadioButton classicSelfRadioButton;

    //the variables that we need to set the settings
    //if this is set to false we know that the player wants normal colors
    private boolean rainbowColor;
    private int framerate;
    private String gamemode;
    private int difficultySliderValue;

    //the path to save the settings
    private Path settingsFile = Paths.get("src/logik/Settings.csv");

    //constructor containing all the listeners
    public SettingsGUI() {
        frame = new JFrame("Settings");
        frame.setContentPane(this.Settings);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //default operation on close
        frame.setResizable(false); //window must not be resizable!
        frame.pack(); //layout of the window must be sized accordingly to the computers screen
        frame.setVisible(true); //show the window (we want to see our game actually ^^
        frame.setLocationRelativeTo(null); //window should be in the center of the screen
        openWindow();
    }

    //function to open the settings window
    public void openWindow() {
        //check for an existing settings file

        //listener for the color radio buttons
        normalColorsRadioButton.addActionListener(e -> {
            rainbowColor=false;
        });
        rainbowColorsRadioButton.addActionListener(e -> {
            rainbowColor=true;
        });

        //listener for the difficulty slider
        difficultySlider.addChangeListener(e -> {
            difficultySliderValue = difficultySlider.getValue();
            setFramerateDifficulty(difficultySliderValue);
        });

        //listener for the game mode selection radio buttons
        robotVersion1RadioButton.addActionListener(e -> {gamemode = "RobotV1";});
        robotVersion2RadioButton.addActionListener(e -> {gamemode = "RobotV2";});
        robotVersion3RadioButton.addActionListener(e -> {gamemode = "RobotV3";});
        robotVersion4RadioButton.addActionListener(e -> {gamemode = "RobotV4";});
        classicSelfRadioButton.addActionListener(e -> {gamemode = "Classic";});

        //listener for the start button
        startGameButton.addActionListener(e -> {
            //create an object of the game
            GameWindow snakeGame = new GameWindow(framerate, gamemode, rainbowColor);
            snakeGame.runGame();

            //close this window
            frame.dispose();

            //save the current settings by calling the write function
            writeSettings();
        });
        checkForSettingsFile();
    }

    private void checkForSettingsFile(){
        //check if a settings.csv file exists and read it if it exists
        //if it does not exist create it and set the default values
        try {
            if (Files.exists(settingsFile)) {
                //call the function that reads the settings.csv file and applies it
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
                //if it does not exist create it
                Files.createFile(settingsFile);
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


    private void setFramerateDifficulty(int sliderValue){
        switch (sliderValue){
            case 0:
                difficultyText.setText("Easy");
                framerate = 200;
                break;
            case 1:
                difficultyText.setText("Normal");
                framerate = 150;
                break;
            case 2:
                difficultyText.setText("Hard");
                framerate = 60;
                break;
            case 3:
                difficultyText.setText("Boss Mode");
                framerate = 30;
                break;
            case 4:
                difficultyText.setText("Unplayable");
                framerate = 5;
                break;
        }
    }

    private void readSettings() throws NoValidSettingsFileException{
        try {
            BufferedReader settingsReader = Files.newBufferedReader(settingsFile);
            settingsReader.readLine(); //we only need the second line!
            String secondLine = settingsReader.readLine(); //we only need the second line!
            String[] settings = secondLine.split(";");

            if (secondLine == null) {
                //if the second line is empty or we can not split it correctly this means that the settings file is not a valid settings file
                throw new NoValidSettingsFileException();
            }


            //read the values that were set last time and set them again
            rainbowColor = Boolean.parseBoolean(settings[0]);
            gamemode = settings[2];

            //framerate will be set through the setFramerateDifficulty function!
            //this also sets the gui accordingly
            setFramerateDifficulty(Integer.parseInt(settings[1]));
            difficultySlider.setValue(Integer.parseInt(settings[1]));

            //also set the other gui elements accordingly
            //if the coloring is set to rainbow mode
            if(rainbowColor){
                rainbowColorsRadioButton.setSelected(true);
            } else {
                normalColorsRadioButton.setSelected(true);
            }

            //the gamemode
            switch (gamemode) {
                case "RobotV1":
                    robotVersion1RadioButton.setSelected(true);
                    break;
                case "RobotV2":
                    robotVersion2RadioButton.setSelected(true);
                    break;
                case "RobotV3":
                    robotVersion3RadioButton.setSelected(true);
                    break;
                case "RobotV4":
                    robotVersion4RadioButton.setSelected(true);
                    break;
                default:
                    classicSelfRadioButton.setSelected(true);
                    break;
            }

            //close the reader
            settingsReader.close();

        } catch (IOException e) {
            System.out.printf("IO Exception!");
            e.printStackTrace();
        }
    }

    private void writeSettings(){
        try {
            BufferedWriter settingsWriter = Files.newBufferedWriter(settingsFile, StandardOpenOption.WRITE);
            String settingsLine = rainbowColor + ";" + difficultySliderValue + ";" + gamemode;
            settingsWriter.write("rainbow coloring mode (boolean), difficulty slider value (int), gamemode (string)\n");
            settingsWriter.write(settingsLine);
            settingsWriter.close();

        } catch (IOException e) {
            System.out.println("IO Exception!");
            e.printStackTrace();
        }
    }
}
