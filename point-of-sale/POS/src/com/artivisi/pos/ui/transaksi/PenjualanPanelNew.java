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

import com.artivisi.pos.model.master.Produk;
import com.artivisi.pos.model.master.PulsaElektrik;
import com.artivisi.pos.model.transaksi.Penjualan;
import com.artivisi.pos.model.transaksi.PenjualanDetail;
import com.artivisi.pos.ui.dialog.master.ProdukDanPulsaElektrikSearchDialog;
import com.artivisi.pos.ui.dialog.transaksi.PembayaranDialog;
import com.artivisi.pos.ui.frame.FrameUtama;
import com.artivisi.pos.util.POSTableCellRenderer;
import com.artivisi.pos.util.TextComponentUtils;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ifnu
 */
public class PenjualanPanelNew extends javax.swing.JInternalFrame {

    private List<PenjualanDetail> penjualanDetails = new ArrayList<PenjualanDetail>();
    private Penjualan penjualan;
    private PenjualanDetail selectedPenjualanDetail;

    /** Creates new form PenjualanPanel */
    public PenjualanPanelNew() {
        initComponents();

        tblPenjualanDetail.setAutoCreateColumnsFromModel(false);
        tblPenjualanDetail.getSelectionModel().addListSelectionListener(new PenjualanDetailSelectionListener());
        tblPenjualanDetail.setDefaultRenderer(Object.class, new POSTableCellRenderer());
        tblPenjualanDetail.setDefaultEditor(String.class, new PenjualanDetailTableEditor(new JTextField()));
        tblPenjualanDetail.setModel(new PenjualanDetailTableModel());

    }

    private void loadFormToDomain(){
        penjualan.setTanggal(FrameUtama.getMasterService().tanggalKerja());
        penjualan.setTotal(hitungTotal());
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

    private void clearForm(){
        penjualanDetails.clear();
        tblPenjualanDetail.setModel(new PenjualanDetailTableModel());
        lblTotal.setText("");
    }

    private BigDecimal hitungTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for(PenjualanDetail d : penjualanDetails){
            total = total.add(d.getSubTotal());
        }
        lblTotal.setText(TextComponentUtils.formatNumber(total));
        return total;
    }
    private class PenjualanDetailSelectionListener implements ListSelectionListener{

        public void valueChanged(ListSelectionEvent e) {
            if(tblPenjualanDetail.getSelectedRow()>=0 && tblPenjualanDetail.getSelectedRow()<penjualanDetails.size()){
                selectedPenjualanDetail = penjualanDetails.get(tblPenjualanDetail.getSelectedRow());
            }
        }

    }
    private class PenjualanDetailTableEditor extends DefaultCellEditor{

        public PenjualanDetailTableEditor(JComboBox comboBox) {
            super(comboBox);
        }

        public PenjualanDetailTableEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public PenjualanDetailTableEditor(JTextField textField) {
            super(textField);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            Component c= super.getTableCellEditorComponent(table, value, isSelected, row, column);
            if(c instanceof JTextField){
                JTextField j = (JTextField) c;
            }
            return c;
        }


    }
    private class PenjualanDetailTableModel extends AbstractTableModel{
        public int getRowCount() {
            return penjualanDetails.size() + 1;
        }
        public int getColumnCount() {
            return 5;
        }
        public Object getValueAt(int rowIndex, int columnIndex) {
            if(rowIndex>= penjualanDetails.size()) return "";
            PenjualanDetail p = penjualanDetails.get(rowIndex);
            switch(columnIndex){
                case 0 : 
                        if(p.getPulsaElektrik()!=null){
                            return p.getPulsaElektrik().getId();
                        } else {
                            return p.getProduk().getId();
                        }
                case 1 : 
                    if(p.getPulsaElektrik()!=null){
                        return p.getPulsaElektrik().getNama();
                    } else {
                        return p.getProduk().getNama();
                    }
                case 2 : return p.getKuantitas();
                case 3 : return p.getHarga();
                case 4 : return p.getSubTotal();
                default: return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex){
                case 3 : return BigDecimal.class;
                case 5 : return BigDecimal.class;
                case 6 : return BigDecimal.class;
                default : return Object.class;
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            PenjualanDetail d = null;
            if(rowIndex<penjualanDetails.size()){
                d = penjualanDetails.get(rowIndex);
            }

            switch(columnIndex){
                case 0 :
                    Produk p = FrameUtama.getMasterService().produkBerdasarId((String)aValue);
                    if(p!=null){
                        d = buatPenjualanDetail(p);
                        tambahkanPenjualanDetailKeTable(d);
                        hitungTotal();
                    } else {
                        PulsaElektrik pulsa = FrameUtama.getMasterService().pulsaElektrikBerdasarId((String) aValue);
                        if(pulsa!=null){
                            d = buatPenjualanDetail(pulsa);
                            tambahkanPenjualanDetailKeTable(d);
                            hitungTotal();
                        }
                    }
                    break;
                case 2:
                    d.setKuantitas(Integer.valueOf((String)aValue));
                    d.setSubTotal(hitungSubTotal(d));
                    hitungTotal();
                    break;
            }
            fireTableDataChanged();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if(columnIndex == 0 || columnIndex == 2 || columnIndex == 4) return true;
            return false;
        }

    }

    private BigDecimal hitungSubTotal(PenjualanDetail d){
            BigDecimal subTotal =BigDecimal.ZERO;
            if(d.getKuantitas()!=null && d.getHarga()!=null){
                subTotal = new BigDecimal(d.getKuantitas()).multiply(d.getHarga());
            }
            return subTotal;
        }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTotal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPenjualanDetail = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();

        tblPenjualanDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PLU", "Produk", "Quantity", "Harga Jual", "Sub Total"
            }
        ));
        tblPenjualanDetail.setCellSelectionEnabled(true);
        tblPenjualanDetail.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPenjualanDetail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblPenjualanDetailKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblPenjualanDetail);

        jLabel1.setText("Hapus:ESC Q1:F3 Q2:F4 Bayar&Simpan:F5 Cari:F6");

        txtKode.setFont(new java.awt.Font("Lucida Grande", 0, 18));
        txtKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeActionPerformed(evt);
            }
        });
        txtKode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKodeKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(555, 555, 555)
                                .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPenjualanDetailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblPenjualanDetailKeyReleased
            
        if(evt.getKeyCode() == KeyEvent.VK_F3){
            if(tblPenjualanDetail.getSelectedRow() == penjualanDetails.size()
                     && penjualanDetails.size()>0){
                tblPenjualanDetail.getColumnModel().getSelectionModel().setSelectionInterval(2, 2);
                tblPenjualanDetail.getSelectionModel()
                        .setSelectionInterval(tblPenjualanDetail.getSelectedRow()-1, tblPenjualanDetail.getSelectedRow()-1);
            }
        } else if(evt.getKeyCode() == KeyEvent.VK_F4){
            if(tblPenjualanDetail.getSelectedRow() == penjualanDetails.size()
                     && penjualanDetails.size()>0){
                tblPenjualanDetail.getColumnModel().getSelectionModel().setSelectionInterval(4, 4);
                tblPenjualanDetail.getSelectionModel()
                        .setSelectionInterval(tblPenjualanDetail.getSelectedRow()-1, tblPenjualanDetail.getSelectedRow()-1);
            }
        } else if(evt.getKeyCode() == KeyEvent.VK_F5){
            handleF5();
        } else if(evt.getKeyCode() == KeyEvent.VK_F6){
            handleF6();
        } else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            if(tblPenjualanDetail.getSelectedRow() < penjualanDetails.size() - 1
                    && tblPenjualanDetail.getSelectedRow()>=0){
                int col = tblPenjualanDetail.getSelectedColumn();
                int row = tblPenjualanDetail.getSelectedRow();
                penjualanDetails.remove(tblPenjualanDetail.getSelectedRow());
                if(row == penjualanDetails.size()){
                    col = 0;
                }
                tblPenjualanDetail.getColumnModel().getSelectionModel().setSelectionInterval(col, col);
                tblPenjualanDetail.getSelectionModel().setSelectionInterval(row, row);
                tblPenjualanDetail.setEditingColumn(col);
                tblPenjualanDetail.setEditingRow(row);
            }
        }

    }//GEN-LAST:event_tblPenjualanDetailKeyReleased

    private void txtKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeActionPerformed
        String id = txtKode.getText();
        if(id!=null && id.length()>0){
            Produk p = FrameUtama.getMasterService().produkBerdasarId(id);
            if(p!=null){
                PenjualanDetail d = buatPenjualanDetail(p);
                tambahkanPenjualanDetailKeTable(d);
                tblPenjualanDetail.setModel(new PenjualanDetailTableModel());
            }
            txtKode.setText("");
        }
    }//GEN-LAST:event_txtKodeActionPerformed

    private void txtKodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_F6){
            handleF6();
            hitungTotal();
        } else if (evt.getKeyCode() == KeyEvent.VK_F5){
            handleF5();
        }
    }//GEN-LAST:event_txtKodeKeyReleased

    private void handleF5(){
        BigDecimal pembayaran = new PembayaranDialog().showDialog(TextComponentUtils.parseNumberToBigDecimal(lblTotal.getText()));
        if(pembayaran.compareTo(BigDecimal.ZERO)>0
                && pembayaran.compareTo(hitungTotal())>=0){
            //TODO direct print here
            //save transaksi
            if(penjualan == null) penjualan = new Penjualan();
            loadFormToDomain();
            FrameUtama.getTransaksiService().simpan(penjualan);
            penjualan = null;
            clearForm();
            tblPenjualanDetail.getColumnModel().getSelectionModel().setSelectionInterval(0, 0);
            tblPenjualanDetail.getSelectionModel()
                    .setSelectionInterval(0,0);
        }
    }

    private void handleF6(){
        Object o = new ProdukDanPulsaElektrikSearchDialog().showDialog();
        if(o!=null){
            if(o instanceof Produk){
                Produk p = (Produk) o;
                PenjualanDetail d = buatPenjualanDetail(p);
                tambahkanPenjualanDetailKeTable(d);
                hitungTotal();
            } else {
                PulsaElektrik p = (PulsaElektrik) o;
                PenjualanDetail d = buatPenjualanDetail(p);
                tambahkanPenjualanDetailKeTable(d);
                hitungTotal();
            }
            tblPenjualanDetail.setModel(new PenjualanDetailTableModel());
        }
    }

    private PenjualanDetail buatPenjualanDetail(Produk p){
        PenjualanDetail d = new PenjualanDetail();
        d.setProduk(p);
        d.setHarga(p.getHargaJual());
        d.setKuantitas(1);
        d.setSubTotal(hitungSubTotal(d));
        d.setProduk(p);
        return d;
    }

    private PenjualanDetail buatPenjualanDetail(PulsaElektrik p){
        PenjualanDetail d = new PenjualanDetail();
        d.setPulsaElektrik(p);
        d.setHarga(p.getHargaJual());
        d.setKuantitas(1);
        d.setSubTotal(hitungSubTotal(d));
        return d;
    }

    private void tambahkanPenjualanDetailKeTable(PenjualanDetail d){
        for(PenjualanDetail detail : penjualanDetails){
            if(detail.getProduk() !=null &&
                    d.getProduk()!=null &&
                    detail.getProduk().equals(d.getProduk())){
                detail.setKuantitas(detail.getKuantitas() + d.getKuantitas());
                detail.setSubTotal(hitungSubTotal(detail));
                return;
            } else if(detail.getPulsaElektrik()!=null &&
                    d.getPulsaElektrik()!=null &&
                    detail.getPulsaElektrik().equals(d.getPulsaElektrik())){
                detail.setKuantitas(detail.getKuantitas() + d.getKuantitas());
                detail.setSubTotal(hitungSubTotal(detail));
                return;
            }
        }
        penjualanDetails.add(d);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblPenjualanDetail;
    private javax.swing.JTextField txtKode;
    // End of variables declaration//GEN-END:variables

}
