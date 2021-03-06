
package entityGraphClasses;

import java.io.Serializable;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */

public class EdgeItemCategory_v2 {
    private int itemID;
    private String categoryID;
    private double rating;
    private double flow_cost;
    private double item_cost;
    private int maxFlow;
    private int minFlow;

    public EdgeItemCategory_v2(int itemId, String categoryID, double rating, double flow_cost, double item_cost, int maxFlow, int minFlow) {
        this.itemID = itemId;
        this.categoryID = categoryID;
        this.rating = rating;
        this.flow_cost = flow_cost;
        this.item_cost = item_cost;
        this.maxFlow = maxFlow;
        this.minFlow = minFlow;
    }
    
    public EdgeItemCategory_v2() {

    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
