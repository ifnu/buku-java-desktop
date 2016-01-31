/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.master;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="MST_SYSTEM_PROPERTY")
public class SystemProperty implements Serializable{

    public static final String TANGGAL_TRANSAKSI = "tanggal_transaksi";
    public static final String TANGGAL_FORMAT = "tanggal_format";
    public static final String CABANG = "cabang";

    @Id
    @Column(name="ID",length=255)
    private String id;

    @Column(name="VAL",length=255)
    private String val;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    

}
