package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;
import java.io.BufferedWriter;
import java.nio.file.*;

public class Settings implements ChangeListener, ActionListener {

    JFrame frame;
    JPanel panel;
    JLabel label;
    JSlider slider;
    JRadioButton normalButton;
    JRadioButton regenbogenButton;

    Settings(){
        frame = new JFrame("Settings");
        panel = new JPanel();
        label = new JLabel();
        slider = new JSlider(1,4,1);

        //radiobuttons erstellen
        normalButton = new JRadioButton("normaler Modus");
        regenbogenButton = new JRadioButton("Regenbogen Modus");
        panel.add(normalButton);
        panel.add(regenbogenButton);

        //radiobuttons einer buttongroup zuweisen
        ButtonGroup group = new ButtonGroup();
        group.add(normalButton);
        group.add(regenbogenButton);

        //actionlistener hinzufügen
        normalButton.addActionListener(this);
        regenbogenButton.addActionListener(this);

        slider.setPreferredSize(new Dimension(400,200));
        //macht die Striche
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(1);

        //macht die Zahlen
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);

        //jetzt noch Label mit Schwierigkeit drunter machen
        label.setText("Schwierigkeit: einfach");

        //change listener einbauen zum aktualisieren
        slider.addChangeListener(this);

        //panel und frame zum laufen bringen
        panel.add(slider);
        panel.add(label);
        frame.add(panel);
        frame.setSize(420,420);
        frame.setVisible(true);
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        //Logik ergänzen!!!
        if(slider.getValue()==1){
            label.setText("Schwierigkeit: einfach");
        } else if(slider.getValue()==2){
            label.setText("Schwierigkeit: mittel");
        } else if(slider.getValue()==3){
            label.setText("Schwierigkeit: schwer");
        } else if(slider.getValue()==4){
            label.setText("Schwierigkeit: boss mode");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Zuweisung durch Klicken der Radiobuttons
        if(e.getSource()==normalButton){
            //Logik, dass normale Farben

        } else if (e.getSource()== regenbogenButton){
            //Logik, dass Regenbogen
        }
    }

    public static class Schreiben {
        public static void main(String[] args) {

            try {
                Path meineDatei = Paths.get("Aufgaben/res/test2.txt");
                if (!Files.exists(meineDatei))
                    Files.createFile(meineDatei);
                int naechsteZeilenNummer = 1;
                BufferedWriter meinWriter =
                        Files.newBufferedWriter(meineDatei, StandardOpenOption.APPEND);
                meinWriter.write(naechsteZeilenNummer + ": test\n");
                meinWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    }
