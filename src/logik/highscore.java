package logik;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class highscore {
    public void writeHighscore(int highscore, String alt) throws IOException {
        Path path = Paths.get("highscore.csv");

        String name = "Spieler"; //new line in content
        String spielmodus = "Spielmodus"; //new line in content
        String punkte = String.valueOf(highscore);                          //Write the new highscore
        String altepunkte = String.valueOf(alt);                            //Writes the old highscore

        Files.write(path, (System.getProperty("line.separator") + name + ";").getBytes(), StandardOpenOption.APPEND);          //Append mode + New Line
        Files.write(path, (spielmodus + ";").getBytes(), StandardOpenOption.APPEND);                                            //Append mode
        Files.write(path, (punkte + altepunkte + ";").getBytes(), StandardOpenOption.APPEND);                                   //Can write new highscore and old one

    }
    public void checkHighscore(int highscore) throws IOException {


        Path path = Paths.get("highscore.csv");

        if (Files.exists(path)) {
            try {
                String line = Files.readAllLines(Paths.get("highscore.csv")).get(2);        //Beginnt ab null
                String[] erste_zeile = line.split(";");
                String line2 = Files.readAllLines(Paths.get("highscore.csv")).get(3);
                String[] zweite_zeile = line2.split(";");
                String line3 = Files.readAllLines(Paths.get("highscore.csv")).get(4);
                String[] dritte_zeile = line3.split(";");
                String line4 = Files.readAllLines(Paths.get("highscore.csv")).get(5);
                String[] vierte_zeile = line4.split(";");
                String line5 = Files.readAllLines(Paths.get("highscore.csv")).get(6);
                String[] fünfte_zeile = line5.split(";");
                int pos_highscore = 2;

                if ((highscore > Integer.parseInt(erste_zeile[pos_highscore])) && highscore != 0) {                             //Checks and compares highscore with first line, creates a new updated file if highscore is greater
                    createHighscore();
                    writeHighscore(highscore, "");
                    writeHighscore(0, erste_zeile[pos_highscore]);
                    writeHighscore(0, zweite_zeile[pos_highscore]);
                    writeHighscore(0, dritte_zeile[pos_highscore]);
                    writeHighscore(0, vierte_zeile[pos_highscore]);

                } else if ((highscore > Integer.parseInt(zweite_zeile[pos_highscore])) && highscore != 0) {                             //Checks and compares highscore with second line, creates a new updated file if highscore is greater
                    createHighscore();
                    writeHighscore(0, erste_zeile[pos_highscore]);
                    writeHighscore(highscore, "");
                    writeHighscore(0, zweite_zeile[pos_highscore]);
                    writeHighscore(0, dritte_zeile[pos_highscore]);
                    writeHighscore(0, vierte_zeile[pos_highscore]);

                } else if ((highscore > Integer.parseInt(dritte_zeile[pos_highscore])) && highscore != 0) {                             //Checks and compares highscore with third line, creates a new updated file if highscore is greater
                    createHighscore();
                    writeHighscore(0, erste_zeile[pos_highscore]);
                    writeHighscore(0, zweite_zeile[pos_highscore]);
                    writeHighscore(highscore, "");
                    writeHighscore(0, dritte_zeile[pos_highscore]);
                    writeHighscore(0, vierte_zeile[pos_highscore]);

                } else if ((highscore > Integer.parseInt(vierte_zeile[pos_highscore])) && highscore != 0) {                             //Checks and compares highscore with fourth line, creates a new updated file if highscore is greater
                    createHighscore();
                    writeHighscore(0, erste_zeile[pos_highscore]);
                    writeHighscore(0, zweite_zeile[pos_highscore]);
                    writeHighscore(0, dritte_zeile[pos_highscore]);
                    writeHighscore(highscore, "");
                    writeHighscore(0, vierte_zeile[pos_highscore]);

                } else if ((highscore > Integer.parseInt(fünfte_zeile[pos_highscore])) && highscore != 0) {                             //Checks and compares highscore with fifth line, creates a new updated file if highscore is greater
                    createHighscore();
                    writeHighscore(0, erste_zeile[pos_highscore]);
                    writeHighscore(0, zweite_zeile[pos_highscore]);
                    writeHighscore(0, dritte_zeile[pos_highscore]);
                    writeHighscore(0, vierte_zeile[pos_highscore]);
                    writeHighscore(highscore, "");

                }
                else {
                    System.out.println("Leider schaffst du es nicht in die Highscore Liste!");
                }

            } catch (IndexOutOfBoundsException e) {                             //Falls die Zeilen noch nicht existieren sollen Standartwerde erstellt werden

                writeHighscore(0, "");
                writeHighscore(0, "");
                writeHighscore(0, "");
                writeHighscore(0, "");
                checkHighscore(highscore);

            }
        }
        else if (Files.notExists(path)) {
            createHighscore();
            writeHighscore(highscore, "");

        }
    }
    public void createHighscore() throws IOException {

        try {
            File file = new File("highscore.csv");
            file.delete();                                          //Cleans the file to overwrite the new Highscore
            Path path = Paths.get("highscore.csv");
            file.createNewFile();

            String column_name = "Spieler"; //new line in content                   //create the columns
            String column_spielmodus = "Spielmodus"; //new line in content
            String column_punkte = "Highscore";

            Files.write(path, (column_name + ";").getBytes(), StandardOpenOption.APPEND);  //Append mode            //Column Name
            Files.write(path, (column_spielmodus + ";").getBytes(), StandardOpenOption.APPEND);  //Append mode      //Column Spielmodus
            Files.write(path, (column_punkte + ";").getBytes(), StandardOpenOption.APPEND);  //Append mode          //Column Highscore

            Files.write(path, (System.getProperty ("line.separator")).getBytes(), StandardOpenOption.APPEND);       //Space




        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
