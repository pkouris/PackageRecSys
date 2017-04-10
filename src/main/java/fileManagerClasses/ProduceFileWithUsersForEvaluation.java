/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileManagerClasses;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;
import recommend_and_evaluation.A_Start;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class ProduceFileWithUsersForEvaluation {

    public ProduceFileWithUsersForEvaluation() {
    }

    
    
    public void writeFileWithUserIdAndRatings() throws IOException {
        String filePath = A_Start.dataset + "anime_usersForEvaluation_25pc.txt";
        BufferedWriter bW = new BufferedWriter(new FileWriter(filePath));
        A_Start a = new A_Start();
        a.loadFiles_exceptUserId(0, A_Start.ratingsDataFile); //It is loaded in order to be taken the ratings per user
        //int size = A_Start.ratingsPerUser_hashTable.size();
        Random random = new Random();
       // for (int u = 1; u < size + 1; u++) {
       Enumeration<Integer> users = A_Start.ratingsPerUser_hashTable.keys();      
       while (users.hasMoreElements()) {
            //List<MovieForPackage> movieForPackage_list = new ArrayList<>();
            int u = users.nextElement();
            int numOfRatings = A_Start.ratingsPerUser_hashTable.get(u).getNumOfRatings();
            if (numOfRatings > 500) {
                //////////////
                if (random.nextInt(100) < 25) {
                    System.out.print(u + ", ");
                    bW.write(u + " # " + numOfRatings + "\r\n");
                }
            }
        }
        bW.close();
    }

}
