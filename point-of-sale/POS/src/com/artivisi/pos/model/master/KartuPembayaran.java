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
 * @author ifnu
 */
@Entity
@Table(name="MST_KARTU_BAYAR")
public class KartuPembayaran implements Serializable {

    @Id
    @Column(name="ID_KARTU",length=10)
    private String id;

    @Column(name="NAMA",length=35)
    private String nama;

    @Column(name="BANK",length=50)
    private String bank;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


}
