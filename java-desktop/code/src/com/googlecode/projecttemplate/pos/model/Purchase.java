package com.googlecode.projecttemplate.pos.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author ifnu
 */
@Entity
@Table(name="T_PURCHASE")
public class Purchase implements Serializable{

    @Id @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="PURCHASE_DATE",nullable=false)
    private Date purchaseDate;

    @OneToMany(mappedBy="purchase",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<PurchaseDetail> purchaseDetail;

    @Column(name="TOTAL_SALES",precision=18,scale=0,nullable=false)
    private BigDecimal totalPurchase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<PurchaseDetail> getPurchaseDetail() {
        return purchaseDetail;
    }

    public void setPurchaseDetail(List<PurchaseDetail> purchaseDetail) {
        this.purchaseDetail = purchaseDetail;
    }

    public BigDecimal getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(BigDecimal totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

}
