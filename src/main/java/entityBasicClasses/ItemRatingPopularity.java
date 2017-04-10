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

public class ItemRatingPopularity {

    private int itemID;
    private double rating;
    private int popularity;

    public ItemRatingPopularity(int movieID, double rating, int popularity) {
        this.itemID = movieID;
        this.rating = rating;
        this.popularity = popularity;
    }

   
    
    public ItemRatingPopularity() {
        
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
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
    

   
    
    
    
    
   
    
}
