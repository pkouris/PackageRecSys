
package entityGraphClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class VertexCategory {

    //private int categoryIndex; //a number for each category, the position of the category in the list, categoryIdex = {1, 2, 3 ..}
    private String categoryID;
    private int moveisPerCategory;
    private List<EdgeItemCategory> edgeMovieCategory_list = new ArrayList<EdgeItemCategory>();
    private EdgeCategorySink edgeCategorySink= new EdgeCategorySink();

    public VertexCategory( String categoryID,  List<EdgeItemCategory> edgeMovieCategory_list, EdgeCategorySink edgeCategorySink ) {
        //this.categoryIndex = categoryIndex;
        this.categoryID = categoryID;
        this.moveisPerCategory = edgeMovieCategory_list.size(); 
        this.edgeMovieCategory_list = edgeMovieCategory_list;
        this.edgeCategorySink = edgeCategorySink;
    }

    public VertexCategory() {
       
    }

  
    public int getMoveisPerCategory() {
        return moveisPerCategory;
    }

    public void setMoveisPerCategory(int moveisPerCategory) {
        this.moveisPerCategory = moveisPerCategory;
    }

    
    
    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public List<EdgeItemCategory> getEdgeMovieCategory_list() {
        return edgeMovieCategory_list;
    }

    public void setEdgeMovieCategory_list(List<EdgeItemCategory> edgeMovieCategory_list) {
        this.edgeMovieCategory_list = edgeMovieCategory_list;
    }

    public EdgeCategorySink getEdgeCategorySink() {
        return edgeCategorySink;
    }

    public void setEdgeCategorySink(EdgeCategorySink edgeCategorySink) {
        this.edgeCategorySink = edgeCategorySink;
    }
    
    
    
    
   

   
    
    
    
    
    


    
}
