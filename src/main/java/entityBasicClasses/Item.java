/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityBasicClasses;

/**
 * @author: Panagiotis Kouris date: Nov 2015
 */
public class Item {

    private int ItemID;
    private String title;
    private String[] categories;
    private int numOfCategories;
    private int numOfMaxAppearances;
    private int popularity;

    public Item(int itemId, String title, String[] categories, int numOfCategories, int numOfMaxAppearances, int popularity) {
        this.ItemID = itemId;
        this.title = title;
        this.categories = categories;
        this.numOfCategories = numOfCategories;
        this.numOfMaxAppearances = numOfMaxAppearances;
        this.popularity = popularity;
    }

    public Item() {
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getNumOfMaxAppearances() {
        return numOfMaxAppearances;
    }

    public void setNumOfMaxAppearances(int numOfMaxAppearances) {
        this.numOfMaxAppearances = numOfMaxAppearances;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public int getNumOfCategories() {
        return numOfCategories;
    }

    public void setNumOfCategories(int numOfCategories) {
        this.numOfCategories = numOfCategories;
    }

}
