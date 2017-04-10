
package entityGraphClasses;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class GraphMaxFlowMinCost {
   
     private List<VertexItem> vertexMovie_list = new ArrayList<VertexItem>();
     private List<VertexCategory> vertexCategory_list = new ArrayList<VertexCategory>();
     private VertexSink vertexSink; // = new VertexSink();
     private VertexSource vertexSource; // = new VertexSource();

    public GraphMaxFlowMinCost(List<VertexItem> vertexMovie_list, 
            List<VertexCategory> vertexCategory_list, 
            VertexSource vertexSource, VertexSink vertexSink  ) {
        this.vertexMovie_list = vertexMovie_list;
        this.vertexCategory_list = vertexCategory_list;
        this.vertexSource = vertexSource;
        this.vertexSink = vertexSink;
    }
     
     public GraphMaxFlowMinCost( ) {
  
    }

    public List<VertexItem> getVertexMovie_list() {
        return vertexMovie_list;
    }

    public void setVertexMovie_list(List<VertexItem> vertexMovie_list) {
        this.vertexMovie_list = vertexMovie_list;
    }

    public List<VertexCategory> getVertexCategory_list() {
        return vertexCategory_list;
    }

    public void setVertexCategory_list(List<VertexCategory> vertexCategory_list) {
        this.vertexCategory_list = vertexCategory_list;
    }

    public VertexSink getVertexSink() {
        return vertexSink;
    }

    public void setVertexSink(VertexSink vertexSink) {
        this.vertexSink = vertexSink;
    }

    public VertexSource getVertexSource() {
        return vertexSource;
    }

    public void setVertexSource(VertexSource vertexSource) {
        this.vertexSource = vertexSource;
    }
     
     
     
     
     
}
