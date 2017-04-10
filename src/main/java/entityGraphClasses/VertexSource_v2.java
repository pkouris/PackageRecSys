
package entityGraphClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */

public class VertexSource_v2 {

 
    private List<EdgeItemSource_v2> edgeMovieSource_list= new ArrayList<EdgeItemSource_v2>();
    private EdgeSinkSource_v2 edgeSinkSource = new EdgeSinkSource_v2();

    public VertexSource_v2() {
    }

    public VertexSource_v2(EdgeSinkSource_v2 edgeSinkSource, List<EdgeItemSource_v2> edgeMovieSource_list) {
        this.edgeSinkSource = edgeSinkSource;
        this.edgeMovieSource_list = edgeMovieSource_list;  
    }

    public List<EdgeItemSource_v2> getEdgeMovieSource_list() {
        return edgeMovieSource_list;
    }

    public void setEdgeMovieSource_list(List<EdgeItemSource_v2> edgeMovieSource_list) {
        this.edgeMovieSource_list = edgeMovieSource_list;
    }

    public EdgeSinkSource_v2 getEdgeSinkSource() {
        return edgeSinkSource;
    }

    public void setEdgeSinkSource(EdgeSinkSource_v2 edgeSinkSource) {
        this.edgeSinkSource = edgeSinkSource;
    }

  
    
}
