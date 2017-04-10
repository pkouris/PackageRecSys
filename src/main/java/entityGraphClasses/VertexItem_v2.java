
package entityGraphClasses;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */

public class VertexItem_v2 {

    //private int movieIndex; //a number for each movie, the position of the movie in the list, movieIdex = {1, 2, 3 ..}
    private int itemID;
    private int numOfMaxAppearances;
    private List<EdgeItemCategory_v2> edgeItemCategory_list = new ArrayList<EdgeItemCategory_v2>();
    private EdgeItemSource_v2 edgeMoveSource = new EdgeItemSource_v2();

    public VertexItem_v2(int itemId, int numOfAppearances, List<EdgeItemCategory_v2> edgeMovieCategory_list, EdgeItemSource_v2 edgeMovieSource) {
       // this.movieIndex= movieIndex;
        this.itemID = itemId;
        this.edgeItemCategory_list = edgeMovieCategory_list;
        this.edgeMoveSource = edgeMovieSource;
    }
    
     public VertexItem_v2() {
       
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getNumOfMaxAppearances() {
        return numOfMaxAppearances;
    }

    public void setNumOfMaxAppearances(int numOfMaxAppearances) {
        this.numOfMaxAppearances = numOfMaxAppearances;
    }

    public List<EdgeItemCategory_v2> getEdgeItemCategory_list() {
        return edgeItemCategory_list;
    }

    public void setEdgeItemCategory_list(List<EdgeItemCategory_v2> edgeItemCategory_list) {
        this.edgeItemCategory_list = edgeItemCategory_list;
    }

    public EdgeItemSource_v2 getEdgeMoveSource() {
        return edgeMoveSource;
    }

    public void setEdgeMoveSource(EdgeItemSource_v2 edgeMoveSource) {
        this.edgeMoveSource = edgeMoveSource;
    }

     
     
}
