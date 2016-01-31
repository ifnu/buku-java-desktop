/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MasterPeran.java
 *
 * Created on Apr 29, 2009, 10:25:26 AM
 */
package com.artivisi.pos.ui.sekuriti;

import com.artivisi.pos.model.sekuriti.Menu;
import com.artivisi.pos.model.sekuriti.Pengguna;
import com.artivisi.pos.model.sekuriti.Peran;
import com.artivisi.pos.ui.dialog.sekuriti.PeranMenuDialog;
import com.artivisi.pos.ui.dialog.sekuriti.PeranPenggunaDialog;
import com.artivisi.pos.ui.frame.FrameUtama;
import com.artivisi.pos.util.BigDecimalRenderer;
import com.artivisi.pos.util.TextComponentUtils;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author kurusgw
 */
public class PeranPanel extends javax.swing.JInternalFrame {

    private List<Peran> perans;
    private Peran peran;
    private List<Pengguna> penggunas;
    private Pengguna pengguna;
    private List<Menu> orderedMenu;
    private List<Menu> menus;
    private Menu menu;
    
    /** Creates new form MasterPeran */
    public PeranPanel() {
        initComponents();

        TextComponentUtils.setAutoUpperCaseText(txtDeskripsi);
        TextComponentUtils.setAutoUpperCaseText(txtId);

        tblPeran.setAutoCreateColumnsFromModel(false);
        tblPengguna.setAutoCreateColumnsFromModel(false);

        perans = FrameUtama.getSekuritiService().semuaPeran();
        tblPeran.setModel(new PeranModel(perans));
        tblPeran.getSelectionModel().addListSelectionListener(new SelectionListener());
        tblPengguna.getSelectionModel().addListSelectionListener(new SelectionListener());
        lstMenu.getSelectionModel().addListSelectionListener(new SelectionListener());

        enableForm(false);
        initListener();
        
        lstMenu.setCellRenderer(new MenuListRenderer());
    }

    private void initListener(){
        masterToolbarPanel1.getBtnTambah().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableForm(true);
                masterToolbarPanel1.kondisiTambah();
            }
        });

        masterToolbarPanel1.getBtnBatal().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
                masterToolbarPanel1.kondisiAwal();
            }
        });

        masterToolbarPanel1.getBtnEdit().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableForm(true);
                txtId.setEnabled(false);
                masterToolbarPanel1.kondisiTambah();
            }
        });

        masterToolbarPanel1.getBtnHapus().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(peran!=null){
                    FrameUtama.getSekuritiService().hapus(peran);
                    peran = null;
                    clearForm();
                    enableForm(false);
                    perans = FrameUtama.getSekuritiService().semuaPeran();
                    tblPeran.setModel(new PeranModel(perans));
                    masterToolbarPanel1.kondisiAwal();
                } else {
                    JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih Peran terlebih dahulu di sebelah kiri!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        masterToolbarPanel1.getBtnSimpan().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(validateForm()){
                    if(peran == null) peran = new Peran();
                    loadFormToModel();
                    FrameUtama.getSekuritiService().simpan(peran);
                    clearForm();
                    enableForm(false);
                    perans = FrameUtama.getSekuritiService().semuaPeran();
                    tblPeran.setModel(new PeranModel(perans));
                    masterToolbarPanel1.kondisiAwal();
                }
            }
        });

        masterToolbarPanel1.getBtnKeluar().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                FrameUtama.getInstance().removeInternalFrame(PeranPanel.this);
            }
        });

    }

    private class PeranModel extends AbstractTableModel{

        private List<Peran> peransModel;

        public PeranModel(List<Peran> peransModel) {
            this.peransModel = peransModel;
        }

        public int getRowCount() {
            return peransModel.size();
        }

        public int getColumnCount() {
            return 2;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Peran peran = peransModel.get(rowIndex);
            switch(columnIndex){
                case 0 : return peran.getId();
                case 1 : return peran.getDeskripsi();
                default : return "";
            }
        }
    }
    private class PenggunaModel extends AbstractTableModel{

        private List<Pengguna> penggunasModel;

        public PenggunaModel(List<Pengguna> penggunasModel) {
            this.penggunasModel = penggunasModel;
        }

        public int getRowCount() {
            return penggunasModel.size();
        }

        public int getColumnCount() {
            return 2;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Pengguna peran = penggunasModel.get(rowIndex);
            switch(columnIndex){
                case 0 : return peran.getId();
                case 1 : return peran.getNamaLengkap();
                default : return "";
            }
        }
    }

    private class SelectionListener implements ListSelectionListener{

        public void valueChanged(ListSelectionEvent e) {
            if(e.getSource().equals(tblPeran.getSelectionModel())){
                if(tblPeran.getSelectedRow()>=0){
                    Peran selectedPeran = perans.get(tblPeran.getSelectedRow());
                    peran = FrameUtama.getSekuritiService().peranBerdasarId(selectedPeran.getId());
                    penggunas = peran.getPenggunas();
                    menus = peran.getMenus();
                    loadModelToForm();
                    enableForm(false);
                    masterToolbarPanel1.kondisiTabelTerpilih();
                }
            } else if(e.getSource().equals(tblPengguna.getSelectionModel())){
                if(tblPengguna.getSelectedRow()>=0){
                    pengguna = penggunas.get(tblPengguna.getSelectedRow());
                    btnHapusPengguna.setEnabled(true);
                } else {
                    btnHapusPengguna.setEnabled(false);
                }
            } else if(e.getSource().equals(lstMenu.getSelectionModel())){
                if(lstMenu.getSelectedIndex()>=0){
                    menu = orderedMenu.get(lstMenu.getSelectedIndex());
                    btnHapusMenu.setEnabled(true);
                } else {
                    btnHapusMenu.setEnabled(false);
                }
            }
        }
    }

    private void loadModelToForm(){
        txtDeskripsi.setText(peran.getDeskripsi());
        txtId.setText(peran.getId());
        constructMenu();
        tblPengguna.setModel(new PenggunaModel(penggunas));
    }

    private void loadFormToModel(){
        peran.setId(txtId.getText());
        peran.setDeskripsi(txtDeskripsi.getText());
        peran.setMenus(orderedMenu);
        peran.setPenggunas(penggunas);
    }

    private void enableForm(boolean status){
        txtId.setEnabled(status);
        txtDeskripsi.setEnabled(status);
        btnTambahMenu.setEnabled(status);
        btnTambahPengguna.setEnabled(status);
        lstMenu.setEnabled(status);
        tblPengguna.setEnabled(status);
    }

    private void clearForm(){
        txtId.setText("");
        txtDeskripsi.setText("");
        peran = null;
        menu = null;
        pengguna = null;
        penggunas = null;
        orderedMenu = null;
        tblPeran.getSelectionModel().clearSelection();
        tblPengguna.setModel(new PenggunaModel(new ArrayList<Pengguna>()));
        lstMenu.setModel(new MenuListModel(new ArrayList<Menu>()));
    }

    private boolean validateForm(){
        if(txtId.getText().length()>0 &&
                txtDeskripsi.getText().length()>0){
            return true;
        } else if(txtId.getText().length()==0){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Nama Peran harus diisi!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if(txtDeskripsi.getText().length()==0){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Deskripsi Peran harus diisi!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private void constructMenu(){
        if(orderedMenu!=null) orderedMenu.clear();
        else orderedMenu = new ArrayList<Menu>();
        if(menus == null || menus.isEmpty()) {
            lstMenu.setModel(new MenuListModel(orderedMenu));
            return;
        }
        Integer maximumLevel = 0;
        Map<Integer,List<Menu>> menuMap = new HashMap<Integer, List<Menu>>();
        for(Menu m : menus){
            List<Menu> menuList = null;
            if(menuMap.get(m.getMenuLevel()) == null){
                menuList = new ArrayList<Menu>();
                menuMap.put(m.getMenuLevel(),menuList);
            } else {
                menuList = menuMap.get(m.getMenuLevel());
            }
            //maximum level
            if(m.getMenuLevel()>maximumLevel){
                maximumLevel = m.getMenuLevel();
            }
            menuList.add(m);
        }
        //kalau parentnya saja ga ada ya berarti kosong toh?
        if(menuMap.get(0) == null || menuMap.get(0).isEmpty()) {
            lstMenu.setModel(new MenuListModel(orderedMenu));
            return;
        }
        //construct child and parent
        List<Menu> parents = menuMap.get(0);
        
        //
        List<Menu> childs = null;
        for(int i=1;i<=maximumLevel;i++){
            childs = menuMap.get(i);
            if(childs!=null){
                for(Menu m : childs){
                    if(parents.indexOf(m.getParent())>=0){
                        Menu parent = parents.get(parents.indexOf(m.getParent()));
                        parent.addChild(m);
                        m.setParent(parent);
                    }
                }
            }
            parents = childs;
        }
        Stack<Set<Menu>> stack = new Stack<Set<Menu>>();
        Set<Menu> parentsOrdered = new TreeSet<Menu>(new UrutanComparator());
        parentsOrdered.addAll(menuMap.get(0));
        stack.push(parentsOrdered);

        while(true){
            if(stack.isEmpty()) break;
            Set<Menu> current = stack.pop();
            if(current!=null && !current.isEmpty()){
                Menu currMenu = current.iterator().next();
                current.remove(currMenu);
                orderedMenu.add(currMenu);
                if(!current.isEmpty()) {
                    stack.push(current);
                }
                if(currMenu.getChilds()!=null && !currMenu.getChilds().isEmpty()) {
                    Set<Menu> currentChilds = new TreeSet<Menu>(new UrutanComparator());
                    currentChilds.addAll(currMenu.getChilds());
                    stack.push(currentChilds);
                }
            } else if(stack.isEmpty()){
                break;
            }
        }
        lstMenu.setModel(new MenuListModel(orderedMenu));
    }

    private static class MenuListModel extends AbstractListModel{
        private List<Menu> orderedMenu;

        public MenuListModel(List<Menu> orderedMenu) {
            this.orderedMenu=orderedMenu;
        }

        public int getSize() {
            return orderedMenu.size();
        }

        public Object getElementAt(int index) {
            return orderedMenu.get(index);
        }

    }

    private static String padTabs(String data,int num){
        if(num<=0) return data;
        else {
            StringBuilder builder = new StringBuilder();
            for(int i=0;i<num;i++){
                builder.append("  ");
            }
            builder.append(data);
            return builder.toString();
        }
    }

    private static class UrutanComparator implements Comparator<Menu>{

        public int compare(Menu o1, Menu o2) {
            return o1.getUrutan().compareTo(o2.getUrutan());
        }

    }

    private static class MenuListRenderer extends DefaultListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(c instanceof JLabel && value instanceof Menu){
                Menu m = (Menu) value;
                JLabel label = (JLabel) c;
                label.setText(padTabs(m.getId(), m.getMenuLevel()));
                return label;
            }
            return c;
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

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtDeskripsi = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnTambahPengguna = new javax.swing.JButton();
        btnHapusPengguna = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPengguna = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnTambahMenu = new javax.swing.JButton();
        btnHapusMenu = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstMenu = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeran = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        masterToolbarPanel1 = new com.artivisi.pos.ui.toolbar.MasterToolbarPanel();

        setClosable(true);
        setTitle("Peran");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Form"));

        jLabel3.setText("Nama");

        jLabel4.setText("Deskripsi");

        btnTambahPengguna.setText("Tambah");
        btnTambahPengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPenggunaActionPerformed(evt);
            }
        });

        btnHapusPengguna.setText("Hapus");
        btnHapusPengguna.setEnabled(false);
        btnHapusPengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusPenggunaActionPerformed(evt);
            }
        });

        tblPengguna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama Lengkap"
            }
        ));
        jScrollPane2.setViewportView(tblPengguna);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTambahPengguna)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusPengguna)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahPengguna)
                    .addComponent(btnHapusPengguna))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pengguna", jPanel1);

        btnTambahMenu.setText("Tambah");
        btnTambahMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahMenuActionPerformed(evt);
            }
        });

        btnHapusMenu.setText("Hapus");
        btnHapusMenu.setEnabled(false);
        btnHapusMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusMenuActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(lstMenu);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnTambahMenu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusMenu)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahMenu)
                    .addComponent(btnHapusMenu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Menu", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(352, 352, 352))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
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
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtDeskripsi, txtId});

        tblPeran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama", "Deskripsi"
            }
        ));
        tblPeran.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblPeran);
        tblPeran.getColumnModel().getColumn(0).setMinWidth(100);
        tblPeran.getColumnModel().getColumn(0).setMaxWidth(100);

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
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(masterToolbarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(masterToolbarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahMenuActionPerformed
        List<Menu> addedMenus = new PeranMenuDialog().showDialog();
        if(addedMenus!=null){
            menus.addAll(addedMenus);
            constructMenu();
        }
    }//GEN-LAST:event_btnTambahMenuActionPerformed

    private void btnTambahPenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPenggunaActionPerformed
        Pengguna currPengguna = new PeranPenggunaDialog().showDialog();
        if(currPengguna!=null){
            penggunas.add(currPengguna);
            tblPengguna.setModel(new PenggunaModel(penggunas));
        }
    }//GEN-LAST:event_btnTambahPenggunaActionPerformed

    private void btnHapusPenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPenggunaActionPerformed
        if(pengguna!=null){
            penggunas.remove(pengguna);
            pengguna = null;
            tblPengguna.setModel(new PenggunaModel(penggunas));
        }
    }//GEN-LAST:event_btnHapusPenggunaActionPerformed

    private void btnHapusMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusMenuActionPerformed
        List<Menu> deletedMenus = new ArrayList<Menu>();
         if(menu!=null){
            if(menu.getChilds()!=null && !menu.getChilds().isEmpty()){
                //iterasi dari ordered menu, cari terus ke bawah hingga habis atau hingga ketemu sibling dari menu
                int menuIndex = orderedMenu.indexOf(menu);
                deletedMenus.add(menu);
                for(int i=menuIndex+1;i<orderedMenu.size();i++){
                    Menu currMenu = orderedMenu.get(i);
                    if(currMenu.getMenuLevel() > menu.getMenuLevel()){
                        currMenu.setParent(null);
                        if(currMenu.getChilds()!=null) currMenu.getChilds().clear();
                        deletedMenus.add(currMenu);
                    } else {
                        break;
                    }
                }
                menus.removeAll(deletedMenus);
                menu = null;
                constructMenu();
            } else {
                if(menu.getParent()!=null) menu.getParent().getChilds().remove(menu);
                menu.setParent(null);
                if(menu.getChilds()!=null) menu.getChilds().clear();
                menus.remove(menu);
                menu = null;
                constructMenu();
            }
        }
    }//GEN-LAST:event_btnHapusMenuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapusMenu;
    private javax.swing.JButton btnHapusPengguna;
    private javax.swing.JButton btnTambahMenu;
    private javax.swing.JButton btnTambahPengguna;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList lstMenu;
    private com.artivisi.pos.ui.toolbar.MasterToolbarPanel masterToolbarPanel1;
    private javax.swing.JTable tblPengguna;
    private javax.swing.JTable tblPeran;
    private javax.swing.JTextField txtDeskripsi;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
