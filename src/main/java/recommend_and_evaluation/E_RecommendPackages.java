/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommend_and_evaluation;

import entityBasicClasses.ItemForPackage_v2;
import entityBasicClasses.RatingsCost_OfPackage;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import linearProgrammingClasses.LinearProgramming_v2_int;
import linearProgrammingClasses.SolutionType_v2;
import static recommend_and_evaluation.C_ProblemModeling.vertexItemOfTopCategories_list;
import static recommend_and_evaluation.C_ProblemModeling.vertexTopCategory_list;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class E_RecommendPackages {

    SolutionType_v2 solutionOfProblem = new SolutionType_v2();
    long overallRunningTime = 0;

    //HashTable with number of package and the list of movies
    static public Hashtable<Integer, Hashtable<String, List<ItemForPackage_v2>>> packages_hashTable = new Hashtable<>();

    public E_RecommendPackages() {
        //overallRunningTime = 0;
    }

    //Load packages in packages_hashTable. Key is the package number, it returns the running time.
    public double[] createPackages() {
        double[] runningTime = null;
        try {
            
            int numOfPackages = C_ProblemModeling.numOfPackages;
            int numOfCategories = C_ProblemModeling.numOfTopCategories;
            runningTime = new double[numOfPackages];
            //int numOfItemsPerCategory = C_ProblemModeling.numOfItemsPerCategory;
            //modelOfPackage = 1: remove the edge with maximum rating
            //modelOfPackage = 2: remove the edge with minimum rating
            //modelOfPackage = 3: remove the 50% of Edges with upper ratings
            //modelOfPackage = 4: remove the 50% of Edges with lower ratings
            //modelOfPackage = 5: remove all (100%) the Edges
            //modelOfPackage = 6: remove all (100%) movie nodes in order to produce distinct packages
            int modelOfCreatingPackages = C_ProblemModeling.modelOfCreatingPackages;
            if (C_ProblemModeling.numOfMaxAppearancesPerItem > 0) { //initialization of num of max appearances per item
                for (int i = 0; i < C_ProblemModeling.vertexItemOfTopCategories_list.size(); i++) {
                    vertexItemOfTopCategories_list.get(i).setNumOfMaxAppearances(C_ProblemModeling.numOfMaxAppearancesPerItem);
                }
            }
            //the hashTable of the solution with movies. 
            this.packages_hashTable.clear(); //The key of the hashTable is the number of package
            //for each package run min cost flow algorithm and create packages
            overallRunningTime = 0;
            for (int p = 0; p < numOfPackages; p++) { //package number = p+1
                long startTime = System.currentTimeMillis();
                try {
                    if (runMinCostFlow(p, numOfCategories) == 0) {
                        //runningTime[p] = overallRunningTime;
                        //C_ProblemModeling.numOfPackages = p;
                        break;
                    }
                    runningTime[p] = overallRunningTime + (System.currentTimeMillis() - startTime);
                    overallRunningTime = (long) runningTime[p];
                    
                } catch (Exception e) {
                    //C_ProblemModeling.numOfPackages = p;
                    System.out.println(overallRunningTime + " msec");
                    System.out.println("\n Exception: " + e);
                    return runningTime;
                }
                //if the modelOfCreatingPackages == 1 or 2, the maximum or minimum rating edge will be removed from the graph
                double ratingOfRemovingEdge = 0.0;
                if (modelOfCreatingPackages == 2) {//finding the mimimum ratiting of edges
                    ratingOfRemovingEdge = 1000.0;
                }
                int movieIDOfRemovingEdge = 0;
                String categoryOfRemovingEdge = ""; //The category of best movie edge
                int numOfProbability = 2; //probability = 1/numOfProbability.  Probability of removing or remaing an edge of the graph
                Random random = new Random(); //it uses in order to deside for remaining or removing of an edge

                //HashTable of <category, List<MovieForPackage>
                Hashtable<String, List<ItemForPackage_v2>> category_movieForPackage_hashTable = new Hashtable<>();
                int len = solutionOfProblem.getNamesOfEdges().length;

                List<ItemForPackage_v2> moviesForRemovingEdges_list = new ArrayList<>();

                //put solution in category_movieForPackage_hashTable
                for (int i = 0; i < len; i++) {
                    String[] part = solutionOfProblem.getNamesOfEdges()[i].split("_");
                    if (solutionOfProblem.getFlowOfEdges()[i] > 0.0
                            && !part[0].equals("source") && !part[0].equals("sink")
                            && !part[1].equals("source") && !part[1].equals("sink")) {
                        int movieID = Integer.valueOf(part[0]);
                        String category = part[1];
                        double cost = solutionOfProblem.getFlow_costOfEdges()[i];
                        double rating = 1.0 / cost;
                        double item_cost = solutionOfProblem.getItem_costOfEdges()[i];
                        //remaining the removing edges    
                        if (modelOfCreatingPackages == 1) { //remove the maximum rating edge   
                            //finding the best Rating
                            //The remaing movie has the greatest rating
                            //if two or more movies have the same rating then the probability to remove one edge is the same for each edge 
                            if (rating > ratingOfRemovingEdge) {
                                ratingOfRemovingEdge = rating;
                                movieIDOfRemovingEdge = movieID;
                                categoryOfRemovingEdge = category;
                                numOfProbability = 2;
                            } else if (rating == ratingOfRemovingEdge) {
                                if (random.nextInt(numOfProbability) == 0) {
                                    ratingOfRemovingEdge = rating;
                                    movieIDOfRemovingEdge = movieID;
                                    categoryOfRemovingEdge = category;
                                    numOfProbability++;
                                    ///////////
                                    // System.out.println(" random: " + random.nextInt(numOfProbability) + " numOfProbability: " + numOfProbability);
                                    /////////
                                }
                            }
                        } else if (modelOfCreatingPackages == 2) { //remove the minimum rating edge   
                            //finding the edge with the minimum rating
                            //The remaing movie has the lowest rating
                            //if two or more movies have the same rating then the probability to remove one edge is the same for each edge 
                            if (rating < ratingOfRemovingEdge) {
                                ratingOfRemovingEdge = rating;
                                movieIDOfRemovingEdge = movieID;
                                categoryOfRemovingEdge = category;
                                numOfProbability = 2;
                            } else if (rating == ratingOfRemovingEdge) {
                                if (random.nextInt(numOfProbability) == 0) {
                                    ratingOfRemovingEdge = rating;
                                    movieIDOfRemovingEdge = movieID;
                                    categoryOfRemovingEdge = category;
                                    numOfProbability++;
                                    ///////////
                                    // System.out.println(" random: " + random.nextInt(numOfProbability) + " numOfProbability: " + numOfProbability);
                                    /////////
                                }
                            }
                        } else { //creating the list in order to remove the edges of the graph. For removing 50% and 100% of edges
                            int numOfMaxAppear = 1;
                            for (int s = 0; s < vertexItemOfTopCategories_list.size(); s++) { //find number of max appearances for this movie
                                if (movieID == vertexItemOfTopCategories_list.get(s).getItemID()) {
                                    numOfMaxAppear = vertexItemOfTopCategories_list.get(s).getNumOfMaxAppearances();
                                    break;
                                }

                            }
                            moviesForRemovingEdges_list.add(new ItemForPackage_v2(movieID, numOfMaxAppear, category, "", rating, item_cost));
                            /////////////////////////////////
                            //System.out.println(moviesForRemovingEdges_list.get(0).getMovieID());
                        }
                        String title = A_Start.movies_hashTable.get(movieID).getTitle();
                        int maxOfAppearances = A_Start.movies_hashTable.get(movieID).getNumOfMaxAppearances();
                        List<ItemForPackage_v2> movieForPackage_list = new ArrayList<>();
                        if (category_movieForPackage_hashTable.get(category) != null) {
                            movieForPackage_list = category_movieForPackage_hashTable.get(category);
                        }
                        ItemForPackage_v2 movieForPackage = new ItemForPackage_v2(movieID, maxOfAppearances, category, title, rating, item_cost);
                        movieForPackage_list.add(movieForPackage);
                        if (movieForPackage_list.size() > 1) {
                            //sorting based on ratings                 
                            Collections.sort(movieForPackage_list, new Comparator<ItemForPackage_v2>() {
                                @Override
                                public int compare(ItemForPackage_v2 mfp1, ItemForPackage_v2 mfp2) {
                                    return (int) (mfp2.getRating() * 100000.0 - mfp1.getRating() * 100000.0);
                                }
                            });
                        }
                        category_movieForPackage_hashTable.put(category, movieForPackage_list);
                    }
                }
                ///////////////
                // System.out.println();
                // System.out.println(" bestRatingMovieID: " + bestRatingMovieID + " bestCategory: " + bestMovieCategory + " bestRating: " + bestRating);
                // System.out.println(" remove Edges: ");
                /////////////////
                this.packages_hashTable.put(p + 1, category_movieForPackage_hashTable); //Key of hashtable is the package Number, 
                //remove the best edge of the graph and run the min cost flow algorithm againt            
                if (p < numOfPackages) { //remove edges in order to re-run the min cost flow algorithm
                    if (modelOfCreatingPackages == 1 || modelOfCreatingPackages == 2) {
                        this.removeOneEdgeOfGraph(movieIDOfRemovingEdge, categoryOfRemovingEdge);
                    } else { //modelOfPackage is 3, 4, 5 or 6
                        removeEdgesOrNodes(modelOfCreatingPackages, moviesForRemovingEdges_list);
                    }
                }

                //if number Of Appearances == Number Of Max appearances then remove the item node
                if (C_ProblemModeling.numOfMaxAppearancesPerItem > -1) {
                    //if (moviesForRemovingEdges_list.get(r).getMaxAppearances() > 1) { //remove one edge only
                    //int sizeOfMovieList = vertexMovieOfTopCategories_list.size();
                    for (int m = 0; m < moviesForRemovingEdges_list.size(); m++) {
                        for (int i = 0; i < vertexItemOfTopCategories_list.size(); i++) {
                            if (vertexItemOfTopCategories_list.get(i).getItemID() == moviesForRemovingEdges_list.get(m).getItemID()) {
                                int numOfAppears = vertexItemOfTopCategories_list.get(i).getNumOfMaxAppearances();
                                if (numOfAppears > 1) {
                                    vertexItemOfTopCategories_list.get(i).setNumOfMaxAppearances((numOfAppears - 1));
                                    break;
                                } else {//remove whole the node
                                    removeOneItemNodeOfGraph(vertexItemOfTopCategories_list.get(i).getItemID());
                                    break;
                                }
                            }
                        }
                    }
                }
            }//close for(packageNubler)
            System.out.println(overallRunningTime + " msec");
            return runningTime;
        } catch (Exception e) {
            e.printStackTrace();
            return runningTime;
        }
    }

    //running Min Cost Flow algorithm by using the CPLEX linear programming system
    //it returns 1 on succeed or 0 on failure (no feasible solution)
    public int runMinCostFlow(int packageNumber, int numOfCategories) {
        try {
            LinearProgramming_v2_int linearProgramming = new LinearProgramming_v2_int();
            solutionOfProblem = null;
           // long startTime = System.currentTimeMillis();
            solutionOfProblem = linearProgramming.linearProgrammingCalc(); //run min cost flow problem
           // long runningTime = System.currentTimeMillis() - startTime;
            if (solutionOfProblem == null && packageNumber == 0) {
                //this.overallRunningTime += runningTime;
                System.out.println("No feasible solution!");
                return 0;
            } else if (solutionOfProblem == null && packageNumber > 0) {
                if (packageNumber == 1) { //the package is one
                    System.out.println("Optimal Solution Found for the best " + packageNumber + " package!");
                } else { //the packages are more one
                    System.out.println("Optimal Solution Found for the best " + packageNumber + " packages!");
                }
                return 0;
            } else if (packageNumber == 0) {
                //this.overallRunningTime += runningTime;
                //printStatisticsAndSolution(packageNumber, numOfCategories, runningTime);
                System.out.println("Optimal Solution Found!");
            } else {
            //    this.overallRunningTime += runningTime;
                //printStatisticsAndSolution(packageNumber, numOfCategories, runningTime);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //remove one item node from the graph
    public void removeOneItemNodeOfGraph(int movieID) {
        int sizeOfMovieList = vertexItemOfTopCategories_list.size();
        int sizeOfCategoryList = vertexTopCategory_list.size();
        //remove edge from movie node
        for (int i = 0; i < sizeOfMovieList; i++) {
            if (movieID == vertexItemOfTopCategories_list.get(i).getItemID()) {
                // if (vertexMovieOfTopCategories_list.get(i).getMovieID() == moviesForRemovingEdges_list.get(r).getMovieID()) {
                vertexItemOfTopCategories_list.remove(i);
                /////////////////////////////
                //System.out.println("remove movie node: " + moviesForRemovingEdges_list.get(r).getMovieID());
                /////////////
                break;
            }
        }
        // remove category Edges
        for (int i = 0; i < sizeOfCategoryList; i++) {
            int len = vertexTopCategory_list.get(i).getEdgeMovieCategory_list().size();
            for (int e = 0; e < len; e++) {
                if (movieID == vertexTopCategory_list.get(i).getEdgeMovieCategory_list().get(e).getItemID()) {
                    vertexTopCategory_list.get(i).getEdgeMovieCategory_list().remove(e);
                    /////////////////////////////
                    //System.out.println("remove category edge for movieID: " + moviesForRemovingEdges_list.get(r).getMovieID());
                    /////////////
                    break;
                }
            }
        }
    }

    //it removes one endge of the graph
    public void removeOneEdgeOfGraph(int movieID, String category) {
        //remove edge from movie node
        int sizeOfMovieList = vertexItemOfTopCategories_list.size();
        for (int i = 0; i < sizeOfMovieList; i++) {
            if (movieID == vertexItemOfTopCategories_list.get(i).getItemID()) {
                int len = vertexItemOfTopCategories_list.get(i).getEdgeItemCategory_list().size();
                for (int j = 0; j < len; j++) { //it finds the endge with movieID to remove it
                    if (category.equals(vertexItemOfTopCategories_list.get(i).getEdgeItemCategory_list().get(j).getCategoryID())) { //it does not add the edge with this categoryID
                        vertexItemOfTopCategories_list.get(i).getEdgeItemCategory_list().remove(j);
                        break;
                    }
                }
            }
            break;
        }
        //remove edge from category node
        int sizeOfCategoryList = vertexTopCategory_list.size();
        for (int i = 0; i < sizeOfCategoryList; i++) {
            if (category.equals(vertexTopCategory_list.get(i).getCategoryID())) {
                int len = vertexTopCategory_list.get(i).getEdgeMovieCategory_list().size();
                for (int j = 0; j < len; j++) { //it finds the ende with movieID to remove it
                    if (movieID == vertexTopCategory_list.get(i).getEdgeMovieCategory_list().get(j).getItemID()) { //it does not add the edge with this movieID
                        vertexTopCategory_list.get(i).getEdgeMovieCategory_list().remove(j);
                        break;
                    }
                }

            }
        }
    }

    //remove edges from the graph in order to re-run the algorithm
    //modelOfPackage = 3: remove the 50% of Edges with upper ratings
    //modelOfPackage = 4: remove the 50% of Edges with lower ratings
    //modelOfPackage = 5: remove all (100%) the Edges
    //modelOfPackage = 6: remove all movie nodes in order to produce distinct packages
    public void removeEdgesOrNodes(int modelOfPackage, List<ItemForPackage_v2> moviesForRemovingEdges_list) {
        if (moviesForRemovingEdges_list.isEmpty()) {
            return;
        }
        if (modelOfPackage == 3) { //modelOfPackage == 3: remove the 50% of Edges with upper ratings
            //sorting moviesForRemovingEdges_list based on rating. Descent sorting               
            Collections.sort(moviesForRemovingEdges_list, new Comparator<ItemForPackage_v2>() {
                public int compare(ItemForPackage_v2 mfp1, ItemForPackage_v2 mfp2) {
                    return (int) (mfp2.getRating() * 100000.0 - mfp1.getRating() * 100000.0);
                }
            });
            int numOfRemovingEdges = (int) round(((double) moviesForRemovingEdges_list.size() / 2.0), 0);
            for (int r = 0; r < numOfRemovingEdges; r++) {
                removeOneEdgeOfGraph(
                        moviesForRemovingEdges_list.get(r).getItemID(),
                        moviesForRemovingEdges_list.get(r).getCategory());
            }
        } else if (modelOfPackage == 4) { //modelOfPackage == 4: remove the 50% of Edges with lower rating
            //sorting moviesForRemovingEdges_list based on rating. Ascent sorting               
            Collections.sort(moviesForRemovingEdges_list, new Comparator<ItemForPackage_v2>() {
                @Override
                public int compare(ItemForPackage_v2 mfp1, ItemForPackage_v2 mfp2) {
                    return (int) (mfp1.getRating() * 100000.0 - mfp2.getRating() * 100000.0);
                }
            });
            int numOfRemovingEdges = (int) round(((double) moviesForRemovingEdges_list.size() / 2.0), 0);
            for (int r = 0; r < numOfRemovingEdges; r++) {
                removeOneEdgeOfGraph(
                        moviesForRemovingEdges_list.get(r).getItemID(),
                        moviesForRemovingEdges_list.get(r).getCategory());
            }
        } else if (modelOfPackage == 5) { //modelOfPackage = 5: remove all (100%) the Edges
            int numOfRemovingEdges = moviesForRemovingEdges_list.size();
            for (int r = 0; r < numOfRemovingEdges; r++) {
                removeOneEdgeOfGraph(
                        moviesForRemovingEdges_list.get(r).getItemID(),
                        moviesForRemovingEdges_list.get(r).getCategory());
            }
        } else if (modelOfPackage == 6) { //modelOfPackage = 6: remove all movie nodes in order to produce distinct packages
            //int sizeOfMovieList = vertexMovieOfTopCategories_list.size();
            //int sizeOfCategoryList = vertexTopCategory_list.size();
            int numOfRemovingEdges = moviesForRemovingEdges_list.size();
            /////////////////////////////
            //System.out.println("len: " + numOfRemovingEdges);
            ///////////////////
            for (int r = 0; r < numOfRemovingEdges; r++) {
                //remove movie nodes
                removeOneItemNodeOfGraph(moviesForRemovingEdges_list.get(r).getItemID());

            }//close --> for (int r = 0; r < numOfRemovingEdges; r++)
        }//close --> else if (modelOfPackage == 6)
        else if (modelOfPackage == 7) { //modelOfPackage = 7: Each node has a number of max appearances

        }//close --> else if (modelOfPackage == 7)      
    }
    
    public void printSolution() {
        try {
            //print the solution
            double overallRating = 0.0;
            double overallPackageCost = 0.0;
            double sumOfRatings = 0.0;
            double packageCost = 0.0;
            System.out.println("\nPackages:");
            System.out.println("countItemsPerPackage, category, movieID, title, rating, itemCost");
            for (int k = 0; k < C_ProblemModeling.numOfPackages; k++) {
                //countPackage++;
                // int numOfPackage = keys.nextElement();
                System.out.println("P" + (k + 1) + ":");

                //Hashtable<String, List<MovieForPackage>> category_movieForPackage_hashTable = new Hashtable<>();
                Hashtable<String, List<ItemForPackage_v2>> category_movieForPackage_hashTable = packages_hashTable.get(k + 1);

                Enumeration<String> categoryKeys = category_movieForPackage_hashTable.keys();
                int countItemsPerPackage = 0; //item number
                while (categoryKeys.hasMoreElements()) {
                    List<ItemForPackage_v2> movieForPackage_list = new ArrayList<>();
                    String category = categoryKeys.nextElement();
                    movieForPackage_list = category_movieForPackage_hashTable.get(category);
                    for (int i = 0; i < movieForPackage_list.size(); i++) {
                        int movieID = movieForPackage_list.get(i).getItemID();
                        double rating = movieForPackage_list.get(i).getRating();
                        double item_cost = movieForPackage_list.get(i).getItem_cost();
                        String title = movieForPackage_list.get(i).getTitle();
                        //String category = movieForPackage_list.get(i).getCategory();
                        countItemsPerPackage++;
                        System.out.println(countItemsPerPackage + ", " + category + ", " + movieID + ", " + title + ", " + round(rating, 2) + ", " + item_cost);
                        sumOfRatings += rating;
                        packageCost += item_cost;
                    }
                }
                System.out.println("\t\tSum Of Ratings: " + round(sumOfRatings, 2));
                System.out.println("\t\tPackage Cost: " + round(packageCost, 2));
                overallRating += sumOfRatings;
                overallPackageCost += packageCost;
                sumOfRatings = 0.0;
                packageCost = 0.0;
            }
            System.out.println("\nOverall Ratings: " + String.valueOf(round(overallRating, 2)));
            System.out.println("\nAverage Package Rating: " + String.valueOf(round((overallRating / C_ProblemModeling.numOfPackages), 2)));
            System.out.println("\nOverall PackageCost: " + String.valueOf(round(overallPackageCost, 2)));
            System.out.println("\nAverage Package Cost: " + String.valueOf(round((overallPackageCost / C_ProblemModeling.numOfPackages), 2)));
////////////
            //System.out.println("num Of Items Per Package = " + forms.ItemsAllocationToCategoriesForm.numOfItemsPerPackage);
            ////////////
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    

    public RatingsCost_OfPackage RatingsAndCostPerPackage() {
        try {
            
            RatingsCost_OfPackage[] ratingsCost_ofPackage = null;
            double[] ratingPerPackage = new double[C_ProblemModeling.numOfPackages];
            double[] costPerPackage = new double[C_ProblemModeling.numOfPackages];
            double sumOfRatings = 0.0;
            double sumOfCost = 0.0;
            for (int k = 0; k < C_ProblemModeling.numOfPackages; k++) {
                Hashtable<String, List<ItemForPackage_v2>> category_movieForPackage_hashTable = packages_hashTable.get(k + 1);
                Enumeration<String> categoryKeys = category_movieForPackage_hashTable.keys();
                //int countItemsPerPackage = 0; //item number
                while (categoryKeys.hasMoreElements()) {
                    List<ItemForPackage_v2> movieForPackage_list = new ArrayList<>();
                    String category = categoryKeys.nextElement();
                    movieForPackage_list = category_movieForPackage_hashTable.get(category);
                    for (int i = 0; i < movieForPackage_list.size(); i++) {
                        double rating = movieForPackage_list.get(i).getRating();
                        double cost = movieForPackage_list.get(i).getItem_cost();
                        //countItemsPerPackage++;
                        sumOfRatings += rating;
                        sumOfCost += cost;
                    }
                }
                ratingPerPackage[k] =  sumOfRatings;
                costPerPackage[k] = sumOfCost;
                
                sumOfRatings = 0.0;
                sumOfCost = 0.0;
            }
             RatingsCost_OfPackage rc= new RatingsCost_OfPackage(ratingPerPackage, costPerPackage);
            return rc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    
    public void printResultsToFile() {
        try {
            File resultFilePath_file = new File(A_Start.ratingsDataFile);
            File file = resultFilePath_file.getParentFile();
            String resultFilePath = file.getAbsolutePath() + "\\results_temp.txt";
            // System.out.println(resultFilePath);
            String nl = System.getProperty("line.separator");
            //System.out.println(nl);

            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(resultFilePath));
            //print the solution
            double overallRating = 0.0;
            double sumOfRatings = 0.0;
            /////////////////
            //String resultText = "no:  category,  movieID,  title,  rating\n";
            bufferWriter.write("no:  category,   itemId,   title,   rating" + nl + " " + nl);
            for (int k = 0; k < C_ProblemModeling.numOfPackages; k++) {
                //countPackage++;
                // int numOfPackage = keys.nextElement();

                //((DefaultTableModel) this.tb_1.getModel()).addRow(new Object[]{"P" + (k + 1) + ":", "", "", "", ""});
                bufferWriter.write("Package " + (k + 1) + ":" + nl);
                ///////////////////////
                //resultText += "Package " + (k + 1) + ":\n";
                //Hashtable<String, List<MovieForPackage>> category_movieForPackage_hashTable = new Hashtable<>();
                Hashtable<String, List<ItemForPackage_v2>> category_movieForPackage_hashTable = packages_hashTable.get(k + 1);

                Enumeration<String> categoryKeys = category_movieForPackage_hashTable.keys();
                int countItemsPerPackage = 0; //item number
                while (categoryKeys.hasMoreElements()) {
                    List<ItemForPackage_v2> movieForPackage_list = new ArrayList<>();
                    String category = categoryKeys.nextElement();
                    movieForPackage_list = category_movieForPackage_hashTable.get(category);
                    for (int i = 0; i < movieForPackage_list.size(); i++) {
                        int movieID = movieForPackage_list.get(i).getItemID();
                        double rating = movieForPackage_list.get(i).getRating();
                        String title = movieForPackage_list.get(i).getTitle();
                        //String category = movieForPackage_list.get(i).getCategory();
                        countItemsPerPackage++;

                        int category_len = category.length();
                        String category_blanks = "";
                        for (int c = 0; c < 12 - category_len; c++) {
                            category_blanks += " ";
                        }
                        int title_len = title.length();
                        String title_blanks = "";
                        for (int c = 0; c < 40 - title_len; c++) {
                            title_blanks += " ";
                        }
                        int movie_len = 5;
                        if (movieID < 10) {
                            movie_len = 1;
                        } else if (movieID < 100) {
                            movie_len = 2;
                        } else if (movieID < 1000) {
                            movie_len = 3;
                        } else if (movieID < 10000) {
                            movie_len = 4;
                        }
                        String movie_blanks = "";
                        for (int c = 0; c < 6 - movie_len; c++) {
                            movie_blanks += " ";
                        }
                        int no_len = 2;
                        if (countItemsPerPackage < 10) {
                            no_len = 1;
                        }
                        String no_blanks = "";
                        for (int c = 0; c < 4 - no_len; c++) {
                            no_blanks += " ";
                        }

                        //((DefaultTableModel) tb_1.getModel()).addRow(new Object[]{countItemsPerPackage, category, movieID, title, round(rating, 2)});
                        /*  bufferWriter.write(countItemsPerPackage + ":" + no_blanks +
                                "" + category + ",\t" + category_blanks +
                                "" + movieID + ",\t"+ movie_blanks +
                                "" + title + ",\t" + title_blanks + 
                                "" + round(rating, 2) + nl);
                         */
                        bufferWriter.write(countItemsPerPackage + ":" + no_blanks + ""
                                + category + ",\t"
                                + movieID + ",\t"
                                + title + ",   "
                                + round(rating, 2) + nl);
                        //resultText += countItemsPerPackage + ": " + category + ", " + movieID + ", " + title + ", " + ", " + round(rating, 2) + "\n";
                        sumOfRatings += rating;
                    }
                }
                //((DefaultTableModel) tb_1.getModel()).addRow(new Object[]{"", "", "", "", "Sum Of Ratings: " + round(sumOfRatings, 2)});
                bufferWriter.write("\t\t\t\tSum Of Ratings: " + round(sumOfRatings, 2) + nl + " " + nl);
                /////////////////
                //resultText += "Sum Of Ratings: " + round(sumOfRatings, 2) + "\n\n";
                overallRating += sumOfRatings;
                sumOfRatings = 0.0;
                //((DefaultTableModel) tb_1.getModel()).addRow(new Object[]{"", "", "", "", ""});
            }
            bufferWriter.write("__________________________________________________________________" + nl);
            bufferWriter.write("\t\t\t\tOverall Rating: " + round(overallRating, 2) + nl);
            bufferWriter.write("\t\t\t\tAverage Rating: " + round((overallRating / C_ProblemModeling.numOfPackages), 2) + nl);
            ////////////////////
            //resultText += "Overall Rating " + round(overallRating, 2) + "\n";
            //resultText += "Average Rating " + round((overallRating/C_ProblemModeling.numOfPackages), 2) + "\n";
            bufferWriter.close();

            File result_file = new File(resultFilePath);

            /*   
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().edit(result_file);
            } else {
                // dunno, up to you to handle this
            }
             */
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                String cmd = "rundll32 url.dll,FileProtocolHandler " + result_file.getCanonicalPath();
                Runtime.getRuntime().exec(cmd);
            } else {
                Desktop.getDesktop().edit(result_file);
            }

            // this.tx_overallRating.setText(String.valueOf(round(overallRating, 2)));
            //this.tx_averageRating.setText(String.valueOf(round((overallRating/C_ProblemModeling.numOfPackages), 2)));
            ////////////
            //System.out.println(resultText);
            ////////////
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void printStatisticsAndSolution(int packageNumber, int numOfCategories, long runningTime) {
        try {
            int len = solutionOfProblem.getNamesOfEdges().length;
            //print statistics
            System.out.println(
                    "packageNumber, Nodes,"
                    + " Edges, Items, numOfCategories, runningTime");
            System.out.println((packageNumber + 1)
                    + ", " + String.valueOf(C_ProblemModeling.vertexItemOfTopCategories_list.size() + numOfCategories + 2)
                    + ", " + String.valueOf(len)
                    + ", " + String.valueOf(C_ProblemModeling.vertexItemOfTopCategories_list.size())
                    + ", " + String.valueOf(numOfCategories)
                    + ", " + runningTime);
            System.out.println("\nCycle " + (packageNumber + 1) + ":");
            //print the general solution
            System.out.println("\nno, NamesOfEdges, FlowOfEdges, flowCostOfEdges, itemCost");
            for (int i = 0; i < len; i++) {
                System.out.println(String.valueOf(i + 1) + ", " + solutionOfProblem.getNamesOfEdges()[i]
                        + ", " + String.valueOf(solutionOfProblem.getFlowOfEdges()[i])
                        + ", " + String.valueOf(round(solutionOfProblem.getFlow_costOfEdges()[i], 2))
                        + ", " + String.valueOf(round(solutionOfProblem.getItem_costOfEdges()[i], 2))); //round of cost in to decimals
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
