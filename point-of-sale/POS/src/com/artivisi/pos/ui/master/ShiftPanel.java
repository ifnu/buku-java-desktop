/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MasterShift.java
 *
 * Created on Apr 29, 2009, 10:25:26 AM
 */
package com.artivisi.pos.ui.master;

import com.artivisi.pos.model.master.Shift;
import com.artivisi.pos.ui.frame.FrameUtama;
import com.artivisi.pos.util.ShiftTableModelRenderer;
import com.artivisi.pos.util.TextComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import org.joda.time.DateTime;

/**
 *
 * @author kurusgw
 */
public class ShiftPanel extends javax.swing.JInternalFrame {

    private List<Shift> daftarShift;
    private Shift pilihanShift;

    /** Creates new form MasterShift */
    public ShiftPanel() {
        initComponents();

        initListener();
        isiTableDaftarShift();

        enableForm(false);

        tblShift.setAutoCreateColumnsFromModel(false);
        tblShift.setDefaultRenderer(Date.class, new ShiftTableModelRenderer());

        TextComponentUtils.setAutoUpperCaseText(10,txtKode);
        TextComponentUtils.setAutoUpperCaseText(txtSearch);
    }

    private void initListener() {
        // Listener untuk table ketika table di Klik 
        tblShift.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                refreshBarisTableShift();
            }
        });

        //ketika tombol delete di Klik
        masterToolbarPanel1.getBtnHapus().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                askDelete();
                FrameUtama.getMasterService().hapus(pilihanShift);
                isiTableDaftarShift();
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
                FrameUtama.getInstance().removeInternalFrame(ShiftPanel.this);
            }
        });

        // Ketika tombol Simpan di Klik
        masterToolbarPanel1.getBtnSimpan().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                //buat Object Shift terlebih dahulu
                if (validasi()) {
                    //buat Object Shift terlebih dahulu
                    if (pilihanShift == null) {
                        pilihanShift = new Shift();
                    }

                    //ambil value kemudian dari text field kemudian di save ke dalam database
                    pilihanShift.setId(txtKode.getText());
                    DateTime jamServer = new DateTime(FrameUtama.getMasterService().tanggalServer().getTime());
                    DateTime dateTime = new DateTime(jamServer.getYear(),jamServer.getMonthOfYear(),jamServer.getDayOfMonth(),
                            spnJamMulai.getValue(),spnMenitMulai.getValue(),0,0);
                    pilihanShift.setJamMulai(dateTime.toDate());
                    dateTime = new DateTime(jamServer.getYear(),jamServer.getMonthOfYear(),jamServer.getDayOfMonth(),
                            spnJamSelesai.getValue(),spnMenitSelesai.getValue(),0,0);
                    pilihanShift.setJamSelesai(dateTime.toDate());

                    //simpan ke database dengan Menggunakan Method Simpan di Object Service
                    FrameUtama.getMasterService().simpan(pilihanShift);

                    //Kembali Kondisi Awal
                    clearForm();
                    enableForm(false);
                    masterToolbarPanel1.kondisiAwal();
                    //refresh table Shift
                    isiTableDaftarShift();
                }
            }
        });
    }

    private void refreshBarisTableShift(){
        //kalau tidak ada row yang di pilih maka akan menghasilkan -1
        if (tblShift.getSelectedRow() >= 0) {
            //ambil row yang di Pilihan
            int row = tblShift.getSelectedRow();

            pilihanShift = FrameUtama.getMasterService().shiftBerdasarId(daftarShift.get(row).getId());
            daftarShift.set(row, pilihanShift);
            tblShift.tableChanged(new TableModelEvent(tblShift.getModel(), row));

            txtKode.setText(pilihanShift.getId());
            DateTime jamMulai = new DateTime(pilihanShift.getJamMulai().getTime());
            spnJamMulai.setValue(jamMulai.getHourOfDay());
            spnMenitMulai.setValue(jamMulai.getMinuteOfHour());
            DateTime jamSelesai = new DateTime(pilihanShift.getJamSelesai().getTime());
            spnJamSelesai.setValue(jamSelesai.getHourOfDay());
            spnMenitSelesai.setValue(jamSelesai.getMinuteOfHour());
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
        spnJamSelesai.setValue(0);
        spnMenitSelesai.setValue(0);
        spnJamMulai.setValue(0);
        spnMenitMulai.setValue(0);
        tblShift.getSelectionModel().clearSelection();
    }

    private void enableForm(boolean status){
        spnJamSelesai.setEnabled(status);
        spnMenitSelesai.setEnabled(status);
        spnJamMulai.setEnabled(status);
        spnMenitMulai.setEnabled(status);
        txtKode.setEnabled(status);
    }

    private void isiTableDaftarShift() {
        daftarShift = FrameUtama.getMasterService().semuaShift();
        tblShift.setModel(new ShiftTableModel(daftarShift));

    }
    private class ShiftTableModel extends AbstractTableModel {

        private List<Shift> daftarShift;

        public ShiftTableModel(List<Shift> daftarShift) {
            this.daftarShift = daftarShift;
        }

        public int getRowCount() {
            return daftarShift.size();
        }

        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int col) {
            switch(col){
                case 0 : return "Kode shict";
                case 1 : return "Jam Mulai";
                case 2 : return "Jam Selesai";
                default : return "";
            }

        }

        public Object getValueAt(int row, int col) {
            Shift p = daftarShift.get(row);
            switch(col){
                case 0 : return p.getId();
                case 1 : return p.getJamMulai();
                case 2 : return p.getJamSelesai();
                default : return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex){
                case 1 : return Date.class;
                case 2 : return Date.class;
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
        if (spnJamMulai.getValue() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Jam mulai Harus di isi", "Field Harus di isi", JOptionPane.ERROR_MESSAGE);
            spnJamMulai.requestFocusInWindow();
            return false;
        }
        // Periksa Kalau HArga Kosong tampilkan Message Box
        if (spnJamSelesai.getValue() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Jam selesai Harus di isi", "Field Harus di isi", JOptionPane.ERROR_MESSAGE);
            spnJamSelesai.requestFocusInWindow();
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
        jLabel5 = new javax.swing.JLabel();
        spnJamMulai = new com.toedter.components.JSpinField();
        spnMenitMulai = new com.toedter.components.JSpinField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        spnMenitSelesai = new com.toedter.components.JSpinField();
        spnJamSelesai = new com.toedter.components.JSpinField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblShift = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        masterToolbarPanel1 = new com.artivisi.pos.ui.toolbar.MasterToolbarPanel();

        setClosable(true);
        setTitle("Master Produk");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Form"));

        jLabel3.setText("Kode Produk");

        jLabel4.setText("Jam Mulai");

        jLabel5.setText("Jam Selesai");

        spnJamMulai.setMaximum(24);
        spnJamMulai.setMinimum(0);

        spnMenitMulai.setMaximum(59);
        spnMenitMulai.setMinimum(0);

        jLabel2.setText(":");

        jLabel6.setText(":");

        spnMenitSelesai.setMaximum(59);
        spnMenitSelesai.setMinimum(0);

        spnJamSelesai.setMaximum(24);
        spnJamSelesai.setMinimum(0);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(spnJamSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnMenitSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(spnJamMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnMenitMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(289, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(spnJamMulai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnMenitMulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(spnJamSelesai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnMenitSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(325, 325, 325))
        );

        tblShift.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Jam Mulai", "Jam Selesai"
            }
        ));
        tblShift.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblShift.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblShiftMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblShift);

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
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblShiftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblShiftMouseClicked
        refreshBarisTableShift();
    }//GEN-LAST:event_tblShiftMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.artivisi.pos.ui.toolbar.MasterToolbarPanel masterToolbarPanel1;
    private com.toedter.components.JSpinField spnJamMulai;
    private com.toedter.components.JSpinField spnJamSelesai;
    private com.toedter.components.JSpinField spnMenitMulai;
    private com.toedter.components.JSpinField spnMenitSelesai;
    private javax.swing.JTable tblShift;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
