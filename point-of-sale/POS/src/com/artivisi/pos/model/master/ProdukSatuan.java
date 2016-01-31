/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.master;

import java.io.Serializable;
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
@Table(name="MST_PRODUK_SATUAN")
public class ProdukSatuan implements Serializable {

    @Id
    @Column(name="ID_PRODUK_SATUAN",length=60)
    private String id;

    @Column(name="KUANTITAS_KEMASAN",length=60)
    private Long kuantitasKemasan;

    @ManyToOne
    @JoinColumn(name="ID_PRODUK",referencedColumnName="ID_PRODUK",nullable=false)
    private Produk produk;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getKuantitasKemasan() {
        return kuantitasKemasan;
    }

    public void setKuantitasKemasan(Long kuantitasKemasan) {
        this.kuantitasKemasan = kuantitasKemasan;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

}
