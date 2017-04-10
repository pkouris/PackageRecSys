/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileManagerClasses;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class AddCostToItems {

    public AddCostToItems() {
    }
    
    
    //It converts file to mahout format
    public String addCostToItemsRandomly(String inputFileName, int minCost, int maxCost) {
        String convertedFileName = "";
        try {
            //removing the extension from inpute file and adding "_converted.dat"
            int len = inputFileName.length();
            for (int i = 0; i < len; i++) {
                char c = inputFileName.charAt(i);
                if (c == '.') {
                    break;
                }
                convertedFileName += c;
            }
            convertedFileName += "_covnerted_withCost.dat";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(convertedFileName));
            Random random = new Random();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // String[] part = new String[]{"", "", ""};
                String[] part = line.split("::");
                if (part != null && part.length > 2) {
                    String movieId = part[0].replaceAll("[\\D]", "");
                    String title = part[1];
                    String genres = part[2];//part[2].split("\\|");
                    int random_duration = random.nextInt(maxCost - minCost) + minCost;
                    bufferWriter.write(movieId + "#" + title + "#" + genres + "#" + random_duration + "\n");
                }
            }
            bufferedReader.close();
            bufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Select a valid item file please!", "Message", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Select a valid item file please!", "Message", JOptionPane.ERROR_MESSAGE);
            System.out.println("ReadFiles.convertItems() Exception: " + e);
            return "";
        }
        return convertedFileName;
    }

    
    
    
/*
    //It converts file to mahout format
    public String addCostToDatasetRandomly(String inputFileName, int minCost, int maxCost) {
        String convertedFileName = "";
        //removing the extension from inpute file and adding "_converted.dat"
        int filename_len = inputFileName.length();
        for (int i = 0; i < filename_len; i++) {
            char c = inputFileName.charAt(i);
            if (c == '.') {
                break;
            }
            convertedFileName += c;
        }
        convertedFileName += "_withCost.dat";
        try {
            //String inputFile = "C:\\movie_datasets\\ml1m\\ratings.dat";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(convertedFileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int line_len = line.length();
                String[] part = new String[]{"", "", ""};
                int countOfPart = 0;
                for (int i = 0; i < line_len; i++) {
                    char c = line.charAt(i);
                    if (c == ',') {
                        countOfPart++;
                    } else if (countOfPart == 0) {
                        part[0] += c; //userId
                    } else if (countOfPart == 1) {
                        part[1] += c; //itemID
                    } else if (countOfPart == 2) {
                        part[2] += c; //rating
                    }
                }
                //int userID = Integer.parseInt(part[0].replaceAll("[\\D]", ""));//repalce non digit with blank
                //String[] values = line.split("::", -1);
                Random random = new Random();
                int random_duration = random.nextInt(maxCost - minCost) + minCost;
                bufferWriter.write(part[0] + "," + part[1] + "," + part[2] + "," + random_duration + "\n");
            }
            bufferedReader.close();
            bufferWriter.close();
        } catch (IOException e) {
            Component frame = null;
            //JOptionPane.showMessageDialog(frame, "Select a valid rating file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(frame, "Select a valid rating file please!", "Message", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
            return "";
        } catch (Exception e) {
            Component frame = null;
            //JOptionPane.showMessageDialog(frame, "Select a valid rating file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(frame, "Select a valid rating file please!", "Message", JOptionPane.ERROR_MESSAGE);
            System.out.println("ReadFiles.readRatings() Exception: " + e);
            return "";
        }
        return convertedFileName;
    }
*/
    
}
