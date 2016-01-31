/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.master;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="MST_PULSA_ELEKTRIK")
public class PulsaElektrik implements Serializable {

    @Id
    @Column(name="ID_PULSA",length=30)
    private String id;

    @Column(name="NOMINAL",nullable=false)
    private BigDecimal nominal;

    @Column(name="HARGA_JUAL",nullable=false)
    private BigDecimal hargaJual;

    @Column(name="NAMA",nullable=false,length=90)
    private String nama;

    @ManyToOne
    @JoinColumn(name="ID_PRODUK",referencedColumnName="ID_PRODUK",nullable=false)
    private Produk produk;

    public BigDecimal getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(BigDecimal hargaJual) {
        this.hargaJual = hargaJual;
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

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }


}
