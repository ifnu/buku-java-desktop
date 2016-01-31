/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model;

import com.artivisi.pos.model.master.Cabang;
import com.artivisi.pos.model.sekuriti.Pengguna;
import java.util.Date;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ifnu
 */
public class BaseEntity {

    @ManyToOne
    @JoinColumn(name="ID_CABANG",nullable=false)
    private Cabang cabang;

    @ManyToOne
    @JoinColumn(name="ID_PEMBUAT",nullable=false,updatable=false)
    private Pengguna pembuat;

    @ManyToOne
    @JoinColumn(name="ID_PEMBARU")
    private Pengguna pembaru;

    @Temporal(TemporalType.DATE)
    @JoinColumn(name="TANGGAL_PEMBUAT",nullable=false,updatable=false)
    private Date tanggalPembuatan;

    @JoinColumn(name="TANGGAL_PEMBARU")
    private Date tanggalPembaruan;

    public Cabang getCabang() {
        return cabang;
    }

    public void setCabang(Cabang cabang) {
        this.cabang = cabang;
    }

    public Pengguna getPembaru() {
        return pembaru;
    }

    public void setPembaru(Pengguna pembaru) {
        this.pembaru = pembaru;
    }

    public Pengguna getPembuat() {
        return pembuat;
    }

    public void setPembuat(Pengguna pembuat) {
        this.pembuat = pembuat;
    }

    public Date getTanggalPembaruan() {
        return tanggalPembaruan;
    }

    public void setTanggalPembaruan(Date tanggalPembaruan) {
        this.tanggalPembaruan = tanggalPembaruan;
    }

    public Date getTanggalPembuatan() {
        return tanggalPembuatan;
    }

    public void setTanggalPembuatan(Date tanggalPembuatan) {
        this.tanggalPembuatan = tanggalPembuatan;
    }

}
