package entityBasicClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */

public class RatingsPerUser {

    private int userID;
    private int numOfRatings;
    private List<ItemRating> ItemRatingList = new ArrayList<ItemRating>(); 

    public RatingsPerUser(int userID, int numOfRatings, List<ItemRating> movieRatingList) {
        this.userID = userID;
        this.numOfRatings = numOfRatings;
        this.ItemRatingList = movieRatingList;
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

    public List<ItemRating> getItemRatingList() {
        return ItemRatingList;
    }

    public void setItemRatingList(List<ItemRating> ItemRatingList) {
        this.ItemRatingList = ItemRatingList;
    }  
   
}
