
package entityGraphClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */

public class VertexSink_v2 {
 
    private List<EdgeCategorySink_v2> edgeCategorySink_list= new ArrayList<EdgeCategorySink_v2>();
    private EdgeSinkSource_v2 edgeSinkSource = new EdgeSinkSource_v2();

    public VertexSink_v2() {
    }

    public VertexSink_v2(EdgeSinkSource_v2 edgeSinkSource, List<EdgeCategorySink_v2> edgeCategorySink_list) {
        this.edgeSinkSource = edgeSinkSource;
        this.edgeCategorySink_list = edgeCategorySink_list;  
    }

    public List<EdgeCategorySink_v2> getEdgeCategorySink_list() {
        return edgeCategorySink_list;
    }

    public void setEdgeCategorySink_list(List<EdgeCategorySink_v2> edgeCategorySink_list) {
        this.edgeCategorySink_list = edgeCategorySink_list;
    }

    public EdgeSinkSource_v2 getEdgeSinkSource() {
        return edgeSinkSource;
    }

    public void setEdgeSinkSource(EdgeSinkSource_v2 edgeSinkSource) {
        this.edgeSinkSource = edgeSinkSource;
    }

   
  
    
}
