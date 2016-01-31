/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.report;

import java.math.BigDecimal;

/**
 *
 * @author ifnu
 */
public class LaporanSetoranKasir {

    private String namaKassa;

    private String namaKasir;

    private BigDecimal tunai;

    private BigDecimal kredit;

    private BigDecimal voucher;

    private BigDecimal totalSetoran;

    private BigDecimal modal;

    public BigDecimal getKredit() {
        return kredit;
    }

    public void setKredit(BigDecimal kredit) {
        this.kredit = kredit;
    }

    public BigDecimal getModal() {
        return modal;
    }

    public void setModal(BigDecimal modal) {
        this.modal = modal;
    }

    public String getNamaKasir() {
        return namaKasir;
    }

    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    public String getNamaKassa() {
        return namaKassa;
    }

    public void setNamaKassa(String namaKassa) {
        this.namaKassa = namaKassa;
    }

    public BigDecimal getTotalSetoran() {
        return totalSetoran;
    }

    public void setTotalSetoran(BigDecimal totalSetoran) {
        this.totalSetoran = totalSetoran;
    }

    public BigDecimal getTunai() {
        return tunai;
    }

    public void setTunai(BigDecimal tunai) {
        this.tunai = tunai;
    }

    public BigDecimal getVoucher() {
        return voucher;
    }

    public void setVoucher(BigDecimal voucher) {
        this.voucher = voucher;
    }

}
