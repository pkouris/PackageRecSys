/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommend_and_evaluation;

import entityBasicClasses.CategoryPopularityOverallRating;
import entityBasicClasses.ItemRatingCost;
import entityBasicClasses.Item_withCost;
import entityGraphClasses.EdgeCategorySink_v2;
import entityGraphClasses.EdgeItemCategory_v2;
import entityGraphClasses.EdgeItemSource_v2;
import entityGraphClasses.EdgeSinkSource_v2;
import entityGraphClasses.VertexCategory_v2;
import entityGraphClasses.VertexItem_v2;
import entityGraphClasses.VertexSink_v2;
import entityGraphClasses.VertexSource_v2;
import static recommend_and_evaluation.A_Start.movies_hashTable;
import static recommend_and_evaluation.A_Start.ratingsPerUser_hashTable;
import static recommend_and_evaluation.A_Start.selectedUserID;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import static recommend_and_evaluation.D_ItemsAllocationToCategories.numOfItemsPerPackage;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class C_ProblemModeling {

    static public List<VertexItem_v2> vertexItem_list = new ArrayList<>();
    static public List<VertexCategory_v2> vertexCategory_list = new ArrayList<>();
    static public List<VertexCategory_v2> vertexTopCategory_list = new ArrayList<>();
    static public List<VertexItem_v2> vertexItemOfTopCategories_list = new ArrayList<>();

    //static public List<VertexCategory> vertexCategoryHidden_list = new ArrayList<>();
    //static public List<VertexItem> vertexItemHedden_list = new ArrayList<>();
    static public List<CategoryPopularityOverallRating> categoriesPopularity_list = new ArrayList<>();
    //Parameters
    static public int numOfTopCategories = 3; //it can change via user interface, default value is 4
    static public int numOfPackages = 10;
    static public int numOfItemsPerCategory = 1;
    static public int numOfMaxAppearancesPerItem = -1; //-1:it is not taken into account, 0: read from file, 1,2,3...: it appears 1 or 2 ... times  
    static public int modelOfCreatingPackages = 2;
    static public VertexSource_v2 vertexSource;
    static public VertexSink_v2 vertexSink;
    static public Integer[] itemsAllocationToCategories = null;
    static public int categoryPopularity_mode = 1;
    static public String categoryPopularity_mode_str = "Based on the number of items per category";
    static public int packageSize = 0; //if package Size>0 then the items are allocated to categories proportionally
    static public double packageCost = 60.0; //for movies it is minutes 

    public C_ProblemModeling() {
    }

    //only this method needs for the next step that incudes all the graph
    public void loadGraphOfTopCategories(int userId) {
        try {
            loadGraph(userId);
            this.loadTopCategories(this.numOfTopCategories);
            this.loadItemsOfTopCategories();
            this.loadSourceSinkNodesOfTopCategories();
            //this.cb_numOfCategories.setSelectedIndex((ProblemModeling.numOfTopCategories - 1));
            if (numOfItemsPerCategory > 0) { //load the num of items per category to itemsAllocationToCategories[]
                Integer[] itemsAllocation = new Integer[numOfTopCategories];
                for (int i = 0; i < numOfTopCategories; i++) {
                    itemsAllocation[i] = numOfItemsPerCategory;
                }
                itemsAllocationToCategories = itemsAllocation;
            } else //
            {
                if (itemsAllocationToCategories == null) {
                    this.numOfItemsPerCategory = 1;
                    //this.cb_numOfItemsPerCategory.setSelectedIndex(1);
                    Integer[] itemsAllocation = new Integer[numOfTopCategories];
                    for (int i = 0; i < numOfTopCategories; i++) {
                        itemsAllocation[i] = numOfItemsPerCategory;
                    }
                    itemsAllocationToCategories = itemsAllocation;
                }
            }
            // this.printItemNodes(vertexItemOfTopCategories_list);
            // this.printCategoryNodes(vertexTopCategory_list);
            // this.printSourceNode();
            // this.printSinkNode();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void print_NodesOfTopCategoriesGraph() {
        printSourceNode();
        printItemNodes(vertexItemOfTopCategories_list);
        printCategoryNodes(vertexTopCategory_list);
        printSinkNode();
    }

    public void loadGraph(int userId) {
        try {
            if (numOfItemsPerCategory == 0) {
                if (packageSize > numOfItemsPerPackage) {
                    packageSize = 0;
                }
            } else if (packageSize > numOfItemsPerCategory * numOfTopCategories) {
                packageSize = 0;
            }

            this.createGraph(userId);
            //this.printItemNodes(vertexItem_list);
            //this.printCategoryNodes(vertexCategory_list);
            //this.printSourceNode();
            //this.printSinkNode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //it loads to the vertexTopCategory_list that the Category nodes have the most films.
    //it loads the (numOfCategories) most popular categories.
    //categoryPopularity_mode = 0 --> according to the number of viewed movies per category, 
    //categoryPopularity_mode = 1 --> according to the overall ratings, 
    //categoryPopularity_mode = 2 --> according to the the number of predicted movies per category. 
    public void loadTopCategories(int numOfCategories) {
        try {
            vertexTopCategory_list.clear();
            categoriesPopularity_list.clear();
            if (categoryPopularity_mode == 2) {
                int count = 0;
                Iterator<VertexCategory_v2> itr = vertexCategory_list.iterator();
                while (itr.hasNext()) {
                    VertexCategory_v2 vertexCategory_temp = new VertexCategory_v2();
                    vertexCategory_temp = itr.next();
                    if (count < numOfCategories) {
                        count++;
                        vertexTopCategory_list.add(vertexCategory_temp);
                        CategoryPopularityOverallRating temp = new CategoryPopularityOverallRating(vertexCategory_temp.getCategoryID(), vertexCategory_temp.getMoveisPerCategory(), 0.0);
                        categoriesPopularity_list.add(temp);
                    } else {
                        break;
                    }
                }
                numOfTopCategories = count;
            } else {
                HashMap<String, CategoryPopularityOverallRating> popularityOfCategories_hashMap = new HashMap<>();
                List<ItemRatingCost> movieRatingCostList = ratingsPerUser_hashTable.get(selectedUserID).getItemRatingCostList();
                // int movieIndex = 0;
                Iterator<ItemRatingCost> itr = movieRatingCostList.iterator();
                while (itr.hasNext()) {
                    ItemRatingCost movieRating_temp = itr.next();
                    int movieID = movieRating_temp.getItemID();
                    double rating = movieRating_temp.getRating();
                    Item_withCost movie = movies_hashTable.get(movieID);
                    String[] genres = movie.getCategories();
                    //creating the category nodes
                    for (String category : genres) {
                        CategoryPopularityOverallRating categoryPopularity = popularityOfCategories_hashMap.get(category);
                        if (categoryPopularity == null) {
                            CategoryPopularityOverallRating temp = new CategoryPopularityOverallRating(category, 1, rating);
                            popularityOfCategories_hashMap.put(category, temp);
                        } else {
                            int newPopulation = categoryPopularity.getPopularity() + 1;
                            double newOverallRating = categoryPopularity.getOverallRating() + rating;
                            CategoryPopularityOverallRating temp = new CategoryPopularityOverallRating(category, newPopulation, newOverallRating);
                            popularityOfCategories_hashMap.put(category, temp);
                        }
                    }
                }

                Iterator<String> keySetIterator = popularityOfCategories_hashMap.keySet().iterator();
                //Enumeration<String> keys = popularityOfCategories_hashtable.keys();
                // while (keys.hasMoreElements()) {
                while (keySetIterator.hasNext()) {
                    String category = keySetIterator.next();
                    CategoryPopularityOverallRating temp = popularityOfCategories_hashMap.get(category);
                    categoriesPopularity_list.add(temp);
                }

                if (categoryPopularity_mode == 0) {
                    //sorting descending based on the number of items per category
                    Collections.sort(categoriesPopularity_list, new Comparator<CategoryPopularityOverallRating>() {
                        public int compare(CategoryPopularityOverallRating o1, CategoryPopularityOverallRating o2) {
                            return o2.getPopularity() - o1.getPopularity();
                        }
                    });
                } else if (categoryPopularity_mode == 1) {
                    //sorting descending based on the overall rating
                    Collections.sort(categoriesPopularity_list, new Comparator<CategoryPopularityOverallRating>() {
                        public int compare(CategoryPopularityOverallRating o1, CategoryPopularityOverallRating o2) {
                            return (int) ((o2.getOverallRating() - o1.getOverallRating()) * 1000.0);
                        }
                    });
                }
                /////////////////
                // for (int i = 0; i < categoriesPopularity_list.size(); i++) {
                //      System.out.println("category = " + categoriesPopularity_list.get(i).getCategory()
                //              + " Population = " + categoriesPopularity_list.get(i).getPopularity()
                //              + " Population = " + categoriesPopularity_list.get(i).getOverallRating());
                //  }
                //////////////////
                int count = 0;
                Iterator<CategoryPopularityOverallRating> itr_cpl = categoriesPopularity_list.iterator();
                while (itr_cpl.hasNext()) {
                    String category = itr_cpl.next().getCategory();
                    Iterator<VertexCategory_v2> itr_vcl = vertexCategory_list.iterator();
                    while (itr_vcl.hasNext()) {
                        VertexCategory_v2 vertexCategory_temp = itr_vcl.next();
                        if (category.equals(vertexCategory_temp.getCategoryID())) {
                            vertexTopCategory_list.add(vertexCategory_temp);
                            count++;
                            break;
                        }
                    }
                    if (count == numOfCategories) {
                        break;
                    }
                }
                numOfTopCategories = count;
                ///////////////////////////
                //System.out.println(this.numOfTopCategories);
                //////////////////////
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    //it loads to the vertexMoviesOfTopCategoris_list that the movie nodes have categories of the vertexTocCategory_list.   
    public void loadItemsOfTopCategories() {
        //remove list
        Iterator<VertexItem_v2> itr1 = vertexItemOfTopCategories_list.iterator();
        while (itr1.hasNext()) { //remove all contents of list
            this.vertexItemOfTopCategories_list.remove(0);
        }
        //add nodes to vertexMovieOfTopCategories_list
        int sizeOf_vertexMovieList = vertexItem_list.size();
        for (int i = 0; i < sizeOf_vertexMovieList; i++) {
            VertexItem_v2 vertexMovie = new VertexItem_v2();
            vertexMovie = vertexItem_list.get(i);
            if (existenceOfCategory(vertexMovie) == 1) {
                vertexItemOfTopCategories_list.add(vertexMovie);
            }
        }
    }

    public void loadSourceSinkNodesOfTopCategories() {
        //creating the Sink Node
        List<EdgeCategorySink_v2> edgeCategorySink_list = new ArrayList<EdgeCategorySink_v2>();
        Iterator<VertexCategory_v2> itrC = vertexTopCategory_list.iterator();
        int sinkSourceMaxFlow = 0;
        while (itrC.hasNext()) {
            VertexCategory_v2 vertexCategory_temp = new VertexCategory_v2();
            vertexCategory_temp = itrC.next();
            //String categoryID = vertexCategory_temp.getCategoryID();
            EdgeCategorySink_v2 edgeCategorySink = new EdgeCategorySink_v2();
            edgeCategorySink = vertexCategory_temp.getEdgeCategorySink();
            edgeCategorySink_list.add(edgeCategorySink);
            sinkSourceMaxFlow += edgeCategorySink.getMaxFlow();
        }
        EdgeSinkSource_v2 edgeSinkSource_sink = new EdgeSinkSource_v2(0.0, sinkSourceMaxFlow, 0);
        this.vertexSink = new VertexSink_v2(edgeSinkSource_sink, edgeCategorySink_list);
        //creating the Source Node
        List<EdgeItemSource_v2> edgeMovieSource_list = new ArrayList<EdgeItemSource_v2>();
        Iterator<VertexItem_v2> itrM = vertexItemOfTopCategories_list.iterator();
        while (itrM.hasNext()) {
            VertexItem_v2 vertexMovie_temp = new VertexItem_v2();
            vertexMovie_temp = itrM.next();
            //int movieID = vertexMovie_temp.getMovieID();
            EdgeItemSource_v2 edgeMovieSource = new EdgeItemSource_v2();
            edgeMovieSource = vertexMovie_temp.getEdgeMoveSource();
            edgeMovieSource_list.add(edgeMovieSource);
        }
        EdgeSinkSource_v2 edgeSinkSource_source = new EdgeSinkSource_v2();
        edgeSinkSource_source = edgeSinkSource_sink;
        this.vertexSource = new VertexSource_v2(edgeSinkSource_source, edgeMovieSource_list);
    }

    public void createGraph(int userID) {
        try {
            vertexItem_list.clear(); //clear vertexMovie_list
            vertexCategory_list.clear(); //clear vertexCategory_list

            //creating movie nodes
            List<ItemRatingCost> movieRatingsList = new ArrayList<>();
            movieRatingsList = B_RunningMode.predictedItemRatingCostWithPopularity_list;
            // int movieIndex = 0;
            Iterator<ItemRatingCost> itr = movieRatingsList.iterator();
            while (itr.hasNext()) {
                // MovieRating movieRating_temp = new MovieRating();
                ItemRatingCost movieRating_temp = itr.next();
                int movieID = movieRating_temp.getItemID();
                double rating = movieRating_temp.getRating();
                double flow_cost = 1.0 / ((double) rating);
                double item_cost = movieRating_temp.getCost();
                ////////////////////////////////
                //System.out.println("item_cost## " + item_cost);
                int maxFlow = 1;
                int minFlow = 0;
                //Movie movie = new Movie();
                Item_withCost movie = movies_hashTable.get(movieID);
                //double item_cost = movie.getCost();
                String[] genres = movie.getCategories();
                int numOfMaxAppearances = movie.getNumOfMaxAppearances();

                //creating the Movie Nodes
                //creating the edgeMovieCategory_list
                List<EdgeItemCategory_v2> edgeMovieCategory_list = new ArrayList<>();
                //String[] categories = new String[15]; //all gentres are less than 15
                for (String category : genres) {
                    EdgeItemCategory_v2 edgeMovieCategory_temp = new EdgeItemCategory_v2(movieID, category, rating, flow_cost, item_cost, maxFlow, minFlow);
                    edgeMovieCategory_list.add(edgeMovieCategory_temp);
                    
                }
                //creating the edge movie-source
                EdgeItemSource_v2 edgeMovieSource = new EdgeItemSource_v2(movieID, 0.0, 0.0, 1, 0);

                //movieIndex++; //the position of movie in the list, movieIndex = {1, 2, 3,...}
                //creating the vertex of this film
                VertexItem_v2 vertexMovie = new VertexItem_v2(movieID, numOfMaxAppearances, edgeMovieCategory_list, edgeMovieSource);
                //Add the vertex of movie to the vertexMovie_list
                vertexItem_list.add(vertexMovie);
                ///////////////////////
                //int si = vertexMovie.getEdgeItemCategory_list().size();
               // for (int k=0; k<si; k++){
               // System.out.println("itemID## " + vertexMovie.getEdgeItemCategory_list().get(k).getItemID()
               // + "item cost## " + vertexMovie.getEdgeItemCategory_list().get(k).getItem_cost());
               // }
                /////////////////////////////////

                //creating the category nodes
                for (String category : genres) {
                    int size = vertexCategory_list.size();
                    //System.out.println(size);
                    if (size == 0) { //The vertexCategory is null
                        List<EdgeItemCategory_v2> edgeMovieCategory_list_temp = new ArrayList<>();
                        //VertexCategory vertexCategory = new VertexCategory();
                        //edgeMovieCategory_list_temp = vertexCategory.getEdgeMovieCategory_list();
                        EdgeItemCategory_v2 edgeMovieCategory_2 = new EdgeItemCategory_v2(movieID, category, rating, flow_cost, item_cost, maxFlow, minFlow);
                        edgeMovieCategory_list_temp.add(edgeMovieCategory_2);
                        EdgeCategorySink_v2 edgeCategorySink = new EdgeCategorySink_v2(category, 0.0, 0.0, numOfItemsPerCategory, numOfItemsPerCategory);///////////
                        VertexCategory_v2 vc = new VertexCategory_v2(category, edgeMovieCategory_list_temp, edgeCategorySink);
                        vertexCategory_list.add(vc);
                    } else {
                        int flag = 1;
                        for (int i = 0; i < size; i++) { //searh for the existence of this genre in the list
                            if ((vertexCategory_list.get(i).getCategoryID()).equals(category)) { //this genre exists in the list
                                //List<EdgeMovieCategory> edgeMovieCategory_list_temp = new ArrayList<>();
                                //VertexCategory vertexCategory = new VertexCategory();
                                VertexCategory_v2 vertexCategory = vertexCategory_list.get(i);
                                List<EdgeItemCategory_v2> edgeMovieCategory_list_temp = vertexCategory.getEdgeMovieCategory_list();
                                EdgeItemCategory_v2 edgeMovieCategory_2 = new EdgeItemCategory_v2(movieID, category, rating, flow_cost, item_cost, maxFlow, minFlow);
                                edgeMovieCategory_list_temp.add(edgeMovieCategory_2);
                                VertexCategory_v2 vc = new VertexCategory_v2(category, edgeMovieCategory_list_temp, vertexCategory.getEdgeCategorySink());
                                vertexCategory_list.remove(i);
                                vertexCategory_list.add(vc);
                                i = size + 1;
                                flag = 0;
                            }
                        }
                        if (flag == 1) { //this genre does not exists in the list 
                            List<EdgeItemCategory_v2> edgeMovieCategory_list_temp = new ArrayList<>();
                            //VertexCategory vertexCategory = new VertexCategory();
                            //edgeMovieCategory_list_temp = vertexCategory.getEdgeMovieCategory_list();
                            EdgeItemCategory_v2 edgeMovieCategory_2 = new EdgeItemCategory_v2(movieID, category, rating, flow_cost, item_cost, maxFlow, minFlow);
                            edgeMovieCategory_list_temp.add(edgeMovieCategory_2);
                            EdgeCategorySink_v2 edgeCategorySink = new EdgeCategorySink_v2(category, 0, 0, numOfItemsPerCategory, numOfItemsPerCategory);////////////
                            VertexCategory_v2 vc = new VertexCategory_v2(category, edgeMovieCategory_list_temp, edgeCategorySink);
                            vertexCategory_list.add(vc);
                        }
                    }
                }
            }

            //creating the Sink Node
            List<EdgeCategorySink_v2> edgeCategorySink_list = new ArrayList<>();
            Iterator<VertexCategory_v2> itrC = vertexCategory_list.iterator();
            int sinkSourceMaxFlow = 0;
            while (itrC.hasNext()) {
                //VertexCategory vertexCategory_temp = new VertexCategory();
                VertexCategory_v2 vertexCategory_temp = itrC.next();
                //String categoryID = vertexCategory_temp.getCategoryID();

                //EdgeCategorySink edgeCategorySink = new EdgeCategorySink();
                EdgeCategorySink_v2 edgeCategorySink = vertexCategory_temp.getEdgeCategorySink();

                edgeCategorySink_list.add(edgeCategorySink);
                sinkSourceMaxFlow += edgeCategorySink.getMaxFlow();
            }
            EdgeSinkSource_v2 edgeSinkSource_sink = new EdgeSinkSource_v2(0.0, sinkSourceMaxFlow, sinkSourceMaxFlow);/////////////
            vertexSink = new VertexSink_v2(edgeSinkSource_sink, edgeCategorySink_list);

            //creating the Source Node
            List<EdgeItemSource_v2> edgeMovieSource_list = new ArrayList<>();
            Iterator<VertexItem_v2> itrM = vertexItem_list.iterator();
            while (itrM.hasNext()) {
                // VertexMovie vertexMovie_temp = new VertexMovie();
                VertexItem_v2 vertexMovie_temp = itrM.next();
                //int movieID = vertexMovie_temp.getMovieID();

                //EdgeMovieSource edgeMovieSource = new EdgeMovieSource();
                EdgeItemSource_v2 edgeMovieSource = vertexMovie_temp.getEdgeMoveSource();
                edgeMovieSource_list.add(edgeMovieSource);
            }
            //EdgeSinkSource edgeSinkSource_source = new EdgeSinkSource();
            EdgeSinkSource_v2 edgeSinkSource_source = edgeSinkSource_sink;
            vertexSource = new VertexSource_v2(edgeSinkSource_source, edgeMovieSource_list);

            //sorting based on number of movies per category
            Collections.sort(vertexCategory_list, new Comparator<VertexCategory_v2>() {
                public int compare(VertexCategory_v2 vc1, VertexCategory_v2 vc2) {
                    return vc2.getMoveisPerCategory() - vc1.getMoveisPerCategory();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    //it returns 1 if category of movie exists in vertexTopCategory_list else it returns 0
    public int existenceOfCategory(VertexItem_v2 vertexItem) {
        List<EdgeItemCategory_v2> eMC_list = new ArrayList<>();
        eMC_list = vertexItem.getEdgeItemCategory_list();
        int sizeOf_eMC = eMC_list.size();
        int sizeOf_vertexTopCategoryList = vertexTopCategory_list.size();
        for (int i = 0; i < sizeOf_eMC; i++) { //searh for the existence of this genre in the list
            for (int j = 0; j < sizeOf_vertexTopCategoryList; j++) {
                if ((vertexTopCategory_list.get(j).getCategoryID()).equals(eMC_list.get(i).getCategoryID())) { //this genre exists in the list
                    return 1;
                }
            }
        }
        return 0;
    }

    public void printItemNodes(List<VertexItem_v2> vertexItem_list) {
        int numOfMovieNodes = 0;
        Iterator<VertexItem_v2> itr = vertexItem_list.iterator();
        System.out.println("\nItem Nodes:\nno, movieID, sourceMovieEdge[flow cost, item cost, max flow, min flow], movieGenreEdge[category, rating, flowCost, itemCost, maxFlow, minFlow]");
        while (itr.hasNext()) {
            //VertexItem vertexMovie_temp = new VertexItem();
            VertexItem_v2 vertexMovie_temp = itr.next();
            int movieID = vertexMovie_temp.getItemID();
            // int movieIndex = vertexMovie_temp.getMovieIndex();

            //EdgeItemSource edgeMovieSource = new EdgeItemSource();
            EdgeItemSource_v2 edgeMovieSource = vertexMovie_temp.getEdgeMoveSource();

            List<EdgeItemCategory_v2> edgeMovieCategory_list = new ArrayList<>();
            edgeMovieCategory_list = vertexMovie_temp.getEdgeItemCategory_list();
            /*
             ///////////////
             System.out.print(movieID + " [ " + edgeMovieSource.getMovieID() + 
             ", " + edgeMovieSource.getCost() +  ", " 
             + edgeMovieSource.getMaxFlow() +  
             ", " + edgeMovieSource.getMinFlow() + " ], ");
             */
            String sourceMovieEdge_text = " [ " + edgeMovieSource.getFlow_cost()
                    + ", " + edgeMovieSource.getItem_cost() + ", "
                    + ", " + edgeMovieSource.getMaxFlow() + ", "
                    + edgeMovieSource.getMinFlow() + " ] ";
            String movieGenreEdge_text = "";

            Iterator<EdgeItemCategory_v2> iter = edgeMovieCategory_list.iterator();
            while (iter.hasNext()) {
                EdgeItemCategory_v2 edgeMovieCagegory_temp = new EdgeItemCategory_v2();
                edgeMovieCagegory_temp = iter.next();
                String category = edgeMovieCagegory_temp.getCategoryID();
                //int movieID_ = edgeMovieCagegory_temp.getMovieID();
                double rating = edgeMovieCagegory_temp.getRating();
                double flow_cost = edgeMovieCagegory_temp.getFlow_cost();
                double item_cost = edgeMovieCagegory_temp.getItem_cost();
                int maxFlow = edgeMovieCagegory_temp.getMaxFlow();
                int minFlow = edgeMovieCagegory_temp.getMinFlow();
                /*
                 //////////
                 System.out.print(" [ " + movieID_ + ", " + category + 
                 ", " + rating +  ", " 
                 + cost +  
                 ", " + maxFlow + 
                 ",  " + minFlow + "], ");
                 */
                movieGenreEdge_text += " [ " + category
                        + ", " + round(rating, 1) + ", "
                        + round(flow_cost, 2)
                        + ", " + round(item_cost, 2)
                        + ", " + maxFlow
                        + ",  " + minFlow + "] ";
            }
            numOfMovieNodes++;
            System.out.println(numOfMovieNodes + ", " + movieID + ", " + sourceMovieEdge_text + ", " + movieGenreEdge_text);
            // System.out.println(" ");
        }
    }

    public void printCategoryNodes(List<VertexCategory_v2> vertexCategory_list) {
        Iterator<VertexCategory_v2> itr = vertexCategory_list.iterator();
        int numOfCategoryNodes = 0;
        System.out.println("\nCategory Nodes:\nno, categoryID, moviesPerCategory, categorySinkEdge_text, movieCategoryEdge_text");
        while (itr.hasNext()) {
            VertexCategory_v2 vertexCategory_temp = new VertexCategory_v2();
            vertexCategory_temp = itr.next();
            // int categoryIndex = vertexCategory_temp.getCategoryIndex();
            String categoryID = vertexCategory_temp.getCategoryID();
            int moviesPerCategory = vertexCategory_temp.getMoveisPerCategory();

            EdgeCategorySink_v2 edgeCategorySink = new EdgeCategorySink_v2();
            edgeCategorySink = vertexCategory_temp.getEdgeCategorySink();

            List<EdgeItemCategory_v2> edgeMovieCategory_list = new ArrayList<EdgeItemCategory_v2>();
            edgeMovieCategory_list = vertexCategory_temp.getEdgeMovieCategory_list();
            /*
             ///////////////
             System.out.print(movieID + " [ " + edgeMovieSource.getMovieID() + 
             ", " + edgeMovieSource.getCost() +  ", " 
             + edgeMovieSource.getMaxFlow() +  
             ", " + edgeMovieSource.getMinFlow() + " ], ");
             */
            String categorySinkEdge_text = " [ " + edgeCategorySink.getFlow_cost()
                    + ", " + edgeCategorySink.getItem_cost()
                    + ", " + edgeCategorySink.getMaxFlow() + ", "
                    + edgeCategorySink.getMinFlow() + " ] ";
            String movieCategoryEdge_text = "";

            Iterator<EdgeItemCategory_v2> iter = edgeMovieCategory_list.iterator();
            while (iter.hasNext()) {
                EdgeItemCategory_v2 edgeMovieCagegory_temp = new EdgeItemCategory_v2();
                edgeMovieCagegory_temp = iter.next();
                String category = edgeMovieCagegory_temp.getCategoryID();
                int movieID_ = edgeMovieCagegory_temp.getItemID();
                double rating = edgeMovieCagegory_temp.getRating();
                double flow_cost = edgeMovieCagegory_temp.getFlow_cost();
                double item_cost = edgeMovieCagegory_temp.getItem_cost();
                int maxFlow = edgeMovieCagegory_temp.getMaxFlow();
                int minFlow = edgeMovieCagegory_temp.getMinFlow();
                /*
                 //////////
                 System.out.print(" [ " + movieID_ + ", " + category + 
                 ", " + rating +  ", " 
                 + cost +  
                 ", " + maxFlow + 
                 ",  " + minFlow + "], ");
                 */

                movieCategoryEdge_text += " [ " + movieID_
                        + ", " + round(rating, 1) + ", "
                        + round(flow_cost, 2)
                        + ", " + round(item_cost, 2)
                        + ", " + maxFlow
                        + ",  " + minFlow + "] ";

            }
            numOfCategoryNodes++;
            System.out.println(numOfCategoryNodes + "," + categoryID + "," + moviesPerCategory + "," + categorySinkEdge_text + "," + movieCategoryEdge_text);
            //System.out.println(" ");
        }
    }

    public void printSourceNode() {

        //    Integer b = tb_sourceNode.getRowCount();
        //  for (int i = 0; i < b; i++) {
        //    ((DefaultTableModel) tb_sourceNode.getModel()).removeRow(0);
        // }
        String sourceSinkEdge_text = " [ " + this.vertexSource.getEdgeSinkSource().getFlow_cost() 
                + ", " + this.vertexSource.getEdgeSinkSource().getMaxFlow() + ", "
                + this.vertexSource.getEdgeSinkSource().getMinFlow() + " ] ";
        String movieSourceEdge_text = "";

        List<EdgeItemSource_v2> edgeMovieSource_list = new ArrayList<EdgeItemSource_v2>();
        edgeMovieSource_list = this.vertexSource.getEdgeMovieSource_list();

        Iterator<EdgeItemSource_v2> iter = edgeMovieSource_list.iterator();
        while (iter.hasNext()) {
            EdgeItemSource_v2 edgeMovieSource_temp = new EdgeItemSource_v2();
            edgeMovieSource_temp = iter.next();
            int movieID = edgeMovieSource_temp.getItemID();
            double flow_cost = edgeMovieSource_temp.getFlow_cost();
            double item_cost = edgeMovieSource_temp.getItem_cost();
            int maxFlow = edgeMovieSource_temp.getMaxFlow();
            int minFlow = edgeMovieSource_temp.getMinFlow();
            movieSourceEdge_text += " [ " + movieID
                    + ", " + round(flow_cost, 2)
                    + ", " + round(item_cost, 2)
                    + ", " + maxFlow
                    + ",  " + minFlow + "] ";
        }
        System.out.println("\nSource Node:\nsourceSinkEdge[flow_cost, max flow, min flow], movieSourceEdge[itemId, flow_cost, item_cost, max_flow, Min_flow]");
        System.out.println(sourceSinkEdge_text + "," + movieSourceEdge_text);
    }

    public void printSinkNode() {
        //  Integer b = tb_sinkNode.getRowCount();
        //  for (int i = 0; i < b; i++) {
        //    ((DefaultTableModel) tb_sinkNode.getModel()).removeRow(0);
        //  }

        String sourceSinkEdge_text = " [ " + this.vertexSink.getEdgeSinkSource().getFlow_cost()
                + ", " + this.vertexSink.getEdgeSinkSource().getMaxFlow() + ", "
                + this.vertexSink.getEdgeSinkSource().getMinFlow() + " ] ";
        String categorySinkEdge_text = "";

        List<EdgeCategorySink_v2> edgeCategorySink_list = new ArrayList<EdgeCategorySink_v2>();
        edgeCategorySink_list = this.vertexSink.getEdgeCategorySink_list();

        Iterator<EdgeCategorySink_v2> iter = edgeCategorySink_list.iterator();
        while (iter.hasNext()) {
            EdgeCategorySink_v2 edgeCategorySink_temp = new EdgeCategorySink_v2();
            edgeCategorySink_temp = iter.next();
            String categoryID = edgeCategorySink_temp.getCategoryID();
            double flow_cost = edgeCategorySink_temp.getFlow_cost();
            double item_cost = edgeCategorySink_temp.getItem_cost();
            int maxFlow = edgeCategorySink_temp.getMaxFlow();
            int minFlow = edgeCategorySink_temp.getMinFlow();
            categorySinkEdge_text += " [ " + categoryID
                    + ", " + round(flow_cost, 2)
                    + ", " + round(item_cost, 2)
                    + ", " + maxFlow
                    + ",  " + minFlow + "] ";
        }
        System.out.println("\nSinkNode:\nsourceSinkEdge_text, categorySinkEdge_text");
        System.out.println(sourceSinkEdge_text + "," + categorySinkEdge_text);
    }

    //it rounds the double number in specific number of decimals
    public double round(double number, int decimals) {
        if (decimals < 0) {
            return number; //no change in number
        }
        long factor = (long) Math.pow(10, decimals);
        number = number * factor;
        long tmp = Math.round(number);
        return (double) tmp / factor;
    }

}
