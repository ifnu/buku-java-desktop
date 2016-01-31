/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MasterKartuPembayaran.java
 *
 * Created on Apr 29, 2009, 10:25:26 AM
 */
package com.artivisi.pos.ui.master;

import com.artivisi.pos.model.master.KartuPembayaran;
import com.artivisi.pos.ui.frame.FrameUtama;
import com.artivisi.pos.util.BigDecimalRenderer;
import com.artivisi.pos.util.TextComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author kurusgw
 */
public class KartuPembayaranPanel extends javax.swing.JInternalFrame {

    private List<KartuPembayaran> daftarKartuPembayaran;
    private KartuPembayaran pilihanKartuPembayaran;

    /** Creates new form MasterKartuPembayaran */
    public KartuPembayaranPanel() {
        initComponents();

        initListener();
        isiTableDaftarKartuPembayaran();

        enableForm(false);

        tblKartuPembayaran.setAutoCreateColumnsFromModel(false);
        tblKartuPembayaran.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());

        TextComponentUtils.setAutoUpperCaseText(10,txtId);
        TextComponentUtils.setAutoUpperCaseText(35,txtNama);
        TextComponentUtils.setAutoUpperCaseText(txtSearch);
        TextComponentUtils.setAutoUpperCaseText(50,txtBank);
    }

    private void initListener() {
        // Listener untuk table ketika table di Klik 
        tblKartuPembayaran.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                refreshBarisTableKartuPembayaran();
            }
        });

        //ketika tombol delete di Klik
        masterToolbarPanel1.getBtnHapus().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                askDelete();
                FrameUtama.getMasterService().hapus(pilihanKartuPembayaran);
                isiTableDaftarKartuPembayaran();
                clearForm();
                enableForm(false);
                masterToolbarPanel1.kondisiAwal();
            }
        });

        
        //button edit di Klik
        masterToolbarPanel1.getBtnEdit().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                enableForm(true);
                masterToolbarPanel1.kondisiTambah();
            }
        });

        // Ketika Button Tambah di Klick
        masterToolbarPanel1.getBtnTambah().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                clearForm();
                enableForm(true);
                masterToolbarPanel1.kondisiTambah();
            }
        });

        // Ketika Tombol Batal di Klik
        masterToolbarPanel1.getBtnBatal().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                enableForm(false);
                clearForm();
                masterToolbarPanel1.kondisiAwal();
            }
        });

        // Ketika Tombol Keluar di Klik
        masterToolbarPanel1.getBtnKeluar().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                FrameUtama.getInstance().removeInternalFrame(KartuPembayaranPanel.this);
            }
        });

        // Ketika tombol Simpan di Klik
        masterToolbarPanel1.getBtnSimpan().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                //buat Object KartuPembayaran terlebih dahulu
                if (validasi()) {
                    //buat Object KartuPembayaran terlebih dahulu
                    if (pilihanKartuPembayaran == null) {
                        pilihanKartuPembayaran = new KartuPembayaran();
                    }

                    //ambil value kemudian dari text field kemudian di save ke dalam database
                    pilihanKartuPembayaran.setId(txtId.getText());
                    pilihanKartuPembayaran.setNama(txtNama.getText());
                    pilihanKartuPembayaran.setBank(txtBank.getText());

                    //simpan ke database dengan Menggunakan Method Simpan di Object Service
                    FrameUtama.getMasterService().simpan(pilihanKartuPembayaran);

                    //Kembali Kondisi Awal
                    clearForm();
                    enableForm(false);
                    masterToolbarPanel1.kondisiAwal();
                    //refresh table KartuPembayaran
                    isiTableDaftarKartuPembayaran();
                }
            }
        });
    }

    private void refreshBarisTableKartuPembayaran(){
        //kalau tidak ada row yang di pilih maka akan menghasilkan -1
        if (tblKartuPembayaran.getSelectedRow() >= 0) {
            //ambil row yang di Pilihan
            int row = tblKartuPembayaran.getSelectedRow();

            pilihanKartuPembayaran = FrameUtama.getMasterService().kartuPembayaranBerdasarId(daftarKartuPembayaran.get(row).getId());
            daftarKartuPembayaran.set(row, pilihanKartuPembayaran);
            tblKartuPembayaran.tableChanged(new TableModelEvent(tblKartuPembayaran.getModel(), row));

            txtId.setText(pilihanKartuPembayaran.getId());
            txtNama.setText(pilihanKartuPembayaran.getNama());
            txtBank.setText(TextComponentUtils.formatNumber(pilihanKartuPembayaran.getBank()));
            enableForm(false);
            masterToolbarPanel1.kondisiTabelTerpilih();
        } else {
            //dianggap batal
            enableForm(false);
            clearForm();
            masterToolbarPanel1.kondisiAwal();
        }
    }

    private void clearForm(){
        txtBank.setText("");
        txtId.setText("");
        txtNama.setText("");
        tblKartuPembayaran.getSelectionModel().clearSelection();
    }

    private void enableForm(boolean status){
        txtBank.setEnabled(status);
        txtId.setEnabled(status);
        txtNama.setEnabled(status);
    }

    private void isiTableDaftarKartuPembayaran() {
        daftarKartuPembayaran = FrameUtama.getMasterService().semuaKartuPembayaran();
        tblKartuPembayaran.setModel(new KartuPembayaranTableModel(daftarKartuPembayaran));

    }
    private class KartuPembayaranTableModel extends AbstractTableModel {

        private List<KartuPembayaran> daftarKartuPembayaran;

        public KartuPembayaranTableModel(List<KartuPembayaran> daftarKartuPembayaran) {
            this.daftarKartuPembayaran = daftarKartuPembayaran;
        }

        public int getRowCount() {
            return daftarKartuPembayaran.size();
        }

        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int col) {
            switch(col){
                case 0 : return "Id Kartu";
                case 1 : return "Nama Kartu";
                case 2 : return "Bank";
                default : return "";
            }

        }

        public Object getValueAt(int row, int col) {
            KartuPembayaran p = daftarKartuPembayaran.get(row);
            switch(col){
                case 0 : return p.getId();
                case 1 : return p.getNama();
                case 2 : return p.getBank();
                default : return "";
            }
        }

    }

    private void askDelete(){
        JOptionPane.showConfirmDialog(this, "Yakin ???");
    }

    private boolean validasi() {
        // Periksa Kalau Kode Kosong tampilkan Message Box
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Kode Harus di isi", "Field Harus di isi", JOptionPane.ERROR_MESSAGE);
            txtId.requestFocus();
            return false;
        }
        // Periksa Kalau Nama Kosong tampilkan Message Box
        if (txtNama.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nama Harus di isi", "Field Harus di isi", JOptionPane.ERROR_MESSAGE);
            txtNama.requestFocus();
            return false;
        }
        // Periksa Kalau HArga Kosong tampilkan Message Box
        if (txtBank.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Bank Harus di isi", "Field Harus di isi", JOptionPane.ERROR_MESSAGE);
            txtBank.requestFocus();
            return false;
        }

        return true;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBank = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKartuPembayaran = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        masterToolbarPanel1 = new com.artivisi.pos.ui.toolbar.MasterToolbarPanel();

        setClosable(true);
        setTitle("Master Kartu Pembayaran");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Form"));

        jLabel3.setText("Id Kartu");

        jLabel4.setText("Nama Kartu");

        jLabel5.setText("Nama Bank");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtBank, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(270, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(321, 321, 321))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtBank, txtId, txtNama});

        tblKartuPembayaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Kartu", "Nama Kartu", "Nama Bank"
            }
        ));
        tblKartuPembayaran.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblKartuPembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKartuPembayaranMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKartuPembayaran);

        jLabel1.setText("Search Character");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(masterToolbarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(masterToolbarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblKartuPembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKartuPembayaranMouseClicked
        refreshBarisTableKartuPembayaran();
    }//GEN-LAST:event_tblKartuPembayaranMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.artivisi.pos.ui.toolbar.MasterToolbarPanel masterToolbarPanel1;
    private javax.swing.JTable tblKartuPembayaran;
    private javax.swing.JFormattedTextField txtBank;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
