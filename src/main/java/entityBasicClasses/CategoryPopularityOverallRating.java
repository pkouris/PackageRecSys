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

public class CategoryPopularityOverallRating {

    private String category;
    private int popularity;
    private double overallRating;

    public CategoryPopularityOverallRating(String category, int popularity, double overallRating) {
        this.category = category;
        this.popularity = popularity;
        this.overallRating = overallRating;
    }

 
    
    public CategoryPopularityOverallRating() {
        
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    
   
   
    
    
    
    
   
    
}
