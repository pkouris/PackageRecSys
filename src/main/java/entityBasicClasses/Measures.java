/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityBasicClasses;

/**
 * @author: Panagiotis Kouris
 * date: Dec 2015
 */
public class Measures {
    int TP_perPackage[];
    int FN_perPackage[];
    int FP_perPackage[];
    int TP_overall;
    int FN_overall;
    int FP_overall;
    double[] precision_perPackage;
    double[] recall_perPackage;
    double precision_overall;
    double recall_overall;

    public Measures(int[] TP_perPackage, int[] FN_perPackage, int[] FP_perPackage, int TP_overall, int FN_overall, int FP_overall, double[] precision_perPackage, double[] recall_perPackage, double precision_overall, double recall_overall) {
        this.TP_perPackage = TP_perPackage;
        this.FN_perPackage = FN_perPackage;
        this.FP_perPackage = FP_perPackage;
        this.TP_overall = TP_overall;
        this.FN_overall = FN_overall;
        this.FP_overall = FP_overall;
        this.precision_perPackage = precision_perPackage;
        this.recall_perPackage = recall_perPackage;
        this.precision_overall = precision_overall;
        this.recall_overall = recall_overall;
    }

    public int[] getTP_perPackage() {
        return TP_perPackage;
    }

    public void setTP_perPackage(int[] TP_perPackage) {
        this.TP_perPackage = TP_perPackage;
    }

    public int[] getFN_perPackage() {
        return FN_perPackage;
    }

    public void setFN_perPackage(int[] FN_perPackage) {
        this.FN_perPackage = FN_perPackage;
    }

    public int[] getFP_perPackage() {
        return FP_perPackage;
    }

    public void setFP_perPackage(int[] FP_perPackage) {
        this.FP_perPackage = FP_perPackage;
    }

    public int getTP_overall() {
        return TP_overall;
    }

    public void setTP_overall(int TP_overall) {
        this.TP_overall = TP_overall;
    }

    public int getFN_overall() {
        return FN_overall;
    }

    public void setFN_overall(int FN_overall) {
        this.FN_overall = FN_overall;
    }

    public int getFP_overall() {
        return FP_overall;
    }

    public void setFP_overall(int FP_overall) {
        this.FP_overall = FP_overall;
    }

    public double[] getPrecision_perPackage() {
        return precision_perPackage;
    }

    public void setPrecision_perPackage(double[] precision_perPackage) {
        this.precision_perPackage = precision_perPackage;
    }

    public double[] getRecall_perPackage() {
        return recall_perPackage;
    }

    public void setRecall_perPackage(double[] recall_perPackage) {
        this.recall_perPackage = recall_perPackage;
    }

    public double getPrecision_overall() {
        return precision_overall;
    }

    public void setPrecision_overall(double precision_overall) {
        this.precision_overall = precision_overall;
    }

    public double getRecall_overall() {
        return recall_overall;
    }

    public void setRecall_overall(double recall_overall) {
        this.recall_overall = recall_overall;
    }



    
}
