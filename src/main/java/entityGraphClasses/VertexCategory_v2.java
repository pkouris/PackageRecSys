
package entityGraphClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */

public class VertexCategory_v2 {

    //private int categoryIndex; //a number for each category, the position of the category in the list, categoryIdex = {1, 2, 3 ..}
    private String categoryID;
    private int moveisPerCategory;
    private List<EdgeItemCategory_v2> edgeMovieCategory_list = new ArrayList<EdgeItemCategory_v2>();
    private EdgeCategorySink_v2 edgeCategorySink= new EdgeCategorySink_v2();

    public VertexCategory_v2( String categoryID,  List<EdgeItemCategory_v2> edgeMovieCategory_list, EdgeCategorySink_v2 edgeCategorySink ) {
        //this.categoryIndex = categoryIndex;
        this.categoryID = categoryID;
        this.moveisPerCategory = edgeMovieCategory_list.size(); 
        this.edgeMovieCategory_list = edgeMovieCategory_list;
        this.edgeCategorySink = edgeCategorySink;
    }

    public VertexCategory_v2() {
       
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public int getMoveisPerCategory() {
        return moveisPerCategory;
    }

    public void setMoveisPerCategory(int moveisPerCategory) {
        this.moveisPerCategory = moveisPerCategory;
    }

    public List<EdgeItemCategory_v2> getEdgeMovieCategory_list() {
        return edgeMovieCategory_list;
    }

    public void setEdgeMovieCategory_list(List<EdgeItemCategory_v2> edgeMovieCategory_list) {
        this.edgeMovieCategory_list = edgeMovieCategory_list;
    }

    public EdgeCategorySink_v2 getEdgeCategorySink() {
        return edgeCategorySink;
    }

    public void setEdgeCategorySink(EdgeCategorySink_v2 edgeCategorySink) {
        this.edgeCategorySink = edgeCategorySink;
    }



  


    
}
