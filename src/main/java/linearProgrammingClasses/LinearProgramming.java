
package linearProgrammingClasses;

import formsAndFunctionality.ProblemModelingForm;
import java.util.ArrayList;
import java.util.List;
import entityGraphClasses.EdgeCategorySink;
import entityGraphClasses.EdgeItemCategory;
import entityGraphClasses.EdgeItemSource;
import ilog.concert.*;
import ilog.cplex.*;
import java.awt.Component;
import javax.swing.JOptionPane;
/**
 * @author: Panagiotis Kouris
 * date: Nov 2015
 */
public class LinearProgramming {
/*
    public void main(String[] args) {
        //linearProgrammingCalculator();
        return;
    }
*/
    

      //it is executed for one until N packages. it uses the CPLEX system
     public SolutionType linearProgrammingCalc() {
        try {
            int numOfPackages = ProblemModelingForm.numOfPackages;
            int numOfItemsPerCategory = ProblemModelingForm.numOfItemsPerCategory;
            int numOfNodes = 0;
            int numOfEdges = 0;

            int numOfMovieNodes = 0;
            int numOfMovieCategoryEdges = 0;
            int numOfCategoryNodes = 0;

            numOfMovieNodes = ProblemModelingForm.vertexItemOfTopCategories_list.size();
            numOfCategoryNodes += ProblemModelingForm.vertexTopCategory_list.size();
            numOfMovieCategoryEdges += ProblemModelingForm.vertexTopCategory_list.size() * ProblemModelingForm.vertexItemOfTopCategories_list.size();

            numOfNodes += numOfMovieNodes + numOfCategoryNodes + 2; //it is the maximum number of Edges
            numOfEdges += numOfMovieNodes + numOfMovieCategoryEdges + numOfCategoryNodes + 1;
            ///////////////
            //System.out.println(numOfEdges);
           // System.out.println(numOfNodes);
            /////////////

            //name of Edges and Cost of Edges
            String[] ee = new String[numOfEdges]; //name of Edges
            double[] cc = new double[numOfEdges]; //Cost of Edges
            int index = 0;
            //1st level edges: source-movie edges
            for (int i = 0; i < numOfMovieNodes; i++) {
                ee[index] = "source_" + ProblemModelingForm.vertexItemOfTopCategories_list.get(i).getItemID();
                cc[index] = ProblemModelingForm.vertexItemOfTopCategories_list.get(i).getEdgeMoveSource().getCost();
                index++;
            }
            //2nd level edges: movie-Category edges
            for (int i = 0; i < numOfCategoryNodes; i++) {
                List<EdgeItemCategory> edgeMovieCategory_list_temp = new ArrayList<>();
                edgeMovieCategory_list_temp = ProblemModelingForm.vertexTopCategory_list.get(i).getEdgeMovieCategory_list();
                int size = edgeMovieCategory_list_temp.size();
                for (int j = 0; j < size; j++) {
                    ee[index] = edgeMovieCategory_list_temp.get(j).getItemID() + "_" + edgeMovieCategory_list_temp.get(j).getCategoryID();
                    cc[index] = edgeMovieCategory_list_temp.get(j).getCost();
                    index++;
                }
            }
            //3rd level edges: category-sink edges
            for (int i = 0; i < numOfCategoryNodes; i++) {
                ee[index] = ProblemModelingForm.vertexTopCategory_list.get(i).getCategoryID() + "_sink";
                cc[index] = ProblemModelingForm.vertexTopCategory_list.get(i).getEdgeCategorySink().getCost();
                index++;
            }
            //4th level edges: source-sink edge
            ee[index] = "sink_source";
            cc[index] = 0.0;
            index++;

            numOfEdges = index; //Actual number of edges

            //////////////////
            //System.out.println("E[i] :: C[i]:");

            String[] E = new String[numOfEdges]; //name of Edges, names of Variables
            double[] C = new double[numOfEdges]; //Cost of Edges
            for (int i = 0; i < numOfEdges; i++) {
                C[i] = cc[i];
                E[i] = ee[i];
                //////////////////////////
                //System.out.println(E[i] + " :: " + C[i]);
            }

            //A table for equations AX=0
            double[][] A = new double[numOfNodes][numOfEdges];
            for (int i = 0; i < numOfNodes; i++) {
                for (int j = 0; j < numOfEdges; j++) {
                    A[i][j] = 0.0;
                }
            }

            //movie nodes
            index = 0;
            for (int i = 0; i < numOfMovieNodes; i++) {
                A[index][index] = 1.0;
                List<EdgeItemCategory> edgeMovieCategory_list_temp = new ArrayList<>();
                edgeMovieCategory_list_temp = ProblemModelingForm.vertexItemOfTopCategories_list.get(i).getEdgeItemCategory_list();
                int size = edgeMovieCategory_list_temp.size();
                for (int j = 0; j < size; j++) {
                    String str = edgeMovieCategory_list_temp.get(j).getItemID() + "_" + edgeMovieCategory_list_temp.get(j).getCategoryID();
                    int position = indexOfStrInTable(E, str, numOfEdges);
                    if (position > -1) {
                        A[i][position] = -1.0;
                    }
                }
                index++;
            }

            //Category Nodes
            for (int i = 0; i < numOfCategoryNodes; i++) {
                List<EdgeItemCategory> edgeMovieCategory_list_temp = new ArrayList<>();
                edgeMovieCategory_list_temp = ProblemModelingForm.vertexTopCategory_list.get(i).getEdgeMovieCategory_list();
                int size = edgeMovieCategory_list_temp.size();
                for (int j = 0; j < size; j++) {
                    String str = edgeMovieCategory_list_temp.get(j).getItemID() + "_" + edgeMovieCategory_list_temp.get(j).getCategoryID();
                    int position = indexOfStrInTable(E, str, numOfEdges);
                    if (position > -1) {
                        A[index][position] = 1.0;
                    }
                }
                EdgeCategorySink edgeCategorySink_temp = ProblemModelingForm.vertexTopCategory_list.get(i).getEdgeCategorySink();
                String str2 = edgeCategorySink_temp.getCategoryID() + "_sink";
                int position = indexOfStrInTable(E, str2, numOfEdges);
                if (position > -1) {
                    A[index][position] = (double) -1.0;
                }
                index++;
            }

            //Sink Node
            List<EdgeCategorySink> edgeCategorySink_list_temp = new ArrayList<>();
            edgeCategorySink_list_temp = ProblemModelingForm.vertexSink.getEdgeCategorySink_list();
            int size = edgeCategorySink_list_temp.size();
            for (int j = 0; j < size; j++) {
                String str = edgeCategorySink_list_temp.get(j).getCategoryID() + "_sink";
                int position = indexOfStrInTable(E, str, numOfEdges);
                if (position > -1) {
                    A[index][position] = (double) 1.0;
                }
            }
            A[index][numOfEdges - 1] = -1.0;
            index++;

            //Source Node
            List<EdgeItemSource> edgeMovieSource_list_temp = new ArrayList<>();
            edgeMovieSource_list_temp = ProblemModelingForm.vertexSource.getEdgeMovieSource_list();
            size = edgeMovieSource_list_temp.size();
            for (int j = 0; j < size; j++) {
                String str = "source_" + edgeMovieSource_list_temp.get(j).getItemID();
                int position = indexOfStrInTable(E, str, numOfEdges);
                if (position > -1) {
                    A[index][position] = -1.0;
                }
            }
            A[index][numOfEdges - 1] = 1.0;
            index++;

            //////////////////////
            /*
            System.out.println("A:");
            for (int i = 0; i < numOfNodes; i++) {
                for (int j = 0; j < numOfEdges; j++) {
                    System.out.print(A[i][j] + " ");
                }
                System.out.println();
            }
            */
        ///////////////////////

            
            
            //CPLEX
            //try {
                IloCplex cplex = new IloCplex();
                // create model and solve it
                Integer overallItemsOfCategories = 0;
                double[] lowerBound = new double[numOfEdges];
                double[] upperBound = new double[numOfEdges];
                for (int i = 0; i < numOfEdges; i++) {
                    if (i < (numOfEdges - numOfCategoryNodes - 1)) { //source-movie and movie-category edges 
                        lowerBound[i] = 0.0; //Xi >= 0
                        //upperBound[i] = (double) ProblemModelingForm.numOfMaxAppearancesPerItem;  //Xi <= numOfMaxAppearancesPerItem
                        upperBound[i] = 1.0;  //Xi <= 1
                    } else if (i < (numOfEdges - 1)) { //Xcategory-sink = numOfPackages
                        lowerBound[i] = 0.0;
                        //lowerBound[i] = (double) ProblemModelingForm.itemsAllocationToCategories[-1*(numOfEdges-1-i-ProblemModelingForm.numOfTopCategories)];
                        upperBound[i] = (double) ProblemModelingForm.itemsAllocationToCategories[-1*(numOfEdges-1-i-ProblemModelingForm.numOfTopCategories)];
                        overallItemsOfCategories +=  ProblemModelingForm.itemsAllocationToCategories[-1*(numOfEdges-1-i-ProblemModelingForm.numOfTopCategories)];                    
                        //lowerBound[i] = (double) numOfItemsPerCategory;
                        //upperBound[i] = (double) numOfItemsPerCategory;
                    } else { //0.0 <= Xsource-sink <= numOfCategoryNodes*numOfPackages
                        /////////////////////////////////////////
                        System.out.println("overallItemsOfCategories= " + overallItemsOfCategories);
                        if(formsAndFunctionality.ProblemModelingForm.packageSize == 0){
                            lowerBound[i] = (double) overallItemsOfCategories;
                            upperBound[i] = (double) overallItemsOfCategories;
                        }
                        else{
                            lowerBound[i] = (double) formsAndFunctionality.ProblemModelingForm.packageSize;
                            upperBound[i] = (double) formsAndFunctionality.ProblemModelingForm.packageSize;
                        }
                    }
                }
                //Xi variables
                IloNumVar[] x = cplex.numVarArray(numOfEdges, lowerBound, upperBound); //0.0 <= Xi < =1.0 
                //Objective function 
                cplex.addMinimize(cplex.scalProd(x, C)); 
            
                //constraints.add(new LinearConstraint(A[i], Relationship.EQ, 0.0)); //AX=0
                for (int i = 0; i < numOfNodes; i++) {
                    IloLinearNumExpr lhs = cplex.linearNumExpr(); //lhs as in left hand side
                    for (int j = 0; j < numOfEdges; j++) {
                        lhs.addTerm(A[i][j], x[j]);
                    }
                    IloRange con = cplex.addEq(lhs, 0.0);
                    //con.setName("yourConstraintName(" + i + ")");
                }
                
                double[] sol = new double[numOfEdges];
                
                if (cplex.solve()) {
                    System.out.println("Solution status = " + cplex.getStatus());
                    System.out.println("Solution value = " + cplex.getObjValue());
                    sol = cplex.getValues(x);
                   // int ncols = cplex.getNcols();
                   // for (int j = 0; j < ncols; ++j) {
                   //     System.out.println("Column: " + j + " Value = " + sol[j]);
                   // }
                    cplex.end();
                }
                else{
                    cplex.end();
                    return null;
                }
                
            SolutionType solution = new SolutionType(E, sol, C);
            return solution;

        } catch (Exception e) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Error: "+e, "Message", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(frame, "Select a valid rating file please!", "Message", JOptionPane.ERROR_MESSAGE);
            System.err.println("Concert exception caught: " + e);
            System.out.println("Linear Programming Calculator returned null");
            return null;
        }
    }
    
        //it returns the index of the table or -1
    public int indexOfStrInTable(String[] str_table, String str, int size_of_table) {
        //int len = str_table.length;
        int len = size_of_table;
        for (int i = 0; i < len; i++) {
            if (str.equals(str_table[i])) {
                return i;
            }
        }
        return -1;
    }

    
}
