/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityBasicClasses;



/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class ItemRatingCost {

    private int itemID;
    private double rating;
    private double cost;

 
    public ItemRatingCost(int movieID, double rating, double cost) {
        this.itemID = movieID;
        this.rating = rating;
        this.cost = cost;
    }

    public ItemRatingCost() {
    }

    
    
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
  
   
    
    
    
    
   
    
}
