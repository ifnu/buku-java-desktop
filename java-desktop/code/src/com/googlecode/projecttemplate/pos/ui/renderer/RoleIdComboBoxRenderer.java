/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.ui.renderer;

import com.googlecode.projecttemplate.pos.model.Role;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author ifnu
 */
public class RoleIdComboBoxRenderer extends DefaultListCellRenderer{

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        JLabel renderer =  (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
        if(value instanceof Role){
            Role r = (Role) value;
            renderer.setText(String.valueOf(r.getId()));
        }
        return renderer;
    }
}
