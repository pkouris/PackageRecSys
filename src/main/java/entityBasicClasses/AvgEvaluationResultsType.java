/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityBasicClasses;

import java.util.List;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class AvgEvaluationResultsType {


    private double[] precisionPerPackage; //avg precision of all the folds
    private double avg_precision; //avg precision of all the packages and all the folds
    private double[] modPrecisionPerPackage; //avg precision of all the folds
    private double avg_modPrecision; //avg precision of all the packages and all the folds
    private double[] ratingsPerPackage; //avg rating of all the folds
    private double avg_ratings;
    private double[] costPerPackage;
    private double avg_cost;
    private double[] runningTime;
    private double avg_runningTime;

    public AvgEvaluationResultsType(double[] precisionPerPackage, double[] modPrecisionPerPackage,
            double[] ratingsPerPackage, double[] costPerPackage, double[] runningTime) {
        this.precisionPerPackage = precisionPerPackage;
        this.modPrecisionPerPackage = modPrecisionPerPackage;
        this.ratingsPerPackage = ratingsPerPackage;
        this.costPerPackage = costPerPackage;
        this.runningTime = runningTime;
        this.avg_precision = avg(precisionPerPackage);
        this.avg_modPrecision = avg(modPrecisionPerPackage);
        this.avg_ratings = avg(ratingsPerPackage);
        this.avg_cost = avg(costPerPackage);
        this.avg_runningTime = runningTime[runningTime.length-1];
    }

    public AvgEvaluationResultsType() {
    }

    
    
    //avg array values
    public double avg(double[] array) {
        int len = array.length;
        double avg = 0.0;
        for (int l = 0; l < len; l++) { //for each fold
            avg = ((double) (l) * avg + array[l]) / (double) (l + 1);
        }
        return avg;
    }

    public double getAvg_runningTime() {
        return avg_runningTime;
    }

    public void setAvg_runningTime(double avg_runningTime) {
        this.avg_runningTime = avg_runningTime;
    }

    
    
    public double[] getModPrecisionPerPackage() {
        return modPrecisionPerPackage;
    }

    public void setModPrecisionPerPackage(double[] modPrecisionPerPackage) {
        this.modPrecisionPerPackage = modPrecisionPerPackage;
    }

    public double getAvg_modPrecision() {
        return avg_modPrecision;
    }

    public void setAvg_modPrecision(double avg_modPrecision) {
        this.avg_modPrecision = avg_modPrecision;
    }
    
    
    

    public double getAvg_ratings() {
        return avg_ratings;
    }

    public void setAvg_ratings(double avg_ratings) {
        this.avg_ratings = avg_ratings;
    }

    public double getAvg_cost() {
        return avg_cost;
    }

    public void setAvg_cost(double avg_cost) {
        this.avg_cost = avg_cost;
    }

    public double[] getCostPerPackage() {
        return costPerPackage;
    }

    public void setCostPerPackage(double[] costPerPackage) {
        this.costPerPackage = costPerPackage;
    }



    public double[] getPrecisionPerPackage() {
        return precisionPerPackage;
    }

    public void setPrecisionPerPackage(double[] precisionPerPackage) {
        this.precisionPerPackage = precisionPerPackage;
    }

    public double getAvg_precision() {
        return avg_precision;
    }

    public void setAvg_precision(double avg_precision) {
        this.avg_precision = avg_precision;
    }

    public double[] getRatingsPerPackage() {
        return ratingsPerPackage;
    }

    public void setRatingsPerPackage(double[] ratingsPerPackage) {
        this.ratingsPerPackage = ratingsPerPackage;
    }

    public double[] getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(double[] runningTime) {
        this.runningTime = runningTime;
    }

}
