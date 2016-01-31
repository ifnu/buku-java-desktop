/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PeranMenuDialog.java
 *
 * Created on May 10, 2009, 6:09:14 PM
 */

package com.artivisi.pos.ui.dialog.sekuriti;

import com.artivisi.pos.model.sekuriti.Menu;
import com.artivisi.pos.ui.frame.FrameUtama;
import java.awt.Component;
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
public class PeranMenuDialog extends javax.swing.JDialog {

    private List<Menu> menuAndItsCilds = new ArrayList<Menu>();

    /** Creates new form PeranMenuDialog */
    public PeranMenuDialog() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        setLocationRelativeTo(null);
        
        lstMenu.setCellRenderer(new MenuListRenderer());
    }

    public List<Menu> showDialog(){
        constructMenu();
        setVisible(true);
        return menuAndItsCilds;
    }

    private List<Menu> orderedMenu = new ArrayList<Menu>();

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

        jScrollPane1 = new javax.swing.JScrollPane();
        lstMenu = new javax.swing.JList();
        btnOK = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pilih Menu");

        jScrollPane1.setViewportView(lstMenu);

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnBatal))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        if(lstMenu.getSelectedIndex()>=0){
            menuAndItsCilds.clear();
            Menu selectedMenu = orderedMenu.get(lstMenu.getSelectedIndex());
            int menuIndex = orderedMenu.indexOf(selectedMenu);
            menuAndItsCilds.add(selectedMenu);
            //cari childsnya
            for(int i=menuIndex+1;i<orderedMenu.size();i++){
                Menu currMenu = orderedMenu.get(i);
                if(currMenu.getMenuLevel() > selectedMenu.getMenuLevel()){
                    menuAndItsCilds.add(currMenu);
                } else {
                    break;
                }
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Pilih salah satu menu diatas",
                    "Warning",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnOKActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        menuAndItsCilds = null;
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnOK;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstMenu;
    // End of variables declaration//GEN-END:variables

}
