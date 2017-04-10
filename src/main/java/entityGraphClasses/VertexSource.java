
package entityGraphClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class VertexSource {

 
    private List<EdgeItemSource> edgeMovieSource_list= new ArrayList<EdgeItemSource>();
    private EdgeSinkSource edgeSinkSource = new EdgeSinkSource();

    public VertexSource() {
    }

    public VertexSource(EdgeSinkSource edgeSinkSource, List<EdgeItemSource> edgeMovieSource_list) {
        this.edgeSinkSource = edgeSinkSource;
        this.edgeMovieSource_list = edgeMovieSource_list;  
    }

    public List<EdgeItemSource> getEdgeMovieSource_list() {
        return edgeMovieSource_list;
    }

    public void setEdgeMovieSource_list(List<EdgeItemSource> edgeMovieSource_list) {
        this.edgeMovieSource_list = edgeMovieSource_list;
    }

    public EdgeSinkSource getEdgeSinkSource() {
        return edgeSinkSource;
    }

    public void setEdgeSinkSource(EdgeSinkSource edgeSinkSource) {
        this.edgeSinkSource = edgeSinkSource;
    }

    
}
