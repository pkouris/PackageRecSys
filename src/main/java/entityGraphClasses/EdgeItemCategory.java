
package entityGraphClasses;

import java.io.Serializable;

/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class EdgeItemCategory {
    private int itemID;
    private String categoryID;
    private double rating;
    private double cost;
    private int maxFlow;
    private int minFlow;

    public EdgeItemCategory(int itemId, String categoryID, double rating, double cost, int maxFlow, int minFlow) {
        this.itemID = itemId;
        this.categoryID = categoryID;
        this.rating = rating;
        this.cost = cost;
        this.maxFlow = maxFlow;
        this.minFlow = minFlow;
    }
    
    public EdgeItemCategory() {

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
