/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.transaksi;

import com.artivisi.pos.model.master.KartuPembayaran;
import com.artivisi.pos.model.transaksi.constant.JenisPembayaran;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="TR_PEMBAYARAN")
public class Pembayaran implements Serializable{

    @Id
    @Column(name="ID_PEMBAYARAN",length=15)
    private String id;

    @ManyToOne
    @JoinColumn(name="ID_PENJUALAN",referencedColumnName="ID_PENJUALAN")
    private Penjualan penjualan;

    @Column(name="TOTAL_TRANSAKSI")
    private BigDecimal totalTransaksi;
    
    @Column(name="NILAI")
    private BigDecimal nilai;

    @ManyToOne
    @JoinColumn(name="ID_KARTU",referencedColumnName="ID_KARTU")
    private KartuPembayaran kartuPembayaran;

    @Column(name="JUMLAH_UANG")
    private BigDecimal jumlahUang;

    @Column(name="KEMBALIAN")
    private BigDecimal kembalian;

    @Column(name="NOMOR_VOUCHER")
    private String nomorVoucher;

    @Enumerated(EnumType.STRING)
    @Column(name="JENIS_PEMBAYARAN",length=10)
    private JenisPembayaran jenisPembayaran;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getNilai() {
        return nilai;
    }

    public void setNilai(BigDecimal nilai) {
        this.nilai = nilai;
    }

    public Penjualan getPenjualan() {
        return penjualan;
    }

    public void setPenjualan(Penjualan penjualan) {
        this.penjualan = penjualan;
    }

    public BigDecimal getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(BigDecimal totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

    public BigDecimal getJumlahUang() {
        return jumlahUang;
    }

    public void setJumlahUang(BigDecimal jumlahUang) {
        this.jumlahUang = jumlahUang;
    }

    public KartuPembayaran getKartuPembayaran() {
        return kartuPembayaran;
    }

    public void setKartuPembayaran(KartuPembayaran kartuPembayaran) {
        this.kartuPembayaran = kartuPembayaran;
    }

    public BigDecimal getKembalian() {
        return kembalian;
    }

    public void setKembalian(BigDecimal kembalian) {
        this.kembalian = kembalian;
    }

    public String getNomorVoucher() {
        return nomorVoucher;
    }

    public void setNomorVoucher(String nomorVoucher) {
        this.nomorVoucher = nomorVoucher;
    }

    public JenisPembayaran getJenisPembayaran() {
        return jenisPembayaran;
    }

    public void setJenisPembayaran(JenisPembayaran jenisPembayaran) {
        this.jenisPembayaran = jenisPembayaran;
    }
    
}
