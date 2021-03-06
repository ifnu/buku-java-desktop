/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SalesLookupDialog.java
 *
 * Created on Nov 14, 2010, 4:20:15 PM
 */

package com.googlecode.projecttemplate.pos.ui.transaction;

import com.googlecode.projecttemplate.pos.Main;
import com.googlecode.projecttemplate.pos.model.Sales;
import com.googlecode.projecttemplate.pos.util.ComponentUtils;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ifnu
 */
public class SalesLookupDialog extends javax.swing.JDialog {

    private Sales sales;
    private List<Sales> salesList;

    /** Creates new form SalesLookupDialog */
    public SalesLookupDialog() {
        super(Main.getFrame(), true);
        initComponents();
        setLocationRelativeTo(null);
        tblSales.setAutoCreateColumnsFromModel(false);
        tblSales.getSelectionModel().addListSelectionListener(new SalesSelectionListener());
        salesList = Main.getSalesService().getSales();
        tblSales.setModel(new SalesTableModel(salesList));
    }

    public Sales getSales(){
        setVisible(true);
        return sales;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSales = new javax.swing.JTable();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Cari");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        tblSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Penjualan", "Tgl Penjualan", "Total Harga"
            }
        ));
        jScrollPane1.setViewportView(tblSales);

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setText("Batal");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        btnOkActionPerformed(evt);
}//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        for(int i =0;i<tblSales.getRowCount();i++){
            String val = tblSales.getValueAt(i, 0).toString();
            if(val!=null && val.startsWith(txtSearch.getText())){
                tblSales.getSelectionModel().setSelectionInterval(i, i);
                ComponentUtils.scrollToRect(tblSales, i);
                break;
            }
        }
}//GEN-LAST:event_txtSearchKeyReleased

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if(sales!=null){
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih satu barang terlebih dahulu!",
                    "error",JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        sales = null;
        dispose();
}//GEN-LAST:event_btnCancelActionPerformed

    private class SalesSelectionListener implements ListSelectionListener{

        public void valueChanged(ListSelectionEvent e) {
            if(tblSales.getSelectedRow()>=0){
                sales = salesList.get(tblSales.getSelectedRow());
            }
        }
        
    }

    private class SalesTableModel extends AbstractTableModel{

        private List<Sales> salesList;

        public SalesTableModel(List<Sales> salesList) {
            this.salesList = salesList;
        }

        public int getRowCount() {
            return salesList.size();
        }

        public int getColumnCount() {
            return 3;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Sales s = salesList.get(rowIndex);
            switch(columnIndex){
                case 0: return s.getId();
                case 1: return s.getSalesDate();
                case 2: return s.getTotalSales();
                default: return "";
            }
        }
        
    }


    /**
    * @param args the command line arguments
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSales;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

}
