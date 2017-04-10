
package linearProgrammingClasses;

/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */
public class SolutionType_v2 {
    String[] namesOfEdges; 
    double[] flowOfEdges;
    double[] flow_costOfEdges;
    double[] item_costOfEdges;

    public SolutionType_v2(String[] namesOfEdges, double[] flowOfEdges, double[] flow_costOfEdges, double[] item_costOfEdges) {
        this.namesOfEdges = namesOfEdges;
        this.flowOfEdges = flowOfEdges;
        this.flow_costOfEdges = flow_costOfEdges;
        this.item_costOfEdges = item_costOfEdges;
    }
    
    
     public SolutionType_v2() {
     
    }

    public String[] getNamesOfEdges() {
        return namesOfEdges;
    }

    public void setNamesOfEdges(String[] namesOfEdges) {
        this.namesOfEdges = namesOfEdges;
    }

    public double[] getFlowOfEdges() {
        return flowOfEdges;
    }

    public void setFlowOfEdges(double[] flowOfEdges) {
        this.flowOfEdges = flowOfEdges;
    }

    public double[] getFlow_costOfEdges() {
        return flow_costOfEdges;
    }

    public void setFlow_costOfEdges(double[] flow_costOfEdges) {
        this.flow_costOfEdges = flow_costOfEdges;
    }

    public double[] getItem_costOfEdges() {
        return item_costOfEdges;
    }

    public void setItem_costOfEdges(double[] item_costOfEdges) {
        this.item_costOfEdges = item_costOfEdges;
    }

  
  
}
