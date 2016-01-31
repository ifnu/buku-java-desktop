/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.master;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="MST_SHIFT")
public class Shift implements Serializable{

    @Id
    @Column(name="ID_SHIFT",length=10)
    private String id;

    @Temporal(TemporalType.TIME)
    @Column(name="JAM_MULAI",nullable=false)
    private Date jamMulai;

    @Temporal(TemporalType.TIME)
    @Column(name="JAM_SELESAI",nullable=false)
    private Date jamSelesai;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(Date jamMulai) {
        this.jamMulai = jamMulai;
    }

    public Date getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(Date jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Shift other = (Shift) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return id;
    }

}
