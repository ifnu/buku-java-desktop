/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.util;

import java.awt.Component;
import java.math.BigDecimal;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ifnu
 */
public class POSTableCellRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(value instanceof BigDecimal){
            JLabel label = (JLabel) c;
            BigDecimal bigDecimal = (BigDecimal) value;
            String strAngka = TextComponentUtils.formatNumber(bigDecimal);
            if(bigDecimal.compareTo(BigDecimal.ZERO)>=0){
                label.setText(strAngka);
            } else {
                label.setText("<html><font color=\"red\">" + strAngka + "</font></html>");
            }
        } else if(value!=null && value.getClass().isAssignableFrom(Number.class)){
            Number number = (Number) value;
            JLabel label = (JLabel) c;
            if(number.intValue()< 0){
                label.setText("<html><font color=\"red\">" + value.toString() + "</font></html>");
            }
        }
        return c;
    }
}
