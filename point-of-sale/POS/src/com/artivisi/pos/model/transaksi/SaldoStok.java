/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.transaksi;

import com.artivisi.pos.model.master.Produk;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="TR_SALDO_STOK",
    uniqueConstraints={
        @UniqueConstraint(columnNames={"ID_PRODUK","TAHUN","BULAN"})
    }
)
public class SaldoStok implements Serializable {

    @Id
    @Column(name="ID_SALDO_STOK",length=15)
    private String id;

    @ManyToOne
    @JoinColumn(name="ID_PRODUK",referencedColumnName="ID_PRODUK",nullable=false)
    private Produk produk;
    
    @Column(name="TAHUN",length=4,nullable=false)
    private String tahun;

    @Column(name="BULAN",length=2,nullable=false)
    private String bulan;

    @Column(name="BELI",nullable=false)
    private Long beli = 0l;

    @Column(name="RETUR_BELI",nullable=false)
    private Long returBeli = 0l;

    @Column(name="JUAL",nullable=false)
    private Long jual = 0l;

    @Column(name="RETUR_JUAL",nullable=false)
    private Long returJual = 0l;

    @Column(name="TOTAL_BELI",nullable=false)
    private Long totalBeli = 0l;

    @Column(name="SALDO_AWAL",nullable=false)
    private Long saldoAwal = 0l;

    public Long getBeli() {
        return beli;
    }

    public void setBeli(Long beli) {
        this.beli = beli;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getJual() {
        return jual;
    }

    public void setJual(Long jual) {
        this.jual = jual;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public Long getReturBeli() {
        return returBeli;
    }

    public void setReturBeli(Long returBeli) {
        this.returBeli = returBeli;
    }

    public Long getReturJual() {
        return returJual;
    }

    public void setReturJual(Long returJual) {
        this.returJual = returJual;
    }

    public Long getSaldoAwal() {
        return saldoAwal;
    }

    public void setSaldoAwal(Long saldoAwal) {
        this.saldoAwal = saldoAwal;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public Long getTotalBeli() {
        return totalBeli;
    }

    public void setTotalBeli(Long totalBeli) {
        this.totalBeli = totalBeli;
    }
    
}
