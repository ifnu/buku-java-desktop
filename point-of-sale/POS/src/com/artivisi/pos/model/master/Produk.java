/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author endy
 */
@Entity @Table(name="MST_PRODUK")
public class Produk implements Serializable {

    @Id
    @Column(name="ID_PRODUK",length=30)
    private String id;

    @Column(name="NAMA",length=90)
    private String nama;

    @Column(name="HARGA_JUAL")
    private BigDecimal hargaJual = BigDecimal.ZERO;

    @Column(name="HARGA_POKOK")
    private BigDecimal hargaPokok = BigDecimal.ZERO;

    @Column(name="STOK",nullable=false)
    private Integer stok = 0;

    @ManyToOne(optional=false)
    @JoinColumn(name="ID_SATUAN",referencedColumnName="ID_SATUAN")
    private Satuan satuan;

    @Column(name="PULSA_ELEKTRIK",nullable=false)
    private Boolean pulsaElektrik = Boolean.FALSE;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="produk")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<ProdukSatuan> produkSatuans;

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

    public BigDecimal getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(BigDecimal hargaJual) {
        this.hargaJual = hargaJual;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public Boolean isPulsaElektrik() {
        return pulsaElektrik;
    }

    public void setPulsaElektrik(Boolean pulsaElektrik) {
        this.pulsaElektrik = pulsaElektrik;
    }

    public BigDecimal getHargaPokok() {
        return hargaPokok;
    }

    public void setHargaPokok(BigDecimal hargaPokok) {
        this.hargaPokok = hargaPokok;
    }

    public Satuan getSatuan() {
        return satuan;
    }

    public void setSatuan(Satuan satuan) {
        this.satuan = satuan;
    }

    public List<ProdukSatuan> getProdukSatuans() {
        return produkSatuans;
    }

    public void setProdukSatuans(List<ProdukSatuan> produkSatuans) {
        this.produkSatuans = produkSatuans;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produk other = (Produk) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


}
