/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityBasicClasses;

import java.util.ArrayList;
import java.util.List;



/**
 * @author: Panagiotis Kouris
 * date: Mar 2017
 */

public class List_UserItemRating {


    private List<UserItemRating> userItemRatingList = new ArrayList<UserItemRating>();


  public List_UserItemRating(List<UserItemRating> userItemRatingList) {
        this.userItemRatingList = userItemRatingList;
    }

    public List_UserItemRating() {
        
    }
    
     public void addUserItemRatingList(UserItemRating userItemRating){
         this.userItemRatingList.add(userItemRating);
    }

    public List<UserItemRating> getUserItemRatingList() {
        return userItemRatingList;
    }
    
    public void setUserItemRatingList(List<UserItemRating> userItemRatingList) {
        this.userItemRatingList = userItemRatingList;
    }
       
    
}
