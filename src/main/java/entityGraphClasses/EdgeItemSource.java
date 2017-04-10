
package entityGraphClasses;

import java.io.Serializable;

/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class EdgeItemSource  {

    private int itemID;
    private double cost;
    private int maxFlow;
    private int minFlow;

    public EdgeItemSource(int itemId, double cost, int maxFlow, int minFlow) {
        this.itemID = itemId;
        this.cost = cost;
        this.maxFlow = maxFlow;
        this.minFlow = minFlow;
    }
    
    public EdgeItemSource() {

    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
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
