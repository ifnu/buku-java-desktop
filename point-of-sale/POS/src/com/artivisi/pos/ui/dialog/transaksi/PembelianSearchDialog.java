/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PembelianSearchDialog.java
 *
 * Created on Jul 1, 2009, 3:39:44 PM
 */

package com.artivisi.pos.ui.dialog.transaksi;

import com.artivisi.pos.model.transaksi.Pembelian;
import com.artivisi.pos.ui.frame.FrameUtama;
import com.artivisi.pos.util.POSTableCellRenderer;
import com.artivisi.pos.util.TextComponentUtils;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ifnu
 */
public class PembelianSearchDialog extends javax.swing.JDialog {

    private Pembelian selectedPembelian;
    private List<Pembelian> pembelians;
    private boolean isOk = false;

    /** Creates new form PembelianSearchDialog */
    public PembelianSearchDialog() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        setLocationRelativeTo(null);
        tblPembelian.setDefaultRenderer(Object.class, new POSTableCellRenderer());
        tblPembelian.getSelectionModel().addListSelectionListener(new PembelianSelectionListener());
        tblPembelian.setAutoCreateColumnsFromModel(false);
    }

    public Pembelian showDialog(){
        pembelians = FrameUtama.getTransaksiService().semuaPembelian();
        tblPembelian.setModel(new PembelianTableModel());
        setVisible(true);
        if(isOk == false) selectedPembelian = null;
        return selectedPembelian;
    }
    private class PembelianTableModel extends AbstractTableModel{
        public int getRowCount() {
            return pembelians.size();
        }
        public int getColumnCount() {
            return 3;
        }
        public Object getValueAt(int rowIndex, int columnIndex) {
            Pembelian p = pembelians.get(rowIndex);
            switch(columnIndex){
                case 0 : return p.getId();
                case 1 : return p.getTanggal();
                case 2 : return p.getTotal();
                default: return "";
            }
        }
    }
    private class PembelianSelectionListener implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent e) {
            if(tblPembelian.getSelectedRow()>=0){
                int index = tblPembelian.convertRowIndexToModel(tblPembelian.getSelectedRow());
                selectedPembelian = pembelians.get(index);
            }
        }
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
        tblPembelian = new javax.swing.JTable();
        btnOk = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cari Pembelian");

        jLabel1.setText("Search");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        tblPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tanggal", "Total"
            }
        ));
        jScrollPane1.setViewportView(tblPembelian);

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnBatal))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if(selectedPembelian!=null){
            isOk =  true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih pembelian terlebih dahulu!"
                    ,"Warning",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        selectedPembelian = null;
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        for(int i=0;i<tblPembelian.getRowCount();i++){
            String current = tblPembelian.getValueAt(i, 0).toString();
            if(current.toLowerCase().indexOf(txtSearch.getText().toLowerCase())>=0){
                tblPembelian.getSelectionModel().setSelectionInterval(i, i);
                tblPembelian.getColumnModel().getSelectionModel().setSelectionInterval(0, 0);
                TextComponentUtils.scrollToRect(tblPembelian, i);
                break;
            }
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPembelian;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

}
