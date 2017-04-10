/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityGraphClasses;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */

public class EdgeCategorySink_v2 {
    private String categoryID;
    private double flow_cost; 
    private double item_cost;
    private int maxFlow;
    private int minFlow;

    public EdgeCategorySink_v2(String categoryID, double flow_cost, double item_cost,  int maxFlow, int minFlow) {
        this.categoryID = categoryID;
        this.item_cost = item_cost;
        this.flow_cost = flow_cost;
        this.maxFlow = maxFlow;
        this.minFlow = minFlow;
    }
    
    public EdgeCategorySink_v2() {
        
    }

    public double getFlow_cost() {
        return flow_cost;
    }

    public void setFlow_cost(double flow_cost) {
        this.flow_cost = flow_cost;
    }

    public double getItem_cost() {
        return item_cost;
    }

    public void setItem_cost(double item_cost) {
        this.item_cost = item_cost;
    }


    
    
    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
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
