/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.transaksi;

import com.artivisi.pos.model.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author endy
 */
@Entity @Table(name="TR_PENJUALAN")
public class Penjualan extends BaseEntity implements Serializable {

    @Id
    @Column(name="ID_PENJUALAN",length=16)
    private String id;

    @Temporal(TemporalType.DATE)
    @Column(name="TANGGAL")
    private Date tanggal;

    @Column(name="TOTAL")
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy="penjualan", cascade=CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<PenjualanDetail> details = new ArrayList<PenjualanDetail>();

    @ManyToOne
    @JoinColumn(name="ID_SESI", referencedColumnName="ID_SESI")
    private SesiKassa sesiKassa;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="penjualan")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<Pembayaran> pembayarans = new ArrayList<Pembayaran>();

    @Version
    private Integer version;

    public void addDetail(PenjualanDetail detail){
        if(details==null){
            details = new ArrayList<PenjualanDetail>();
        }
        details.add(detail);
        detail.setPenjualan(this);
    }

    public void removeDetail(PenjualanDetail detail){
        if(details==null){
            details = new ArrayList<PenjualanDetail>();
        }
        details.remove(detail);
        detail.setPenjualan(null);
    }
    
    public List<PenjualanDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PenjualanDetail> details) {
        this.details = details;
        if(details !=null && !details.isEmpty()){
            for(PenjualanDetail detail : details){
                detail.setPenjualan(this);
            }
        }
    }

    public List<Pembayaran> getPembayarans() {
        return pembayarans;
    }

    public void setPembayarans(List<Pembayaran> pembayarans) {
        this.pembayarans = pembayarans;
        if(pembayarans!=null && !pembayarans.isEmpty()){
            for(Pembayaran p : pembayarans){
                p.setPenjualan(this);
            }
        }
    }

    public SesiKassa getSesiKassa() {
        return sesiKassa;
    }

    public void setSesiKassa(SesiKassa sesiKassa) {
        this.sesiKassa = sesiKassa;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
