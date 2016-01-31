/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.transaksi;

import com.artivisi.pos.model.master.Produk;
import com.artivisi.pos.model.master.PulsaElektrik;
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
 * @author endy
 */
@Entity @Table(name="TR_PENJUALAN_DETAIL")
public class PenjualanDetail implements Serializable {
    @Id
    @Column(name="ID_PENJUALAN_DETAIL",length=19)
    private String id;

    @ManyToOne
    @JoinColumn(name="ID_PENJUALAN", nullable=false)
    private Penjualan penjualan;

    @ManyToOne
    @JoinColumn(name="ID_PRODUK", nullable=false)
    private Produk produk;

    @Column(name="KUANTITAS",nullable=false)
    private Integer kuantitas = 0;

    @Column(name="HARGA",nullable=false)
    private BigDecimal harga = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name="ID_PULSA",referencedColumnName="ID_PULSA")
    private PulsaElektrik pulsaElektrik;

    @Column(name="SUB_TOTAL")
    private BigDecimal subTotal = BigDecimal.ZERO;

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Integer kuantitas) {
        this.kuantitas = kuantitas;
    }

    public Penjualan getPenjualan() {
        return penjualan;
    }

    public void setPenjualan(Penjualan penjualan) {
        this.penjualan = penjualan;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public PulsaElektrik getPulsaElektrik() {
        return pulsaElektrik;
    }

    public void setPulsaElektrik(PulsaElektrik pulsaElektrik) {
        this.pulsaElektrik = pulsaElektrik;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PenjualanDetail other = (PenjualanDetail) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

   
    
}
