/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityGraphClasses;

import java.io.Serializable;

/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class EdgeCategorySink {
    private String categoryID;
    private double cost;
    private int maxFlow;
    private int minFlow;

    public EdgeCategorySink(String categoryID, double cost, int maxFlow, int minFlow) {
        this.categoryID = categoryID;
        this.cost = cost;
        this.maxFlow = maxFlow;
        this.minFlow = minFlow;
    }
    
    public EdgeCategorySink() {
        
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(int maxFlow) {
        this.maxFlow = maxFlow;
    }

    public int getMinFlow() {
        return minFlow;
    }

    public void setMinFlow(int minFlow) {
        this.minFlow = minFlow;
    }
    
    
    
    
}
