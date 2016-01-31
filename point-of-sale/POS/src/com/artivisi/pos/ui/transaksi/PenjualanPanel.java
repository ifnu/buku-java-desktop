/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PenjualanPanel.java
 *
 * Created on Jul 1, 2009, 3:16:37 PM
 */

package com.artivisi.pos.ui.transaksi;

import com.artivisi.pos.model.master.constant.TransaksiRunningNumberEnum;
import com.artivisi.pos.model.transaksi.Penjualan;
import com.artivisi.pos.model.transaksi.PenjualanDetail;
import com.artivisi.pos.ui.dialog.transaksi.PenjualanAddDialog;
import com.artivisi.pos.ui.dialog.transaksi.PenjualanSearchDialog;
import com.artivisi.pos.ui.frame.FrameUtama;
import com.artivisi.pos.util.POSTableCellRenderer;
import com.artivisi.pos.util.TextComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ifnu
 */
public class PenjualanPanel extends javax.swing.JInternalFrame {

    private List<PenjualanDetail> penjualanDetails = new ArrayList<PenjualanDetail>();
    private Penjualan penjualan;
    private PenjualanDetail selectedPenjualanDetail;

    /** Creates new form PenjualanPanel */
    public PenjualanPanel() {
        initComponents();
        initListeners();
        enableForm(false);

        tblPenjualanDetail.setAutoCreateColumnsFromModel(false);
        tblPenjualanDetail.getSelectionModel().addListSelectionListener(new PenjualanDetailSelectionListener());
        tblPenjualanDetail.setDefaultRenderer(Object.class, new POSTableCellRenderer());

        TextComponentUtils.setCurrency(txtTotal);
    }

    private void initListeners(){
        transaksiToolbarPanel1.getBtnTambah().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableForm(true);
                txtId.setText(FrameUtama.getMasterService().ambilBerikutnya(TransaksiRunningNumberEnum.PENJUALAN));
                jdcTanggal.setDate(FrameUtama.getMasterService().tanggalKerja());
            }
        });
        transaksiToolbarPanel1.getBtnSimpan().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(validateForm()){
                    if(penjualan == null) penjualan = new Penjualan();
                    loadFormToDomain();
                    FrameUtama.getTransaksiService().simpan(penjualan);
                    penjualan = null;
                    clearForm();
                    enableForm(false);
                }
            }
        });
        transaksiToolbarPanel1.getBtnBatal().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                clearForm();
                enableForm(false);
                selectedPenjualanDetail = null;
                penjualan = null;
                penjualanDetails.clear();
                tblPenjualanDetail.setModel(new PenjualanDetailTableModel());
            }
        });
        transaksiToolbarPanel1.getBtnEdit().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(penjualan!=null){
                    enableForm(true);
                } else {
                    JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih penjualan lewat tombol cari!",
                            "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
//        transaksiToolbarPanel1.getBtnHapus().addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                if(penjualan!=null){
//                    FrameUtama.getTransaksiService().hapus(penjualan);
//                    penjualan = null;
//                    selectedPenjualanDetail = null;
//                    penjualanDetails.clear();
//                    tblPenjualanDetail.setModel(new PenjualanDetailTableModel());
//                    clearForm();
//                    enableForm(false);
//                } else {
//                    JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih penjualan lewat tombol cari!",
//                            "Warning",JOptionPane.WARNING_MESSAGE);
//                }
//            }
//        });
        transaksiToolbarPanel1.getBtnKeluar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FrameUtama.getInstance().removeInternalFrame(PenjualanPanel.this);
            }
        });
        transaksiToolbarPanel1.getBtnCari().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Penjualan p = new PenjualanSearchDialog().showDialog();
                if(p!=null){
                    penjualan = FrameUtama.getTransaksiService().cariPenjualan(p.getId());
                    loadDomainToForm();
                    transaksiToolbarPanel1.getBtnBatal().setEnabled(true);
                    transaksiToolbarPanel1.getBtnCari().setEnabled(true);
                    transaksiToolbarPanel1.getBtnCetak().setEnabled(true);
                    transaksiToolbarPanel1.getBtnEdit().setEnabled(true);
                    transaksiToolbarPanel1.getBtnHapus().setEnabled(true);
                    transaksiToolbarPanel1.getBtnSimpan().setEnabled(false);
                    transaksiToolbarPanel1.getBtnTambah().setEnabled(false);
                }
            }
        });

    }
 private void loadDomainToForm(){
        jdcTanggal.setDate(penjualan.getTanggal());
        txtId.setText(penjualan.getId().toString());
        txtTotal.setText(TextComponentUtils.formatNumber(penjualan.getTotal()));
        penjualanDetails = penjualan.getDetails();
        tblPenjualanDetail.setModel(new PenjualanDetailTableModel());
    }
    private void loadFormToDomain(){
        penjualan.setTanggal(jdcTanggal.getDate());
        penjualan.setTotal(TextComponentUtils.parseNumberToBigDecimal(txtTotal.getText()));
        penjualan.setDetails(penjualanDetails);
    }
    private boolean validateForm(){
        if(penjualanDetails==null || penjualanDetails.isEmpty()){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Tambahkan dahulu penjualan detail!",
                    "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    private void enableForm(boolean status){
        btnEditDetail.setEnabled(status);
        btnHapusDetail.setEnabled(status);
        btnTambahDetail.setEnabled(status);
        tblPenjualanDetail.setEnabled(status);
        txtId.setEnabled(false);
        jdcTanggal.setEnabled(false);
        txtTotal.setEnabled(false);
    }

    private void clearForm(){
        txtId.setText("");
        txtTotal.setText("");
        jdcTanggal.setDate(new Date());
        penjualanDetails.clear();
        tblPenjualanDetail.setModel(new PenjualanDetailTableModel());
    }

    private void calculateTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for(PenjualanDetail d : penjualanDetails){
            total = total.add(d.getSubTotal());
        }
        txtTotal.setText(TextComponentUtils.formatNumber(total));
    }
    private class PenjualanDetailSelectionListener implements ListSelectionListener{

        public void valueChanged(ListSelectionEvent e) {
            if(tblPenjualanDetail.getSelectedRow()>=0){
                int index = tblPenjualanDetail.convertRowIndexToModel(tblPenjualanDetail.getSelectedRow());
                selectedPenjualanDetail = penjualanDetails.get(index);
            }
        }

    }
    private class PenjualanDetailTableModel extends AbstractTableModel{
        public int getRowCount() {
            return penjualanDetails.size();
        }
        public int getColumnCount() {
            return 4;
        }
        public Object getValueAt(int rowIndex, int columnIndex) {
            PenjualanDetail p = penjualanDetails.get(rowIndex);
            switch(columnIndex){
                case 0 : return p.getProduk().getNama();
                case 1 : return p.getHarga();
                case 2 : return p.getKuantitas();
                case 3 : return p.getSubTotal();
                default: return "";
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
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jdcTanggal = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnTambahDetail = new javax.swing.JButton();
        btnEditDetail = new javax.swing.JButton();
        btnHapusDetail = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPenjualanDetail = new javax.swing.JTable();
        transaksiToolbarPanel1 = new com.artivisi.pos.ui.toolbar.TransaksiToolbarPanel();

        jLabel1.setText("ID");

        jLabel2.setText("Tanggal");

        jLabel3.setText("Total");

        btnTambahDetail.setText("Tambah");
        btnTambahDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahDetailActionPerformed(evt);
            }
        });

        btnEditDetail.setText("Edit");
        btnEditDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditDetailActionPerformed(evt);
            }
        });

        btnHapusDetail.setText("Hapus");
        btnHapusDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusDetailActionPerformed(evt);
            }
        });

        tblPenjualanDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produk", "Harga Jual", "Quantity", "Sub Total"
            }
        ));
        jScrollPane1.setViewportView(tblPenjualanDetail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtId, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(17, 17, 17)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(496, 496, 496))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambahDetail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditDetail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusDetail))
                    .addComponent(transaksiToolbarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(transaksiToolbarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambahDetail)
                            .addComponent(btnEditDetail)
                            .addComponent(btnHapusDetail)))
                    .addComponent(jLabel2)
                    .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDetailActionPerformed
        PenjualanDetail p = new PenjualanAddDialog().showDialog();
        if(p!=null){
            penjualanDetails.add(p);
            tblPenjualanDetail.setModel(new PenjualanDetailTableModel());
            calculateTotal();
        }
    }//GEN-LAST:event_btnTambahDetailActionPerformed

    private void btnEditDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDetailActionPerformed
        if(selectedPenjualanDetail!=null){
            selectedPenjualanDetail = new PenjualanAddDialog().showDialog(selectedPenjualanDetail);
            //notifikasi table bahwa ada perubahan di baris yg sedang dipilih
            tblPenjualanDetail.tableChanged(
                    new TableModelEvent(tblPenjualanDetail.getModel(), tblPenjualanDetail.getSelectedRow()));
            calculateTotal();
        } else {
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih penjualan detail terlebih dahulu!"
                    ,"Warning",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEditDetailActionPerformed

    private void btnHapusDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusDetailActionPerformed
        if(selectedPenjualanDetail!=null){
            penjualanDetails.remove(selectedPenjualanDetail);
            tblPenjualanDetail.setModel(new PenjualanDetailTableModel());
            calculateTotal();
            selectedPenjualanDetail = null;
        } else {
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih penjualan detail terlebih dahulu!",
                    "Warning",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusDetailActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditDetail;
    private javax.swing.JButton btnHapusDetail;
    private javax.swing.JButton btnTambahDetail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private javax.swing.JTable tblPenjualanDetail;
    private com.artivisi.pos.ui.toolbar.TransaksiToolbarPanel transaksiToolbarPanel1;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

}
