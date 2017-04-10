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

public class ItemForPackage {

    private int itemID;
    private int maxAppearances;
    private String title;
    private String category;
    private double rating;

    public ItemForPackage(int itemId,int maxAppearances, String category, String title, double rating) {
        this.itemID = itemId;
        this.maxAppearances = maxAppearances;
        this.title = title;
        this.category = category;
        this.rating = rating;
    }

     public ItemForPackage(){
     }

    public int getMaxAppearances() {
        return maxAppearances;
    }

    public void setMaxAppearances(int maxAppearances) {
        this.maxAppearances = maxAppearances;
    }
    
     
     
     
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

  

   
    
    
    
   
    
}
