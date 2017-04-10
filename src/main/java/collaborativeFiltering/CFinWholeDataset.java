/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collaborativeFiltering;

import entityBasicClasses.ItemRating;
import entityBasicClasses.List_UserItemRating;
import entityBasicClasses.UserItemRating;
import fileManagerClasses.ReadFiles;
import static formsAndFunctionality.StartForm.dataset;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import recommend_and_evaluation.A_Start;

/**
 * @author: Panagiotis Kouris
 * @date: Mar 2017
 */
public class CFinWholeDataset {

    int[] animeUsers = {//5908, 5899, 5895, 5831, 5815,  5672, 5669, 5655, 5562, 5555, 
         //5516, 5504, 5374, 5357,  5310, 5264, 5137, 5073, 4883, 4843,  
         //4468, 4350, 4102, 3657, 3557,  3476, 3203, 3193, 3117, 3127,
         //3040, 2951, 2820,  2701, 2695,  2378, 2200, 1530, 1522, 1497,  
         //1456, 1287, 1176,  1019, 958,   771,  661,  446,  294,  226 
        //6417, 6474, 6569, 6618, 7249, 7421, 7659, 7670, 7824 
        //7852, 8115, 8308, 10194
        //10297, 10497, 10536, 10654
             
        
        //11436, 11423, 11397, 11186, 11080, 10844
    };
    
    public CFinWholeDataset() {
    }

    
    
    
    public void applyCollaborativeFiltering(){
        try {
            cf_inSetOfUsers(A_Start.ratingsDataFile, animeUsers);
        } catch (IOException ex) {
            Logger.getLogger(CFinWholeDataset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    //it writes in files the predicted ratings per user 
    public void cf_inSetOfUsers(String ratingsFile, int[] setOfUsers) throws IOException {
        int len = setOfUsers.length;
        for (int i = 0; i < len; i++) {
            String userTrainingDataPath = dataset + "anime_ratings_per_user/";
            cF_ofKFoldsForUserId(5, setOfUsers[i], ratingsFile, userTrainingDataPath);
            System.gc();
        }
        System.out.println ("End");
    }
    
    
    //it writes in files the predicted ratings per user 
    public void cf_inWholeDatasetPerUser(String ratingsFile, int fromUser, int toUser) throws IOException {
        for (int u = fromUser; u < toUser+1; u++) {
            String userTrainingDataPath = dataset + "anime_ratings_per_user/";
            cF_ofKFoldsForUserId(5, u, ratingsFile, userTrainingDataPath);
        }
    }

    //cf in k-folds of userId and writing the file with recommendations
    public void cF_ofKFoldsForUserId(int k_folds, int userId, String datasetFile, String userTrainingDataPath) throws IOException {
        for (int f = 1; f < k_folds + 1; f++) {
            System.out.println(userId + " " + f + " of " +k_folds);
            String kFold_userTrainingDatafile = userTrainingDataPath + "" + userId + "_training_" + f + ".dat";
            //recItemRatingList = new ArrayList();
            List<ItemRating> recomItemRatingList = cF_forUserId(userId, datasetFile, kFold_userTrainingDatafile);
            
            //listOf_itemRatingLists.add(temp);
            String cfUserRecommendationsFile = dataset + "anime_cf_per_user/" + userId + "_recom_" + f + ".dat";
            BufferedWriter bw = new BufferedWriter(new FileWriter(cfUserRecommendationsFile));
            int size_recItemRatingList = recomItemRatingList.size();
            for (int s = 0; s < size_recItemRatingList; s++) {
                ItemRating temp = recomItemRatingList.get(s);
                bw.write(temp.getItemID() + "," + temp.getRating() + "\n");
                //////////////////
                //System.out.println(temp.getItemID() + "," + temp.getRating());
            }
            bw.close();
        }
    }


    public List<ItemRating> cF_forUserId(int userId, String datasetFile, String userTrainingDataFile) {
        List<ItemRating> itemRatings_list = new ArrayList<>();
        try {
            String tempDataFile = createTempDataset(userId, datasetFile, userTrainingDataFile);
            ////////
            //System.out.println("tempDataFile: " +tempDataFile);
            CollaborativeFiltering cf = new CollaborativeFiltering();
            itemRatings_list = cf.itemBasedColFiltering(userId, 500, tempDataFile);
            
            Collections.sort(itemRatings_list, new Comparator<ItemRating>() {
                public int compare(ItemRating a1, ItemRating a2) {
                    return (int) (a2.getRating()*100000.0 - a1.getRating()*100000.0);
                }
            });
            
            ///////////////////////
            //System.out.println(itemRatings_list);
        } catch (IOException e) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Error: trainingAndTestingSetInWholeDataset() " + e, "Message", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } catch (Exception e) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Error: trainingAndTestingSetInWholeDataset() " + e, "Message", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
        return itemRatings_list;
    }

    //creating a temp file of dataset for userId with the traing set of the userId and without the testing set of the userId
    //It returns the filePath of temp dataset file
    public String createTempDataset(int userId, String datasetFile, String userTrainingDataFile) throws IOException {
        List_UserItemRating list_UserItemRating_userTrainingData = userItemRatinListOfFile(userTrainingDataFile);
        List_UserItemRating list_UserItemRating_datset = userItemRatinListOfFile(datasetFile);

        List<UserItemRating> userTrainingSet_list = list_UserItemRating_userTrainingData.getUserItemRatingList();
        List<UserItemRating> dataset_list = list_UserItemRating_datset.getUserItemRatingList();

        String testingRatinsFile = dataset + "temp/temp_trainigSet.dat";
        BufferedWriter testing_bW = new BufferedWriter(new FileWriter(testingRatinsFile));
        int datasetList_size = dataset_list.size();
        int userTrainingSetList_size = userTrainingSet_list.size();
        //writing user training set
        for (int i = 1; i < userTrainingSetList_size; i++) {
            UserItemRating temp = userTrainingSet_list.get(i);
            testing_bW.write(temp.getUserId() + "," + temp.getItemId() + "," + temp.getRating() + "\n");
        }
        //writing dataset without the userId instances
        for (int i = 1; i < datasetList_size; i++) {
            if (dataset_list.get(i).getUserId() != userId) {
                UserItemRating temp = dataset_list.get(i);
                testing_bW.write(temp.getUserId() + "," + temp.getItemId() + "," + temp.getRating() + "\n");
            }
        }
        testing_bW.close();
        return testingRatinsFile;
    }

    //returns 
    public List_UserItemRating userItemRatinListOfFile(String datafile) throws FileNotFoundException, IOException {
        List_UserItemRating datasetSet_list = new List_UserItemRating();
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
            int userId = Integer.parseInt(part[0].replaceAll("[\\D]", ""));//repalce non digit with blank
            int itemId = Integer.parseInt(part[1].replaceAll("[\\D]", ""));
            double rating = Double.parseDouble(part[2]);
            datasetSet_list.addUserItemRatingList(new UserItemRating(userId, itemId, rating));
        }
        return datasetSet_list;
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
