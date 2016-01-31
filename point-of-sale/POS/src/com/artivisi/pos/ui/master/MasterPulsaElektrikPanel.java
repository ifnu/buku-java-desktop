/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MasterPulsaElektrik.java
 *
 * Created on Apr 29, 2009, 10:25:26 AM
 */
package com.artivisi.pos.ui.master;

import com.artivisi.pos.model.master.Produk;
import com.artivisi.pos.model.master.PulsaElektrik;
import com.artivisi.pos.ui.dialog.master.ProdukSearchDialog;
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
public class MasterPulsaElektrikPanel extends javax.swing.JInternalFrame {

    private List<PulsaElektrik> daftarPulsaElektrik;
    private PulsaElektrik pilihanPulsaElektrik;
    private Produk produk;

    /** Creates new form MasterPulsaElektrik */
    public MasterPulsaElektrikPanel() {
        initComponents();

        initListener();
        isiTableDaftarPulsaElektrik();

        enableForm(false);

        tblPulsaElektrik.setAutoCreateColumnsFromModel(false);
        tblPulsaElektrik.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());

        TextComponentUtils.setAutoUpperCaseText(30,txtKode);
        TextComponentUtils.setAutoUpperCaseText(90,txtNama);
        TextComponentUtils.setAutoUpperCaseText(txtSearch);
        TextComponentUtils.setCurrency(txtHargaJual);
        TextComponentUtils.setCurrency(txtNominal);
    }

    private void initListener() {
        // Listener untuk table ketika table di Klik 
        tblPulsaElektrik.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                refreshBarisTablePulsaElektrik();
            }
        });

        //ketika tombol delete di Klik
        masterToolbarPanel1.getBtnHapus().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                askDelete();
                FrameUtama.getMasterService().hapus(pilihanPulsaElektrik);
                isiTableDaftarPulsaElektrik();
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
                FrameUtama.getInstance().removeInternalFrame(MasterPulsaElektrikPanel.this);
            }
        });

        // Ketika tombol Simpan di Klik
        masterToolbarPanel1.getBtnSimpan().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                //buat Object PulsaElektrik terlebih dahulu
                if (validasi()) {
                    //buat Object PulsaElektrik terlebih dahulu
                    if (pilihanPulsaElektrik == null) {
                        pilihanPulsaElektrik = new PulsaElektrik();
                    }

                    //ambil value kemudian dari text field kemudian di save ke dalam database
                    pilihanPulsaElektrik.setId(txtKode.getText());
                    pilihanPulsaElektrik.setNama(txtNama.getText());
                    pilihanPulsaElektrik.setHargaJual(TextComponentUtils.parseNumberToBigDecimal(txtHargaJual.getText()));
                    pilihanPulsaElektrik.setNominal(TextComponentUtils.parseNumberToBigDecimal(txtNominal.getText()));
                    pilihanPulsaElektrik.setProduk(produk);
                    

                    //simpan ke database dengan Menggunakan Method Simpan di Object Service
                    FrameUtama.getMasterService().simpan(pilihanPulsaElektrik);

                    //Kembali Kondisi Awal
                    clearForm();
                    enableForm(false);
                    masterToolbarPanel1.kondisiAwal();
                    //refresh table PulsaElektrik
                    isiTableDaftarPulsaElektrik();
                }
            }
        });
    }

    private void refreshBarisTablePulsaElektrik(){
        //kalau tidak ada row yang di pilih maka akan menghasilkan -1
        if (tblPulsaElektrik.getSelectedRow() >= 0) {
            //ambil row yang di Pilihan
            int row = tblPulsaElektrik.getSelectedRow();

            pilihanPulsaElektrik = FrameUtama.getMasterService().pulsaElektrikBerdasarId(daftarPulsaElektrik.get(row).getId());
            daftarPulsaElektrik.set(row, pilihanPulsaElektrik);
            tblPulsaElektrik.tableChanged(new TableModelEvent(tblPulsaElektrik.getModel(), row));

            txtKode.setText(pilihanPulsaElektrik.getId());
            txtNama.setText(pilihanPulsaElektrik.getNama());

            txtHargaJual.setText(TextComponentUtils.formatNumber(pilihanPulsaElektrik.getHargaJual()));
            txtNominal.setText(TextComponentUtils.formatNumber(pilihanPulsaElektrik.getNominal()));
            txtProduk.setText(pilihanPulsaElektrik.getProduk().getNama());

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
        txtHargaJual.setText("");
        txtNominal.setText("");
        txtProduk.setText("");
        tblPulsaElektrik.getSelectionModel().clearSelection();
        pilihanPulsaElektrik = null;
        produk = null;
    }

    private void enableForm(boolean status){
        txtKode.setEnabled(status);
        txtNama.setEnabled(status);
        txtNominal.setEnabled(status);

        txtHargaJual.setEnabled(status);
        txtProduk.setEnabled(status);
        btnLookup.setEnabled(status);
    }

    private void isiTableDaftarPulsaElektrik() {
        daftarPulsaElektrik = FrameUtama.getMasterService().semuaPulsaElektrik();
        tblPulsaElektrik.setModel(new PulsaElektrikTableModel(daftarPulsaElektrik));

    }

    private class PulsaElektrikTableModel extends AbstractTableModel {

        private List<PulsaElektrik> daftarPulsaElektrik;

        public PulsaElektrikTableModel(List<PulsaElektrik> daftarPulsaElektrik) {
            this.daftarPulsaElektrik = daftarPulsaElektrik;
        }

        public int getRowCount() {
            return daftarPulsaElektrik.size();
        }

        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int col) {
            switch(col){
                case 0 : return "Kode barang";
                case 1 : return "Nama Barang";
                case 2 : return "Nominal";
                case 3 : return "Harga Jual";
                case 4 : return "Produk";
                default : return "";
            }

        }

        public Object getValueAt(int row, int col) {
            PulsaElektrik p = daftarPulsaElektrik.get(row);
            switch(col){
                case 0 : return p.getId();
                case 1 : return p.getNama();
                case 2 : return p.getNominal();
                case 3 : return p.getHargaJual();
                case 4 : return p.getProduk().getNama();
                default : return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex){
                case 2 : return BigDecimal.class;
                case 3 : return BigDecimal.class;
                default : return String.class;
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
        if (txtHargaJual.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Harga Harus di isi", "Field Harus di isi", JOptionPane.ERROR_MESSAGE);
            txtHargaJual.requestFocus();
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
        txtKode = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtHargaJual = new javax.swing.JFormattedTextField();
        txtProduk = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        btnLookup = new javax.swing.JButton();
        validationPanel = new org.netbeans.validation.api.ui.ValidationPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNominal = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPulsaElektrik = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        masterToolbarPanel1 = new com.artivisi.pos.ui.toolbar.MasterToolbarPanel();

        setClosable(true);
        setTitle("Master Produk");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Form"));

        jLabel3.setText("Kode Pulsa Elektrik");

        jLabel4.setText("Nama");

        jLabel5.setText("Nominal");

        txtProduk.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));

        jLabel7.setText("Produk");

        btnLookup.setText("...");
        btnLookup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLookupActionPerformed(evt);
            }
        });

        jLabel6.setText("Harga Jual");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addGap(6, 6, 6))
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNominal, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLookup))
                    .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNominal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLookup))))
                .addGap(8, 8, 8)
                .addComponent(validationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(279, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtHargaJual, txtKode, txtNama});

        tblPulsaElektrik.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Nama", "Harga Jual", "Produk"
            }
        ));
        tblPulsaElektrik.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPulsaElektrik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPulsaElektrikMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPulsaElektrik);

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

    private void tblPulsaElektrikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPulsaElektrikMouseClicked
        refreshBarisTablePulsaElektrik();
    }//GEN-LAST:event_tblPulsaElektrikMouseClicked

    private void btnLookupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLookupActionPerformed
        Produk p = new ProdukSearchDialog().showDialog();
        if(p!=null){
            produk = p;
            txtProduk.setText(p.getNama());
        }
        
    }//GEN-LAST:event_btnLookupActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLookup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.artivisi.pos.ui.toolbar.MasterToolbarPanel masterToolbarPanel1;
    private javax.swing.JTable tblPulsaElektrik;
    private javax.swing.JFormattedTextField txtHargaJual;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    private javax.swing.JFormattedTextField txtNominal;
    private javax.swing.JFormattedTextField txtProduk;
    private javax.swing.JTextField txtSearch;
    private org.netbeans.validation.api.ui.ValidationPanel validationPanel;
    // End of variables declaration//GEN-END:variables
}
