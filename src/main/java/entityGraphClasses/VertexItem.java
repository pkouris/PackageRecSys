
package entityGraphClasses;


import java.util.ArrayList;
import java.util.List;


/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class VertexItem {

    //private int movieIndex; //a number for each movie, the position of the movie in the list, movieIdex = {1, 2, 3 ..}
    private int itemID;
    private int numOfMaxAppearances;
    private List<EdgeItemCategory> edgeItemCategory_list = new ArrayList<EdgeItemCategory>();
    private EdgeItemSource edgeMoveSource = new EdgeItemSource();

    public VertexItem(int itemId, int numOfAppearances, List<EdgeItemCategory> edgeMovieCategory_list, EdgeItemSource edgeMovieSource) {
       // this.movieIndex= movieIndex;
        this.itemID = itemId;
        this.edgeItemCategory_list = edgeMovieCategory_list;
        this.edgeMoveSource = edgeMovieSource;
    }
    
     public VertexItem() {
       
    }

    public int getNumOfMaxAppearances() {
        return numOfMaxAppearances;
    }

    public void setNumOfMaxAppearances(int numOfMaxAppearances) {
        this.numOfMaxAppearances = numOfMaxAppearances;
    }

     
     
     
 

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public List<EdgeItemCategory> getEdgeItemCategory_list() {
        return edgeItemCategory_list;
    }

    public void setEdgeItemCategory_list(List<EdgeItemCategory> edgeItemCategory_list) {
        this.edgeItemCategory_list = edgeItemCategory_list;
    }

    public EdgeItemSource getEdgeMoveSource() {
        return edgeMoveSource;
    }

    public void setEdgeMoveSource(EdgeItemSource edgeMoveSource) {
        this.edgeMoveSource = edgeMoveSource;
    }
     
     
     
     
}
