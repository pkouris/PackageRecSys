package popularityApproach;

import entityBasicClasses.CategoryItemForPackageList;
import entityBasicClasses.ItemForPackage_v2;
import entityBasicClasses.RatingsCost_OfPackage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import recommend_and_evaluation.A_Start;
import recommend_and_evaluation.B_RunningMode;
import recommend_and_evaluation.C_ProblemModeling;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class PopularityRecommendations {

    //HashTable with number of package and the list of movies
    static public Hashtable<Integer, Hashtable<String, List<ItemForPackage_v2>>> packages_hashTable = new Hashtable<>();
    static Hashtable<String, List<ItemForPackage_v2>> category_moviesForPackageList_hashTable = new Hashtable<>();
    static List<CategoryItemForPackageList> topCategoriesItemsForPackages_list = new ArrayList<>();
    //SolutionType_v2 solutionOfProblem = new SolutionType_v2();
    long overallRunningTime = 0;

    //Load packages in packages_hashTable. Key is the package number, it returns the running time.
    public double[] createPackages() {
        double[] runningTime = null;
        try {
            int numOfPackages = C_ProblemModeling.numOfPackages;
            runningTime = new double[numOfPackages];
            int numOfCategories = C_ProblemModeling.numOfTopCategories;
            //the hashTable of the solution with movies. 
            this.packages_hashTable.clear(); //The key of the hashTable is the number of package
            this.topCategoriesItemsForPackages_list.clear();
            this.category_moviesForPackageList_hashTable.clear();
            /////////////////////
            //System.out.println("numOfPackages:" + numOfPackages);

            overallRunningTime = 0;
            //for each package run min cost flow algorithm and create packages
            for (int p = 1; p < numOfPackages + 1; p++) { //package number = p+1
                //long startTime = System.currentTimeMillis(); 
                long startTime = System.nanoTime();
                Hashtable<String, List<ItemForPackage_v2>> topPackage = new Hashtable<>();
                if (p == 1) {
                    //////////////////////////
                    //System.out.println("topPackage()");
                    topPackage = topPackage(numOfCategories, C_ProblemModeling.packageCost);
                } else {
                    //////////////////////////
                    //System.out.println("nesxtPackage()");
                    topPackage = nextPackage(numOfCategories, C_ProblemModeling.packageCost);

                }
                if (topPackage == null) {
                    //////////////////////////
                    //System.out.println("topPackage == null");
                    //C_ProblemModeling.numOfPackages = p;
                    return runningTime;
                } else {
                    //////////////////////////
                    //System.out.println("topPackage != null");
                    packages_hashTable.put(p, topPackage);

                    //C_ProblemModeling.numOfPackages = p;
                    removeItemWithMinRatioFromTopCategoriesItemList(p); //ratio = rating/cost
                }
                runningTime[p - 1] = overallRunningTime + (System.nanoTime() - startTime);
                overallRunningTime = (long) runningTime[p - 1];
            }

            //System.out.println(overallRunningTime + " msec");
            return runningTime;
        } catch (Exception e) {
            e.printStackTrace();
            return runningTime;
        }
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
                //System.out.println("P" + (k + 1) + ":");

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

    public void removeItemWithMinRatioFromTopCategoriesItemList(int packNo) {
        try {
            double ratio = 999.0;
            int category_deletion_index = 0;
            int itemId_deletion = 0;
            String category_deletion = "";
            Enumeration<Integer> pack_keys = packages_hashTable.keys();
            while (pack_keys.hasMoreElements()) {
                int packageNo = pack_keys.nextElement();
                if (packageNo == packNo) {
                    /////////////
                    //System.out.println("packNo: " + packNo);
                    Hashtable<String, List<ItemForPackage_v2>> cifp_hash = packages_hashTable.get(packNo);
                    Enumeration<String> cat_keys = cifp_hash.keys();
                    while (cat_keys.hasMoreElements()) {
                        String category = cat_keys.nextElement();
                        List<ItemForPackage_v2> temp_list = cifp_hash.get(category);
                        for (int i = 0; i < temp_list.size(); i++) {
                            double newRatio = (temp_list.get(i).getRating() / temp_list.get(i).getItem_cost());
                            if (newRatio < ratio) {
                                ratio = newRatio;
                                itemId_deletion = temp_list.get(i).getItemID();
                                category_deletion = temp_list.get(i).getCategory();
                            }
                        }

                    }
                }
            }
            for (int c = 0; c < topCategoriesItemsForPackages_list.size(); c++) {
                if (category_deletion.equals(topCategoriesItemsForPackages_list.get(c).getCategory())) {
                    category_deletion_index = c;
                    //////////////////////
                    // System.out.println("category_deletion_index:" + category_deletion_index);
                    break;
                }
            }
            List<ItemForPackage_v2> deletion_list = topCategoriesItemsForPackages_list.get(category_deletion_index).getItemForPackage_list();
            for (int i = 0; i < deletion_list.size(); i++) {
                if (deletion_list.get(i).getItemID() == itemId_deletion) {
                    deletion_list.remove(i);
                    CategoryItemForPackageList L = new CategoryItemForPackageList(category_deletion, deletion_list);
                    topCategoriesItemsForPackages_list.set(category_deletion_index, L);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

//
    public Hashtable<String, List<ItemForPackage_v2>> nextPackage(int numOfTopCategories, double packageCost) {
        try {
            Hashtable<String, List<ItemForPackage_v2>> package_hashtable = new Hashtable<>();

            double packCost = 0.0;
            int[] categoryItem_Index = new int[numOfTopCategories];
            for (int c = 0; c < numOfTopCategories; c++) {
                categoryItem_Index[c] = 0;
            }
            for (int c = 0; c < numOfTopCategories; c++) {
                String category = topCategoriesItemsForPackages_list.get(c).getCategory();
                int itemPerCategory = 0;
                while (itemPerCategory < C_ProblemModeling.numOfItemsPerCategory) {
                    //items of category are less than required
                    if (categoryItem_Index[c] > (topCategoriesItemsForPackages_list.get(c).getItemForPackage_list().size() - 1)) {
                        //////////////////////////////////
                        //System.out.println("\nitempercategory>categoryItems  return null:");
                        return null;
                    }
                    ItemForPackage_v2 ifp = topCategoriesItemsForPackages_list.get(c).getItemForPackage_list().get(categoryItem_Index[c]);
                    int itemId = ifp.getItemID();
                    categoryItem_Index[c]++;
                    ////////////////
                    //System.out.println("category:" + c + " categoryItem_Index[c]: " + categoryItem_Index[c]);
                    int exist = itemIdExistence(package_hashtable, itemId);
                    //System.out.println("exist:" + exist);
                    if (exist == 0) { //if item doesn't exist in package
                        //set item for deletion of list

                        packCost += ifp.getItem_cost();
                        if (package_hashtable.get(category) == null) { //if category doesn't exist in package
                            List<ItemForPackage_v2> temp = new ArrayList<>();
                            temp.add(ifp);
                            package_hashtable.put(category, temp);
                            itemPerCategory++;
                        } else {
                            List<ItemForPackage_v2> temp = new ArrayList<>();
                            temp = package_hashtable.get(category);
                            temp.add(ifp);
                            package_hashtable.replace(category, temp);
                            itemPerCategory++;
                        }
                    }
                }
            }

            //////////////
            Enumeration<String> keyts = package_hashtable.keys();
            while (keyts.hasMoreElements()) {
                String cat = keyts.nextElement();
                //System.out.println("Package_hash: " + cat + " " + package_hashtable.get(cat).get(0).getItemID());
            }
            //System.out.println("Package_cost: " + packCost);
            /////////////////////////

             int flag = 0;
            while (flag == 0) {
                int category_deletion_index = 0;
                String category_deletion = "";
                int itemId_deletion = 0;
                double ratio = 999.9; //ratio = item_rating/item_cost
                //if the maximum packageCost is satisfied then the flag change to 1
                if (packCost < packageCost) {
                    flag = 1;
                } else {
                    Enumeration<String> keys = package_hashtable.keys();
                    while (keys.hasMoreElements()) {
                        String cat = keys.nextElement();
                        List<ItemForPackage_v2> list = package_hashtable.get(cat);
                        for (int i = 0; i < list.size(); i++) {
                            double newRatio = list.get(i).getRating() / list.get(i).getItem_cost();
                            /////////////////
                            //System.out.println("newRation" + newRatio);
                            if (newRatio < ratio) {
                                ratio = newRatio;
                                category_deletion = cat;
                                for (int q = 0; q < topCategoriesItemsForPackages_list.size(); q++) {
                                    String temp_category = topCategoriesItemsForPackages_list.get(q).getCategory();
                                    if (cat.equals(temp_category)) {
                                        category_deletion_index = q;
                                        break;
                                    }
                                }
                                itemId_deletion = list.get(i).getItemID();
                            }
                        }
                    }
                    ////////////////
                    //System.out.println("\nitemId_deletion " + itemId_deletion + ", category_deletion_index " + category_deletion_index + ", category_deletion " + category_deletion);
                    //String cat = topCategoriesItemsForPackages_list.get(category_deletion_index).getCategory();
                    List<ItemForPackage_v2> temp = new ArrayList<>(package_hashtable.get(category_deletion));
                    // temp = package_hashtable.get(category_deletion);
                    for (int i = 0; i < temp.size(); i++) {
                        if (temp.get(i).getItemID() == itemId_deletion) {
                            ////////////
                           // p("packCost = packCost - temp.get(i).getItem_cost();");
                            packCost = packCost - temp.get(i).getItem_cost();
                            temp.remove(i);
                            break;
                        }
                    }
                    int flag_a = 0;
                    while (flag_a == 0) { //if flag_a = 1 then an item was added to package
                        int size = topCategoriesItemsForPackages_list.get(category_deletion_index).getItemForPackage_list().size();
                        if (categoryItem_Index[category_deletion_index] > (size - 1)) {

                            return null;
                        }
                        ///////////////////////////////////////////////////
                        //System.out.println(categoryItem_Index[category_deletion_index]);

                        //get the next item that will be added to the package 
                        ItemForPackage_v2 ifp = topCategoriesItemsForPackages_list.get(category_deletion_index).getItemForPackage_list().get(categoryItem_Index[category_deletion_index]);
                        int addedItemId = ifp.getItemID();
                        /////////////////////////////
                        //System.out.println("__itemId " + addedItemId + " itemId_deletion " + itemId_deletion);

                        //category_deletion_index is the index of the category that should be added item 
                        categoryItem_Index[category_deletion_index]++;
                        if (itemIdExistence(package_hashtable, addedItemId) == 0) {//if item doesn't exist in the package
                            /////////
                            //System.out.println("replace IF");
                            packCost += ifp.getItem_cost();
                            temp.add(ifp);
                            package_hashtable.replace(category_deletion, temp);
                            flag_a = 1;
                            /////////////////////////////////
                            //System.out.println("item added " + category_deletion + ", itemId " + temp.get(0).getItemID());
                            break;
                        }
                    }
                    //////////////////////////////
                    /*
                    Enumeration<String> keyts_ = package_hashtable.keys();
                    while (keyts_.hasMoreElements()) {
                        String cat = keyts_.nextElement();
                        System.out.println("Package_hash: " + cat + " itemId " + package_hashtable.get(cat).get(0).getItemID()
                                + " rating " + package_hashtable.get(cat).get(0).getRating()
                                + " cost " + package_hashtable.get(cat).get(0).getItem_cost());
                    }
                    p("cost: " + packCost);
                    *//////////////////////////////////// 
                }
            }
            return package_hashtable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //
    public Hashtable<String, List<ItemForPackage_v2>> topPackage(int numOfTopCategories, double packageCost) {
        try {
            int size = B_RunningMode.predictedItemRatingCostWithPopularity_list.size();
            category_moviesForPackageList_hashTable.clear();
            topCategoriesItemsForPackages_list.clear();

            //load category list of times
            for (int r = 0; r < size; r++) {
                //Hashtable<String, List<ItemForPackage_v2>> category_movieForPackage_hashTable = new Hashtable<>();  
                int itemId = B_RunningMode.predictedItemRatingCostWithPopularity_list.get(r).getItemID();
                String[] category = A_Start.movies_hashTable.get(itemId).getCategories();
                int maxAppearances = A_Start.movies_hashTable.get(itemId).getNumOfMaxAppearances();
                String title = A_Start.movies_hashTable.get(itemId).getTitle();
                double rating = B_RunningMode.predictedItemRatingCostWithPopularity_list.get(r).getRating();
                double item_cost = B_RunningMode.predictedItemRatingCostWithPopularity_list.get(r).getCost();
                int categories = category.length;
                for (int c = 0; c < categories; c++) {
                    String cat = category[c];
                    ItemForPackage_v2 ifp = new ItemForPackage_v2(itemId, maxAppearances, cat, title, rating, item_cost);
                    List<ItemForPackage_v2> ifp_list = new ArrayList<>();
                    if (category_moviesForPackageList_hashTable.get(cat) != null) {
                        ifp_list = category_moviesForPackageList_hashTable.get(cat);
                    }
                    ifp_list.add(ifp);
                    category_moviesForPackageList_hashTable.put(cat, ifp_list);
                }
            }
            //////////////////////
            //System.out.println("hashTable:" + category_moviesForPackageList_hashTable);

            //load list with itmes of top categories
            for (int c = 0; c < numOfTopCategories; c++) {
                String category = C_ProblemModeling.categoriesPopularity_list.get(c).getCategory();
                List<ItemForPackage_v2> tempL = new ArrayList<>();
                tempL = category_moviesForPackageList_hashTable.get(category);
                if (tempL.size() > 2) {
                    Collections.sort(tempL, new Comparator<ItemForPackage_v2>() {
                        public int compare(ItemForPackage_v2 o1, ItemForPackage_v2 o2) {
                            return (int) (o2.getRating() * 100100100100.0 - o1.getRating() * 100100100100.0);
                        }
                    });
                }
                CategoryItemForPackageList cip
                        = new CategoryItemForPackageList(category, tempL);
                topCategoriesItemsForPackages_list.add(cip);
            }

            //Collections.reverse(topCategoriesItemsForPackages_list);//reverse list to bring the top category first
            ///////////////////////////
            //////////////////////////
            /*
            for (int i = 0; i < C_ProblemModeling.categoriesPopularity_list.size(); i++) {
                System.out.println("\ncategory:"
                        + C_ProblemModeling.categoriesPopularity_list.get(i).getCategory()
                        + " popularity: " + C_ProblemModeling.categoriesPopularity_list.get(i).getPopularity());
            }

             */
            ////////////////////////
            /*
            for (int i = 0; i < topCategoriesItemsForPackages_list.size(); i++) {
                System.out.println("\ncategory:"
                        + topCategoriesItemsForPackages_list.get(i).getCategory()
                        + " itemslistSize: " + topCategoriesItemsForPackages_list.get(i).getItemForPackage_list().size());
            }

             *//////////////////////////
            Hashtable<String, List<ItemForPackage_v2>> package_hashtable = new Hashtable<>();

            double packCost = 0.0;
            int[] categoryItem_Index = new int[numOfTopCategories];
            for (int c = 0; c < numOfTopCategories; c++) {
                categoryItem_Index[c] = 0;
            }
            for (int c = 0; c < numOfTopCategories; c++) {
                String category = topCategoriesItemsForPackages_list.get(c).getCategory();
                int itemPerCategory = 0;
                while (itemPerCategory < C_ProblemModeling.numOfItemsPerCategory) {
                    //items of category are less than required
                    if (categoryItem_Index[c] > (topCategoriesItemsForPackages_list.get(c).getItemForPackage_list().size() - 1)) {
                        //////////////////////////////////
                        //System.out.println("\nitempercategory>categoryItems  return null:");
                        return null;
                    }
                    ItemForPackage_v2 ifp = topCategoriesItemsForPackages_list.get(c).getItemForPackage_list().get(categoryItem_Index[c]);
                    int itemId = ifp.getItemID();
                    categoryItem_Index[c]++;
                    ////////////////
                    //System.out.println("category:" + c + " categoryItem_Index[c]: " + categoryItem_Index[c]);
                    int exist = itemIdExistence(package_hashtable, itemId);
                    //System.out.println("exist:" + exist);
                    if (exist == 0) { //if item doesn't exist in package
                        //set item for deletion of list

                        packCost += ifp.getItem_cost();
                        if (package_hashtable.get(category) == null) { //if category doesn't exist in package
                            List<ItemForPackage_v2> temp = new ArrayList<>();
                            temp.add(ifp);
                            package_hashtable.put(category, temp);
                            itemPerCategory++;
                        } else {
                            List<ItemForPackage_v2> temp = new ArrayList<>();
                            temp = package_hashtable.get(category);
                            temp.add(ifp);
                            package_hashtable.replace(category, temp);
                            itemPerCategory++;
                        }
                    }
                }
            }

            /////////////
            /*
            Enumeration<String> keyts = package_hashtable.keys();
            while (keyts.hasMoreElements()) {
                String cat = keyts.nextElement();
                System.out.println("Package_hash: " + cat + " itemId " + package_hashtable.get(cat).get(0).getItemID()
                        + " rating " + package_hashtable.get(cat).get(0).getRating()
                        + " cost " + package_hashtable.get(cat).get(0).getItem_cost());
            }

            *////////////////////////////////////
            //System.out.println("Package_cost: " + packCost);
            /////////////////////////

            int flag = 0;
            while (flag == 0) {
                int category_deletion_index = 0;
                String category_deletion = "";
                int itemId_deletion = 0;
                double ratio = 999.9; //ratio = item_rating/item_cost
                //if the maximum packageCost is satisfied then the flag change to 1
                if (packCost < packageCost) {
                    flag = 1;
                } else {
                    Enumeration<String> keys = package_hashtable.keys();
                    while (keys.hasMoreElements()) {
                        String cat = keys.nextElement();
                        List<ItemForPackage_v2> list = package_hashtable.get(cat);
                        for (int i = 0; i < list.size(); i++) {
                            double newRatio = list.get(i).getRating() / list.get(i).getItem_cost();
                            /////////////////
                            //System.out.println("newRation" + newRatio);
                            if (newRatio < ratio) {
                                ratio = newRatio;
                                category_deletion = cat;
                                for (int q = 0; q < topCategoriesItemsForPackages_list.size(); q++) {
                                    String temp_category = topCategoriesItemsForPackages_list.get(q).getCategory();
                                    if (cat.equals(temp_category)) {
                                        category_deletion_index = q;
                                        break;
                                    }
                                }
                                itemId_deletion = list.get(i).getItemID();
                            }
                        }
                    }
                    ////////////////
                    //System.out.println("\nitemId_deletion " + itemId_deletion + ", category_deletion_index " + category_deletion_index + ", category_deletion " + category_deletion);
                    //String cat = topCategoriesItemsForPackages_list.get(category_deletion_index).getCategory();
                    List<ItemForPackage_v2> temp = new ArrayList<>(package_hashtable.get(category_deletion));
                    // temp = package_hashtable.get(category_deletion);
                    for (int i = 0; i < temp.size(); i++) {
                        if (temp.get(i).getItemID() == itemId_deletion) {
                            ////////////
                            //p("packCost = packCost - temp.get(i).getItem_cost();");
                            packCost = packCost - temp.get(i).getItem_cost();
                            temp.remove(i);
                            break;
                        }
                    }
                    int flag_a = 0;
                    while (flag_a == 0) { //if flag_a = 1 then an item was added to package
                        size = topCategoriesItemsForPackages_list.get(category_deletion_index).getItemForPackage_list().size();
                        if (categoryItem_Index[category_deletion_index] > (size - 1)) {

                            return null;
                        }
                        ///////////////////////////////////////////////////
                        //System.out.println(categoryItem_Index[category_deletion_index]);

                        //get the next item that will be added to the package 
                        ItemForPackage_v2 ifp = topCategoriesItemsForPackages_list.get(category_deletion_index).getItemForPackage_list().get(categoryItem_Index[category_deletion_index]);
                        int addedItemId = ifp.getItemID();
                        /////////////////////////////
                       // System.out.println("__itemId " + addedItemId + " itemId_deletion " + itemId_deletion);

                        //category_deletion_index is the index of the category that should be added item 
                        categoryItem_Index[category_deletion_index]++;
                        if (itemIdExistence(package_hashtable, addedItemId) == 0) {//if item doesn't exist in the package
                            packCost += ifp.getItem_cost();
                            temp.add(ifp);
                            package_hashtable.replace(category_deletion, temp);
                            flag_a = 1;
                            /////////////////////////////////
                            //System.out.println("item added " + category_deletion + ", itemId " + temp.get(0).getItemID());
                            break;
                        }
                    }
                    /////////////////////////////
                    /*
                    Enumeration<String> keyts_ = package_hashtable.keys();
                    while (keyts_.hasMoreElements()) {
                        String cat = keyts_.nextElement();
                        System.out.println("Package_hash: " + cat + " itemId " + package_hashtable.get(cat).get(0).getItemID()
                                + " rating " + package_hashtable.get(cat).get(0).getRating()
                                + " cost " + package_hashtable.get(cat).get(0).getItem_cost());
                    }
                    p("cost: " + packCost);
                    *//////////////////////////////////// 
                }
            }
            return package_hashtable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //it returns 1 when the itemId exists

    public int itemIdExistence(Hashtable<String, List<ItemForPackage_v2>> package_hashTable, int itemId) {
        try {
            Enumeration<String> keys = package_hashTable.keys();
            while (keys.hasMoreElements()) {
                String category = keys.nextElement();
                List<ItemForPackage_v2> list = package_hashTable.get(category);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getItemID() == itemId) {
                        ;
                        return 1;
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //print s
    public void p(String s) {
        System.out.println(s);
    }

    //it returns 1 when the itemId exists
    public int categoryExistence(Hashtable<String, List<ItemForPackage_v2>> package_hashTable, String category) {
        try {
            Enumeration<String> keys = package_hashTable.keys();
            while (keys.hasMoreElements()) {
                String cat = keys.nextElement();
                if (cat.equals(category)) {
                    return 1;
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //
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
                ratingPerPackage[k] = sumOfRatings;
                costPerPackage[k] = sumOfCost;

                sumOfRatings = 0.0;
                sumOfCost = 0.0;
            }
            RatingsCost_OfPackage rc = new RatingsCost_OfPackage(ratingPerPackage, costPerPackage);
            return rc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
