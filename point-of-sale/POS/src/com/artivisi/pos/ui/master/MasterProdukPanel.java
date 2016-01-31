/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MasterProduk.java
 *
 * Created on Apr 29, 2009, 10:25:26 AM
 */
package com.artivisi.pos.ui.master;

import com.artivisi.pos.model.master.Produk;
import com.artivisi.pos.model.master.ProdukSatuan;
import com.artivisi.pos.model.master.Satuan;
import com.artivisi.pos.ui.dialog.master.ProdukSearchDialog;
import com.artivisi.pos.ui.dialog.master.SatuanSearchDialog;
import com.artivisi.pos.ui.frame.FrameUtama;
import com.artivisi.pos.util.BigDecimalRenderer;
import com.artivisi.pos.util.TextComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class MasterProdukPanel extends javax.swing.JInternalFrame {

    private List<Produk> daftarProduk;
    private Produk pilihanProduk;
    private ProdukSatuan pilihanProdukSatuan;
    private List<ProdukSatuan> produkSatuans = new ArrayList<ProdukSatuan>();
    private Satuan satuan;
    private Long saldoAwal;

    /** Creates new form MasterProduk */
    public MasterProdukPanel() {
        initComponents();

        initListener();
        isiTableDaftarProduk();

        enableForm(false);

        tblProduk.setAutoCreateColumnsFromModel(false);
        tblProduk.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tblRelasiProduk.setAutoCreateColumnsFromModel(false);
        tblRelasiProduk.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());

        TextComponentUtils.setAutoUpperCaseText(30,txtKode);
        TextComponentUtils.setAutoUpperCaseText(90,txtNama);
        TextComponentUtils.setAutoUpperCaseText(txtSearch);
        TextComponentUtils.setAutoUpperCaseText(90,txtSatuan);
        TextComponentUtils.setNumericTextOnly(txtSaldoAwal);
        TextComponentUtils.setNumericTextOnly(txtStok);
        TextComponentUtils.setCurrency(txtHargaBeli);
        TextComponentUtils.setCurrency(txtHargaJual);
    }

    private void initListener() {
        // Listener untuk table ketika table di Klik
        tblRelasiProduk.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if(tblRelasiProduk.getSelectedRow()>=0){
                    pilihanProdukSatuan = produkSatuans.get(tblRelasiProduk.getSelectedRow());
                }
            }
        });

        tblProduk.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                refreshBarisTableProduk();
            }
        });

        //ketika tombol delete di Klik
        masterToolbarPanel1.getBtnHapus().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                askDelete();
                FrameUtama.getMasterService().hapus(pilihanProduk);
                isiTableDaftarProduk();
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
                FrameUtama.getInstance().removeInternalFrame(MasterProdukPanel.this);
            }
        });

        // Ketika tombol Simpan di Klik
        masterToolbarPanel1.getBtnSimpan().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                //buat Object Produk terlebih dahulu
                if (validasi()) {
                    //buat Object Produk terlebih dahulu
                    if (pilihanProduk == null) {
                        pilihanProduk = new Produk();
                    }

                    //ambil value kemudian dari text field kemudian di save ke dalam database
                    pilihanProduk.setId(txtKode.getText());
                    pilihanProduk.setNama(txtNama.getText());
                    pilihanProduk.setHargaPokok(TextComponentUtils.parseNumberToBigDecimal(txtHargaBeli.getText()));
                    pilihanProduk.setHargaJual(TextComponentUtils.parseNumberToBigDecimal(txtHargaJual.getText()));
                    pilihanProduk.setStok(Integer.valueOf(txtStok.getText()));
                    pilihanProduk.setSatuan(satuan);
                    pilihanProduk.setProdukSatuans(produkSatuans);
                    if(txtSaldoAwal.getText().length()>0){
                        saldoAwal=Long.valueOf(txtSaldoAwal.getText());
                    }

                    //simpan ke database dengan Menggunakan Method Simpan di Object Service
                    FrameUtama.getMasterService().simpan(pilihanProduk,saldoAwal);

                    //Kembali Kondisi Awal
                    clearForm();
                    enableForm(false);
                    masterToolbarPanel1.kondisiAwal();
                    //refresh table Produk
                    isiTableDaftarProduk();
                }
            }
        });
    }

    private void refreshBarisTableProduk(){
        //kalau tidak ada row yang di pilih maka akan menghasilkan -1
        if (tblProduk.getSelectedRow() >= 0) {
            //ambil row yang di Pilihan
            int row = tblProduk.getSelectedRow();

            pilihanProduk = FrameUtama.getMasterService().produkBerdasarId(daftarProduk.get(row).getId());
            daftarProduk.set(row, pilihanProduk);
            tblProduk.tableChanged(new TableModelEvent(tblProduk.getModel(), row));

            produkSatuans = pilihanProduk.getProdukSatuans();
            tblRelasiProduk.setModel(new ProdukSatuanTableModel(produkSatuans));

            txtKode.setText(pilihanProduk.getId());
            txtNama.setText(pilihanProduk.getNama());

            txtHargaBeli.setText(TextComponentUtils.formatNumber(pilihanProduk.getHargaPokok()));
            txtHargaJual.setText(TextComponentUtils.formatNumber(pilihanProduk.getHargaJual()));
            txtStok.setText(pilihanProduk.getStok().toString());
            satuan = pilihanProduk.getSatuan();
            txtSatuan.setText(pilihanProduk.getSatuan().getNama());

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
        txtHargaBeli.setText("");
        txtHargaJual.setText("");
        txtStok.setText("");
        txtSatuan.setText("");
        txtSaldoAwal.setText("");
        tblProduk.getSelectionModel().clearSelection();
    }

    private void enableForm(boolean status){
        txtKode.setEnabled(false);
        txtNama.setEnabled(status);

        txtHargaBeli.setEnabled(status);
        txtHargaJual.setEnabled(status);
        txtStok.setEnabled(status);
        txtSatuan.setEnabled(status);
        if(pilihanProduk==null){
            txtSaldoAwal.setEnabled(status);
        } else {
            txtSaldoAwal.setEnabled(false);
        }
        btnLookupProduk.setEnabled(status);
        btnLookupSatuan.setEnabled(status);
        tblRelasiProduk.setEnabled(status);
        btnHapus.setEnabled(status);
    }

    private void isiTableDaftarProduk() {
        daftarProduk = FrameUtama.getMasterService().semuaProduk();
        tblProduk.setModel(new ProdukTableModel(daftarProduk));
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
        if (txtHargaBeli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Harga Harus di isi", "Field Harus di isi", JOptionPane.ERROR_MESSAGE);
            txtHargaBeli.requestFocus();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduk = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        masterToolbarPanel1 = new com.artivisi.pos.ui.toolbar.MasterToolbarPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtHargaBeli = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        txtHargaJual = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSatuan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSaldoAwal = new javax.swing.JTextField();
        btnLookupSatuan = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRelasiProduk = new javax.swing.JTable();
        btnLookupProduk = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();

        setClosable(true);
        setTitle("Master Produk");

        tblProduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Nama", "Harga Pokok", "Harga Jual", "Stok"
            }
        ));
        tblProduk.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduk);

        jLabel1.setText("Search Character");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Form"));

        jLabel3.setText("Kode Produk");

        jLabel4.setText("Nama Produk");

        jLabel5.setText("Harga Beli");

        jLabel6.setText("Stok");

        txtHargaJual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));

        jLabel7.setText("Harga Jual");

        jLabel11.setText("Satuan");

        txtSatuan.setEditable(false);

        jLabel12.setText("Saldo Awal");

        btnLookupSatuan.setText("...");
        btnLookupSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLookupSatuanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSaldoAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLookupSatuan))
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(btnLookupSatuan))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSaldoAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(214, 214, 214))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtHargaBeli, txtKode, txtNama});

        jTabbedPane1.addTab("Form", jPanel2);

        tblRelasiProduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produk", "Satuan", "Jumlah Per Kemasan"
            }
        ));
        tblRelasiProduk.setCellSelectionEnabled(true);
        jScrollPane2.setViewportView(tblRelasiProduk);

        btnLookupProduk.setText("Cari Produk...");
        btnLookupProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLookupProdukActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLookupProduk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLookupProduk)
                    .addComponent(btnHapus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Kemasan", jPanel1);

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
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdukMouseClicked
        refreshBarisTableProduk();
    }//GEN-LAST:event_tblProdukMouseClicked

    private void btnLookupProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLookupProdukActionPerformed
        Produk p = new ProdukSearchDialog().showDialog();
        if(p!=null){
            if(pilihanProduk!=null && pilihanProduk.equals(p)){
                return;
            }
            ProdukSatuan ps = new ProdukSatuan();
            ps.setId(p.getNama() + "-" + p.getSatuan().getId());
            ps.setKuantitasKemasan(0l);
            ps.setProduk(pilihanProduk);
            produkSatuans.add(ps);
            tblRelasiProduk.setModel(new ProdukSatuanTableModel(produkSatuans));
        }
    }//GEN-LAST:event_btnLookupProdukActionPerformed

    private void btnLookupSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLookupSatuanActionPerformed
        Satuan s = new SatuanSearchDialog().showDialog();
        if(s!=null){
            satuan = s;
            txtSatuan.setText(satuan.getNama());
        }
    }//GEN-LAST:event_btnLookupSatuanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        if(pilihanProdukSatuan!=null){
            produkSatuans.remove(pilihanProdukSatuan);
            pilihanProdukSatuan = null;
            tblRelasiProduk.setModel(new ProdukSatuanTableModel(produkSatuans));
        } else {
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih salah satu produk di tabel !"
                    ,"Peringatan",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusActionPerformed
    private class ProdukTableModel extends AbstractTableModel {

        private List<Produk> daftarProduk;

        public ProdukTableModel(List<Produk> daftarProduk) {
            this.daftarProduk = daftarProduk;
        }

        public int getRowCount() {
            return daftarProduk.size();
        }

        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int col) {
            switch(col){
                case 0 : return "Kode barang";
                case 1 : return "Nama Barang";
                case 2 : return "Harga Beli";
                case 3 : return "Harga Jual";
                case 4 : return "Stok";
                default : return "";
            }

        }

        public Object getValueAt(int row, int col) {
            Produk p = daftarProduk.get(row);
            switch(col){
                case 0 : return p.getId();
                case 1 : return p.getNama();
                case 2 : return p.getHargaPokok();
                case 3 : return p.getHargaJual();
                case 4 : return p.getStok();
                default : return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex){
                case 2 : return BigDecimal.class;
                case 3 : return BigDecimal.class;
                case 4 : return Integer.class;
                default : return String.class;
            }
        }

    }

    private class ProdukSatuanTableModel extends AbstractTableModel{

        private List<ProdukSatuan> produkSatuans;

        public ProdukSatuanTableModel(List<ProdukSatuan> produkSatuans) {
            this.produkSatuans = produkSatuans;
        }

        public int getRowCount() {
            return produkSatuans.size();
        }

        public int getColumnCount() {
            return 3;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            ProdukSatuan p = produkSatuans.get(rowIndex);
            switch(columnIndex){
                case 0 : return p.getProduk().getNama();
                case 1 : return p.getProduk().getSatuan().getNama();
                case 2 : return p.getKuantitasKemasan();
                default : return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if(columnIndex==2) return Long.class;
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if(columnIndex == 2 ) return true;
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if(columnIndex == 2){
                ProdukSatuan p = produkSatuans.get(rowIndex);
                p.setKuantitasKemasan((Long) aValue);
            }
        }
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnLookupProduk;
    private javax.swing.JButton btnLookupSatuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.artivisi.pos.ui.toolbar.MasterToolbarPanel masterToolbarPanel1;
    private javax.swing.JTable tblProduk;
    private javax.swing.JTable tblRelasiProduk;
    private javax.swing.JFormattedTextField txtHargaBeli;
    private javax.swing.JFormattedTextField txtHargaJual;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSaldoAwal;
    private javax.swing.JTextField txtSatuan;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStok;
    // End of variables declaration//GEN-END:variables
}
