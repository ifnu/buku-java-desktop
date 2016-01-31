/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.transaksi;

import com.artivisi.pos.model.master.Kassa;
import com.artivisi.pos.model.master.Shift;
import com.artivisi.pos.model.sekuriti.Pengguna;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="TR_SESI_KASSA")
public class SesiKassa implements Serializable{

    @Id
    @Column(name="ID_SESI",length=15)
    private String id;

    @ManyToOne
    @JoinColumn(name="ID_KASSA", referencedColumnName="ID_KASSA")
    private Kassa kassa;

    @ManyToOne
    @JoinColumn(name="ID_PENGGUNA", referencedColumnName="ID_PENGGUNA")
    private Pengguna kasir;

    @ManyToOne
    @JoinColumn(name="ID_SHIFT", referencedColumnName="ID_SHIFT")
    private Shift shift;

    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalLogin;

    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalTutup;

    private BigDecimal modal;

    private BigDecimal totalSetoran;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pengguna getKasir() {
        return kasir;
    }

    public void setKasir(Pengguna kasir) {
        this.kasir = kasir;
    }

    public Kassa getKassa() {
        return kassa;
    }

    public void setKassa(Kassa kassa) {
        this.kassa = kassa;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Date getTanggalLogin() {
        return tanggalLogin;
    }

    public void setTanggalLogin(Date tanggalLogin) {
        this.tanggalLogin = tanggalLogin;
    }

    public BigDecimal getModal() {
        return modal;
    }

    public void setModal(BigDecimal modal) {
        this.modal = modal;
    }

    public Date getTanggalTutup() {
        return tanggalTutup;
    }

    public void setTanggalTutup(Date tanggalTutup) {
        this.tanggalTutup = tanggalTutup;
    }

    public BigDecimal getTotalSetoran() {
        return totalSetoran;
    }

    public void setTotalSetoran(BigDecimal totalSetoran) {
        this.totalSetoran = totalSetoran;
    }


}
