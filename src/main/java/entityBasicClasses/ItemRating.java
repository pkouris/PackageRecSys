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

public class ItemRating {

    private int itemID;
    private double rating;

    /**
     *
     * @param movieID
     * @param rating
     */
    public ItemRating(int movieID, double rating) {
        this.itemID = movieID;
        this.rating = rating;
    }
    
    /**
     *
     */
    public ItemRating() {
        
    }

    /**
     *
     * @return
     */
    public int getItemID() {
        return itemID;
    }

    /**
     *
     * @param itemID
     */
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    /**
     *
     * @return
     */
    public double getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }
    

   
    
    
    
    
   
    
}
