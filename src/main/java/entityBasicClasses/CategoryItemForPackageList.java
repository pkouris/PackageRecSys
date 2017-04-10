/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityBasicClasses;

import java.util.List;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class CategoryItemForPackageList {

    private String category;
    private List<ItemForPackage_v2> itemForPackage_list;

    public CategoryItemForPackageList(String category, List<ItemForPackage_v2> itemForPackage_list) {
        this.category = category;
        this.itemForPackage_list = itemForPackage_list;
    }

    public CategoryItemForPackageList() {
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ItemForPackage_v2> getItemForPackage_list() {
        return itemForPackage_list;
    }

    public void setItemForPackage_list(List<ItemForPackage_v2> itemForPackage_list) {
        this.itemForPackage_list = itemForPackage_list;
    }

    
    
    
}
