package fileManagerClasses;

import entityBasicClasses.Item;
import entityBasicClasses.Item;
import entityBasicClasses.ItemRating;
import entityBasicClasses.RatingsPerUser;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author: Panagiotis Kouris date: Nov 2015
 */
public class ReadFiles {

    static public Hashtable<Integer, RatingsPerUser> ratingsPerUser_hashTable = new Hashtable<>();
    //static public ArrayList<Map.Entry<Integer, RatingsPerUser>> ratingsPerUser_list = new ArrayList<>();
    static public ArrayList<RatingsPerUser> ratingsPerUser_list = new ArrayList<>();

    //static Hashtable<Integer, Integer> numberOfRatingsPerUser_hashTable = new Hashtable<Integer, Integer>();
    static public Hashtable<Integer, Item> movies_hashTable = new Hashtable<>();
    static public List<Item> movies_list = new ArrayList<>();

    //static public Hashtable<Integer, Movie> moviesPopularity_hashTable = new Hashtable<Integer, Movie>();
    static public double maxValueOfRating = 0.0;

    // static int minMoviePrint = 0;
    // static int maxMoviePrint = 100;
    // static int minRatingPrint = 0;
    // static int maxRatingPrint = 100;
    public ReadFiles() {
    }

    //read and split the line of the ratings data file
    public void splitRatingsLine(String line) {
        try {
            //read the userID, movieID and rating
            //Split line into three parts efficiently
            String[] part = new String[]{"", "", ""};
            int countOfPart = 0;
            //int index = 0;
            int len = line.length();
            for (int i = 0; i < len; i++) {
                char c = line.charAt(i);
                if (c == ',') {
                    countOfPart++;
                } else if (countOfPart == 0) {
                    part[0] += c;
                } else if (countOfPart == 1) {
                    part[1] += c;
                } else if (countOfPart == 2) {
                    part[2] += c;
                }
            }
            // String[] part = line.split("::");
            int userID = Integer.parseInt(part[0].replaceAll("[\\D]", ""));//repalce non digit with blank
            //int userID = Integer.valueOf(part[0]);
            int movieID = Integer.valueOf(part[1]);
            double rating = Double.valueOf(part[2]);
            if (rating > maxValueOfRating) {
                maxValueOfRating = rating;
            }
            int numOfRatings = 1;
            //create the object movieRating
            //MovieRating movieRating = new MovieRating(Integer.valueOf(part[1]), Integer.valueOf(part[2]));
            //MovieRating movieRating = new MovieRating(movieID, Double.valueOf(part[2]));
            ItemRating movieRating = new ItemRating(movieID, rating);

            //update the popularity of the movie
            int newPopularity = movies_hashTable.get(movieID).getPopularity() + 1;
            movies_hashTable.get(movieID).setPopularity(newPopularity);

            //create the object movieRatingsList_temp
            List<ItemRating> movieRatingsList_temp = new ArrayList<>();
            if (ratingsPerUser_hashTable.get(userID) != null) {
                movieRatingsList_temp = ratingsPerUser_hashTable.get(userID).getItemRatingList();
                numOfRatings = ratingsPerUser_hashTable.get(userID).getNumOfRatings() + 1;
            }
            movieRatingsList_temp.add(movieRating);
            RatingsPerUser ratingsPerUser_temp = new RatingsPerUser(userID, numOfRatings, movieRatingsList_temp);
            ratingsPerUser_hashTable.put(userID, ratingsPerUser_temp);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    //read ratings file using the Mahout format
    public void readRatingsFile(String path) {
        long start = System.currentTimeMillis();
        //read the file (ratings.dat) line by line
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            ReadFiles.ratingsPerUser_hashTable.clear();
            for (String line; (line = bufferedReader.readLine()) != null;) {
                splitRatingsLine(line);
            }
            bufferedReader.close();

            // ArrayList<Entry<Integer,RatingsPerUser>> ratingsPerUser_list_temp = new ArrayList<>(ReadFiles.ratingsPerUser_hashTable.entrySet());
            //ratingsPerUser_list = ratingsPerUser_list_temp;
            ratingsPerUser_list.clear();
            Enumeration<Integer> keys = fileManagerClasses.ReadFiles.ratingsPerUser_hashTable.keys();
            while (keys.hasMoreElements()) {
                int key = keys.nextElement();
                RatingsPerUser temp = ReadFiles.ratingsPerUser_hashTable.get(key);
                ratingsPerUser_list.add(temp);
            }
            //////////////////////
            long elapsedTime = System.currentTimeMillis() - start;
            System.out.println((double) elapsedTime / 1000.0 + "sec");
            /////////////////
            // } catch (IOException ex) {
        } catch (IOException e) {
            Component frame = null;
            //JOptionPane.showMessageDialog(frame, "Select a valid rating file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(frame, "Select a valid rating file please!", "Message", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            Component frame = null;
            //JOptionPane.showMessageDialog(frame, "Select a valid rating file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(frame, "Select a valid rating file please!", "Message", JOptionPane.ERROR_MESSAGE);
            //System.out.println("ReadFiles.readRatings() Exception: " + e);
            return;
        }
    }

    public Item splitItemsLine(String line) {
        try {
            String part[] = {"", "", "", ""};
            int countOfPart = 0;
            int len = line.length();
            for (int i = 0; i < len; i++) {
                char c = line.charAt(i);
                if (c == '#') {
                    countOfPart++;
                } else if (countOfPart == 0) {
                    part[0] += c;
                } else if (countOfPart == 1) {
                    part[1] += c;
                } else if (countOfPart == 2) {
                    part[2] += c;
                } else if (countOfPart == 3) {
                    part[3] += c;
                }
            }
            int numOfMaxAppearances = 1;
            int movieID = Integer.parseInt(part[0].replaceAll("[\\D]", ""));
            String title = part[1];
            String[] genres = part[2].split("\\|");
            if (!part[3].equals("")) {
                numOfMaxAppearances = convertStringToInt(part[3]);
            }
            Item item = new Item(movieID, title, genres, genres.length, numOfMaxAppearances, 0);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //read items from file
    public void readItemsFile(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            this.movies_hashTable.clear();
            for (String line; (line = bufferedReader.readLine()) != null;) {
                Item item = splitItemsLine(line);
                if (item != null) {
                    movies_hashTable.put(item.getItemID(), item);
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
                Item movie_temp = ReadFiles.movies_hashTable.get(key);
                movies_list.add(movie_temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Select a valid item file please! \nError: " + e, "Message", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            e.printStackTrace();
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Select a valid item file please! \nError: " + e, "Message", JOptionPane.ERROR_MESSAGE);
        }

    }

    //if the str is an integer returns true else false
    public boolean isThisStringInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //convert a string to int or return 1
    public int convertStringToInt(String str) {
        try {
            int i = Integer.parseInt(str);
            return i;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

//sortingMode = 0: sorting descending ratings Per User by number of ratings
//sortingMode != 0: sorting ascending ratings Per User by userId  
    public void sortRatingsPerUserList(int sortingMode) {
        if (sortingMode == 0) {
            Collections.sort(ratingsPerUser_list, new Comparator<RatingsPerUser>() {
                public int compare(RatingsPerUser o1, RatingsPerUser o2) {
                    return (o2.getNumOfRatings()) - (o1.getNumOfRatings());
                }
            });
        } else {
            Collections.sort(ratingsPerUser_list, new Comparator<RatingsPerUser>() {
                public int compare(RatingsPerUser o1, RatingsPerUser o2) {
                    return (o1.getUserID()) - (o2.getUserID());
                }
            });
        }
    }

//sorting items by itemId or item popularity 
    public void sortItemsList(int mode) {
        if (mode == 0) { //mode = 0: sorting by movieId
            //sorting ascending items by itemId or item popularity 
            Collections.sort(movies_list, new Comparator<Item>() {
                public int compare(Item o1, Item o2) {
                    return (o1.getItemID()) - (o2.getItemID());
                }

            });
        } else {//mode != 0: sorting by item Popularity
            //sorting descending by item Popularity
            Collections.sort(movies_list, new Comparator<Item>() {
                public int compare(Item o1, Item o2) {
                    return (o2.getPopularity()) - (o1.getPopularity());
                }

            });
        }

    }

    /*
    
      //read items from file
    public void readItemsFile_old(String path) {
         try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
             this.movies_hashTable.clear();
             int movieID;
             int numOfMaxAppearances = 1;
             for (String line; (line = bufferedReader.readLine()) != null;) {
                 // if(line.equals());
                 String[] part = line.split(",");
                 if (part!=null && part.length == 3) {
                     movieID = Integer.parseInt(part[0].replaceAll("[\\D]", ""));
                     //String title = part[1];
                     String[] genres = part[2].split("\\|");
                     //int numOfGenres = genres.length;
                     numOfMaxAppearances = 1;
                     Item movie = new Item(movieID, part[1], genres, genres.length, numOfMaxAppearances, 0);
                     movies_hashTable.put(movieID, movie);
                 }
                 else if (part!=null && part.length == 4) {
                     int len = part.length;
                     movieID = Integer.parseInt(part[0].replaceAll("[\\D]", ""));
                     String title = part[1];
                      char c = line.charAt(0);
                     if(c == '"'){
                         
                        title += ", " + part[2];
                     } 
                     
                     numOfMaxAppearances = convertStringToInt(part[(len-1)]);
                     
                     String[] genres = part[len-2].split("\\|");
                     Item movie = new Item(movieID, title, genres, genres.length, numOfMaxAppearances, 0);
                     movies_hashTable.put(movieID, movie);
                     //////////////////////
                     //System.out.println("movies_hashTable.get(movieId).get(movieID).getNumOfMaxAppearances() = " + movies_hashTable.get(movieID).getNumOfMaxAppearances() );
                     ////////////////////  
                 }
                 else if (part!=null && part.length > 4) {
                     int len = part.length;
                     movieID = Integer.parseInt(part[0].replaceAll("[\\D]", ""));
                     String title = part[1];
                      String[] genres = null;
                      title += ", " + part[2];
                      genres = part[len-2].split("\\|");
                      numOfMaxAppearances = convertStringToInt(part[len-1]);
                      //numOfMaxAppearances = Integer.valueOf(part[len-1]);
                     Item movie = new Item(movieID, title, genres, genres.length, numOfMaxAppearances, 0);
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
                Item movie_temp = ReadFiles.movies_hashTable.get(key);
                movies_list.add(movie_temp);
            }   
             
           
         } catch (IOException e) {
             e.printStackTrace();
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Select a valid item file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            e.printStackTrace();
           Component frame = null;
           JOptionPane.showMessageDialog(frame, "Select a valid item file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
        }
    
     }
     */
 /*
     //read ratings file using the Mahout format
     public void readRatingsFile_old(String path) {
        long start = System.currentTimeMillis();
        //read the file (ratings.dat) line by line
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            ReadFiles.ratingsPerUser_hashTable.clear();
            for (String line; (line = bufferedReader.readLine()) != null;) {
                //read the userID, movieID and rating
                //Split line into three parts efficiently
               String[] part = new String[]{"", "", ""};
               int countOfPart = 0;
               //int index = 0;
               int len = line.length();
                for (int i=0; i<len; i++){
                    char c = line.charAt(i);
                    if (c == ',') {
                        countOfPart++;
                    } else {
                        if (countOfPart == 0) {
                            part[0] += c;
                        } else if (countOfPart == 1) {
                            part[1] += c;
                        } else if (countOfPart == 2) {
                            part[2] += c;
                        }
                    }
                }
               // String[] part = line.split("::");
                int userID = Integer.parseInt(part[0].replaceAll("[\\D]", ""));//repalce non digit with blank
                //int userID = Integer.valueOf(part[0]);
                int movieID = Integer.valueOf(part[1]);
               double rating = Double.valueOf(part[2]);
               if( rating > maxValueOfRating){
                    maxValueOfRating = rating;
                }
                int numOfRatings = 1;
                //create the object movieRating
                //MovieRating movieRating = new MovieRating(Integer.valueOf(part[1]), Integer.valueOf(part[2]));
                //MovieRating movieRating = new MovieRating(movieID, Double.valueOf(part[2]));
                ItemRating movieRating = new ItemRating(movieID, rating);
                
                //update the popularity of the movie
                int newPopularity = movies_hashTable.get(movieID).getPopularity()+1;
                movies_hashTable.get(movieID).setPopularity(newPopularity);
                
                //create the object movieRatingsList_temp
                List<ItemRating> movieRatingsList_temp = new ArrayList<>();
                if (ratingsPerUser_hashTable.get(userID) != null) {
                    movieRatingsList_temp = ratingsPerUser_hashTable.get(userID).getItemRatingList();
                    numOfRatings = ratingsPerUser_hashTable.get(userID).getNumOfRatings() + 1;
                }
                movieRatingsList_temp.add(movieRating);
                RatingsPerUser ratingsPerUser_temp = new RatingsPerUser(userID, numOfRatings, movieRatingsList_temp);
                ratingsPerUser_hashTable.put(userID, ratingsPerUser_temp);
            }
            bufferedReader.close();
            
           // ArrayList<Entry<Integer,RatingsPerUser>> ratingsPerUser_list_temp = new ArrayList<>(ReadFiles.ratingsPerUser_hashTable.entrySet());
            //ratingsPerUser_list = ratingsPerUser_list_temp;
            ratingsPerUser_list.clear();
            Enumeration<Integer> keys = fileManagerClasses.ReadFiles.ratingsPerUser_hashTable.keys();
            while (keys.hasMoreElements()) {
                int key = keys.nextElement();
                RatingsPerUser temp = ReadFiles.ratingsPerUser_hashTable.get(key);
                ratingsPerUser_list.add(temp);
            } 
            //////////////////////
           long elapsedTime = System.currentTimeMillis() - start;
           System.out.println((double) elapsedTime / 1000.0 + "sec");
           /////////////////
           // } catch (IOException ex) {
           } catch (IOException e) {
            Component frame = null;
            //JOptionPane.showMessageDialog(frame, "Select a valid rating file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(frame, "Select a valid rating file please!", "Message", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
            return;
          } catch (Exception e) {
              e.printStackTrace();
            Component frame = null;
            //JOptionPane.showMessageDialog(frame, "Select a valid rating file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(frame, "Select a valid rating file please!", "Message", JOptionPane.ERROR_MESSAGE);
            //System.out.println("ReadFiles.readRatings() Exception: " + e);
            return;
        }
    }
     */
 /*    
    //read items from file
     public void readMovies_old(String path) {
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
                     Item movie = new Item(movieID, part[1], genres, genres.length, numOfMaxAppearances, 0);
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
                Item movie_temp = ReadFiles.movies_hashTable.get(key);
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
 /*     
//sorting descending ratings Per User by number of ratings  
public static void sortRatingsPerUserList_old(){
       Collections.sort(ratingsPerUser_list, new Comparator<Map.Entry<Integer, RatingsPerUser>>(){  
         public int compare(Map.Entry<Integer, RatingsPerUser> o1, Map.Entry<Integer, RatingsPerUser> o2) {
            return (o2.getValue().getNumOfRatings()) - (o1.getValue().getNumOfRatings());
        }
       });
    }            
     */

 /* 
    //read ratings from file
    public void readRatings_movieLensFormat(String path) {
        //try {
        //String file = path;

        ///////////////////// 
        long start = System.currentTimeMillis();
        //List<MovieRating> movieRatingsList = new ArrayList<MovieRating>();
        //read the file (ratings.dat) line by line
        //for (String line : Files.readAllLines(Paths.get(path))) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            this.ratingsPerUser_hashTable.clear();
            for (String line; (line = bufferedReader.readLine()) != null;) {
                //read the userID, movieID and rating
                //Split line into three parts efficiently
               String[] part = new String[]{"", "", ""};
               int countOfPart = 0;
               int index = 0;
                while (countOfPart<5) {
                    char c = line.charAt(index);
                    if (c == ':') {
                        countOfPart++;
                    } else {
                        if (countOfPart == 0) {
                            part[0] += c;
                        } else if (countOfPart == 2) {
                            part[1] += c;
                        } else if (countOfPart == 4) {
                            part[2] += c;
                        }
                    }
                    index++;
                }
                
               // String[] part = line.split("::");
                
                int userID = Integer.parseInt(part[0].replaceAll("[\\D]", ""));//repalce non digit with blank
                //int userID = Integer.valueOf(part[0]);

              // int movieID = Integer.valueOf(part[1]);
               // int rating = Integer.valueOf(part[2]);
                int numOfRatings = 1;

                //create the object movieRating
                //MovieRating movieRating = new MovieRating(Integer.valueOf(part[1]), Integer.valueOf(part[2]));
                MovieRating movieRating = new MovieRating(Integer.valueOf(part[1]), Integer.valueOf(part[2]));

                //create the object movieRatingsList_temp
                List<MovieRating> movieRatingsList_temp = new ArrayList<MovieRating>();
                if (ratingsPerUser_hashTable.get(userID) != null) {
                    movieRatingsList_temp = ratingsPerUser_hashTable.get(userID).getMovieRatingList();
                    numOfRatings = ratingsPerUser_hashTable.get(userID).getNumOfRatings() + 1;
                }
                movieRatingsList_temp.add(movieRating);
                RatingsPerUser ratingsPerUser_temp = new RatingsPerUser(userID, numOfRatings, movieRatingsList_temp);
                ratingsPerUser_hashTable.put(userID, ratingsPerUser_temp);
            }
            bufferedReader.close();
            
            
            ArrayList<Entry<Integer,RatingsPerUser>> ratingsPerUser_list_temp = new ArrayList<>(ReadFiles.ratingsPerUser_hashTable.entrySet());
            ratingsPerUser_list = ratingsPerUser_list_temp;
            //////////////////////
           long elapsedTime = System.currentTimeMillis() - start;
           System.out.println((double) elapsedTime / 1000.0 + "sec");
           /////////////////
           // } catch (IOException ex) {
           } catch (IOException e) {
            Component frame = null;
            //JOptionPane.showMessageDialog(frame, "Select a valid rating file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(frame, "Select a valid rating file please!", "Message", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
            return;
          } catch (Exception e) {
            Component frame = null;
            //JOptionPane.showMessageDialog(frame, "Select a valid rating file please! \nError: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(frame, "Select a valid rating file please!", "Message", JOptionPane.ERROR_MESSAGE);
            System.out.println("ReadFiles.readRatings() Exception: " + e);
            return;
        }
    }

     */
 /*
     
    //Unused method
    public String printRatings() {
        //print ratingsPerUserHashTable and numberOfRatingsPerUser_hashTable
        //keys are the userIDs
        String text = "";
        Enumeration<Integer> keys = ratingsPerUser_hashTable.keys();
        int count = 0;
        while (keys.hasMoreElements()) {
            count++;
            if (count > maxRatingPrint) {
                minRatingPrint += 100;
                maxRatingPrint += 100;
                break;
            }
            if (count < minRatingPrint) {
                keys.nextElement();
            } else {
                int userID = keys.nextElement();
                int numOfRatings = ratingsPerUser_hashTable.get(userID).getNumOfRatings();
                List<MovieRating> movieRatingsList = new ArrayList<MovieRating>();
                movieRatingsList = ratingsPerUser_hashTable.get(userID).getMovieRatingList();

                text += userID + " : " + "numOfRatings= " + numOfRatings + " : ";

                //////////////
                //System.out.print(userID + " : " + "numOfRatings= " + numOfRatings + " : ");
                Iterator<MovieRating> itr = movieRatingsList.iterator();
                while (itr.hasNext()) {
                    MovieRating movieRating_temp = new MovieRating();
                    movieRating_temp = itr.next();

                    text += "[" + movieRating_temp.getMovieID() + ", " + movieRating_temp.getRating() + "], ";
                    ////////
                    //System.out.print("[" + movieRating_temp.getMovieID() + ", " + movieRating_temp.getRating() + "], ");
                }

                text += "\n";
                ///////////////////
                //System.out.println(" ");
            }
        }
        return text;
    }

    
    
   
    //Unused method
    public String printMovies() {
        //print ratingsPerUserHashTable and numberOfRatingsPerUser_hashTable
        //keys are the userIDs
        Enumeration<Integer> keys = movies_hashTable.keys();
        String text = "";
        int count = 0;
        while (keys.hasMoreElements()) {
            count++;
            if (count > maxMoviePrint) {
                minMoviePrint += 100;
                maxMoviePrint += 100;
                break;
            }
            if (count < minMoviePrint) {
                keys.nextElement();
            } else {
                int key = keys.nextElement();
                int movieID = movies_hashTable.get(key).getMovieID();
                String title = movies_hashTable.get(key).getTitle();
                String[] genres = movies_hashTable.get(key).getGenres();
                int numOfGenres = movies_hashTable.get(key).getNumOfGenres();

                //////////////////////////////print
                text += movieID + ": " + title + " : " + numOfGenres;
                //////////////
                // System.out.print( movieID + ": " + title + " " + numOfGenres);
                for (String s : genres) {
                    text += " " + s + "|";
                    /////////////
                    //System.out.print(" " + s);
                }
                text += "\n";
                ////////////
                //System.out.println();
            }
        }
        return text;
    }
     */
}
