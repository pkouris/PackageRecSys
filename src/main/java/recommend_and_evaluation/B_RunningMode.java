/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommend_and_evaluation;

import entityBasicClasses.ItemRatingCost;
import static recommend_and_evaluation.A_Start.maxValueOfRating;
import static recommend_and_evaluation.A_Start.movies_hashTable;
import static recommend_and_evaluation.A_Start.ratingsPerUser_hashTable;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class B_RunningMode {
    //static public int currentUserId;

    //runningMode:
    //-1: running by using actual ratings (default value)
    //0: running by using predicted ratings
    //1: evaluation by hidden items
    static public int runningMode = 1; //1 for rcomendations based on CF, 2 for recomendations based on popularity
    static public List<ItemRatingCost> predictedItemRatingCost_list = new ArrayList<ItemRatingCost>();
    static public List<ItemRatingCost> predictedItemRatingCostWithPopularity_list = new ArrayList<ItemRatingCost>();
    static public List<ItemRatingCost> hiddenItemRatings_list = new ArrayList<ItemRatingCost>();
    static public double popularityFactor = 5; //new_rating = rating + (popularity factor)*(popularity fraction)
    static public int itemsForPackages = 500; //num of items for creating packages

    public B_RunningMode() {
    }

    //it applies the running mode
    public void applyRunningMode(int userId, String testingDatafile, String userCFDatafile) throws IOException {
        if (runningMode == -1) { //-1: running by using actual ratings (default value) 
            predictedItemRatingCost_list.clear();
            predictedItemRatingCostWithPopularity_list.clear();
            hiddenItemRatings_list.clear();
            //copy the list by using the temp_list
            List<ItemRatingCost> temp_actualRatigns_list = ratingsPerUser_hashTable.get(userId).getItemRatingCostList();
            List<ItemRatingCost> temp_list = new ArrayList<>();
            for (int i = 0; i < temp_actualRatigns_list.size(); i++) {
                double item_cost = A_Start.movies_hashTable.get(temp_actualRatigns_list.get(i).getItemID()).getCost();
                ItemRatingCost movieRatingCost = new ItemRatingCost(temp_actualRatigns_list.get(i).getItemID(), temp_actualRatigns_list.get(i).getRating(), item_cost);
                temp_list.add(movieRatingCost);
                //System.out.println("actual rating = " + temp_actualRatigns_list.get(i).getRating()); 
            }

            predictedItemRatingCost_list = temp_list;
            this.setItemsForPackagesWithPopularity();

            predictedItemRatingCostWithPopularity_list = temp_list;
            //precisionAndRecallOfRecommendedItems();
            printRatings(0, userId); //print actual ratings sorted by rating value
        } else if (runningMode == 0) {  //0: running by using predicted ratings
            predictedItemRatingCost_list.clear();
            predictedItemRatingCostWithPopularity_list.clear();
            hiddenItemRatings_list.clear();
            predictedItemRatingCost_list = itemRatingCostListOfFile(userCFDatafile);
            setItemsForPackagesWithPopularity();
        } else if (runningMode == 1) {  //1: Evaluation by hidding items
            predictedItemRatingCost_list.clear();
            predictedItemRatingCostWithPopularity_list.clear();
            hiddenItemRatings_list.clear();
            setHiddenAndRecommendedItems(testingDatafile, userCFDatafile);
            setItemsForPackagesWithPopularity();
        } else if (runningMode == 2) {  //1: Evaluation by popular items
            predictedItemRatingCost_list.clear();
            predictedItemRatingCostWithPopularity_list.clear();
            hiddenItemRatings_list.clear();
            hiddenItemRatings_list = ItemRatingCostList_Of_userItemRatingFile(testingDatafile);
            predictedItemRatingCost_list = popularItemRatingCostListOfFile(userCFDatafile, userId);
            setItemsForPackagesWithPopularity();
        }
    }

    //It computes the precission and recall of the CF recommendations 
    //Correct items are the hidden items
    public void precisionAndRecallOfRecommendedItems() {
        int TP = 0; //correct selected items
        int FN = 0; //Not correct not selected items
        int FP = 0; //Not correct selected items
        int numOfPredictions = B_RunningMode.predictedItemRatingCostWithPopularity_list.size();
        int FP_computation[] = new int[numOfPredictions];
        double meanAbsoluteError = 0.0; //mean absolute Error of concealed items
        double rootMeanSqueredError = 0.0; //Root Mean Squared Error (RMSE)
        //int countFormMinAbsErrory = 0;
        for (int i = 0; i < numOfPredictions; i++) {
            FP_computation[i] = 1;
        }
        //int TN_perPackage[] = new int[numberOfPackages];

        //for each concealed item
        for (int i = 0; i < B_RunningMode.hiddenItemRatings_list.size(); i++) {
            //computing the TP and FN
            int FN_flag = 1;
            for (int j = 0; j < numOfPredictions; j++) {
                if (B_RunningMode.hiddenItemRatings_list.get(i).getItemID()
                        == B_RunningMode.predictedItemRatingCostWithPopularity_list.get(j).getItemID()) {
                    TP++;
                    FP_computation[j] = 0;
                    FN_flag = 0;
                    double temp = (B_RunningMode.hiddenItemRatings_list.get(i).getRating()
                            - B_RunningMode.predictedItemRatingCostWithPopularity_list.get(j).getRating());
                    if (temp < 0.0) {
                        temp *= -1.0;
                    }
                    meanAbsoluteError += temp;
                    rootMeanSqueredError += (temp * temp);
                    break;
                }
            }
            FN += FN_flag;
        }

        //mean absolute Error of concealed items
        meanAbsoluteError = meanAbsoluteError / ((double) TP);
        //Root Mean Squared Error (RMSE)
        rootMeanSqueredError = sqrt(rootMeanSqueredError / ((double) TP));

        for (int i = 0; i < numOfPredictions; i++) {
            FP += FP_computation[i];
        }
        //precision = correct retrieved items/retrieved items
        double precision = (double) (((double) TP) / (double) (TP + FP));
        //recall = correct retrieved items/correct tiems
        double recall = (double) ((double) TP / (double) (TP + FN));
        double F = 2 * precision * recall / (precision + recall);
        /////////////////////
        //System.out.println("TP= " + TP + "   FN = "+FN + "   FP= " + FP +
        //       "   Precision" + precision + "   Recall" + recall);
        ///////////////////////
        System.out.println("\nMAE = " + round(meanAbsoluteError, 3)
                + "    RMSE = " + round(rootMeanSqueredError, 3));
        System.out.println("TP = " + TP + "    FN = " + FN + "    FP = " + FP
        );
        System.out.println("Precision = " + round(precision, 3)
                + "    Recall = " + round(recall, 3) + "    F = " + round(F, 3));
    }

    //set items for creating packages with popularity of items to predictedMovieRatingsWithPopularity_list
    public void setItemsForPackagesWithPopularity() {
        try {
            double maxNewRating = 0.0;
            predictedItemRatingCostWithPopularity_list.clear();

            //add the items for packages with the fraction of item populartiy to predictedMovieRatingsWithPopularity_list
            int numberOfUsers = ratingsPerUser_hashTable.size();
            for (int i = 0; i < itemsForPackages; i++) {
                ItemRatingCost movieRatingCost_temp;
                movieRatingCost_temp = predictedItemRatingCost_list.get(i);
                int movieId = movieRatingCost_temp.getItemID();
                double moviePopularityFraction = (double) ((double) movies_hashTable.get(movieId).getPopularity()) / (double) numberOfUsers;
                //double predictedRating = movieRating_temp.getRating();
                double newRating = movieRatingCost_temp.getRating() + popularityFactor * moviePopularityFraction;
                double cost = movieRatingCost_temp.getCost();
                if (newRating > maxNewRating) {
                    maxNewRating = newRating;
                }
                predictedItemRatingCostWithPopularity_list.add(new ItemRatingCost(movieId, newRating, cost));
            }

            //it normalizes the rating from 0 to 5
            if (maxNewRating > 5.0) {
                for (int i = 0; i < itemsForPackages; i++) {
                    double rating_temp = predictedItemRatingCostWithPopularity_list.get(i).getRating();
                    //double normilizedRating = (rating_temp/maxNewRating)*maxPredictedRating;//normalized up to maxPredictedRating
                    double normilizedRating = (rating_temp / maxNewRating) * maxValueOfRating; //
                    predictedItemRatingCostWithPopularity_list.get(i).setRating(normilizedRating);
                }
            }
            //////////////
            //System.out.println("maxActualRating = " + maxActualRating);
            //////////////
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //returns 
    public List<ItemRatingCost> popularItemRatingCostListOfFile(String datafile, int userId) {
        String line = "";
        try{
        List<ItemRatingCost> itemRatingCost_list = new ArrayList<ItemRatingCost>();
        BufferedReader br = new BufferedReader(new FileReader(datafile));
        
        int count = 0;
        while ((line = br.readLine()) != null) {
            int len = line.length();
            String[] part = new String[]{"", "", ""};
            int countOfPart = 0;
            for (int i = 0; i < len; i++) {
                char c = line.charAt(i);
                if (c == ',') {
                    countOfPart++;
                } else if (countOfPart == 0) {
                    part[0] += c; //userId
                } else if (countOfPart == 1) {
                    part[1] += c; //itemID
                }
            }
            int itemId = Integer.parseInt(part[0].replaceAll("[\\D]", ""));//repalce non digit with blank
            double rating = Double.parseDouble(part[1]);
            double cost = A_Start.movies_hashTable.get(itemId).getCost();
            if (existenceItemIdToList(A_Start.ratingsPerUser_hashTable.get(userId).getItemRatingCostList(), itemId) == 0) {
                itemRatingCost_list.add(new ItemRatingCost(itemId, rating, cost));
                count++;
            }
            if (count == 500) {
                return itemRatingCost_list;
            }
        }
        return itemRatingCost_list;
         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in line: " + line);
            return null;
        }
    }

    //it returns 1 for existence and 0 otherwise
    public int existenceItemIdToList(List<ItemRatingCost> L, int itemId) {
        int size = L.size();
        for (int i = 0; i < size; i++) {
            if (L.get(i).getItemID() == itemId) {
                return 1;
            }
        }
        return 0;
    }

    //returns 
    public List<ItemRatingCost> itemRatingCostListOfFile(String datafile) throws FileNotFoundException, IOException {
        List<ItemRatingCost> itemRatingCost_list = new ArrayList<ItemRatingCost>();
        BufferedReader br = new BufferedReader(new FileReader(datafile));
        String line = "";
        while ((line = br.readLine()) != null) {
            int len = line.length();
            String[] part = new String[]{"", "", ""};
            int countOfPart = 0;
            for (int i = 0; i < len; i++) {
                char c = line.charAt(i);
                if (c == ',') {
                    countOfPart++;
                } else if (countOfPart == 0) {
                    part[0] += c; //userId
                } else if (countOfPart == 1) {
                    part[1] += c; //itemID
                }
            }
            int itemId = Integer.parseInt(part[0].replaceAll("[\\D]", ""));//repalce non digit with blank
            double rating = Double.parseDouble(part[1]);
            double cost = A_Start.movies_hashTable.get(itemId).getCost();
            itemRatingCost_list.add(new ItemRatingCost(itemId, rating, cost));
        }
        return itemRatingCost_list;
    }

    //returns 
    public List<ItemRatingCost> ItemRatingCostList_Of_userItemRatingFile(String datafile) throws FileNotFoundException, IOException {
        List<ItemRatingCost> itemRatingCost_list = new ArrayList<ItemRatingCost>();
        BufferedReader br = new BufferedReader(new FileReader(datafile));
        String line = "";
        while ((line = br.readLine()) != null) {
            int len = line.length();
            String[] part = new String[]{"", "", ""};
            int countOfPart = 0;
            for (int i = 0; i < len; i++) {
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
            int itemId = Integer.parseInt(part[1].replaceAll("[\\D]", ""));//repalce non digit with blank
            double rating = Double.parseDouble(part[2]);
            double cost = A_Start.movies_hashTable.get(itemId).getCost();
            itemRatingCost_list.add(new ItemRatingCost(itemId, rating, cost));
        }
        return itemRatingCost_list;
    }

    public void setHiddenAndRecommendedItems(String testingFile, String userCF_File) throws IOException {
        hiddenItemRatings_list = ItemRatingCostList_Of_userItemRatingFile(testingFile);
        predictedItemRatingCost_list = itemRatingCostListOfFile(userCF_File);
    }

    public void printRatings(int sortingMode, int userId) {
        switch (runningMode) {
            case -1: {
                //print
                this.printActualRatings(sortingMode, userId);
                break;
            }
            case 0: {
                //print
                this.printActualRatings(sortingMode, userId);
                this.printPredictedRatings(sortingMode);
                break;
            }
            default: //runningMode = 1
                //print
                this.printActualRatings(sortingMode, userId);
                this.printPredictedRatings(sortingMode);
                this.printHiddenRatings(0);
                break;
        }
    }

    //it prints the actual ratigns of the selected user
    public void printActualRatings(int sortingMode, int userId) {
        try {
            List<ItemRatingCost> actualMovieRatings_list = ratingsPerUser_hashTable.get(userId).getItemRatingCostList();
            int len = actualMovieRatings_list.size();
            if (sortingMode == 0) {
                //sorting ascending based on itemId
                Collections.sort(actualMovieRatings_list, new Comparator<ItemRatingCost>() {
                    public int compare(ItemRatingCost o1, ItemRatingCost o2) {
                        return o1.getItemID() - o2.getItemID();
                    }
                });
            } else {
                //sorting descending based on ratings
                Collections.sort(actualMovieRatings_list, new Comparator<ItemRatingCost>() {
                    public int compare(ItemRatingCost o1, ItemRatingCost o2) {
                        return (int) (o2.getRating() * 100000.0 - o1.getRating() * 100000.0);
                    }
                });
            }
            System.out.println("\nActual Ratings:\nno, Item[id, raiting, cost]");
            for (int i = 0; i < len;) {
                String row_text = "";
                for (int j = 0; j < 10 && i < len; j++) {
                    row_text += "[" + actualMovieRatings_list.get(i).getItemID() + ", " + round(actualMovieRatings_list.get(i).getRating(), 2) + ", " + round(actualMovieRatings_list.get(i).getCost(), 2) + "]  ";
                    i++;
                }
                System.out.println(i + ", " + row_text);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    //it prints the actual ratigns of the selected user
    public void printPredictedRatings(int sortingMode) {
        try {
            if (sortingMode == 0) {
                //sorting ascending based on itemId
                Collections.sort(predictedItemRatingCostWithPopularity_list, new Comparator<ItemRatingCost>() {
                    public int compare(ItemRatingCost o1, ItemRatingCost o2) {
                        return o1.getItemID() - o2.getItemID();
                    }
                });
            } else {
                //sorting descending based on ratings
                Collections.sort(predictedItemRatingCostWithPopularity_list, new Comparator<ItemRatingCost>() {
                    public int compare(ItemRatingCost o1, ItemRatingCost o2) {
                        return (int) (o2.getRating() * 100000.0 - o1.getRating() * 100000.0);
                    }
                });
            }
            int len = predictedItemRatingCostWithPopularity_list.size();
            System.out.println("\nPredicted Ratings:\nno, Item[id, raiting, cost]");
            for (int i = 0; i < len;) {
                String row_text = "";
                for (int j = 0; j < 10 && i < len; j++) {
                    row_text += "[" + predictedItemRatingCostWithPopularity_list.get(i).getItemID() + ", " + round(predictedItemRatingCostWithPopularity_list.get(i).getRating(), 2) + ", " + round(predictedItemRatingCostWithPopularity_list.get(i).getCost(), 2) + "]  ";
                    i++;
                }
                System.out.println(i + ", " + row_text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //it prints the concealed ratigns of the selected user
    public void printHiddenRatings(int sortingMode) {
        try {
            if (sortingMode == 0) {
                //sorting ascending based on itemId
                Collections.sort(hiddenItemRatings_list, new Comparator<ItemRatingCost>() {
                    public int compare(ItemRatingCost o1, ItemRatingCost o2) {
                        return o1.getItemID() - o2.getItemID();
                    }
                });
            } else {
                //sorting descending based on ratings
                Collections.sort(hiddenItemRatings_list, new Comparator<ItemRatingCost>() {
                    public int compare(ItemRatingCost o1, ItemRatingCost o2) {
                        return (int) (o2.getRating() * 100000.0 - o1.getRating() * 100000.0);
                    }
                });
            }
            int len = this.hiddenItemRatings_list.size();
            System.out.println("\nHidden Ratings:\nno, Item[Id, rating, cost]");
            for (int i = 0; i < len;) {
                String row_text = "";
                for (int j = 0; j < 10 && i < len; j++) {
                    row_text += "[" + hiddenItemRatings_list.get(i).getItemID() + ", " + round(hiddenItemRatings_list.get(i).getRating(), 2) + ", " + round(hiddenItemRatings_list.get(i).getCost(), 2) + "]  ";
                    i++;
                }
                System.out.println(i + ", " + row_text);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    //it rounds the double number in specific number of decimals
    public double round(double number, int decimals) {
        if (decimals < 0) {
            return number; //no change in number
        }
        long factor = (long) Math.pow(10, decimals);
        number = number * factor;
        long tmp = Math.round(number);
        return (double) tmp / factor;
    }

}
