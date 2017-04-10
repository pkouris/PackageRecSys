/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommend_and_evaluation;

import static recommend_and_evaluation.C_ProblemModeling.numOfTopCategories;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class D_ItemsAllocationToCategories {
     static public int numOfItemsPerPackage = 1;

    public D_ItemsAllocationToCategories() {
    }
    
   
     
     public Integer[] itemsAllocationToCategories() {
        
        //this.numOfTopCategories = this.cb_numOfCategories.getSelectedIndex() + 1;
        //this.numOfPackages = this.cb_numOfPackages.getSelectedIndex() + 1;
        //int num = numOfItemsPerPackage; //number of Packages
        //numOfItemsPerPackage = 1;
        int remainingItems = numOfItemsPerPackage; //remaining items
        int[] itemsPerCategory = new int[numOfTopCategories];
        String[] category = new String[numOfTopCategories];
        Integer[] itemsAllocation = new Integer[numOfTopCategories];
        int sumOfItems;
        double weightOfCategory;

        if (numOfItemsPerPackage < numOfTopCategories) {
            System.out.println("Infeasible Allocation! (number of Items per Package < number of Categories)");
            C_ProblemModeling.itemsAllocationToCategories = null;
            return null;
        }
        //taking the items per category and the name of category
        for (int i = 0; i < numOfTopCategories; i++) {
            //itemsPerCategory[i] = vertexCategory_list.get(i).getMoveisPerCategory();
            //category[i] = vertexCategory_list.get(i).getCategoryID();
            itemsPerCategory[i] = C_ProblemModeling.categoriesPopularity_list.get(i).getPopularity();
            category[i] = C_ProblemModeling.categoriesPopularity_list.get(i).getCategory();
            //System.out.println("For1");
        }

        for (int i = (numOfTopCategories - 1); i > -1; i--) { //it starts from the last category
            if (i > 0) {
                sumOfItems = 0;
                for (int j = 0; j < i + 1; j++) {
                    sumOfItems += itemsPerCategory[j];
                }
                weightOfCategory = (double) (((double) itemsPerCategory[i]) / ((double) sumOfItems));
                itemsAllocation[i] = (int) round((double) (weightOfCategory * remainingItems), 0);
                if (itemsAllocation[i] == 0) { //each category will have at least one item
                    itemsAllocation[i] = 1;
                }
                if (itemsAllocation[i] > itemsPerCategory[i]) {
                    System.out.println("Infeasible Allocation! The size of package is greater than number of items!");
                    C_ProblemModeling.itemsAllocationToCategories = null;
                    return null;
                }
                remainingItems -= itemsAllocation[i];
                if (remainingItems < 1) { //the allocation is impossible
                    System.out.println("Infeasible Allocation!");
                    C_ProblemModeling.itemsAllocationToCategories = null;
                    return null;
                }

            } else { //for the first category
                itemsAllocation[i] = remainingItems;
                if (itemsAllocation[i] > itemsPerCategory[i]) {
                    System.out.println("Infeasible Allocation! The size of package is greater than number of items!");
                    C_ProblemModeling.itemsAllocationToCategories = null;
                    return null;
                }
            }
        }
        Arrays.sort(itemsAllocation, Collections.reverseOrder()); //sort items allocation in order to get first the greater number
        C_ProblemModeling.itemsAllocationToCategories = itemsAllocation;
        C_ProblemModeling.numOfItemsPerCategory = 0; //items allocated proportionally
        ////////////////
        System.out.println("item per category = " + C_ProblemModeling.numOfItemsPerCategory);
        /////////////
        

        //print table of item allocation
        System.out.println("no, category, itemsPerCategory, itemsAllocationToCategory");
        for (int i = 0; i < numOfTopCategories; i++) {
            System.out.println((i + 1) + "," + category[i] + "," + itemsPerCategory[i] + "," + C_ProblemModeling.itemsAllocationToCategories[i]);
        }
        System.out.println("Items allocated to categories proportionally!");
        
        return itemsAllocation;
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
