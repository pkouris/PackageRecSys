
package fileManagerClasses;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author: Panagiotis Kouris
 * date: Dec 2015
 */
public class ConvertFile {

    public ConvertFile() {
    }
    
    
    //It converts file to mahout format
    public String converRatignsFile(String inputFileName) {
        String convertedFileName = "";
        //removing the extension from inpute file and adding "_converted.dat"
        int len = inputFileName.length();
        for (int i = 0; i < len; i++) {
            char c = inputFileName.charAt(i);
            if (c == '.') {
                break;
            }
            convertedFileName += c;
        }
        convertedFileName += "_converted.dat";
        try {
            //String inputFile = "C:\\movie_datasets\\ml1m\\ratings.dat";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(convertedFileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] part = new String[]{"", "", ""};
                int countOfPart = 0;
                int index = 0;
                while (countOfPart < 5) {
                    char c = line.charAt(index);
                    if (c == ':') {
                        countOfPart++;
                    } else if (countOfPart == 0) {
                        part[0] += c;
                    } else if (countOfPart == 2) {
                        part[1] += c;
                    } else if (countOfPart == 4) {
                        part[2] += c;
                    }
                    index++;
                }
                //int userID = Integer.parseInt(part[0].replaceAll("[\\D]", ""));//repalce non digit with blank
                //String[] values = line.split("::", -1);
                bufferWriter.write(part[0] + "," + part[1] + "," + part[2] + "\n");
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


    
    
      //It converts file to mahout format
    public String converItemsFile(String inputFileName) {
        String convertedFileName = "";
        //removing the extension from inpute file and adding "_converted.dat"
        int len = inputFileName.length();
        for (int i = 0; i < len; i++) {
            char c = inputFileName.charAt(i);
            if (c == '.') {
                break;
            }
            convertedFileName += c;
        }
        convertedFileName += "_converted.dat";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(convertedFileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // String[] part = new String[]{"", "", ""};
                 String[] part = line.split("::");
                 if (part!=null && part.length > 2) {
                     String movieId = part[0].replaceAll("[\\D]", "");
                     String title = part[1];
                     String genres = part[2];//part[2].split("\\|");
                     //int numOfGenres = genres.length;
                     //////////////////////
                     //System.out.println("part.length= " +part.length);
                     String numOfMaxAppearances;
                     if (part.length > 3) {
                         numOfMaxAppearances = part[3];
                     } else {
                         numOfMaxAppearances = "";
                     }
                     
                    //bufferWriter.write(part[0] + "," + part[1] + "," + part[2] + "\n");
                    bufferWriter.write(movieId + "#" + title + "#" + genres + "#" + numOfMaxAppearances + "\n");
                     //////////////////////
                     //System.out.println("numOfAppearances= " + numOfMaxAppearances );
                     ////////////////////
                     //////////////////////
                     //System.out.println("movies_hashTable.get(movieId).get(movieID).getNumOfMaxAppearances() = " + movies_hashTable.get(movieID).getNumOfMaxAppearances() );
                     ////////////////////  
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
        //read items from file
     public void readMovies(String path) {
         try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
             this.movies_hashTable.clear();
             int movieID;
             int numOfMaxAppearances = 1;
             for (String line; (line = bufferedReader.readLine()) != null;) {
                 // if(line.equals());
                 String[] part = line.split("::");
                 if (part!=null && part.length > 2) {
                     movieID = Integer.parseInt(part[0].replaceAll("[\\D]", ""));
                     //String title = part[1];
                     String[] genres = part[2].split("\\|");
                     //int numOfGenres = genres.length;
                     //////////////////////
                     //System.out.println("part.length= " +part.length);
                     if (part.length > 3) {
                         numOfMaxAppearances = Integer.valueOf(part[3]);
                     } else {

                         numOfMaxAppearances = 1;
                     }
                     //////////////////////
                     //System.out.println("numOfAppearances= " + numOfMaxAppearances );
                     ////////////////////
                     Movie movie = new Movie(movieID, part[1], genres, genres.length, numOfMaxAppearances, 0);
                     movies_hashTable.put(movieID, movie);
                     //////////////////////
                     //System.out.println("movies_hashTable.get(movieId).get(movieID).getNumOfMaxAppearances() = " + movies_hashTable.get(movieID).getNumOfMaxAppearances() );
                     ////////////////////  
                 }
            } 
            bufferedReader.close();
            movies_list.clear();
            Enumeration<Integer> keys = fileManagerClasses.ReadFiles.movies_hashTable.keys();
            while (keys.hasMoreElements()) {
                int key = keys.nextElement();
                Movie movie_temp = ReadFiles.movies_hashTable.get(key);
                movies_list.add(movie_temp);
            }   
        } catch (IOException e) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Select a valid item file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(frame, "Select a valid item file please!", "Message", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
            return;
        } catch (Exception e) {
           Component frame = null;
           JOptionPane.showMessageDialog(frame, "Select a valid item file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
           //JOptionPane.showMessageDialog(frame, "Select a valid item file please!", "Message", JOptionPane.ERROR_MESSAGE);
           System.out.println("ReadFiles.readMovies() Exception: " + e);
           return;
        }
    }

     
  */  
    


}