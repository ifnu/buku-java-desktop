/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MenuPanel.java
 *
 * Created on May 24, 2009, 6:34:13 PM
 */

package com.artivisi.pos.ui.sekuriti;

import com.artivisi.pos.model.sekuriti.Menu;
import com.artivisi.pos.ui.dialog.sekuriti.MenuDialog;
import com.artivisi.pos.ui.frame.FrameUtama;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/**
 *
 * @author ifnu
 */
public class MenuPanel extends javax.swing.JInternalFrame {

    private Menu menu;
    /** Creates new form MenuPanel */
    public MenuPanel() {
        initComponents();
        constructMenu();
        lstMenu.setCellRenderer(new MenuListRenderer());
        initalizeListener();
    }

    private void initalizeListener(){
        lstMenu.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if(lstMenu.getSelectedIndex()>=0){
                    menu = (Menu) lstMenu.getSelectedValue();
                    masterToolbarPanel1.kondisiTabelTerpilih();
                }
            }
        });

        masterToolbarPanel1.getBtnSimpan().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //validate menu
                //Menu yang nilai classnya != null tidak boleh punya child
                //menu yang nilai classnya == null tidak boleh tidak punya child
                //menu yang menu levelnya > 0 harus punya parent
                //simpan
                FrameUtama.getSekuritiService().simpan(orderedMenu);

                masterToolbarPanel1.getBtnSimpan().setEnabled(false);
            }
        });
        masterToolbarPanel1.getBtnTambah().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Menu menu = new MenuDialog().showDialog();
                if (menu != null) {
                    //tambahkan di dalam menu sesuai dengan posisinya
                    FrameUtama.getSekuritiService().simpan(menu);
                    constructMenu();
                }
            }
        });
        masterToolbarPanel1.getBtnHapus().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                List<Menu> deletedMenus = new ArrayList<Menu>();
                //cek apakah punya child
                if(menu!=null){
                    if(menu.getChilds()!=null && !menu.getChilds().isEmpty()){
                        //iterasi dari ordered menu, cari terus ke bawah hingga habis atau hingga ketemu sibling dari menu
                        int menuIndex = orderedMenu.indexOf(menu);
                        deletedMenus.add(menu);
                        for(int i=menuIndex+1;i<orderedMenu.size();i++){
                            Menu currMenu = orderedMenu.get(i);
                            if(currMenu.getMenuLevel() > menu.getMenuLevel()){
                                deletedMenus.add(currMenu);
                            } else {
                                break;
                            }
                        }
                        try {
                            FrameUtama.getSekuritiService().hapus(deletedMenus);
                            constructMenu();
                            masterToolbarPanel1.kondisiAwal();
                        } catch (Throwable th){
                            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Data menu tidak bisa dihapus, masih digunakan!"
                                    ,"Error",JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        try{
                            FrameUtama.getSekuritiService().hapus(menu);
                            constructMenu();
                            masterToolbarPanel1.kondisiAwal();
                        } catch (Throwable th){
                            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Data menu tidak bisa dihapus, masih digunakan!"
                                    ,"Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        masterToolbarPanel1.getBtnBatal().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                lstMenu.clearSelection();
                menu = null;
                masterToolbarPanel1.kondisiAwal();
            }
        });

        masterToolbarPanel1.getBtnKeluar().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                FrameUtama.getInstance().removeInternalFrame(MenuPanel.this);
            }
        });

        masterToolbarPanel1.getBtnEdit().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                if(menu!=null){
                    Menu m = new MenuDialog().showDialog(menu);
                    if(m!=null){
                        FrameUtama.getSekuritiService().simpan(m);
                        constructMenu();
                    }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        lstMenu = new javax.swing.JList();
        btnNaik = new javax.swing.JButton();
        btnKiri = new javax.swing.JButton();
        btnKanan = new javax.swing.JButton();
        btnTurun = new javax.swing.JButton();
        masterToolbarPanel1 = new com.artivisi.pos.ui.toolbar.MasterToolbarPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Menu");

        lstMenu.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lstMenu);

        btnNaik.setText("Naik");
        btnNaik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNaikActionPerformed(evt);
            }
        });

        btnKiri.setText("<");
        btnKiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKiriActionPerformed(evt);
            }
        });

        btnKanan.setText(">");
        btnKanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKananActionPerformed(evt);
            }
        });

        btnTurun.setText("Turun");
        btnTurun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(masterToolbarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnKiri)
                                .addGap(28, 28, 28)
                                .addComponent(btnKanan))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnNaik, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnTurun, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(29, 29, 29)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(masterToolbarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnNaik)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnKiri)
                            .addComponent(btnKanan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTurun))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKananActionPerformed
        masterToolbarPanel1.getBtnSimpan().setEnabled(true);
        int index = lstMenu.getSelectedIndex();
        Menu m = (Menu) lstMenu.getSelectedValue();
        m.setMenuLevel(m.getMenuLevel() + 1);
        //cek apakah diatasnya merupakan sibling, kalau iya jadikan sibling itu sebagai parentnya
        //add m sebagai child dari sibling
        //remove m dari daftar child di parentnya
        if(index>0){
            Menu above = orderedMenu.get(index-1);
            if(above.getMenuLevel().equals(m.getMenuLevel())){
                m.setParent(above.getParent());
                above.getParent().addChild(m);
                //sesuaikan urutanya menjadi + 1 dari siblingnya
                m.setUrutan(above.getUrutan() + 1);
                //cek apakah m mempunyai childs, kalau ada parentnya juga sama dengan m
                if(m.getChilds()!=null && !m.getChilds().isEmpty()){
                    for(Menu child : m.getChilds()){
                        child.setParent(m.getParent());
                        m.getParent().addChild(child);
                    }
                }
                lstMenu.updateUI();
                return;
            } else if(above.getMenuLevel()<m.getMenuLevel()){
                
            }
            //kalau ga ketemu, maka parentnya
        } else {
            m.setParent(null);
            if(m.getParent()!=null) m.getParent().removeChild(m);
        }
        lstMenu.updateUI();
}//GEN-LAST:event_btnKananActionPerformed

    private void btnNaikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNaikActionPerformed
        masterToolbarPanel1.getBtnSimpan().setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNaikActionPerformed

    private void btnKiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKiriActionPerformed
        masterToolbarPanel1.getBtnSimpan().setEnabled(true);
        //cek kalau indexnya bukan 0
        int index = lstMenu.getSelectedIndex();
        Menu m = (Menu) lstMenu.getSelectedValue();
        if(m.getMenuLevel()>0){
            m.setMenuLevel(m.getMenuLevel()-1);
            //remove m dari daftar child parentnya
            if(m.getParent()!=null){
                m.getParent().removeChild(m);
            }
            //mencari parent baru
            if(index>0){
                //defaultnya tidak punya parent alias jadi top level menu
                m.setParent(null);
                for(int i=index-1;i>=0;i--){
                    Menu upperSibling = orderedMenu.get(i);
                    if(upperSibling.getMenuLevel().equals(m.getMenuLevel()-1)){
                        m.setParent(upperSibling);
                        upperSibling.addChild(m);
                        break;
                    }
                }
                //menyesuaikan urutan sekarang
                for(int i=index-1;i>=0;i--){
                    Menu upperSibling = orderedMenu.get(i);
                    if(upperSibling.getMenuLevel().equals(m.getMenuLevel())){
                        m.setUrutan(upperSibling.getUrutan() + 1);
                        break;
                    }
                }
                //menyesuaikan urutan sibling di bawahnya
                for(int i = index+1;i<orderedMenu.size();i++){
                    Menu lowerSibling = orderedMenu.get(i);
                    if(lowerSibling.getMenuLevel().equals(m.getMenuLevel())){
                        lowerSibling.setUrutan(lowerSibling.getUrutan()+1);
                    } else if(lowerSibling.getMenuLevel()>m.getMenuLevel()+1){
                        continue;
                    } else {
                        break;
                    }
                }
            }
            //kalau tidak punya child ada kemungkinan punya child dari mantan siblingnya
            if(m.getChilds()== null || m.getChilds().isEmpty()){
                for(int i = index+1;i<orderedMenu.size();i++){
                    Menu lowerSibling = orderedMenu.get(index);
                    if(lowerSibling.getMenuLevel().equals(m.getMenuLevel() + 1)){
                        lowerSibling.setParent(m);
                        m.addChild(lowerSibling);
                    } else if(lowerSibling.getMenuLevel()>m.getMenuLevel()+1) {
                        continue;
                    } else {
                        break;
                    }
                }
            }
            //kalau punya child, disesuaikan semuanya menu levelnya berkurang 1 menyesuaikan child
            else {
                Set<Menu> childs = new TreeSet<Menu>(new UrutanComparator());
                childs.addAll(m.getChilds());
                Stack<Set<Menu>> stack = new Stack<Set<Menu>>();
                stack.push(childs);
                while(true){
                    childs = stack.pop();
                    if(childs!=null && !childs.isEmpty()){
                        Menu childMenu = childs.iterator().next();
                        childMenu.setMenuLevel(childMenu.getMenuLevel()-1);
                        childs.remove(childMenu);
                        if(!childs.isEmpty()){
                            stack.push(childs);
                        }
                        if(childMenu.getChilds()!=null && !childMenu.getChilds().isEmpty()){
                            Set<Menu> grandChilds = new TreeSet<Menu>();
                            grandChilds.addAll(childMenu.getChilds());
                            stack.push(grandChilds);
                        }
                    } 
                }
            }
        }
        lstMenu.updateUI();
    }//GEN-LAST:event_btnKiriActionPerformed

    private void btnTurunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurunActionPerformed
        masterToolbarPanel1.getBtnSimpan().setEnabled(true);
        //cek atasnya kalau sibling tuker urutan aja
        int index = lstMenu.getSelectedIndex();
        Menu m = (Menu) lstMenu.getSelectedValue();
        if(index>0 && index<orderedMenu.size()-1){
            Menu lowerSibling = orderedMenu.get(index+1);
            Menu upperSibling = orderedMenu.get(index-1);
            //kalau di bawahnya sama levelnya, berarti cuma tukeran urutan saja
            if(lowerSibling!=null && lowerSibling.getMenuLevel().equals(m.getMenuLevel())){
                lowerSibling.setUrutan(lowerSibling.getUrutan()-1);
                m.setUrutan(m.getUrutan()+1);
                if(m.getParent()!=null){
                    m.getParent().removeChild(m);
                    m.getParent().addChild(m);
                    m.getParent().removeChild(lowerSibling);
                    m.getParent().addChild(lowerSibling);
                }
            } else if(lowerSibling!=null && lowerSibling.getMenuLevel()> m.getMenuLevel()){
                //kalau di bawahnya lebih besar, cek apakah punya child apa nggak
                if(lowerSibling.getChilds()!=null && !lowerSibling.getChilds().isEmpty()){
                    //
                }
            }
        } else if(index == 0 && orderedMenu.size()>1){
            Menu lowerSibling = orderedMenu.get(index+1);
            if(lowerSibling!=null && lowerSibling.getMenuLevel().equals(m.getMenuLevel())){
                lowerSibling.setUrutan(lowerSibling.getUrutan()-1);
                m.setUrutan(m.getUrutan()+1);
            } else if(lowerSibling!=null && lowerSibling.getMenuLevel() > m.getMenuLevel()){
                
            }
        } else if(index == orderedMenu.size()-1){
            
        }
    }//GEN-LAST:event_btnTurunActionPerformed

    List<Menu> orderedMenu = new ArrayList<Menu>();

    private void constructMenu(){
        orderedMenu.clear();
        List<Menu> menus = FrameUtama.getSekuritiService().semuaMenu();
        Map<Integer,List<Menu>> menuMap = new HashMap<Integer, List<Menu>>();
        for(Menu m:menus){
            List<Menu> menuList = null;
            if(menuMap.get(m.getMenuLevel()) == null){
                menuList = new ArrayList<Menu>();
                menuMap.put(m.getMenuLevel(),menuList);
            } else {
                menuList = menuMap.get(m.getMenuLevel());
            }
            menuList.add(m);
        }
        //construct child and parent
        Integer maximumLevel = FrameUtama.getSekuritiService().maximumMenuLevel();
        List<Menu> parents = menuMap.get(0);
        if(parents==null){
            throw new IllegalStateException("Menu level 0 tidak ada!");
        }
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
                Menu menu = current.iterator().next();
                current.remove(menu);
                orderedMenu.add(menu);
                if(!current.isEmpty()) {
                    stack.push(current);
                }
                if(menu.getChilds()!=null && !menu.getChilds().isEmpty()) {
                    Set<Menu> currentChilds = new TreeSet<Menu>(new UrutanComparator());
                    currentChilds.addAll(menu.getChilds());
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKanan;
    private javax.swing.JButton btnKiri;
    private javax.swing.JButton btnNaik;
    private javax.swing.JButton btnTurun;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstMenu;
    private com.artivisi.pos.ui.toolbar.MasterToolbarPanel masterToolbarPanel1;
    // End of variables declaration//GEN-END:variables

}
