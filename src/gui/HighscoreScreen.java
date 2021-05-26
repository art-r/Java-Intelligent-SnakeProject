package gui;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HighscoreScreen {
    private JFrame frame;
    private JTextField tfHighscore1;
    private JPanel highscorePanel;
    private JButton closeButton;
    private JButton ShowButton;
    private JRadioButton robotV4RadioButton;
    private JRadioButton robotV3RadioButton;
    private JRadioButton robotV2RadioButton;
    private JRadioButton robotV1RadioButton;
    private JRadioButton classicRadioButton;
    private JTextField tfHighscore2;
    private JTextField tfHighscore3;
    private String gamemode;
    private ArrayList<Integer> highscores;
    HighscoreReadHelper highscoreReadHelper = new HighscoreReadHelper();


    HighscoreScreen() {

        JFrame frame = new JFrame("higscoreScreen");
        frame.setContentPane(new HighscoreScreen().highscorePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        openHighscoreScreen();



    }

    private void openHighscoreScreen() {


        classicRadioButton.addActionListener(e -> gamemode = "classic");
        robotV1RadioButton.addActionListener(e -> gamemode = "RobotV1");
        robotV1RadioButton.addActionListener(e -> gamemode = "RobotV2");
        robotV3RadioButton.addActionListener(e -> gamemode = "RobotV3");
        robotV4RadioButton.addActionListener(e -> gamemode = "RobotV4");

        ShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (gamemode) {
                    case "classic" -> {
                        highscores = highscoreReadHelper.getHighscoreValues("classic");
                        tfHighscore1.setText(String.valueOf(highscores.get(0)));
                        tfHighscore1.setText(String.valueOf(highscores.get(1)));
                        tfHighscore1.setText(String.valueOf(highscores.get(2)));
                    }
                    case "RobotV1" -> {
                        highscores = highscoreReadHelper.getHighscoreValues("RobotV1");
                        tfHighscore1.setText(String.valueOf(highscores.get(0)));
                        tfHighscore1.setText(String.valueOf(highscores.get(1)));
                        tfHighscore1.setText(String.valueOf(highscores.get(2)));

                    }
                    case "RobotV2" -> {
                        highscores = highscoreReadHelper.getHighscoreValues("RobotV2");
                        tfHighscore1.setText(String.valueOf(highscores.get(0)));
                        tfHighscore1.setText(String.valueOf(highscores.get(1)));
                        tfHighscore1.setText(String.valueOf(highscores.get(2)));
                    }
                    case "RobotV3" -> {
                        highscores = highscoreReadHelper.getHighscoreValues("RobotV3");
                        tfHighscore1.setText(String.valueOf(highscores.get(0)));
                        tfHighscore1.setText(String.valueOf(highscores.get(1)));
                        tfHighscore1.setText(String.valueOf(highscores.get(2)));
                    }
                    case "RobotV4" -> {
                        highscores = highscoreReadHelper.getHighscoreValues("RobotV4");
                        tfHighscore1.setText(String.valueOf(highscores.get(0)));
                        tfHighscore1.setText(String.valueOf(highscores.get(1)));
                        tfHighscore1.setText(String.valueOf(highscores.get(2)));
                    }
                }

            }
        });




        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartScreen startScreen = new StartScreen();

            }
        });




    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        highscorePanel = new JPanel();
        highscorePanel.setLayout(new GridBagLayout());
        tfHighscore1 = new JTextField();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        highscorePanel.add(tfHighscore1, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        highscorePanel.add(spacer1, gbc);
        closeButton = new JButton();
        closeButton.setForeground(new Color(-15811807));
        closeButton.setText("Close");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        highscorePanel.add(closeButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return highscorePanel;
    }
}
