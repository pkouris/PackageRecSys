/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityBasicClasses;



/**
 * @author: Panagiotis Kouris
 * date: Mar 2017
 */

public class UserItemRating {

    private int userId;
    private int itemId;
    private double rating;
    /**
     *  @param userID
     * @param movieID
     * @param rating
     * @param cost
     */
    public UserItemRating(int userID, int itemID, double rating) {
        this.userId = userID;
        this.itemId = itemID;
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }



   
   
    
    
    
    
   
    
}
