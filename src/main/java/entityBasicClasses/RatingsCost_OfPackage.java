/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityBasicClasses;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class RatingsCost_OfPackage {
    
    private double[] package_ratings;
    private double[] package_cost;

    public RatingsCost_OfPackage(double[] package_ratings, double[] package_cost) {
        this.package_ratings = package_ratings;
        this.package_cost = package_cost;
    }


    public double[] getPackage_ratings() {
        return package_ratings;
    }

    public void setPackage_ratings(double[] package_ratings) {
        this.package_ratings = package_ratings;
    }

    public double[] getPackage_cost() {
        return package_cost;
    }

    public void setPackage_cost(double[] package_cost) {
        this.package_cost = package_cost;
    }

   
    
    
    
    
}
