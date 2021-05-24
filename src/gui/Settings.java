package gui;
import logik.GameWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.StringTokenizer;

public class Settings implements ChangeListener, ActionListener {

    //declaring swing components
    JFrame frame;
    JPanel panel;
    JLabel label;
    JLabel labelModusAuswahl;
    JLabel labelSchwierigkeit;
    JSlider slider;
    JRadioButton normalButton;
    JRadioButton regenbogenButton;
    JButton buttonStart;

    //declaring snake and settingsfile
    GameWindow snake;
    Path settingsfile;

    public Settings(){

        //initialize swing components
        frame = new JFrame("Settings");
        panel = new JPanel();
        label = new JLabel();
        labelModusAuswahl = new JLabel();
        slider = new JSlider(1,5,1);
        labelSchwierigkeit = new JLabel();

        //create labelModusAuswahl
        labelModusAuswahl.setText("Wähle hier deine gewünschten Farbkonstellation aus:");
        labelModusAuswahl.setBackground(Color.black);
        labelModusAuswahl.setForeground(Color.red);

        // create radiobuttons
        normalButton = new JRadioButton("normaler Modus");
        normalButton.setBackground(Color.BLACK);
        normalButton.setForeground(Color.GREEN);
        regenbogenButton = new JRadioButton("Regenbogen Modus");
        regenbogenButton.setBackground(Color.BLACK);
        regenbogenButton.setForeground(Color.GREEN);

        //assign radiobuttons to buttongroup
        ButtonGroup group = new ButtonGroup();
        group.add(normalButton);
        group.add(regenbogenButton);

        // add actionlistener
        normalButton.addActionListener(this);
        regenbogenButton.addActionListener(this);


        //set labeltext for difficulty
        labelSchwierigkeit.setText("Wähle hier deinen gewünschten Schwiereigkeitsgrad aus:");
        labelSchwierigkeit.setBackground(Color.BLACK);
        labelSchwierigkeit.setForeground(Color.red);

        //configure slider
        slider.setPreferredSize(new Dimension(400,200));
        slider.setBackground(Color.black);
        slider.setForeground(Color.GREEN);

        //adds the lines
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(1);

        //adds the numbers
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);

        //sets the label text
        label.setText("Schwierigkeit: einfach");
        label.setBackground(Color.BLACK);
        label.setForeground(Color.GREEN);

        //add change listener to slider
        slider.addChangeListener(this);

        //add button to start game
        buttonStart = new JButton("Spiel starten");
        buttonStart.setBackground(Color.BLACK);
        buttonStart.setForeground(Color.red);

        //add to panel
        panel.add(labelModusAuswahl);
        panel.add(normalButton);
        panel.add(regenbogenButton);
        panel.add(labelSchwierigkeit);
        panel.add(slider);
        panel.add(label);
        panel.add(buttonStart);
        panel.setBackground(Color.BLACK);
        frame.add(panel);
        frame.setSize(420,420);
        frame.setVisible(true);


        //check the path for csv file
        settingsfile = Paths.get("src/gui/Settings.csv");
        try{
            if (!Files.exists(settingsfile))
                Files.createFile(settingsfile);
            else {
                lesen();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    //writing method for csv file
    public void schreiben(String framerate, String farbe){
        try {
            BufferedWriter meinWriter = Files.newBufferedWriter(settingsfile, StandardOpenOption.WRITE);
            meinWriter.write(framerate+","+farbe);
            meinWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //declaring variables for contstructor
    int framerate;
    boolean farbe;

    //reading method to read the csv file
    public void lesen(){
        try {
            BufferedReader meinReader = Files.newBufferedReader(settingsfile);
            String[] zeile = meinReader.readLine().split(",");
            framerate = Integer.parseInt(zeile[0]);
            switch (framerate){
                case 200:
                    slider.setValue(1);
                    break;
                case 100:
                    slider.setValue(2);
                    break;
                case 50:
                    slider.setValue(3);
                    break;
                case 25:
                    slider.setValue(4);
                    break;
                case 2:
                    slider.setValue(5);
                    break;
                default:
                    slider.setValue(2);
                    break;
            }

            farbe = Boolean.parseBoolean(zeile[1]);
            regenbogenButton.setSelected(farbe);
            meinReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //changeevent for slider
    @Override
    public void stateChanged(ChangeEvent e) {
        if(slider.getValue()==1){
            label.setText("Schwierigkeit: einfach");
            framerate=200;
        } else if(slider.getValue()==2){
            label.setText("Schwierigkeit: mittel");
            framerate=100;
        } else if(slider.getValue()==3){
            label.setText("Schwierigkeit: schwer");
            framerate=50;
        } else if(slider.getValue()==4){
            label.setText("Schwierigkeit: boss mode");
            framerate=25;
        } else if(slider.getValue()==5){
            label.setText("Schwierigkeit: unplayable");
            framerate=2;
        }

    }

    //actionevent for radiobuttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==normalButton){
            farbe=false;

        } else if (e.getSource()== regenbogenButton){
            farbe=true;
        }

        //actionevent for the start button
        //using the writing method an using the constructor to start the game
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                schreiben(Integer.toString(framerate),Boolean.toString(farbe));
                snake = new GameWindow(framerate,"",farbe);
                snake.runGame();
            }
        });
    }
}
