package com.googlecode.projecttemplate.pos.model;


import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name="T_PRODUCT")
public class Product implements Serializable{

    @Id @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(name="NAME",unique=true,nullable=false,length=150)
    private String name;

    @Column(name="PRICE",scale=0,precision=18)
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
