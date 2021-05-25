package logik;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Highscore {
    public void writeHighscore(int highscore, String alt, String gametype) throws IOException {
        Path path = Paths.get("src/logik/Highscore.csv");

        String name = "Spieler"; //new line in content
        String spielmodus = gametype; //new line in content
        String punkte = String.valueOf(highscore);                          //Write the new Highscore
        String altepunkte = String.valueOf(alt);                            //Writes the old Highscore

        Files.write(path, (System.getProperty("line.separator") + name + ";").getBytes(), StandardOpenOption.APPEND);          //Append mode + New Line
        Files.write(path, (spielmodus + ";").getBytes(), StandardOpenOption.APPEND);                                            //Append mode
        Files.write(path, (punkte + altepunkte + ";").getBytes(), StandardOpenOption.APPEND);                                   //Can write new Highscore and old one

    }
    public void checkHighscore(int highscore, String gametype) throws IOException {


        Path path = Paths.get("src/logik/Highscore.csv");

        if (Files.exists(path)) {
            try {
                String line = Files.readAllLines(Paths.get("src/logik/Highscore.csv")).get(2);        //Beginnt ab null
                String[] erste_zeile = line.split(";");
                String line2 = Files.readAllLines(Paths.get("src/logik/Highscore.csv")).get(3);
                String[] zweite_zeile = line2.split(";");
                String line3 = Files.readAllLines(Paths.get("src/logik/Highscore.csv")).get(4);
                String[] dritte_zeile = line3.split(";");
                String line4 = Files.readAllLines(Paths.get("src/logik/Highscore.csv")).get(5);
                String[] vierte_zeile = line4.split(";");
                String line5 = Files.readAllLines(Paths.get("src/logik/Highscore.csv")).get(6);
                String[] fünfte_zeile = line5.split(";");
                int pos_highscore = 2;

                if ((highscore > Integer.parseInt(erste_zeile[pos_highscore])) && highscore != 0) {                             //Checks and compares Highscore with first line, creates a new updated file if Highscore is greater
                    createHighscore();
                    writeHighscore(highscore, "", gametype);
                    writeHighscore(0, erste_zeile[pos_highscore], gametype);
                    writeHighscore(0, zweite_zeile[pos_highscore], gametype);
                    writeHighscore(0, dritte_zeile[pos_highscore], gametype);
                    writeHighscore(0, vierte_zeile[pos_highscore], gametype);

                } else if ((highscore > Integer.parseInt(zweite_zeile[pos_highscore])) && highscore != 0) {                             //Checks and compares Highscore with second line, creates a new updated file if Highscore is greater
                    createHighscore();
                    writeHighscore(0, erste_zeile[pos_highscore], gametype);
                    writeHighscore(highscore, "", gametype);
                    writeHighscore(0, zweite_zeile[pos_highscore], gametype);
                    writeHighscore(0, dritte_zeile[pos_highscore], gametype);
                    writeHighscore(0, vierte_zeile[pos_highscore], gametype);

                } else if ((highscore > Integer.parseInt(dritte_zeile[pos_highscore])) && highscore != 0) {                             //Checks and compares Highscore with third line, creates a new updated file if Highscore is greater
                    createHighscore();
                    writeHighscore(0, erste_zeile[pos_highscore], gametype);
                    writeHighscore(0, zweite_zeile[pos_highscore], gametype);
                    writeHighscore(highscore, "", gametype);
                    writeHighscore(0, dritte_zeile[pos_highscore], gametype);
                    writeHighscore(0, vierte_zeile[pos_highscore], gametype);

                } else if ((highscore > Integer.parseInt(vierte_zeile[pos_highscore])) && highscore != 0) {                             //Checks and compares Highscore with fourth line, creates a new updated file if Highscore is greater
                    createHighscore();
                    writeHighscore(0, erste_zeile[pos_highscore], gametype);
                    writeHighscore(0, zweite_zeile[pos_highscore], gametype);
                    writeHighscore(0, dritte_zeile[pos_highscore], gametype);
                    writeHighscore(highscore, "", gametype);
                    writeHighscore(0, vierte_zeile[pos_highscore], gametype);

                } else if ((highscore > Integer.parseInt(fünfte_zeile[pos_highscore])) && highscore != 0) {                             //Checks and compares Highscore with fifth line, creates a new updated file if Highscore is greater
                    createHighscore();
                    writeHighscore(0, erste_zeile[pos_highscore], gametype);
                    writeHighscore(0, zweite_zeile[pos_highscore], gametype);
                    writeHighscore(0, dritte_zeile[pos_highscore], gametype);
                    writeHighscore(0, vierte_zeile[pos_highscore], gametype);
                    writeHighscore(highscore, "", gametype);

                }
                else {
                    System.out.println("Leider schaffst du es nicht in die Highscore Liste!");
                }

            } catch (IndexOutOfBoundsException e) {                             //Falls die Zeilen noch nicht existieren sollen Standartwerde erstellt werden

                writeHighscore(0, "", gametype);
                writeHighscore(0, "", gametype);
                writeHighscore(0, "", gametype);
                writeHighscore(0, "", gametype);
                checkHighscore(highscore, gametype);

            }
        }
        else if (Files.notExists(path)) {
            createHighscore();
            writeHighscore(highscore, "", gametype);

        }
    }
    public void createHighscore() throws IOException {

        try {
            File file = new File("src/logik/Highscore.csv");
            file.delete();                                          //Cleans the file to overwrite the new Highscore
            Path path = Paths.get("src/logik/Highscore.csv");
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
