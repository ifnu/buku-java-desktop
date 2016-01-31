/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.sekuriti;

import com.artivisi.pos.model.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="SEC_PENGGUNA")
public class Pengguna extends BaseEntity implements Serializable {

    @Id
    @Column(name="ID_PENGGUNA",length=30)
    private String id;

    @Column(name="NAMA_LENGKAP",length=100)
    private String namaLengkap;

    @ManyToMany(mappedBy="penggunas",cascade=CascadeType.ALL)
    private List<Peran> perans;

    @Column(name="KATA_SANDI",length=100,nullable=false)
    private String kataSandi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addPeran(Peran peran){
        if(perans == null){
             perans = new ArrayList<Peran>();
        }
        perans.add(peran);
    }

    public List<Peran> getPerans() {
        return perans;
    }

    public void setPerans(List<Peran> perans) {
        this.perans = perans;
    }

    public String getKataSandi() {
        return kataSandi;
    }

    public void setKataSandi(String kataSandi) {
        this.kataSandi = kataSandi;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

}
