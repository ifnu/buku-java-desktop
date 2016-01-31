/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MasterSatuan.java
 *
 * Created on Apr 29, 2009, 10:25:26 AM
 */
package com.artivisi.pos.ui.master;

import com.artivisi.pos.model.master.Satuan;
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
public class MasterSatuanPanel extends javax.swing.JInternalFrame {

    private List<Satuan> daftarSatuan;
    private Satuan pilihanSatuan;

    /** Creates new form MasterSatuan */
    public MasterSatuanPanel() {
        initComponents();

        initListener();
        isiTableDaftarSatuan();

        enableForm(false);

        tblSatuan.setAutoCreateColumnsFromModel(false);
        tblSatuan.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());

        TextComponentUtils.setAutoUpperCaseText(10,txtKode);
        TextComponentUtils.setAutoUpperCaseText(25,txtNama);
        TextComponentUtils.setAutoUpperCaseText(txtSearch);
    }

    private void initListener() {
        // Listener untuk table ketika table di Klik 
        tblSatuan.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                refreshBarisTableSatuan();
            }
        });

        //ketika tombol delete di Klik
        masterToolbarPanel1.getBtnHapus().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                askDelete();
                FrameUtama.getMasterService().hapus(pilihanSatuan);
                isiTableDaftarSatuan();
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
                FrameUtama.getInstance().removeInternalFrame(MasterSatuanPanel.this);
            }
        });

        // Ketika tombol Simpan di Klik
        masterToolbarPanel1.getBtnSimpan().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                //buat Object Satuan terlebih dahulu
                if (validasi()) {
                    //buat Object Satuan terlebih dahulu
                    if (pilihanSatuan == null) {
                        pilihanSatuan = new Satuan();
                    }

                    //ambil value kemudian dari text field kemudian di save ke dalam database
                    pilihanSatuan.setId(txtKode.getText());
                    pilihanSatuan.setNama(txtNama.getText());
                    

                    //simpan ke database dengan Menggunakan Method Simpan di Object Service
                    FrameUtama.getMasterService().simpan(pilihanSatuan);

                    //Kembali Kondisi Awal
                    clearForm();
                    enableForm(false);
                    masterToolbarPanel1.kondisiAwal();
                    //refresh table Satuan
                    isiTableDaftarSatuan();
                }
            }
        });
    }

    private void refreshBarisTableSatuan(){
        //kalau tidak ada row yang di pilih maka akan menghasilkan -1
        if (tblSatuan.getSelectedRow() >= 0) {
            //ambil row yang di Pilihan
            int row = tblSatuan.getSelectedRow();

            pilihanSatuan = daftarSatuan.get(row);
            daftarSatuan.set(row, pilihanSatuan);
            tblSatuan.tableChanged(new TableModelEvent(tblSatuan.getModel(), row));

            txtKode.setText(pilihanSatuan.getId());
            txtNama.setText(pilihanSatuan.getNama());

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
        txtKode.setText("");
        txtNama.setText("");
        tblSatuan.getSelectionModel().clearSelection();
        pilihanSatuan = null;
    }

    private void enableForm(boolean status){
        txtKode.setEnabled(status);
        txtNama.setEnabled(status);
    }

    private void isiTableDaftarSatuan() {
        daftarSatuan = FrameUtama.getMasterService().semuaSatuan();
        tblSatuan.setModel(new SatuanTableModel(daftarSatuan));

    }

    private class SatuanTableModel extends AbstractTableModel {

        private List<Satuan> daftarSatuan;

        public SatuanTableModel(List<Satuan> daftarSatuan) {
            this.daftarSatuan = daftarSatuan;
        }

        public int getRowCount() {
            return daftarSatuan.size();
        }

        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int col) {
            switch(col){
                case 0 : return "Kode Satuan";
                case 1 : return "Nama Satuan";
                default : return "";
            }

        }

        public Object getValueAt(int row, int col) {
            Satuan p = daftarSatuan.get(row);
            switch(col){
                case 0 : return p.getId();
                case 1 : return p.getNama();
                default : return "";
            }
        }

    }

    private void askDelete(){
        JOptionPane.showConfirmDialog(this, "Yakin ???");
    }

    private boolean validasi() {
        // Periksa Kalau Kode Kosong tampilkan Message Box
        if (txtKode.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Kode Harus di isi", "Field Harus di isi", JOptionPane.ERROR_MESSAGE);
            txtKode.requestFocus();
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
        txtKode = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        validationPanel = new org.netbeans.validation.api.ui.ValidationPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSatuan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        masterToolbarPanel1 = new com.artivisi.pos.ui.toolbar.MasterToolbarPanel();

        setClosable(true);
        setTitle("Master Produk");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Form"));

        jLabel3.setText("Kode ");

        jLabel4.setText("Nama");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(151, 151, 151))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(validationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(112, 112, 112)
                .addComponent(validationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(279, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtKode, txtNama});

        tblSatuan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Nama"
            }
        ));
        tblSatuan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSatuan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSatuanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSatuan);

        jLabel1.setText("Search Character");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(masterToolbarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblSatuanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSatuanMouseClicked
        refreshBarisTableSatuan();
    }//GEN-LAST:event_tblSatuanMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.artivisi.pos.ui.toolbar.MasterToolbarPanel masterToolbarPanel1;
    private javax.swing.JTable tblSatuan;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSearch;
    private org.netbeans.validation.api.ui.ValidationPanel validationPanel;
    // End of variables declaration//GEN-END:variables
}
