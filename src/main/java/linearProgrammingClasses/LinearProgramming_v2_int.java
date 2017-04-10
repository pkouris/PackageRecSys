package linearProgrammingClasses;

import entityGraphClasses.EdgeCategorySink_v2;
import entityGraphClasses.EdgeItemCategory_v2;
import entityGraphClasses.EdgeItemSource_v2;
import ilog.concert.*;
import ilog.cplex.*;
import java.util.ArrayList;
import java.util.List;
import recommend_and_evaluation.C_ProblemModeling;

/**
 * @author Panagiotis Kouris
 * @date Mar 2017
 */
public class LinearProgramming_v2_int {

    /*
    public void main(String[] args) {
        //linearProgrammingCalculator();
        return;
    }
     */
//it is executed for one until N packages. it uses the CPLEX system
    public SolutionType_v2 linearProgrammingCalc() {
        try {
            int numOfPackages = C_ProblemModeling.numOfPackages;
            int numOfItemsPerCategory = C_ProblemModeling.numOfItemsPerCategory;
            int numOfNodes = 0;
            int numOfEdges = 0;

            int numOfMovieNodes = 0;
            int numOfMovieCategoryEdges = 0;
            int numOfCategoryNodes = 0;

            numOfMovieNodes = C_ProblemModeling.vertexItemOfTopCategories_list.size();
            numOfCategoryNodes += C_ProblemModeling.vertexTopCategory_list.size();
            numOfMovieCategoryEdges += C_ProblemModeling.vertexTopCategory_list.size() * C_ProblemModeling.vertexItemOfTopCategories_list.size();

            numOfNodes += numOfMovieNodes + numOfCategoryNodes + 2; //it is the maximum number of Edges
            numOfEdges += numOfMovieNodes + numOfMovieCategoryEdges + numOfCategoryNodes + 1;
            ///////////////
            //System.out.println(numOfEdges);
            // System.out.println(numOfNodes);
            /////////////

            //name of Edges and Cost of Edges
            String[] ee = new String[numOfEdges]; //name of Edges
            double[] cc = new double[numOfEdges]; //Flow Cost of Edges
            double[] ii = new double[numOfEdges]; //item cost of Edges
            int index = 0;
            //1st level edges: source-movie edges
            for (int i = 0; i < numOfMovieNodes; i++) {
                ee[index] = "source_" + C_ProblemModeling.vertexItemOfTopCategories_list.get(i).getItemID();
                cc[index] = C_ProblemModeling.vertexItemOfTopCategories_list.get(i).getEdgeMoveSource().getFlow_cost();
                ii[index] = C_ProblemModeling.vertexItemOfTopCategories_list.get(i).getEdgeMoveSource().getItem_cost();
                index++;
            }
            //2nd level edges: movie-Category edges
            for (int i = 0; i < numOfCategoryNodes; i++) {
                List<EdgeItemCategory_v2> edgeMovieCategory_list_temp = new ArrayList<>();
                edgeMovieCategory_list_temp = C_ProblemModeling.vertexTopCategory_list.get(i).getEdgeMovieCategory_list();
                int size = edgeMovieCategory_list_temp.size();
                for (int j = 0; j < size; j++) {
                    ee[index] = edgeMovieCategory_list_temp.get(j).getItemID() + "_" + edgeMovieCategory_list_temp.get(j).getCategoryID();
                    cc[index] = edgeMovieCategory_list_temp.get(j).getFlow_cost();
                    ii[index] = edgeMovieCategory_list_temp.get(j).getItem_cost();
                    index++;
                }
            }
            //3rd level edges: category-sink edges
            for (int i = 0; i < numOfCategoryNodes; i++) {
                ee[index] = C_ProblemModeling.vertexTopCategory_list.get(i).getCategoryID() + "_sink";
                cc[index] = C_ProblemModeling.vertexTopCategory_list.get(i).getEdgeCategorySink().getFlow_cost();
                ii[index] = C_ProblemModeling.vertexTopCategory_list.get(i).getEdgeCategorySink().getItem_cost();
                index++;
            }
            //4th level edges: source-sink edge
            ee[index] = "sink_source";
            cc[index] = 0.0;
            ii[index] = 0.0;
            index++;

            numOfEdges = index; //Actual number of edges

            //////////////////
            //System.out.println("E[i] :: C[i]:");
            String[] E = new String[numOfEdges]; //name of Edges, names of Variables
            double[] C = new double[numOfEdges]; //Flow Cost of Edges
            double[] I = new double[numOfEdges]; //Item Cost of Edges
            for (int i = 0; i < numOfEdges; i++) {
                C[i] = cc[i];
                E[i] = ee[i];
                I[i] = ii[i];
                //////////////////////////
                //System.out.println(E[i] + " :: " + I[i]);
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
                List<EdgeItemCategory_v2> edgeMovieCategory_list_temp = new ArrayList<>();
                edgeMovieCategory_list_temp = C_ProblemModeling.vertexItemOfTopCategories_list.get(i).getEdgeItemCategory_list();
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
/*
            //ΣXic = itemsPerCategory, for each C 
            double[][] xic = new double[numOfCategoryNodes][numOfEdges];
            for (int i = 0; i < numOfCategoryNodes; i++) {
                for (int j = 0; j < numOfEdges; j++) {
                    xic[i][j] = 0.0;//ΣXic = itemsPerCategory, for each C 
                }
            }
*/
            //Category Nodes
            for (int i = 0; i < numOfCategoryNodes; i++) {
                List<EdgeItemCategory_v2> edgeMovieCategory_list_temp = new ArrayList<>();
                edgeMovieCategory_list_temp = C_ProblemModeling.vertexTopCategory_list.get(i).getEdgeMovieCategory_list();
                int size = edgeMovieCategory_list_temp.size();
                //////////////
                //System.out.println("size = " + size);
                for (int j = 0; j < size; j++) {
                    String str = edgeMovieCategory_list_temp.get(j).getItemID() + "_" + edgeMovieCategory_list_temp.get(j).getCategoryID();
                    int position = indexOfStrInTable(E, str, numOfEdges);
                    if (position > -1) {
                        A[index][position] = 1.0;
                        //xic[i][position] = 1.0;////ΣXic = itemsPerCategory, for each C 
                        ////////////////////////////////////////////////////
                        ///System.out.println("IF = " + position + " " + xic[i][position] + " " +numOfCategoryNodes);

                    }
                }
                EdgeCategorySink_v2 edgeCategorySink_temp = C_ProblemModeling.vertexTopCategory_list.get(i).getEdgeCategorySink();
                String str2 = edgeCategorySink_temp.getCategoryID() + "_sink";
                int position = indexOfStrInTable(E, str2, numOfEdges);
                if (position > -1) {
                    A[index][position] = (double) -1.0;
                }
                index++;
            }

            //Sink Node
            List<EdgeCategorySink_v2> edgeCategorySink_list_temp = new ArrayList<>();
            edgeCategorySink_list_temp = C_ProblemModeling.vertexSink.getEdgeCategorySink_list();
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
            List<EdgeItemSource_v2> edgeMovieSource_list_temp = new ArrayList<>();
            edgeMovieSource_list_temp = C_ProblemModeling.vertexSource.getEdgeMovieSource_list();
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

            /* //////////////////////
            System.out.println("xic:");
            for (int i = 0; i < numOfCategoryNodes; i++) {
                System.out.println("\ncategoryNode:");
                for (int j = 0; j < numOfEdges; j++) {
                    System.out.println(E[j] + " ### " + A[i][j] + "###" + xic[i][j] + " j= " + j);
                }
                //System.out.println();
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
                    //upperBound[i] = (double) C_ProblemModeling.numOfMaxAppearancesPerItem;  //Xi <= numOfMaxAppearancesPerItem
                    upperBound[i] = 1.0;  //Xi <= 1
                } else if (i < (numOfEdges - 1)) { //Xcategory-sink = numOfPackages
                    lowerBound[i] = 0.0;
                    //lowerBound[i] = (double) C_ProblemModeling.itemsAllocationToCategories[-1*(numOfEdges-1-i-C_ProblemModeling.numOfTopCategories)];
                    upperBound[i] = (double) C_ProblemModeling.itemsAllocationToCategories[-1 * (numOfEdges - 1 - i - C_ProblemModeling.numOfTopCategories)];
                    overallItemsOfCategories += C_ProblemModeling.itemsAllocationToCategories[-1 * (numOfEdges - 1 - i - C_ProblemModeling.numOfTopCategories)];
                    //lowerBound[i] = (double) numOfItemsPerCategory;
                    //upperBound[i] = (double) numOfItemsPerCategory;
                } else { //0.0 <= Xsource-sink <= numOfCategoryNodes*numOfPackages
                    /////////////////////////////////////////
                    //System.out.println("overallItemsOfCategories= " + overallItemsOfCategories);
                    if (C_ProblemModeling.packageSize == 0) {
                        lowerBound[i] = (double) overallItemsOfCategories;
                        upperBound[i] = (double) overallItemsOfCategories;
                    } else {
                        lowerBound[i] = (double) C_ProblemModeling.packageSize;
                        upperBound[i] = (double) C_ProblemModeling.packageSize;
                    }
                }
            }
            IloNumVarType[] xt = new IloNumVarType[numOfEdges];//  = {IloNumVarType.Float, IloNumVarType.Float, IloNumVarType.Float, IloNumVarType.Int};
            for (int j = 0; j < numOfEdges; j++) {
                xt[j] = IloNumVarType.Int;
            }
            
            //Xi variables
            IloNumVar[] x = cplex.numVarArray(numOfEdges, lowerBound, upperBound, xt); //0.0 <= Xi < =1.0 
            //Objective function 
            cplex.addMinimize(cplex.scalProd(x, C));

            //constraints.add(new LinearConstraint(A[i], Relationship.EQ, 0.0)); //AX=0
            for (int i = 0; i < numOfNodes; i++) {
                IloLinearNumExpr a = cplex.linearNumExpr(); //lhs as in left hand side
                for (int j = 0; j < numOfEdges; j++) {
                    a.addTerm(A[i][j], x[j]);
                }
                IloRange con1 = cplex.addEq(a, 0.0);
            }
            
            
            
            
            IloLinearNumExpr b = cplex.linearNumExpr();
            for (int j = 0; j < numOfEdges; j++) {
                b.addTerm(I[j], x[j]); //add P*X 
            }
            IloRange con2 = cplex.addLe(b, C_ProblemModeling.packageCost); //add P*X <= packageCost

            ////////////////////////////////
            System.out.println("packageSize " + C_ProblemModeling.packageSize);

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
            } else {
                cplex.end();
                return null;
            }

            SolutionType_v2 solution = new SolutionType_v2(E, sol, C, I);
            return solution;

        } catch (Exception e) {
            e.printStackTrace();
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
