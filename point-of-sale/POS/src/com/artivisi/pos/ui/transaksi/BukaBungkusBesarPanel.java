/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PembelianPanel.java
 *
 * Created on Jul 1, 2009, 3:16:26 PM
 */

package com.artivisi.pos.ui.transaksi;

import com.artivisi.pos.model.master.constant.TransaksiRunningNumberEnum;
import com.artivisi.pos.model.transaksi.Pembelian;
import com.artivisi.pos.model.transaksi.PembelianDetail;
import com.artivisi.pos.ui.dialog.transaksi.PembelianAddDialog;
import com.artivisi.pos.ui.dialog.transaksi.PembelianSearchDialog;
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
import org.hibernate.StaleObjectStateException;

/**
 *
 * @author ifnu
 */
public class BukaBungkusBesarPanel extends javax.swing.JInternalFrame {

    private List<PembelianDetail> pembelianDetails = new ArrayList<PembelianDetail>();
    private Pembelian pembelian;
    private PembelianDetail selectedPembelianDetail;

    /** Creates new form PembelianPanel */
    public BukaBungkusBesarPanel() {
        initComponents();
        initListeners();
        enableForm(false);
        
        tblPembelianDetail.setAutoCreateColumnsFromModel(false);
        tblPembelianDetail.getSelectionModel().addListSelectionListener(new PembelianDetailSelectionListener());
        tblPembelianDetail.setDefaultRenderer(Object.class, new POSTableCellRenderer());

        TextComponentUtils.setCurrency(txtTotal);
    }

    private void initListeners(){
        transaksiToolbarPanel1.getBtnTambah().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableForm(true);
                txtId.setText(FrameUtama.getMasterService().ambilBerikutnya(TransaksiRunningNumberEnum.PEMBELIAN));
                jdcTanggal.setDate(FrameUtama.getMasterService().tanggalKerja());
            }
        });
        transaksiToolbarPanel1.getBtnSimpan().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(validateForm()){
                    if(pembelian == null) pembelian = new Pembelian();
                    loadFormToDomain();
                    try{
                        FrameUtama.getTransaksiService().simpan(pembelian);
                    } catch(StaleObjectStateException ex){
                        JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Data sudah diupdate oleh user lain!"
                                ,"Warning",JOptionPane.WARNING_MESSAGE);
                    }
                    pembelian = null;
                    clearForm();
                    enableForm(false);
                }
            }
        });
        transaksiToolbarPanel1.getBtnBatal().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                clearForm();
                enableForm(false);
                selectedPembelianDetail = null;
                pembelian = null;
                pembelianDetails.clear();
                tblPembelianDetail.setModel(new PembelianDetailTableModel());
            }
        });
        transaksiToolbarPanel1.getBtnEdit().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(pembelian!=null){
                    enableForm(true);
                } else {
                    JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih pembelian lewat tombol cari!",
                            "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        transaksiToolbarPanel1.getBtnHapus().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(pembelian!=null){
//                    FrameUtama.getTransaksiService().hapus(pembelian);
                    pembelian = null;
                    selectedPembelianDetail = null;
                    pembelianDetails.clear();
                    tblPembelianDetail.setModel(new PembelianDetailTableModel());
                    clearForm();
                    enableForm(false);
                } else {
                    JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih pembelian lewat tombol cari!",
                            "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        transaksiToolbarPanel1.getBtnKeluar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FrameUtama.getInstance().removeInternalFrame(BukaBungkusBesarPanel.this);
            }
        });
        transaksiToolbarPanel1.getBtnCari().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Pembelian p = new PembelianSearchDialog().showDialog();
                if(p!=null){
                    pembelian = FrameUtama.getTransaksiService().cariPembelian(p.getId());
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


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jdcTanggal = new com.toedter.calendar.JDateChooser();
        btnTambahDetail = new javax.swing.JButton();
        btnEditDetail = new javax.swing.JButton();
        btnHapusDetail = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPembelianDetail = new javax.swing.JTable();
        transaksiToolbarPanel1 = new com.artivisi.pos.ui.toolbar.TransaksiToolbarPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel1.setText("ID");

        jLabel2.setText("Tanggal");

        jLabel3.setText("Total ");

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

        tblPembelianDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produk", "Harga Beli", "Quantity", "Sub Total"
            }
        ));
        jScrollPane1.setViewportView(tblPembelianDetail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTambahDetail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditDetail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapusDetail))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(300, 300, 300))))
                    .addComponent(transaksiToolbarPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(transaksiToolbarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambahDetail)
                            .addComponent(btnEditDetail)
                            .addComponent(btnHapusDetail)))
                    .addComponent(jLabel2)
                    .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadDomainToForm(){
        jdcTanggal.setDate(pembelian.getTanggal());
        txtId.setText(pembelian.getId().toString());
        txtTotal.setText(TextComponentUtils.formatNumber(pembelian.getTotal()));
        pembelianDetails = pembelian.getDetails();
        tblPembelianDetail.setModel(new PembelianDetailTableModel());
    }
    private void loadFormToDomain(){
        pembelian.setTanggal(jdcTanggal.getDate());
        pembelian.setTotal(TextComponentUtils.parseNumberToBigDecimal(txtTotal.getText()));
        pembelian.setDetails(pembelianDetails);
    }
    private boolean validateForm(){
        if(pembelianDetails==null || pembelianDetails.isEmpty()){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Tambahkan dahulu pembelian detail!",
                    "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    private void enableForm(boolean status){
        btnEditDetail.setEnabled(status);
        btnHapusDetail.setEnabled(status);
        btnTambahDetail.setEnabled(status);
        tblPembelianDetail.setEnabled(status);
        txtId.setEnabled(false);
        jdcTanggal.setEnabled(false);
        txtTotal.setEnabled(false);
    }

    private void clearForm(){
        txtId.setText("");
        txtTotal.setText("");
        jdcTanggal.setDate(new Date());
        pembelianDetails.clear();
        tblPembelianDetail.setModel(new PembelianDetailTableModel());
    }

    private void btnTambahDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDetailActionPerformed
        PembelianDetail p = new PembelianAddDialog().showDialog();
        if(p!=null){
            pembelianDetails.add(p);
            tblPembelianDetail.setModel(new PembelianDetailTableModel());
            calculateTotal();
        }
    }//GEN-LAST:event_btnTambahDetailActionPerformed

    private void btnEditDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDetailActionPerformed
        if(selectedPembelianDetail!=null){
            selectedPembelianDetail = new PembelianAddDialog().showDialog(selectedPembelianDetail);
            //notifikasi table bahwa ada perubahan di baris yg sedang dipilih
            tblPembelianDetail.tableChanged(
                    new TableModelEvent(tblPembelianDetail.getModel(), tblPembelianDetail.getSelectedRow()));
            calculateTotal();
        } else {
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih pembelian detail terlebih dahulu!"
                    ,"Warning",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEditDetailActionPerformed
    private void btnHapusDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusDetailActionPerformed
        if(selectedPembelianDetail!=null){
            pembelianDetails.remove(selectedPembelianDetail);
            tblPembelianDetail.setModel(new PembelianDetailTableModel());
            calculateTotal();
            selectedPembelianDetail = null;
        } else {
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih pembelian detail terlebih dahulu!",
                    "Warning",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusDetailActionPerformed

    private void calculateTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for(PembelianDetail d : pembelianDetails){
            total = total.add(d.getSubTotal());
        }
        txtTotal.setText(TextComponentUtils.formatNumber(total));
    }
    private class PembelianDetailSelectionListener implements ListSelectionListener{

        public void valueChanged(ListSelectionEvent e) {
            if(tblPembelianDetail.getSelectedRow()>=0){
                int index = tblPembelianDetail.convertRowIndexToModel(tblPembelianDetail.getSelectedRow());
                selectedPembelianDetail = pembelianDetails.get(index);
            }
        }

    }
    private class PembelianDetailTableModel extends AbstractTableModel{
        public int getRowCount() {
            return pembelianDetails.size();
        }
        public int getColumnCount() {
            return 4;
        }
        public Object getValueAt(int rowIndex, int columnIndex) {
            PembelianDetail p = pembelianDetails.get(rowIndex);
            switch(columnIndex){
                case 0 : return p.getProduk().getNama();
                case 1 : return p.getHarga();
                case 2 : return p.getKuantitas();
                case 3 : return p.getSubTotal();
                default: return "";
            }
        }
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditDetail;
    private javax.swing.JButton btnHapusDetail;
    private javax.swing.JButton btnTambahDetail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private javax.swing.JTable tblPembelianDetail;
    private com.artivisi.pos.ui.toolbar.TransaksiToolbarPanel transaksiToolbarPanel1;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

}
