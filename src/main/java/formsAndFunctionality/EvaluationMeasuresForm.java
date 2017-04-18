package formsAndFunctionality;

import entityBasicClasses.Measures;
import entityBasicClasses.ItemForPackage;
import static formsAndFunctionality.ProblemSolutionNPackagesForm.packages_hashTable;
import java.awt.Component;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author: Panagiotis Kouris 
 * date: Dec 2015
 */
public class EvaluationMeasuresForm extends javax.swing.JFrame {

    public EvaluationMeasuresForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_measures = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        tx_selectedUser = new javax.swing.JTextField();
        tx_itemsPerCategory = new javax.swing.JTextField();
        tx_numOfPackages = new javax.swing.JTextField();
        tx_numOfCategories = new javax.swing.JTextField();
        tx_maxAppearances = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tx_modelOfCreatingPackages = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tx_mode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tx_recommendations = new javax.swing.JTextField();
        tx_popularityFactor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tx_categoryPopularityMode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tx_minRatingsOfHiddenItems = new javax.swing.JTextField();
        bt_back = new javax.swing.JButton();
        bt_back1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menu_back = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menu_exit = new javax.swing.JMenuItem();
        menu_about = new javax.swing.JMenu();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PackageRecSys");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Evaluation"));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Measures"));

        tb_measures.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Package", "True Positives", "False Positives", "False Negative", "Precision", "Recall"
            }
        ));
        jScrollPane1.setViewportView(tb_measures);
        if (tb_measures.getColumnModel().getColumnCount() > 0) {
            tb_measures.getColumnModel().getColumn(0).setMinWidth(100);
            tb_measures.getColumnModel().getColumn(0).setPreferredWidth(100);
            tb_measures.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 812, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));

        tx_selectedUser.setEditable(false);
        tx_selectedUser.setBackground(new java.awt.Color(204, 204, 255));
        tx_selectedUser.setText("Selected UserID: ");

        tx_itemsPerCategory.setEditable(false);
        tx_itemsPerCategory.setBackground(new java.awt.Color(204, 204, 255));
        tx_itemsPerCategory.setText("Items per Category: (ni/c)");
        tx_itemsPerCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_itemsPerCategoryActionPerformed(evt);
            }
        });

        tx_numOfPackages.setBackground(new java.awt.Color(204, 204, 255));
        tx_numOfPackages.setText("Number of Packages (N):");
        tx_numOfPackages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_numOfPackagesActionPerformed(evt);
            }
        });

        tx_numOfCategories.setBackground(new java.awt.Color(204, 204, 255));
        tx_numOfCategories.setText("Number of Categories (nc):");
        tx_numOfCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_numOfCategoriesActionPerformed(evt);
            }
        });

        tx_maxAppearances.setEditable(false);
        tx_maxAppearances.setBackground(new java.awt.Color(204, 204, 255));
        tx_maxAppearances.setText("Maximum Appearances/Item:");
        tx_maxAppearances.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_maxAppearancesActionPerformed(evt);
            }
        });

        jLabel2.setText("Model of Creating Packages:");

        tx_modelOfCreatingPackages.setEditable(false);
        tx_modelOfCreatingPackages.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setText("Mode:");

        tx_mode.setEditable(false);
        tx_mode.setBackground(new java.awt.Color(204, 204, 255));

        jLabel4.setText("Items for creating packages:");

        tx_recommendations.setBackground(new java.awt.Color(204, 204, 255));

        tx_popularityFactor.setBackground(new java.awt.Color(204, 204, 255));

        jLabel5.setText("Item Popularity Factor:");

        tx_categoryPopularityMode.setEditable(false);
        tx_categoryPopularityMode.setBackground(new java.awt.Color(204, 204, 255));

        jLabel3.setText("Category Popularity Mode:");

        jLabel6.setText("Minimum hidden rating:");

        tx_minRatingsOfHiddenItems.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tx_maxAppearances, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .addComponent(tx_itemsPerCategory, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx_numOfCategories, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx_numOfPackages, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx_selectedUser, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tx_recommendations, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(tx_popularityFactor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tx_minRatingsOfHiddenItems, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tx_mode)
                    .addComponent(tx_categoryPopularityMode)
                    .addComponent(tx_modelOfCreatingPackages))
                .addGap(0, 41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx_selectedUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_modelOfCreatingPackages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tx_mode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tx_recommendations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tx_minRatingsOfHiddenItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tx_popularityFactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tx_categoryPopularityMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addComponent(tx_numOfPackages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tx_numOfCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tx_itemsPerCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tx_maxAppearances, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel4)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bt_back.setText("Back");
        bt_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_backActionPerformed(evt);
            }
        });

        bt_back1.setText("Back");
        bt_back1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_back1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(bt_back)
                        .addGap(745, 745, 745)
                        .addComponent(bt_back1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_back)
                    .addComponent(bt_back1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(299, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(jPanel5);

        jMenu1.setText("File");

        menu_back.setText("Back");
        menu_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_backActionPerformed(evt);
            }
        });
        jMenu1.add(menu_back);
        jMenu1.add(jSeparator1);

        menu_exit.setText("Exit");
        menu_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_exitActionPerformed(evt);
            }
        });
        jMenu1.add(menu_exit);

        jMenuBar1.add(jMenu1);

        menu_about.setText("About");
        menu_about.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_aboutMouseClicked(evt);
            }
        });
        jMenuBar1.add(menu_about);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        windowOpeningAciton();
    }//GEN-LAST:event_formWindowOpened

    private void tx_numOfPackagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_numOfPackagesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_numOfPackagesActionPerformed

    private void tx_numOfCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_numOfCategoriesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_numOfCategoriesActionPerformed

    private void tx_itemsPerCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_itemsPerCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_itemsPerCategoryActionPerformed

    private void tx_maxAppearancesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_maxAppearancesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_maxAppearancesActionPerformed

    private void menu_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_backActionPerformed
        ProblemModelingForm a = new ProblemModelingForm();
        this.dispose();
        a.setVisible(true);
    }//GEN-LAST:event_menu_backActionPerformed

    private void bt_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_backActionPerformed
        ProblemModelingForm a = new ProblemModelingForm();
        this.dispose();
        a.setVisible(true);
    }//GEN-LAST:event_bt_backActionPerformed

    private void bt_back1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_back1ActionPerformed
        ProblemModelingForm a = new ProblemModelingForm();
        this.dispose();
        a.setVisible(true);
    }//GEN-LAST:event_bt_back1ActionPerformed

    private void menu_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_exitActionPerformed
        Object[] options = {"Yes", "Cancel"};
        Component frame = null;
        int n = JOptionPane.showOptionDialog(frame,
            "Are you sure you want to terminate the program?",
            "Exit",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);
        if (n == 0) {
            System.exit(0);
            //this.dispose();
        }
    }//GEN-LAST:event_menu_exitActionPerformed

    private void menu_aboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_aboutMouseClicked
        Component frame = null;
        JOptionPane.showMessageDialog(frame, "PackageRecSys v1.0\nCopyright © Panagiotis Kouris, 2017\nNTUA and HUA\nAll rights reserved", "About Software", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_menu_aboutMouseClicked

    public void windowOpeningAciton() {
        this.tx_selectedUser.setText("Selected UserID: " + String.valueOf(StartForm.selectedUserID));
        switch (ProblemModelingForm.modelOfCreatingPackages) {
            case 1:
                this.tx_modelOfCreatingPackages.setText("1. Removing the Edge with Maximum Rating");
                break;
            case 2:
                this.tx_modelOfCreatingPackages.setText("2. Removing the Edge with Minimum Rating");
                break;
            case 3:
                this.tx_modelOfCreatingPackages.setText("3. Removing the 50% of Edges with Upper Ratings");
                break;
            case 4:
                this.tx_modelOfCreatingPackages.setText("4. Removing the 50% of Edges with Lower Ratings");
                break;
            case 5:
                this.tx_modelOfCreatingPackages.setText("5. Removing the 100% of the Edges");
                break;
            case 6:
                this.tx_modelOfCreatingPackages.setText("6. Distinct Packages - Removing Movie Nodes");
                break;
            case 7:
                this.tx_modelOfCreatingPackages.setText("7. Maximum Number of Item Appearances");
                break;
            default:
                this.tx_modelOfCreatingPackages.setText("1. Removing the Edge with Maximum Rating");
                break;
        }
        this.tx_numOfPackages.setText(
                "Number of Packages (N): " + String.valueOf(ProblemModelingForm.numOfPackages));
        this.tx_numOfCategories.setText(
                "Number of Categories (nc): " + String.valueOf(ProblemModelingForm.numOfTopCategories));
        if (ProblemModelingForm.numOfItemsPerCategory == 0) {
            this.tx_itemsPerCategory.setText(
                    "Items per Category (ni/c): Proportional");
        } else {
            this.tx_itemsPerCategory.setText(
                    "Items per Category (ni/c): " + String.valueOf(ProblemModelingForm.numOfItemsPerCategory));
        }
        
        switch (ProblemModelingForm.numOfMaxAppearancesPerItem) {
            case -1:
                this.tx_maxAppearances.setText(
                        "Maximum Appearances/Item: Not taken account");
                break;
            case 0:
                this.tx_maxAppearances.setText(
                        "Maximum Appearances/Item: From file");
                break;
            default:
                this.tx_maxAppearances.setText(
                        "Maximum Appearances/Item: " + ProblemModelingForm.numOfMaxAppearancesPerItem);
                break;
        }
        this.tx_recommendations.setText("" + RunningModeForm.itemsForPackages);
        this.tx_mode.setText(formsAndFunctionality.RunningModeForm.runningMode_text);
        this.tx_popularityFactor.setText("" + formsAndFunctionality.RunningModeForm.popularityFactor);
        this.tx_categoryPopularityMode.setText("" + formsAndFunctionality.ProblemModelingForm.categoryPopularity_mode_str);
        this.tx_minRatingsOfHiddenItems.setText("" + formsAndFunctionality.RunningModeForm.minRatingOfHiddenItems);
        //this.tx_selectedUser.setText("Selected UserId: " + StartForm.selectedUserID);
        computeAndPrintMeasures();
    }

    
    
    public void computeAndPrintMeasures() {
        //int numOfPackages = ProblemSolutionNPackagesForm.packages_hashTable.size();
        int len = RunningModeForm.concealedItemRatings_list.size();
        int itemsPerPackage;// ProblemModelingForm.numOfItemsPerCategory*ProblemModelingForm.numOfTopCategories;
        if (ProblemModelingForm.numOfItemsPerCategory == 0) { //Proportional allocation of items to categories
            itemsPerPackage = formsAndFunctionality.ItemsAllocationToCategoriesForm.numOfItemsPerPackage;
            System.out.println("itemsPerPackage= " + itemsPerPackage);
        } else {
            itemsPerPackage = ProblemModelingForm.numOfItemsPerCategory * ProblemModelingForm.numOfTopCategories;
        }

        int ItemsPerPackages[][] = new int[itemsPerPackage * ProblemModelingForm.numOfPackages][itemsPerPackage];
        Integer ItemsOfAllPackages[] = new Integer[itemsPerPackage * ProblemModelingForm.numOfPackages];

        // int TP=0; //True Positive
        int countItemsOfAllPackages = 0;
        for (int k = 0; k < ProblemModelingForm.numOfPackages; k++) {

            //Hashtable<String, List<MovieForPackage>> category_movieForPackage_hashTable = new Hashtable<>();
            Hashtable<String, List<ItemForPackage>> category_movieForPackage_hashTable = packages_hashTable.get(k + 1);

            Enumeration<String> categoryKeys = category_movieForPackage_hashTable.keys();
            int countItemsPerPackage = 0; //item number
            while (categoryKeys.hasMoreElements()) {
                //List<MovieForPackage> movieForPackage_list = new ArrayList<>();
                String category = categoryKeys.nextElement();
                List<ItemForPackage> movieForPackage_list = category_movieForPackage_hashTable.get(category);
                for (int i = 0; i < movieForPackage_list.size(); i++) {
                    ItemsPerPackages[k][countItemsPerPackage] = movieForPackage_list.get(i).getItemID();
                    // ItemsPerPackages[k][countItemsPerPackage][1] = 0;
                    countItemsPerPackage++;
                    ItemsOfAllPackages[countItemsOfAllPackages] = movieForPackage_list.get(i).getItemID();
                    countItemsOfAllPackages++;
                    // ((DefaultTableModel) tb_evaluationResults.getModel()).addRow(new Object[]{countItemsPerPackage, category, movieID, title, round(rating, 2)});
                }
            }
        }
        Measures measures = computeMeasures(itemsPerPackage, ProblemModelingForm.numOfPackages, ItemsPerPackages, ItemsOfAllPackages);
        printMeasures(measures, ProblemModelingForm.numOfPackages);
        //return 0;
    }

    
    
    public Measures computeMeasures(int itemsPerPackage, int numberOfPackages, int ItemsPerPackages[][], Integer ItemsOfAllPackages[]) {
        //TP: correct selected items
        //FN: Not correct not selected items
        //FP: Not correct selected items
        //precission = correct retrieved items/retrieved items
        //recall = correct retrieved items/correct tiems
        
        Integer distinctItemsOfAllPackages[] = removeDuplicates(ItemsOfAllPackages);
        int TP_perPackage[] = new int[numberOfPackages];
        int FN_perPackage[] = new int[numberOfPackages];
        int FP_perPackage[] = new int[numberOfPackages];
        //int TN_perPackage[] = new int[numberOfPackages];
        for (int i = 0; i < numberOfPackages; i++) {
            TP_perPackage[i] = 0;
            FN_perPackage[i] = 0;
            FP_perPackage[i] = 0;
            //TN_perPackage[i] = 0;
        }
        int TP_overall = 0;
        int FN_overall = 0;
        int FP_overall = 0;
        //int TN_overall = 0;

        //for each concealed item
        for (int i = 0; i < formsAndFunctionality.RunningModeForm.concealedItemRatings_list.size(); i++) {
            //computing the TP and FN per package
            for (int k = 0; k < numberOfPackages; k++) {
                int FN_flag = 1;
                for (int j = 0; j < itemsPerPackage; j++) {
                    if (RunningModeForm.concealedItemRatings_list.get(i).getItemID() == ItemsPerPackages[k][j]) {
                        TP_perPackage[k]++;
                        FN_flag = 0;
                        break;
                    }
                }
                FN_perPackage[k] += FN_flag;
            }

            //Computing the TP and FN for overall packages.
            int len = distinctItemsOfAllPackages.length;
            int FNoverall_flag = 1;
            for (int j = 0; j < len; j++) {
                if (RunningModeForm.concealedItemRatings_list.get(i).getItemID() == distinctItemsOfAllPackages[j]) {
                    TP_overall++;
                    FNoverall_flag = 0;
                    break;
                }
            }
            FN_overall += FNoverall_flag;
        }

        //for each package - computing the FP
        for (int k = 0; k < numberOfPackages; k++) {
            for (int j = 0; j < itemsPerPackage; j++) {
                int FP_flag = 1;
                for (int i = 0; i < formsAndFunctionality.RunningModeForm.concealedItemRatings_list.size(); i++) {
                    if (RunningModeForm.concealedItemRatings_list.get(i).getItemID() == ItemsPerPackages[k][j]) {
                        FP_flag = 0;
                        break;
                    }
                }
                FP_perPackage[k] += FP_flag;
            }
        }
        //computing the FP for overall packages
        int len = distinctItemsOfAllPackages.length;
        for (int j = 0; j < len; j++) {
            int FPoverall_flag = 1;
            for (int i = 0; i < formsAndFunctionality.RunningModeForm.concealedItemRatings_list.size(); i++) {
                if (RunningModeForm.concealedItemRatings_list.get(i).getItemID() == distinctItemsOfAllPackages[j]) {
                    FPoverall_flag = 0;
                    break;
                }
            }
            FP_overall += FPoverall_flag;
        }
        double[] precision_perPackage = new double[numberOfPackages];
        double[] recall_perPackage = new double[numberOfPackages];
        double precision_overall = (double) ((double) TP_overall / (double) (TP_overall + FP_overall));
        double recall_overall = (double) ((double) TP_overall / (double) (TP_overall + FN_overall));
        for (int k = 0; k < numberOfPackages; k++) {
            precision_perPackage[k] = (double) ((double) TP_perPackage[k] / (double) (TP_perPackage[k] + FP_perPackage[k]));
            recall_perPackage[k] = (double) ((double) TP_perPackage[k] / (double) (TP_perPackage[k] + FN_perPackage[k]));
        }

        Measures measures = new Measures(TP_perPackage, FN_perPackage, FP_perPackage, TP_overall, FN_overall, FP_overall, precision_perPackage, recall_perPackage, precision_overall, recall_overall);

        return measures;
    }

    
    //it prints measuers in the table
    public void printMeasures(Measures measures, int numOfPackages) {
        double avg_precition = 0.0;
        double avg_recall = 0.0;
        //print measures per package
        for (int i = 0; i < numOfPackages; i++) {
            ((DefaultTableModel) tb_measures.getModel()).addRow(new Object[]{
                (i + 1),
                measures.getTP_perPackage()[i],
                measures.getFP_perPackage()[i],
                measures.getFN_perPackage()[i],
                round(measures.getPrecision_perPackage()[i], 3),
                round(measures.getRecall_perPackage()[i],3)
            });
            avg_precition += measures.getPrecision_perPackage()[i];
            avg_recall += measures.getRecall_perPackage()[i];
        }

        avg_precition = avg_precition/(double) numOfPackages;
        avg_recall = avg_recall/(double) numOfPackages;
        
        //print a blank line    
        ((DefaultTableModel) tb_measures.getModel()).addRow(new Object[]{ "", "", "", "", "", ""});
        //print measurs for the overall packages
        ((DefaultTableModel) tb_measures.getModel()).addRow(new Object[]{
            "Average:", "", "", "", round(avg_precition,3), round(avg_recall,3)
        });
        //print measurs for the overall packages
        ((DefaultTableModel) tb_measures.getModel()).addRow(new Object[]{
            "Overall:",
            measures.getTP_overall(),
            measures.getFP_overall(),
            measures.getFN_overall(),
            round(measures.getPrecision_overall(),3),
            round(measures.getRecall_overall(),3)
        });

    }

    //it removes duplicates from an Integer array
    public static Integer[] removeDuplicates(Integer[] array) {
        return new HashSet<Integer>(Arrays.asList(array)).toArray(new Integer[0]);
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

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EvaluationMeasuresForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EvaluationMeasuresForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EvaluationMeasuresForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EvaluationMeasuresForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EvaluationMeasuresForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_back;
    private javax.swing.JButton bt_back1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JMenu menu_about;
    private javax.swing.JMenuItem menu_back;
    private javax.swing.JMenuItem menu_exit;
    private javax.swing.JTable tb_measures;
    private javax.swing.JTextField tx_categoryPopularityMode;
    private javax.swing.JTextField tx_itemsPerCategory;
    private javax.swing.JTextField tx_maxAppearances;
    private javax.swing.JTextField tx_minRatingsOfHiddenItems;
    private javax.swing.JTextField tx_mode;
    private javax.swing.JTextField tx_modelOfCreatingPackages;
    private javax.swing.JTextField tx_numOfCategories;
    private javax.swing.JTextField tx_numOfPackages;
    private javax.swing.JTextField tx_popularityFactor;
    private javax.swing.JTextField tx_recommendations;
    private javax.swing.JTextField tx_selectedUser;
    // End of variables declaration//GEN-END:variables
}
