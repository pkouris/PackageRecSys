/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommend_and_evaluation;

import entityBasicClasses.ItemRatingCost;
import entityBasicClasses.Item_withCost;
import entityBasicClasses.Measures;
import entityBasicClasses.RatingsPerUser_v2;
import formsAndFunctionality.StartForm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class A_Start {

    static public Hashtable<Integer, RatingsPerUser_v2> ratingsPerUser_hashTable = new Hashtable<>();
    static public ArrayList<RatingsPerUser_v2> ratingsPerUser_list = new ArrayList<>();
    static public Hashtable<Integer, Item_withCost> movies_hashTable = new Hashtable<>();
    static public List<Item_withCost> movies_list = new ArrayList<>();

    static public double maxValueOfRating = 0.0;

    static public int currentUserID = 1;
    static public String lin_dataset = "/home/pkouris/Dropbox/EMP_DID_dropbox/PackageRecSys/Dataset/";
    static public String win_dataset = "E:/Dataset/";

    static public String dataset = win_dataset; //It should be changed according to pc
    static public int selectedUserID = 1;
    
    //static public String ratingsDataFile = dataset + "m1m/ratings_converted.dat"; //default value
    //static public String ratingsCFDataPath = dataset + "m1m_cf_per_user/"; //path to predicted ratings (recommandations) per user
    //static public String trainingAndTestingRatings_DataPath = dataset + "m1m_ratings_per_user/"; //path to training and testing ratings per user
    //static public String itemsDataFile = dataset + "m1m/movies_converted_withDuration.dat"; //default value
    //static public String morePopularItemsDataFile = dataset + "m1m/movies_morePopular.dat"; //default value
    
    
    
    static public String ratingsDataFile = dataset + "anime/ratings_converted.dat"; //default value
    static public String ratingsCFDataPath = dataset + "anime_cf_per_user/"; //path to predicted ratings (recommandations) per user
    static public String trainingAndTestingRatings_DataPath = dataset + "anime_ratings_per_user/"; //path to training and testing ratings per user
    static public String itemsDataFile = dataset + "anime/items_converted_withDuration.dat"; //default value
    static public String morePopularItemsDataFile = dataset + "anime/items_morePopular.dat"; //default value
    
    
    
    
    public A_Start() {
    }

     public void printAvgRatingsAndPrecisionPerPackage(double[] avgRatingsPerPackage, double[] precisionPerPackage, double avgPrecision, int numOfPackages) {
        String[] heading = {"pack_no", "avgRating", "precision"};
        System.out.format("%-15s%-15s%-15s\n", heading);
        for (int i = 0; i < numOfPackages; i++) {
            String[] row = {(i + 1) + "", avgRatingsPerPackage[i] + "", precisionPerPackage[i] + ""};
            System.out.format("%-15s%-15s%-15s\n", row);
        }
        System.out.println("avgPrecision: " + avgPrecision);
    }

    public void loadFiles(int userId, String ratings, String userRatings) {
        readItemsFile(itemsDataFile);
        readRatingsFile(userId, ratings, userRatings);
    }

    public void loadFiles_exceptUserId(int userId, String ratings) {
        readItemsFile(itemsDataFile);
        readRatingsFile_exceptUserId(userId, ratings);
    }
    
    
  
    
     //read ratings file using the Mahout format
    public void loadUserRatings(int userId, String userRatings) {
        //long start = System.currentTimeMillis();
        //read the file (ratings.dat) line by line
        try {
            BufferedReader bf_ratings = new BufferedReader(new FileReader(userRatings));
            
            for (String line; (line = bf_ratings.readLine()) != null;) {
                splitRatingsLine(line, -1);
            }
            bf_ratings.close();
            
            
            writeRatingsPerUser_list();
            //////////////////////
            //long elapsedTime = System.currentTimeMillis() - start;
            // System.out.println((double) elapsedTime / 1000.0 + "sec");
            /////////////////
            // } catch (IOException ex) {
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    
    
    //read ratings file using the Mahout format, except current User
    public void readRatingsFile_exceptUserId(int userId, String ratings) {
        //long start = System.currentTimeMillis();
        //read the file (ratings.dat) line by line
        try {
            ratingsPerUser_hashTable.clear();
            BufferedReader bf_userRatings = new BufferedReader(new FileReader(ratings));
            for (String line; (line = bf_userRatings.readLine()) != null;) {
                splitRatingsLine(line, userId);
            }
            bf_userRatings.close();
            //////////////////////
            //long elapsedTime = System.currentTimeMillis() - start;
            // System.out.println((double) elapsedTime / 1000.0 + "sec");
            /////////////////
            // } catch (IOException ex) {
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    
    
    
    
    
    public void writeRatingsPerUser_list() {
        ratingsPerUser_list.clear();
        Enumeration<Integer> keys = ratingsPerUser_hashTable.keys();
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            RatingsPerUser_v2 temp = ratingsPerUser_hashTable.get(key);
            ratingsPerUser_list.add(temp);
        }
    }

    
    
    //read ratings file using the Mahout format
    public void readRatingsFile(int userId, String ratings, String userRatings) {
        //long start = System.currentTimeMillis();
        //read the file (ratings.dat) line by line
        try {
            BufferedReader bf_ratings = new BufferedReader(new FileReader(userRatings));
            ratingsPerUser_hashTable.clear();
            for (String line; (line = bf_ratings.readLine()) != null;) {
                splitRatingsLine(line, -1);
            }
            bf_ratings.close();
            BufferedReader bf_userRatings = new BufferedReader(new FileReader(ratings));
            for (String line; (line = bf_userRatings.readLine()) != null;) {
                splitRatingsLine(line, userId);
            }
            bf_userRatings.close();

            ratingsPerUser_list.clear();
            Enumeration<Integer> keys = ratingsPerUser_hashTable.keys();
            while (keys.hasMoreElements()) {
                int key = keys.nextElement();
                RatingsPerUser_v2 temp = ratingsPerUser_hashTable.get(key);
                ratingsPerUser_list.add(temp);
            }
            //////////////////////
            //long elapsedTime = System.currentTimeMillis() - start;
            // System.out.println((double) elapsedTime / 1000.0 + "sec");
            /////////////////
            // } catch (IOException ex) {
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    //read and split the line of the ratings data file
    public void splitRatingsLine(String line, int exceptUserId) {
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
            if (userID != exceptUserId) {
                //int userID = Integer.valueOf(part[0]);
                int movieID = Integer.valueOf(part[1]);
                double rating = Double.valueOf(part[2]);
                ///////////////
                //System.out.println("movieId:" + movieID);
                double cost = movies_hashTable.get(movieID).getCost();
                if (rating > maxValueOfRating) {
                    maxValueOfRating = rating;
                }
                int numOfRatings = 1;
                //create the object movieRating
                ItemRatingCost movieRatingCost = new ItemRatingCost(movieID, rating, cost);

                //update the popularity of the movie
                int newPopularity = (movies_hashTable.get(movieID).getPopularity()) + 1;;
                ////////////////////////////
                //System.out.println("movieID: " + movieID + " newPopularity: " + newPopularity);
                (movies_hashTable.get(movieID)).setPopularity(newPopularity);

                //create the object movieRatingsList_temp
                List<ItemRatingCost> movieRatingCostList_temp = new ArrayList<>();
                if (ratingsPerUser_hashTable.get(userID) != null) {
                    movieRatingCostList_temp = ratingsPerUser_hashTable.get(userID).getItemRatingCostList();
                    numOfRatings = ratingsPerUser_hashTable.get(userID).getNumOfRatings() + 1;
                }
                movieRatingCostList_temp.add(movieRatingCost);
                RatingsPerUser_v2 ratingsPerUser_temp = new RatingsPerUser_v2(userID, numOfRatings, movieRatingCostList_temp);
                ratingsPerUser_hashTable.put(userID, ratingsPerUser_temp);
            }

        } catch (Exception e) {
            System.out.println("errorn in line: " + line);
            e.printStackTrace();
            return;
        }
    }

    public Item_withCost splitItemsLine(String line) {
        try {
            String[] part = new String[]{"", "", "", ""};
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
            double cost = (double) Double.parseDouble(part[3].replaceAll("[\\D]", ""));
            //  if (!part[3].equals("")) {
            //      numOfMaxAppearances = convertStringToInt(part[3]);
            //  }
            ///////////////////////
            //System.out.println("movieId: " + movieID + " cost: " + cost);
            Item_withCost item = new Item_withCost(movieID, title, genres, genres.length, numOfMaxAppearances, 0, cost);
            return item;
        } catch (Exception e) {
            System.out.println("error in line: " + line);
            e.printStackTrace();
            return null;
        }
    }

    //read items from file
    public void readItemsFile(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            this.movies_hashTable.clear();
            for (String line; (line = bufferedReader.readLine()) != null;) {
                Item_withCost item = splitItemsLine(line);
                if (item != null) {
                    movies_hashTable.put(item.getItemID(), item);
                }
            }
            bufferedReader.close();
            movies_list.clear();
            Enumeration<Integer> keys = movies_hashTable.keys();
            while (keys.hasMoreElements()) {
                int key = keys.nextElement();
                Item_withCost movie_temp = movies_hashTable.get(key);
                movies_list.add(movie_temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
            Collections.sort(ratingsPerUser_list, new Comparator<RatingsPerUser_v2>() {
                public int compare(RatingsPerUser_v2 o1, RatingsPerUser_v2 o2) {
                    return (o2.getNumOfRatings()) - (o1.getNumOfRatings());
                }
            });
        } else {
            Collections.sort(ratingsPerUser_list, new Comparator<RatingsPerUser_v2>() {
                public int compare(RatingsPerUser_v2 o1, RatingsPerUser_v2 o2) {
                    return (o1.getUserID()) - (o2.getUserID());
                }
            });
        }
    }

//sorting items by itemId or item popularity 
    public void sortItemsList(int mode) {
        if (mode == 0) { //mode = 0: sorting by movieId
            //sorting ascending items by itemId or item popularity 
            Collections.sort(movies_list, new Comparator<Item_withCost>() {
                public int compare(Item_withCost o1, Item_withCost o2) {
                    return (o1.getItemID()) - (o2.getItemID());
                }

            });
        } else {//mode != 0: sorting by item Popularity
            //sorting descending by item Popularity
            Collections.sort(movies_list, new Comparator<Item_withCost>() {
                public int compare(Item_withCost o1, Item_withCost o2) {
                    return (o2.getPopularity()) - (o1.getPopularity());
                }

            });
        }

    }

    //It prints ratings
    public void printRatings() {
        try {
            System.out.println("\nRatings:\nuserID, numOfRatings, MovieRatingCost List");
            for (int i = 0; i < ratingsPerUser_list.size(); i++) {
                int userID = ratingsPerUser_list.get(i).getUserID();
                int numOfRatings = ratingsPerUser_list.get(i).getNumOfRatings();
                List<ItemRatingCost> movieRatingsList = ratingsPerUser_list.get(i).getItemRatingCostList();
                String temp_text = "";
                for (int r = 0; r < movieRatingsList.size(); r++) {
                    temp_text += "[" + movieRatingsList.get(r).getItemID() + ", " + movieRatingsList.get(r).getRating() + ", " + movieRatingsList.get(r).getCost() + "] ";
                }
                System.out.println(userID + ", " + numOfRatings + ", " + temp_text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //it prints items 
    public void printItems() {
        Enumeration<Integer> keys = movies_hashTable.keys();
        System.out.println("\nItems:\nmovieID, maxAppearancesPerMovie, moviePopularity, title, genres, cost");
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            int movieID = movies_hashTable.get(key).getItemID();
            int maxAppearancesPerMovie = movies_hashTable.get(key).getNumOfMaxAppearances();
            int moviePopularity = movies_hashTable.get(key).getPopularity();
            String title = movies_hashTable.get(key).getTitle();
            String[] genres = movies_hashTable.get(key).getCategories();
            double cost = movies_hashTable.get(key).getCost();
            String temp_text = "";
            for (String s : genres) {
                temp_text += s + ", ";
            }
            System.out.println(movieID + ", " + maxAppearancesPerMovie + ", " + moviePopularity + ", " + title + ", " + temp_text + ", " + cost);
        }
    }

}
