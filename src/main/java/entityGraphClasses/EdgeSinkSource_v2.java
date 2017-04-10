
package entityGraphClasses;

import java.io.Serializable;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */

public class EdgeSinkSource_v2 {
    private double flow_cost;
    private int maxFlow;
    private int minFlow;

    public EdgeSinkSource_v2(double flow_cost, int maxFlow, int minFlow) {
        this.flow_cost = flow_cost;
        this.maxFlow = maxFlow;
        this.minFlow = minFlow;
    }
    
    public EdgeSinkSource_v2() {
        
    }

    public double getFlow_cost() {
        return flow_cost;
    }

    public void setFlow_cost(double flow_cost) {
        this.flow_cost = flow_cost;
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
