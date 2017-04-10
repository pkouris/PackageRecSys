
package entityGraphClasses;

import java.io.Serializable;

/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class EdgeSinkSource {
    private double cost;
    private int maxFlow;
    private int minFlow;

    public EdgeSinkSource(double cost, int maxFlow, int minFlow) {
        this.cost = cost;
        this.maxFlow = maxFlow;
        this.minFlow = minFlow;
    }
    
    public EdgeSinkSource() {
        
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
