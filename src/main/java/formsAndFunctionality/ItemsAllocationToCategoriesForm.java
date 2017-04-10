package formsAndFunctionality;

import static formsAndFunctionality.ProblemModelingForm.numOfTopCategories;
import java.awt.Component;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author: Panagiotis Kouris
 * date: Dec 2015
 */
public class ItemsAllocationToCategoriesForm extends javax.swing.JFrame {

    static public int numOfItemsPerPackage = 1;

    /**
     * Creates new form ItemsAllocationToCategoriesForm
     */
    public ItemsAllocationToCategoriesForm() {
        initComponents();
    }

    //it allocates the items to categories proportionally
    public Integer[] itemsAllocationToCategories() {
        
        //this.numOfTopCategories = this.cb_numOfCategories.getSelectedIndex() + 1;
        //this.numOfPackages = this.cb_numOfPackages.getSelectedIndex() + 1;
        //int num = numOfItemsPerPackage; //number of Packages
        numOfItemsPerPackage = this.cb_itemsPerPackage.getSelectedIndex() + 1;
        int remainingItems = numOfItemsPerPackage; //remaining items
        int[] itemsPerCategory = new int[numOfTopCategories];
        String[] category = new String[numOfTopCategories];
        Integer[] itemsAllocation = new Integer[numOfTopCategories];
        int sumOfItems;
        double weightOfCategory;

        if (numOfItemsPerPackage < numOfTopCategories) {
            this.tx_stateOfAllocation.setText("Infeasible Allocation! (number of Items per Package < number of Categories)");
            //remove table
            Integer b = tb_itemsAllocation.getRowCount();
            for (int k = 0; k < b; k++) {
                ((DefaultTableModel) tb_itemsAllocation.getModel()).removeRow(0);
            }
            ProblemModelingForm.itemsAllocationToCategories = null;
            return null;
        }
        //taking the items per category and the name of category
        for (int i = 0; i < numOfTopCategories; i++) {
            //itemsPerCategory[i] = vertexCategory_list.get(i).getMoveisPerCategory();
            //category[i] = vertexCategory_list.get(i).getCategoryID();
            itemsPerCategory[i] = formsAndFunctionality.ProblemModelingForm.categoriesPopularity_list.get(i).getPopularity();
            category[i] = formsAndFunctionality.ProblemModelingForm.categoriesPopularity_list.get(i).getCategory();
            //System.out.println("For1");
        }

        for (int i = (numOfTopCategories - 1); i > -1; i--) { //it starts from the last category
            if (i > 0) {
                sumOfItems = 0;
                for (int j = 0; j < i + 1; j++) {
                    sumOfItems += itemsPerCategory[j];
                }
                weightOfCategory = (double) (((double) itemsPerCategory[i]) / ((double) sumOfItems));
                itemsAllocation[i] = (int) round((double) (weightOfCategory * remainingItems), 0);
                if (itemsAllocation[i] == 0) { //each category will have at least one item
                    itemsAllocation[i] = 1;
                }
                if (itemsAllocation[i] > itemsPerCategory[i]) {
                    this.tx_stateOfAllocation.setText("Infeasible Allocation! The size of package is greater than number of items!");
                    //remove table
                    Integer b = tb_itemsAllocation.getRowCount();
                    for (int k = 0; k < b; k++) {
                        ((DefaultTableModel) tb_itemsAllocation.getModel()).removeRow(0);
                    }
                    ProblemModelingForm.itemsAllocationToCategories = null;
                    return null;
                }
                remainingItems -= itemsAllocation[i];
                if (remainingItems < 1) { //the allocation is impossible
                    this.tx_stateOfAllocation.setText("Infeasible Allocation!");
                    //remove table
                    Integer b = tb_itemsAllocation.getRowCount();
                    for (int k = 0; k < b; k++) {
                        ((DefaultTableModel) tb_itemsAllocation.getModel()).removeRow(0);
                    }
                    ProblemModelingForm.itemsAllocationToCategories = null;
                    return null;
                }

            } else { //for the first category
                itemsAllocation[i] = remainingItems;
                if (itemsAllocation[i] > itemsPerCategory[i]) {
                    this.tx_stateOfAllocation.setText("Infeasible Allocation! The size of package is greater than number of items!");
                    //remove table
                    Integer b = tb_itemsAllocation.getRowCount();
                    for (int k = 0; k < b; k++) {
                        ((DefaultTableModel) tb_itemsAllocation.getModel()).removeRow(0);
                    }
                    ProblemModelingForm.itemsAllocationToCategories = null;
                    return null;
                }
            }
        }
        Arrays.sort(itemsAllocation, Collections.reverseOrder()); //sort items allocation in order to get first the greater number
        ProblemModelingForm.itemsAllocationToCategories = itemsAllocation;
        ProblemModelingForm.numOfItemsPerCategory = 0; //items allocated proportionally
        ////////////////
        System.out.println("item per category = " + ProblemModelingForm.numOfItemsPerCategory);
        /////////////
        
        //remove table
        Integer b = tb_itemsAllocation.getRowCount();
        for (int i = 0; i < b; i++) {
            ((DefaultTableModel) tb_itemsAllocation.getModel()).removeRow(0);
        }
        //print table of item allocation
        for (int i = 0; i < numOfTopCategories; i++) {
            ((DefaultTableModel) tb_itemsAllocation.getModel()).addRow(new Object[]{(i + 1), category[i], itemsPerCategory[i], ProblemModelingForm.itemsAllocationToCategories[i]});
        }
        this.tx_stateOfAllocation.setText("Items allocated to categories proportionally!");
        
        return itemsAllocation;
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_itemsAllocation = new javax.swing.JTable();
        tx_stateOfAllocation = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bt_itemsAllocation = new javax.swing.JButton();
        tx_numOfCategories = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cb_itemsPerPackage = new javax.swing.JComboBox();
        bt_back = new javax.swing.JButton();
        bt_cancel = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menu_exit = new javax.swing.JMenuItem();
        menu_about = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PackageRecSys");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Items Allocation to Categories"));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tb_itemsAllocation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "s/n", "Category", "Actual items per category", "Items Allocation"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tb_itemsAllocation);
        if (tb_itemsAllocation.getColumnModel().getColumnCount() > 0) {
            tb_itemsAllocation.getColumnModel().getColumn(0).setMinWidth(40);
            tb_itemsAllocation.getColumnModel().getColumn(0).setPreferredWidth(40);
            tb_itemsAllocation.getColumnModel().getColumn(0).setMaxWidth(40);
            tb_itemsAllocation.getColumnModel().getColumn(1).setMinWidth(100);
            tb_itemsAllocation.getColumnModel().getColumn(1).setPreferredWidth(100);
            tb_itemsAllocation.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        tx_stateOfAllocation.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_stateOfAllocation))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tx_stateOfAllocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Number of Categories:");

        bt_itemsAllocation.setText("Items Allocation to Categories");
        bt_itemsAllocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_itemsAllocationActionPerformed(evt);
            }
        });

        tx_numOfCategories.setEditable(false);
        tx_numOfCategories.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setText("Items per Package:");

        cb_itemsPerPackage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cb_itemsPerPackage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tx_numOfCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(bt_itemsAllocation, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tx_numOfCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cb_itemsPerPackage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_itemsAllocation))
                .addContainerGap())
        );

        bt_back.setText("OK");
        bt_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_backActionPerformed(evt);
            }
        });

        bt_cancel.setText("Cancel");
        bt_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_cancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bt_back, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_cancel)
                    .addComponent(bt_back))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

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
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_itemsAllocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_itemsAllocationActionPerformed
        this.itemsAllocationToCategories();
    }//GEN-LAST:event_bt_itemsAllocationActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //  ProblemModelingForm a = new ProblemModelingForm();
        // this.dispose();
        //  a.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void bt_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_backActionPerformed
        ProblemModelingForm a = new ProblemModelingForm();
        this.dispose();
        a.setVisible(true);
    }//GEN-LAST:event_bt_backActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.tx_numOfCategories.setText(String.valueOf(numOfTopCategories));
        this.cb_itemsPerPackage.setSelectedIndex(numOfTopCategories - 1);
    }//GEN-LAST:event_formWindowOpened

    private void bt_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cancelActionPerformed
        if (ProblemModelingForm.numOfItemsPerCategory == 0) {
            ProblemModelingForm.numOfItemsPerCategory = 1;
            ProblemModelingForm.itemsAllocationToCategories = null;
        }
        ProblemModelingForm a = new ProblemModelingForm();
        this.dispose();
        a.setVisible(true);
    }//GEN-LAST:event_bt_cancelActionPerformed

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
        JOptionPane.showMessageDialog(frame, "PackageRecSys v1.0\nCopyright © Panagiotis Kouris , 2016\nHarokopio University of Athens (HUA)\nAll rights reserved", "About Software", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_menu_aboutMouseClicked

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
            java.util.logging.Logger.getLogger(ItemsAllocationToCategoriesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItemsAllocationToCategoriesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItemsAllocationToCategoriesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItemsAllocationToCategoriesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItemsAllocationToCategoriesForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_back;
    private javax.swing.JButton bt_cancel;
    private javax.swing.JButton bt_itemsAllocation;
    private javax.swing.JComboBox cb_itemsPerPackage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu menu_about;
    private javax.swing.JMenuItem menu_exit;
    private javax.swing.JTable tb_itemsAllocation;
    private javax.swing.JTextField tx_numOfCategories;
    private javax.swing.JTextField tx_stateOfAllocation;
    // End of variables declaration//GEN-END:variables
}
