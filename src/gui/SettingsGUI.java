package gui;

import logik.GameWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SettingsGUI {
    private static JFrame frame;
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
    private Path settingsFile = Paths.get("src/gui/Settings.csv");

    //empty constructor
    public SettingsGUI() { }

    //function to open the settings window
    public void openWindow() {
        frame = new JFrame("Settings");
        frame.setContentPane(new SettingsGUI().Settings);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //default operation on close
        frame.setResizable(false); //window must not be resizable!
        frame.pack(); //layout of the window must be sized accordingly to the computers screen
        frame.setVisible(true); //show the window (we want to see our game actually ^^
        frame.setLocationRelativeTo(null); //window should be in the center of the screen

        //add the listeners |---START--->
        //listener for the color radio buttons
        normalColorsRadioButton.addActionListener(e -> {
            rainbowColor=true;
        });
        rainbowColorsRadioButton.addActionListener(e -> {
            rainbowColor=false;
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
        //<---STOP---| (added all listeners)

        //check for an existing settings file
        checkForSettingsFile();
    }

    private void checkForSettingsFile(){
        //check if a settings.csv file exists and read it if it exists
        //if it does not exist create it and set the default values
        try {
            if (Files.exists(settingsFile)) {
                //call the function that reads the settings.csv file and applies it
                readSettings();
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

    private void readSettings(){
        try {
            BufferedReader settingsReader = Files.newBufferedReader(settingsFile);
            settingsReader.readLine(); //we only need the second line!
            String[] settings = settingsReader.readLine().split(";"); //we only need the second line!

            //read the values that were set last time and set them again
            rainbowColor = Boolean.parseBoolean(settings[0]);
            gamemode = settings[3];

            //framerate will be set through the setFramerateDifficulty function!
            //this also sets the gui accordingly
            setFramerateDifficulty(Integer.parseInt(settings[2]));

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
            settingsWriter.write("rainbow coloring mode (boolean), difficulty slider value (int), gamemode (string)");
            settingsWriter.write(settingsLine);
            settingsWriter.close();

        } catch (IOException e) {
            System.out.println("IO Exception!");
            e.printStackTrace();
        }
    }
}
