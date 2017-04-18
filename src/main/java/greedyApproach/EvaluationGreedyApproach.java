/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greedyApproach;

import entityBasicClasses.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import recommend_and_evaluation.A_Start;
import recommend_and_evaluation.B_RunningMode;
import recommend_and_evaluation.C_ProblemModeling;
import recommend_and_evaluation.D_ItemsAllocationToCategories;
import static recommend_and_evaluation.E_RecommendPackages.packages_hashTable;
import recommend_and_evaluation.F_Evaluation;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class EvaluationGreedyApproach {

    static List<EvaluationResultsType> evaluationResults_list = new ArrayList<>();
    static AvgEvaluationResultsType Avg_evaluationResults = new AvgEvaluationResultsType();
    static int minRatingForModifiedPrecision = 4;

   /*   
    //User ids have been chosen randomly for evaluating
    int[] movielens = 
        {149,  202, 482,   531,  660,  699,  710,   721,  802,   869, 
         877, 1010, 1112, 1125, 1137, 1264, 1266, 1451, 1579, 1635, 
        1676, 1880, 1988, 2010, 2105, 2419, 2537, 2793, 2909,  2967, 
        3182, 3285, 3336, 3475, 3610, 3626, 3942, 4089,  4277, 4579,    
        4673, 4728, 4732, 4979, 5100, 5306, 5329, 5367, 5394, 5433};
    //User ids have been chosen randomly
   int[] anime = {
         11410, 10536,   8308,  8115, 7852, 7670,  7421,  6569,     
         5908,  5899,  5895,  5831,  5669,  5655, 5562, 5555,  5516,  5374,      
         5357,  5073,  4843,  4468,  4350,  3657, 3476, 3203,  3193,  3117, 
         3127,  3040,  2951,  2820,  2701,  2695, 2378, 2200,  1530,  1522,  
         1497,  1456,  1287,  1176,  958,   771,  661,  446,   294,   226};
    */
    
    int[] evaluationUsersId = F_Evaluation.evaluationUsersId;;  //It should be changed to specific dataset


    public EvaluationGreedyApproach() {
    }

    public void runEvaluation() throws IOException {
        long startTime = System.currentTimeMillis();
        evaluationResults_list.clear();
        
        
        //evaluationOfaRangeOfUsers(10, 10, 1);
        evaluationOfUsers(evaluationUsersId, 5);
        //evaluationOfRandomUsers(1383, 2907, 5);
        
        
        avgEvaluationResults(evaluationResults_list);
        this.printEvaluationResults(evaluationResults_list, 3);
        this.printAvgEvaluationResults(Avg_evaluationResults);
        System.out.println("\nGreedy - overall time:" + (System.currentTimeMillis()- startTime) + "msec" );
    }

    public void evaluationOfUsers(int[] users, int folds) throws IOException {
        int len = users.length; //It is loaded in order to be taken the ratings per user
        for (int u = 0; u < len; u++) {
            evaluationPerUser(users[u], folds);
            System.gc();
        }
    }

    public void evaluationOfRandomUsers(int fromUser, int toUser, int folds) throws IOException {
        A_Start a = new A_Start();
        a.loadFiles_exceptUserId(0, A_Start.ratingsDataFile); //It is loaded in order to be taken the ratings per user
        for (int u = fromUser; u < toUser + 1; u++) {
            if (A_Start.ratingsPerUser_hashTable.get(u).getNumOfRatings() > 500 && u != 438 && u != 482 && u != 2030) {
                evaluationPerUser(u, folds);
            }
            System.gc();
        }
    }

    public void evaluationOfaRangeOfUsers(int fromUser, int toUser, int folds) throws IOException {
        for (int u = fromUser; u < toUser + 1; u++) {
            evaluationPerUser(u, folds);
        }
    }

    //It adds the evaluation measures in evaluationResults_list for the specific user 
    public void evaluationPerUser(int userId, int k_folds) throws IOException {
        try {
            List<double[]> precisionPerPackage_listOfFolds = new ArrayList<>();
            List<double[]> modPrecisionPerPackage_listOfFolds = new ArrayList<>();
            List<double[]> ratingsPerPackage_listOfFolds = new ArrayList<>();
            List<double[]> costPerPackage_listOfFolds = new ArrayList<>();
            List<double[]> runningTime_listOfFolds = new ArrayList<>();
            String ratings_file = A_Start.ratingsDataFile;
            A_Start.selectedUserID = userId;
            A_Start a_start = new A_Start();
            a_start.loadFiles_exceptUserId(userId, ratings_file);
            for (int f = 1; f < k_folds + 1; f++) {
                String userTrainingRatings_file = A_Start.trainingAndTestingRatings_DataPath + "" + userId + "_training_" + f + ".dat";
                String userTestingRatings_file = A_Start.trainingAndTestingRatings_DataPath + "" + userId + "_testing_" + f + ".dat";
                String userCFDatafile_file = A_Start.ratingsCFDataPath + "" + userId + "_recom_" + f + ".dat";
                a_start.loadUserRatings(userId, userTrainingRatings_file);
                //this.printRatings();
                //this.printItems();

                //B_RanningMode:
                ///////////////////////////////
                System.out.println("B_RunningMode:");
                B_RunningMode b = new B_RunningMode();
                B_RunningMode.runningMode = 1; //based on predicted items
                b.applyRunningMode(userId, userTestingRatings_file, userCFDatafile_file);

                //
                //b.printRatings(1, userId);
                //b.precisionAndRecallOfRecommendedItems();//it prints the measueres
                //b.printActualRatings(0, userId);
                //b.printPredictedRatings(0);
                //b.printHiddenRatings(0);
                //C_ProblemModening:
                //////////////////
                System.out.println("\nC_ProblemModening:");
                C_ProblemModeling c = new C_ProblemModeling();
                c.loadGraphOfTopCategories(userId);
                //
                //c.print_NodesOfTopCategoriesGraph();

                //GreedyRecommendations:
                System.out.println("\nGreedyRecommendations:");
                GreedyRecommendations g = new GreedyRecommendations();
                //creating packages and measuring the running time
                double[] runningTime = g.createPackages();
                runningTime_listOfFolds.add(runningTime);
                //
                //g.printSolution();

                System.out.println("user: " + userId);

                RatingsCost_OfPackage ratingsCostPerPackage = g.RatingsAndCostPerPackage();
                double[] ratingsPerPackage = ratingsCostPerPackage.getPackage_ratings();
                ratingsPerPackage_listOfFolds.add(ratingsPerPackage);
                double[] costPerPackage = ratingsCostPerPackage.getPackage_cost();
                costPerPackage_listOfFolds.add(costPerPackage);

                //F_Evaluation:
                System.out.println("Evaluation");
                //F_Evaluation eval = new F_Evaluation();
                Measures_v2 measures = getMeasures(minRatingForModifiedPrecision);
                double[] precisionPerPackage = precisionPerPackage(measures, C_ProblemModeling.numOfPackages);
                precisionPerPackage_listOfFolds.add(precisionPerPackage); //list for all the folds
                double[] modPrecisionPerPackage = modPrecisionPerPackage(measures, C_ProblemModeling.numOfPackages);
                modPrecisionPerPackage_listOfFolds.add(modPrecisionPerPackage); //list for all the folds

                /////////////////////////
                // for (int pr = 0; pr < precisionPerPackage.length; pr++) {
                //     System.out.println("fold:" + f + " pack: " + (pr + 1) + " prec: " + precisionPerPackage[pr]);
                // }
                // System.out.println("avgPrec: " + avgPrecision);
                //////////////////////////
                //eval.prinPrecisionPerPackageAndAvgPrecOfPackage(precisionPerPackage, C_ProblemModeling.numOfPackages);
                //printAvgRatingsAndPrecisionPerPackage(avgRatingsPerPackage, precisionPerPackage, avgPrecision, C_ProblemModeling.numOfPackages);
            }
            double[] avgPrecisionPerPackage = avgArray(precisionPerPackage_listOfFolds);
            double[] avgModPrecisionPerPackage = avgArray(modPrecisionPerPackage_listOfFolds);
            double[] avg_RatingPerPackage_ofAllFolds = avgArray(ratingsPerPackage_listOfFolds);
            double[] avg_CostPerPackage_ofAllFolds = avgArray(costPerPackage_listOfFolds);
            double[] avg_runningTime_ofAllFolds = avgArray(runningTime_listOfFolds);
            //double[] avg_CostPerPackage_ofAllFolds = avgArray(costPerPackage_listOfFolds);
            //double avgPrecision = avgPrecisionsPerUser(avgPrecision_listOfFolds);

            EvaluationResultsType ert = new EvaluationResultsType(userId,
                    avgPrecisionPerPackage, avgModPrecisionPerPackage, avg_RatingPerPackage_ofAllFolds,
                    avg_CostPerPackage_ofAllFolds, avg_runningTime_ofAllFolds);
            evaluationResults_list.add(ert);

            /////////////////////////
            //  for (int pr = 0; pr < avgPrecisionPerPackage.length; pr++) {
            //     System.out.println("pack: " + (pr + 1) + " foldsAvgPrec: " + avgPrecisionPerPackage[pr]);
            // }
            // System.out.println("avgPrec: " + avgPrecision);
            //////////////////////////
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in user: " + userId);
            return;
        }

    }

    //
    public void avgEvaluationResults(List<EvaluationResultsType> evaluationResults_list) {
        try {
            int size = evaluationResults_list.size();
            int packages = evaluationResults_list.get(0).getPrecisionPerPackage().length;
            double[] precision_avg = new double[packages];
            double[] modPrecision_avg = new double[packages];
            double[] ratings_avg = new double[packages];
            double[] cost_avg = new double[packages];
            double[] runningTime_avg = new double[packages];
            for (int u = 0; u < size; u++) {
                EvaluationResultsType er = evaluationResults_list.get(u);
                double[] precisionPerPack = er.getPrecisionPerPackage();
                double[] modPrecisionPerPack = er.getModPrecisionPerPackage();
                double[] ratingsPerPack = er.getRatingsPerPackage();
                double[] costPerPack = er.getCostPerPackage();
                double[] runningTime = er.getRunningTime();

                if (u == 0) {
                    for (int p = 0; p < packages; p++) {
                        precision_avg[p] = precisionPerPack[p];
                        modPrecision_avg[p] = modPrecisionPerPack[p];
                        ratings_avg[p] = ratingsPerPack[p];
                        cost_avg[p] = costPerPack[p];
                        runningTime_avg[p] = runningTime[p];
                    }
                    

                } else {
                    for (int p = 0; p < packages; p++) {
                        precision_avg[p] += precisionPerPack[p];
                        modPrecision_avg[p] += modPrecisionPerPack[p];
                        ratings_avg[p] += ratingsPerPack[p];
                        cost_avg[p] += costPerPack[p];
                        runningTime_avg[p] += runningTime[p];
                    }
                }
            }
            for (int p = 0; p < packages; p++) {
                precision_avg[p] = precision_avg[p] / (double) size;
                modPrecision_avg[p] = modPrecision_avg[p] / (double) size;
                ratings_avg[p] = ratings_avg[p] / (double) size;
                cost_avg[p] = cost_avg[p] / (double) size;
                 runningTime_avg[p] = runningTime_avg[p] / (double)size;
            }
           
            AvgEvaluationResultsType aer
                    = new AvgEvaluationResultsType(precision_avg, modPrecision_avg, ratings_avg, cost_avg, runningTime_avg);
            Avg_evaluationResults = aer;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    //
    public void printAvgEvaluationResults(AvgEvaluationResultsType avg_evaluationResults) {
        try {
            System.out.println("\nAverage results of all users:");
            String[] row = {"PackNo", "Precision", "ModPrecision", "Ratings", "Cost", "RunningTime"};
            System.out.format("%-15s%-16s%-15s%-15s%-15s%-15s\n", row);
            double[] precisionPerPack = avg_evaluationResults.getPrecisionPerPackage();
            double[] modPrecisionPerPack = avg_evaluationResults.getModPrecisionPerPackage();
            double[] ratingsPerPack = avg_evaluationResults.getRatingsPerPackage();
            double[] costPerPack = avg_evaluationResults.getCostPerPackage();
            double[] runningTime = avg_evaluationResults.getRunningTime();
            for (int p = 0; p < avg_evaluationResults.getPrecisionPerPackage().length; p++) {
                String[] row1 = {(p + 1) + "",
                    round(precisionPerPack[p], 3) + "",
                    round(modPrecisionPerPack[p], 3) + "",
                    round(ratingsPerPack[p], 2) + "",
                    round(costPerPack[p], 2) + "",
                    round((runningTime[p]/1000000.0),3) + ""};
                System.out.format("%-15s%-16s%-15s%-15s%-15s%-15s\n", row1);
            }
            String[] row2 = {"Avg:",
                round(avg_evaluationResults.getAvg_precision(), 3) + "",
                round(avg_evaluationResults.getAvg_modPrecision(), 3) + "",
                round(avg_evaluationResults.getAvg_ratings(), 2) + "",
                round(avg_evaluationResults.getAvg_cost(), 2) + "",
                round((avg_evaluationResults.getAvg_runningTime()/1000000.0),3) + " msec"};
            System.out.format("%-15s%-16s%-15s%-15s%-15s%-15s\n", row2);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    //
    public void printEvaluationResults(List<EvaluationResultsType> evaluationResults_list, double minRatingOfModPrecision) {
        try {
            int size = evaluationResults_list.size();
            for (int u = 0; u < size; u++) {
                EvaluationResultsType er = evaluationResults_list.get(u);
                System.out.println("\nuserId: " + er.getUserId());
                String[] row = {"pack no", "precision", "ModPrecision", "Ratings", "Cost", "runningTime"};
                System.out.format("%-15s%-16s%-15s%-15s%-15s%-15s\n", row);
                double[] precisionPerPack = er.getPrecisionPerPackage();
                double[] modPrecisionPerPack = er.getModPrecisionPerPackage();
                double[] ratingsPerPack = er.getRatingsPerPackage();
                double[] costPerPack = er.getCostPerPackage();
                double[] runningTime = er.getRunningTime();
                for (int p = 0; p < er.getPrecisionPerPackage().length; p++) {
                    String[] row1 = {(p + 1) + "",
                        round(precisionPerPack[p], 3) + "",
                        round(modPrecisionPerPack[p], 3) + "",
                        round(ratingsPerPack[p], 2) + "",
                        round(costPerPack[p], 2) + "",
                        round((runningTime[p]/1000000.0),3) + ""};
                    System.out.format("%-15s%-16s%-15s%-15s%-15s%-15s\n", row1);
                }
                String[] row2 = {"Avg:",
                    round(er.getAvg_precision(), 3) + "",
                    round(er.getAvg_modPrecision(), 3) + "",
                    round(er.getAvg_ratings(), 2) + "",
                    round(er.getAvg_cost(), 2) + "",
                    round((er.getAvg_runningTime()/1000000.0),3) + " msec"};
                System.out.format("%-15s%-16s%-15s%-15s%-15s%-15s\n", row2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

//avg precision per user of all the packages and all the folds
    public long avgLongList(List<Long> array_list) {
        int folds = array_list.size();
        long avg = 0;
        for (int f = 0; f < folds; f++) { //for each fold
            avg = (f * avg + array_list.get(f)) / (f + 1);
        }
        return avg;
    }

    //avg precision per package of all the folds
    public double[] avgArray(List<double[]> array_list) {
        int folds = array_list.size();
        int packages = array_list.get(0).length;
        double[] Avg = new double[packages];
        for (int p = 0; p < packages; p++) {
            Avg[p] = 0.0;
        }
        for (int f = 0; f < folds; f++) { //for each fold
            double[] current_fold = array_list.get(f);
            for (int p = 0; p < packages; p++) {
                Avg[p] = ((double) (f) * Avg[p] + current_fold[p]) / (double) (f + 1);
            }
        }
        return Avg;
    }

    public Measures_v2 getMeasures(double minRatingOfModPrecision) {
        //int numOfPackages = ProblemSolutionNPackagesForm.packages_hashTable.size();
        //int len = B_RunningMode.hiddenItemRatings_list.size();
        int itemsPerPackage; //PoblemModelingForm.numOfItemsPerCategory*ProblemModelingForm.numOfTopCategories;
        if (C_ProblemModeling.numOfItemsPerCategory == 0) { //Proportional allocation of items to categories
            itemsPerPackage = D_ItemsAllocationToCategories.numOfItemsPerPackage;
            System.out.println("itemsPerPackage= " + itemsPerPackage);
        } else {
            itemsPerPackage = C_ProblemModeling.numOfItemsPerCategory * C_ProblemModeling.numOfTopCategories;
        }

        int ItemsPerPackages[][] = new int[itemsPerPackage * C_ProblemModeling.numOfPackages][itemsPerPackage];
        Integer ItemsOfAllPackages[] = new Integer[itemsPerPackage * C_ProblemModeling.numOfPackages];

        // int TP=0; //True Positive
        int countItemsOfAllPackages = 0;
        for (int k = 0; k < C_ProblemModeling.numOfPackages; k++) {
            ////////////////
            //System.out.println("k:" + k);
            //Hashtable<String, List<MovieForPackage>> category_movieForPackage_hashTable = new Hashtable<>();
            Hashtable<String, List<ItemForPackage_v2>> category_movieForPackage_hashTable = GreedyRecommendations.packages_hashTable.get(k + 1);

            Enumeration<String> categoryKeys = category_movieForPackage_hashTable.keys();
            int countItemsPerPackage = 0; //item number
            while (categoryKeys.hasMoreElements()) {
                //List<MovieForPackage> movieForPackage_list = new ArrayList<>();
                String category = categoryKeys.nextElement();
                List<ItemForPackage_v2> movieForPackage_list = category_movieForPackage_hashTable.get(category);
                for (int i = 0; i < movieForPackage_list.size(); i++) {
                    ItemsPerPackages[k][countItemsPerPackage] = movieForPackage_list.get(i).getItemID();
                    // ItemsPerPackages[k][countItemsPerPackage][1] = 0;
                    countItemsPerPackage++;
                    ItemsOfAllPackages[countItemsOfAllPackages] = movieForPackage_list.get(i).getItemID();
                    countItemsOfAllPackages++;
                    // ((DefaultTableModel) tb_evaluationResults.getModel()).addRow(new Object[]{countItemsPerPackage, category, movieID, title, round(rating, 2)});
                }
            }
        }
        Measures_v2 measures = measuresCalc(itemsPerPackage, C_ProblemModeling.numOfPackages, ItemsPerPackages, ItemsOfAllPackages, minRatingOfModPrecision);
        return measures;
    }

    public void computeAndPrintMeasures(double minRatingOfModPrecision) {
        //int numOfPackages = ProblemSolutionNPackagesForm.packages_hashTable.size();
        //int len = B_RunningMode.hiddenItemRatings_list.size();
        int itemsPerPackage; //PoblemModelingForm.numOfItemsPerCategory*ProblemModelingForm.numOfTopCategories;
        if (C_ProblemModeling.numOfItemsPerCategory == 0) { //Proportional allocation of items to categories
            itemsPerPackage = D_ItemsAllocationToCategories.numOfItemsPerPackage;
            System.out.println("itemsPerPackage= " + itemsPerPackage);
        } else {
            itemsPerPackage = C_ProblemModeling.numOfItemsPerCategory * C_ProblemModeling.numOfTopCategories;
        }

        int ItemsPerPackages[][] = new int[itemsPerPackage * C_ProblemModeling.numOfPackages][itemsPerPackage];
        Integer ItemsOfAllPackages[] = new Integer[itemsPerPackage * C_ProblemModeling.numOfPackages];

        // int TP=0; //True Positive
        int countItemsOfAllPackages = 0;
        for (int k = 0; k < C_ProblemModeling.numOfPackages; k++) {

            //Hashtable<String, List<MovieForPackage>> category_movieForPackage_hashTable = new Hashtable<>();
            Hashtable<String, List<ItemForPackage_v2>> category_movieForPackage_hashTable = packages_hashTable.get(k + 1);

            Enumeration<String> categoryKeys = category_movieForPackage_hashTable.keys();
            int countItemsPerPackage = 0; //item number
            while (categoryKeys.hasMoreElements()) {
                //List<MovieForPackage> movieForPackage_list = new ArrayList<>();
                String category = categoryKeys.nextElement();
                List<ItemForPackage_v2> movieForPackage_list = category_movieForPackage_hashTable.get(category);
                for (int i = 0; i < movieForPackage_list.size(); i++) {
                    ItemsPerPackages[k][countItemsPerPackage] = movieForPackage_list.get(i).getItemID();
                    // ItemsPerPackages[k][countItemsPerPackage][1] = 0;
                    countItemsPerPackage++;
                    ItemsOfAllPackages[countItemsOfAllPackages] = movieForPackage_list.get(i).getItemID();
                    countItemsOfAllPackages++;
                    // ((DefaultTableModel) tb_evaluationResults.getModel()).addRow(new Object[]{countItemsPerPackage, category, movieID, title, round(rating, 2)});
                }
            }
        }
        Measures_v2 measures = measuresCalc(itemsPerPackage, C_ProblemModeling.numOfPackages, ItemsPerPackages, ItemsOfAllPackages, minRatingOfModPrecision);
        printMeasures(measures, C_ProblemModeling.numOfPackages);
        //return 0;
    }

    public Measures_v2 measuresCalc(int itemsPerPackage, int numberOfPackages,
            int ItemsPerPackages[][], Integer ItemsOfAllPackages[], double minRatingOfModPrecision) {
        //TP: correct selected items
        //FN: Not correct not selected items
        //FP: Not correct selected items
        //precission = correct retrieved items/retrieved items
        //recall = correct retrieved items/correct tiems

        Integer distinctItemsOfAllPackages[] = removeDuplicates(ItemsOfAllPackages);
        int TP_perPackage[] = new int[numberOfPackages];
        int TP_perPackage_mod[] = new int[numberOfPackages];
        int FN_perPackage[] = new int[numberOfPackages];
        int FP_perPackage[] = new int[numberOfPackages];
        int FP_perPackage_mod[] = new int[numberOfPackages];
        //int TN_perPackage[] = new int[numberOfPackages];
        for (int i = 0; i < numberOfPackages; i++) {
            TP_perPackage[i] = 0;
            TP_perPackage_mod[i] = 0;
            FN_perPackage[i] = 0;
            FP_perPackage[i] = 0;
            FP_perPackage_mod[i] = 0;
            //TN_perPackage[i] = 0;
        }
        int TP_overall = 0;
        int FN_overall = 0;
        int FP_overall = 0;
        //int TN_overall = 0;

        //for each hidden item
        for (int i = 0; i < B_RunningMode.hiddenItemRatings_list.size(); i++) {
            //computing the TP and FN per package
            for (int k = 0; k < numberOfPackages; k++) {
                int FN_flag = 1;
                for (int j = 0; j < itemsPerPackage; j++) {
                    if (B_RunningMode.hiddenItemRatings_list.get(i).getItemID() == ItemsPerPackages[k][j]) {
                        TP_perPackage[k]++;
                        if (B_RunningMode.hiddenItemRatings_list.get(i).getRating() > (minRatingOfModPrecision - 0.0001)) {
                            TP_perPackage_mod[k]++;
                        }
                        FN_flag = 0;
                        break;
                    }
                }
                FN_perPackage[k] += FN_flag;
            }

            //Computing the TP and FN for overall packages.
            int len = distinctItemsOfAllPackages.length;
            int FNoverall_flag = 1;
            for (int j = 0; j < len; j++) {
                if (B_RunningMode.hiddenItemRatings_list.get(i).getItemID() == distinctItemsOfAllPackages[j]) {
                    TP_overall++;
                    FNoverall_flag = 0;
                    break;
                }
            }
            FN_overall += FNoverall_flag;
        }

        //for each package - computing the FP
        for (int k = 0; k < numberOfPackages; k++) {
            for (int j = 0; j < itemsPerPackage; j++) {
                int FP_flag = 1;
                int FP_flag_mod = 1;
                for (int i = 0; i < B_RunningMode.hiddenItemRatings_list.size(); i++) {
                    if (B_RunningMode.hiddenItemRatings_list.get(i).getItemID() == ItemsPerPackages[k][j]) {
                        FP_flag = 0;
                        if (B_RunningMode.hiddenItemRatings_list.get(i).getRating() > (minRatingOfModPrecision - 0.0001)) {
                            FP_flag_mod = 0;
                        }
                        break;
                    }
                }
                FP_perPackage[k] += FP_flag;
                FP_perPackage_mod[k] += FP_flag_mod;
            }
        }
        //computing the FP for overall packages
        int len = distinctItemsOfAllPackages.length;
        for (int j = 0; j < len; j++) {
            int FPoverall_flag = 1;
            for (int i = 0; i < B_RunningMode.hiddenItemRatings_list.size(); i++) {
                if (B_RunningMode.hiddenItemRatings_list.get(i).getItemID() == distinctItemsOfAllPackages[j]) {
                    FPoverall_flag = 0;
                    break;
                }
            }
            FP_overall += FPoverall_flag;
        }
        double[] precision_perPackage = new double[numberOfPackages];
        double[] mod_Precision_perPackage = new double[numberOfPackages];
        double[] recall_perPackage = new double[numberOfPackages];
        double precision_overall = (double) ((double) TP_overall / (double) (TP_overall + FP_overall));
        double recall_overall = (double) ((double) TP_overall / (double) (TP_overall + FN_overall));
        for (int k = 0; k < numberOfPackages; k++) {
            precision_perPackage[k] = (double) ((double) TP_perPackage[k] / (double) (TP_perPackage[k] + FP_perPackage[k]));
            mod_Precision_perPackage[k] = (double) ((double) TP_perPackage_mod[k] / (double) (TP_perPackage_mod[k] + FP_perPackage_mod[k]));
            recall_perPackage[k] = (double) ((double) TP_perPackage[k] / (double) (TP_perPackage[k] + FN_perPackage[k]));
        }
        Measures_v2 measures = new Measures_v2(TP_perPackage, FN_perPackage, FP_perPackage, TP_overall, FN_overall, FP_overall, precision_perPackage, mod_Precision_perPackage, recall_perPackage, precision_overall, recall_overall);
        return measures;
    }

    //
    public double[] precisionPerPackage(Measures_v2 measures, int numOfPackages) {
        double[] precisionPerPackage = new double[numOfPackages];
        for (int i = 0; i < numOfPackages; i++) {
            precisionPerPackage[i] = measures.getPrecision_perPackage()[i];
        }
        return precisionPerPackage;
    }

    //
    public double[] modPrecisionPerPackage(Measures_v2 measures, int numOfPackages) {
        double[] modPrecisionPerPackage = new double[numOfPackages];
        for (int i = 0; i < numOfPackages; i++) {
            modPrecisionPerPackage[i] = measures.getMod_precision_perPackage()[i];
        }
        return modPrecisionPerPackage;
    }

    //
    public void prinPrecisionPerPackageAndAvgPrecOfPackage(double[] precisionPerPackage, int numOfPackages) {
        for (int i = 0; i < numOfPackages; i++) {
            System.out.println("pack: " + (i + 1) + " precision:" + precisionPerPackage[i]);
        }
        System.out.println("avgPrecision: " + avgPrecision(precisionPerPackage, numOfPackages));
    }

    public double avgPrecision(double[] precisionPerPackage, int numOfPackages) {
        double avgPrecision = 0.0;
        for (int i = 0; i < numOfPackages; i++) {
            avgPrecision += precisionPerPackage[i];
        }
        return avgPrecision / numOfPackages;
    }

    //it prints measuers in the table
    public void printMeasures(Measures_v2 measures, int numOfPackages) {
        double avg_precition = 0.0;
        double avg_recall = 0.0;
        //print measures per package
        String[] heading = {"Package no", "TP", "FP", "FN", "Precision", "Recall"};
        System.out.format("\n%12s%10s%10s%10s%10s%10s\n", heading);
        for (int i = 0; i < numOfPackages; i++) {
            String[] row = {(i + 1) + "", measures.getTP_perPackage()[i] + "", measures.getFP_perPackage()[i] + "",
                measures.getFN_perPackage()[i] + "", round(measures.getPrecision_perPackage()[i], 3) + "", round(measures.getRecall_perPackage()[i], 3) + ""};
            System.out.format("\n%12s%10s%10s%10s%10s%10s\n", row);
            avg_precition += measures.getPrecision_perPackage()[i];
            avg_recall += measures.getRecall_perPackage()[i];
        }

        avg_precition = avg_precition / (double) numOfPackages;
        avg_recall = avg_recall / (double) numOfPackages;

        //print measurs for the overall packages
        System.out.println("\nAverage precition: " + round(avg_precition, 3) + "  Average recall: " + round(avg_recall, 3));
        //print measurs for the overall packages
        System.out.println("\nOverall: TP: " + measures.getTP_overall()
                + " FP:" + measures.getFP_overall()
                + " FN:" + measures.getFN_overall()
                + " Precision:" + round(measures.getPrecision_overall(), 3)
                + " Recall:" + round(measures.getRecall_overall(), 3));
    }

    //it removes duplicates from an Integer array
    public static Integer[] removeDuplicates(Integer[] array) {
        return new HashSet<Integer>(Arrays.asList(array)).toArray(new Integer[0]);
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
