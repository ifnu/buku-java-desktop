/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.transaksi;

import com.artivisi.pos.model.master.Produk;
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
@Table(name="TR_PEMBELIAN_DETAIL")
public class PembelianDetail implements Serializable{

    @Id
    @Column(name="ID_PEMBELIAN_DETAIL",length=19)
    private String id;//menambahkan index di belakang id pembelian. misalnya pembelian:PEMJKT0908000001 => detailnya : PEMJKT0908000011

    @ManyToOne
    @JoinColumn(name="ID_PRODUK",nullable=false)
    private Produk produk;

    @Column(name="KUANTITAS",nullable=false)
    private Integer kuantitas;

    @Column(name="HARGA",nullable=false)
    private BigDecimal harga;

    @Column(name="SUB_TOTAL")
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name="ID_PEMBELIAN",nullable=false)
    private Pembelian pembelian;

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

    public Pembelian getPembelian() {
        return pembelian;
    }

    public void setPembelian(Pembelian pembelian) {
        this.pembelian = pembelian;
    }

}
