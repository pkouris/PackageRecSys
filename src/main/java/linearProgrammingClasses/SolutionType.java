
package linearProgrammingClasses;

/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */
public class SolutionType {
    String[] namesOfEdges; 
    double[] flowOfEdges;
    double[] costOfEdges;

    public SolutionType(String[] namesOfEdges, double[] flowOfEdges, double[] costOfEdges) {
        this.namesOfEdges = namesOfEdges;
        this.flowOfEdges = flowOfEdges;
        this.costOfEdges = costOfEdges;
    }
    
    
     public SolutionType() {
     
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

    public double[] getCostOfEdges() {
        return costOfEdges;
    }

    public void setCostOfEdges(double[] costOfEdges) {
        this.costOfEdges = costOfEdges;
    }
  
}
