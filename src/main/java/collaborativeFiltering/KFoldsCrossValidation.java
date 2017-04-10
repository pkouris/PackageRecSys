/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collaborativeFiltering;

import entityBasicClasses.List_UserItemRating;
import entityBasicClasses.UserItemRating;
import fileManagerClasses.ReadFiles;
import formsAndFunctionality.StartForm;
import static formsAndFunctionality.StartForm.dataset;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class KFoldsCrossValidation {

    public KFoldsCrossValidation() {
    }

    //it writes in files the predicted ratings per user 
    public void ProduceKFoldsRandomly(int k_folds, int testingPerCent, int sizeOfRatings, int sizeOfItems) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(StartForm.ratingsDataFile));
            String line;
            int[] numOfRatingsPerUser = new int[sizeOfItems];  //the index or array is the user id
            int[] numOfTrainingRatingsPerUser = new int[sizeOfItems]; //the index or array is the user id
            int[] numOfTestingRatingsPerUser = new int[sizeOfItems]; //the index or array is the user id
            int[] userId = new int[sizeOfRatings];
            int[] itemId = new int[sizeOfRatings];
            double[] rating = new double[sizeOfRatings];

            for (int i = 0; i < sizeOfItems; i++) {
                numOfRatingsPerUser[i] = 0;
                numOfTrainingRatingsPerUser[i] = 0;
                numOfTestingRatingsPerUser[i] = 0;
            }
            int maxUserId = 0;
            System.out.println("file is reading");
            int line_index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                line_index++;
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
                userId[line_index] = Integer.parseInt(part[0].replaceAll("[\\D]", ""));//repalce non digit with blank
                itemId[line_index] = Integer.parseInt(part[1].replaceAll("[\\D]", ""));
                rating[line_index] = Double.parseDouble(part[2].replaceAll("[\\D]", ""))/10.0;
                if (maxUserId < userId[line_index]) {
                    maxUserId = userId[line_index];
                }
                numOfRatingsPerUser[userId[line_index]]++;
            }
            System.out.println("file is parsed");
            bufferedReader.close();
            int dataset_size = line_index;
            ///////////////////////
            System.out.println(dataset_size);
            //System.gc();
            Random random = new Random();
            for (int u = 1; u < maxUserId + 1; u++) {
                kfoldCrossvalidation(5, testingPerCent, u, dataset_size, userId, itemId, rating, numOfRatingsPerUser, random);
                System.out.println(u + " of " + maxUserId);
                //System.gc();
            }
        } catch (IOException e) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Error: trainingAndTestingSetInWholeDataset() " + e, "Message", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
            return;
        } catch (Exception e) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Error: trainingAndTestingSetInWholeDataset() " + e, "Message", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
    }

    public void kfoldCrossvalidation(int k_folds, int testingPerCent, int user, int dataset_size,
            int[] userId, int[] itemId, double[] rating, int[] numOfRatingsPerUser, Random random) throws IOException {
        int u = user;
        List_UserItemRating[] trainingSet = new List_UserItemRating[k_folds + 1];
        List_UserItemRating[] testingSet = new List_UserItemRating[k_folds + 1];
        int[] testingRatings_perFold = new int[k_folds + 1];
        for (int f = 1; f < k_folds + 1; f++) {
            testingSet[f] = new List_UserItemRating();
            trainingSet[f] = new List_UserItemRating();
            testingRatings_perFold[f] = 0;
        }
        int maxTestingRatings = numOfRatingsPerUser[u] * testingPerCent / 100;

        for (int i = 1; i < dataset_size; i++) {
            if (userId[i] == u) {
                for (int f = 1; f < k_folds + 1; f++) {
                    int randomInt = random.nextInt(100);
                    /////////////////////////
                    //System.out.println("randomInt: " + randomInt);

                    if (randomInt < (testingPerCent + 5) && testingRatings_perFold[f] < maxTestingRatings) {
                        List<UserItemRating> temp_list = new ArrayList();
                        temp_list = testingSet[f].getUserItemRatingList();
                        temp_list.add(new UserItemRating(u, itemId[i], rating[i]));
                        testingSet[f].setUserItemRatingList(temp_list);
                        testingRatings_perFold[f]++;
                    } else {
                        List<UserItemRating> temp_list = new ArrayList();
                        temp_list = trainingSet[f].getUserItemRatingList();
                        temp_list.add(new UserItemRating(u, itemId[i], rating[i]));
                        trainingSet[f].setUserItemRatingList(temp_list);
                    }
                }
            }
        }
        for (int f = 1; f < k_folds + 1; f++) {
            String testingRatinsFile = dataset + "anime_ratings_per_user/" + u + "_testing_" + f + ".dat";
            BufferedWriter testing_bW = new BufferedWriter(new FileWriter(testingRatinsFile));
            if (testingSet[f].getUserItemRatingList() != null) {
                List<UserItemRating> testing_list = testingSet[f].getUserItemRatingList();
                int size_testingList = testing_list.size();
                for (int test = 0; test < size_testingList; test++) {
                    UserItemRating temp = testing_list.get(test);
                    testing_bW.write(temp.getUserId() + "," + temp.getItemId() + "," + temp.getRating() +"\n");
                }
                testing_bW.close();
            }
            //It writes the training files
            String trainingRatinsFile = dataset + "anime_ratings_per_user/" + u + "_training_" + f + ".dat";
            BufferedWriter training_bW = new BufferedWriter(new FileWriter(trainingRatinsFile));
            if (trainingSet[f].getUserItemRatingList() != null) {
                List<UserItemRating> training_list = trainingSet[f].getUserItemRatingList();
                int size_trainingList = training_list.size();
               /////////////////////
                //System.out.println("size_trainingList:" + size_trainingList);

                for (int train = 0; train < size_trainingList; train++) {
                    UserItemRating temp = training_list.get(train);
                    training_bW.write(temp.getUserId() + "," + temp.getItemId() + "," + temp.getRating() + "\n");
                }
                training_bW.close();
            }
        }
    }

}
