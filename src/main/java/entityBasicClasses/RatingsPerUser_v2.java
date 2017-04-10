package entityBasicClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */

public class RatingsPerUser_v2 {

    private int userID;
    private int numOfRatings;
    private List<ItemRatingCost> ItemRatingCostList = new ArrayList<ItemRatingCost>(); 

    public RatingsPerUser_v2(int userID, int numOfRatings, List<ItemRatingCost> movieRatingCostList) {
        this.userID = userID;
        this.numOfRatings = numOfRatings;
        this.ItemRatingCostList = movieRatingCostList;
    }

    public RatingsPerUser_v2() {
    }

    
    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getNumOfRatings() {
        return numOfRatings;
    }

    public void setNumOfRatings(int numOfRatings) {
        this.numOfRatings = numOfRatings;
    }

    public List<ItemRatingCost> getItemRatingCostList() {
        return ItemRatingCostList;
    }

    public void setItemRatingCostList(List<ItemRatingCost> ItemRatingCostList) {
        this.ItemRatingCostList = ItemRatingCostList;
    }

    
}
