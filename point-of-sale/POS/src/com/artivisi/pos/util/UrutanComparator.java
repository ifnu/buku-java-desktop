/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artivisi.pos.util;

import com.artivisi.pos.model.sekuriti.Menu;
import java.util.Comparator;

/**
 *
 * @author ifnu
 */
public class UrutanComparator implements Comparator<Menu> {
    public int compare(Menu o1, Menu o2) {
        int urutan = o1.getUrutan().compareTo(o2.getUrutan());
        if (urutan == 0) {
            if(o1.getParent()!=null && o2.getParent()!=null){
                return o1.getParent().getUrutan().compareTo(o2.getParent().getUrutan());
            }
            return o1.getId().compareTo(o2.getId());
        } else {
            return urutan;
        }
    }
}
