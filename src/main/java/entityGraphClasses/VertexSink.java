
package entityGraphClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class VertexSink {
 
    private List<EdgeCategorySink> edgeCategorySink_list= new ArrayList<EdgeCategorySink>();
    private EdgeSinkSource edgeSinkSource = new EdgeSinkSource();

    public VertexSink() {
    }

    public VertexSink(EdgeSinkSource edgeSinkSource, List<EdgeCategorySink> edgeCategorySink_list) {
        this.edgeSinkSource = edgeSinkSource;
        this.edgeCategorySink_list = edgeCategorySink_list;  
    }

    public List<EdgeCategorySink> getEdgeCategorySink_list() {
        return edgeCategorySink_list;
    }

    public void setEdgeCategorySink_list(List<EdgeCategorySink> edgeCategorySink_list) {
        this.edgeCategorySink_list = edgeCategorySink_list;
    }

    public EdgeSinkSource getEdgeSinkSource() {
        return edgeSinkSource;
    }

    public void setEdgeSinkSource(EdgeSinkSource edgeSinkSource) {
        this.edgeSinkSource = edgeSinkSource;
    }

   
    

   
    
    
    
    
    
  
    
}
