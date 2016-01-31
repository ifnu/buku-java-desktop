package com.googlecode.projecttemplate.pos.model;


import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author ifnu
 */
@Entity
@Table(name="T_PURCHASE_DETAIL")
public class PurchaseDetail implements Serializable{

    @Id @GeneratedValue
    @Column(name="ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="PRODUCT_ID",nullable=false)
    private Product product;

    @Column(name="QUANTITY",nullable=false)
    private Integer quantity;

    @Column(name="PRICE",nullable=false,precision=18,scale=0)
    private BigDecimal price;

    @Column(name="SUBTOTAL",nullable=false,precision=18,scale=0)
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name="PURCHASE_ID",nullable=false)
    private Purchase purchase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

}
