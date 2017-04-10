package collaborativeFiltering;

import entityBasicClasses.ItemRating;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * @author: Panagiotis Kouris
 * date: Dec 2015
 */
public class CollaborativeFiltering {

    public CollaborativeFiltering() {
    }
 
    //item based collaboriative filtering
    public List<ItemRating> itemBasedColFiltering(int userId, int numOfRecommendations, String dataset) {
        List<ItemRating> movieRating_list = new ArrayList<>();
        try {
            // Data model created to accept the input file
            FileDataModel dataModel = new FileDataModel(new File(dataset));
            // ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
            ItemSimilarity itemSimilarity = new TanimotoCoefficientSimilarity(dataModel); //Specifies the Similarity algorithm
            ItemBasedRecommender itemBasedRecommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
            //recommend method generates the recommendations
            List<RecommendedItem> recommendedItems_list = itemBasedRecommender.recommend(userId, numOfRecommendations);
            ////////////
            //int index = 0;
            //add the recommendations to movieRating_list
            for (RecommendedItem recommendedItem : recommendedItems_list) {
                ItemRating movieRating_temp = new ItemRating((int) recommendedItem.getItemID(), (double) recommendedItem.getValue());
                movieRating_list.add(movieRating_temp);
                /////////////////////////
                //System.out.println(index++ + " " + recommendedItem.getItemID() + " " + recommendedItem.getValue());
                /////////////////////////
            }
            return movieRating_list;
        } catch (TasteException e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }

    
      
      
    //user based collaboriative filtering
    public List<ItemRating> userBasedColFiltering(int userId, int numOfRecommendations, String dataset) {
        List<ItemRating> movieRating_list = new ArrayList<>();
        try {
            //Data model created to accept the input file
            FileDataModel dataModel = new FileDataModel(new File(dataset));
            //The Similarity algorithm
            UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
            //NearestNUserNeighborhood is preferred in situations where we need to have control on the exact no of neighbors
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, userSimilarity, dataModel);
            //Initalizing the recommender 
            Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
            //Generating recommendations
            List<RecommendedItem> recommendedItem_list = recommender.recommend(userId, numOfRecommendations);
            for (RecommendedItem recommendedItem : recommendedItem_list) {
                //////////////
                //System.out.println(recommendedItem.getItemID());
                ///////////////
                ItemRating movieRating_temp = new ItemRating((int) recommendedItem.getItemID(), (double) recommendedItem.getValue());
                movieRating_list.add(movieRating_temp);
            }
            return movieRating_list;
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        } catch (TasteException e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
    
    
}
