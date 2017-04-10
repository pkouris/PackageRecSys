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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

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

    //It adds duration to movies from http://www.omdbapi.com/?t=movie title
    public String retrieveDurationOfMovies(String inputFileName) {
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
            convertedFileName += "_withDuration.dat";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(convertedFileName));
            Random random = new Random();
            int countLine = 0;
            String line = "";
            int count_errors = 0;
            while ((line = bufferedReader.readLine()) != null) {
                countLine++;
                // String[] part = new String[]{"", "", ""};
                String[] part = line.split("#");
                if (part != null && part.length > 2) {
                    String movieId = part[0].replaceAll("[\\D]", "");
                    String title = part[1];
                    String genres = part[2];//part[2].split("\\|");
                    int maxCost = 120;
                    int minCost = 70;
                    int random_duration = random.nextInt(maxCost - minCost) + minCost;

                    String modTitle = title.replaceAll(" ", "%20");
                    String duration = "";
                    try {
                        JSONObject json = readJsonFromUrl("http://www.omdbapi.com/?t=" + modTitle);
                        duration = json.getString("Runtime");
                    } catch (Exception e) {
                        System.out.println("error in line:" + countLine);
                    };
                    String dur = duration.replace(" min", "");
                    if (dur.equals("") || dur.equals("N/A")) {
                        dur = random_duration + "";
                        count_errors++;
                    }
                    System.out.println(countLine + " " + dur);

                    bufferWriter.write(movieId + "#" + title + "#" + genres + "#" + dur + "\n");
                }
            }
            System.out.println("\n countErrors:" + count_errors);
            bufferedReader.close();
            bufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            //Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return convertedFileName;
    }

    //
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    
    //
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

   
}
