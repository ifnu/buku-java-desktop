package com.googlecode.projecttemplate.pos.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="T_PERSON",uniqueConstraints={@UniqueConstraint(columnNames={"NAME"})})
@org.hibernate.annotations.Table(
    appliesTo="T_PERSON",
    indexes={
        @Index(name="NAME_INDEX",columnNames={"NAME"})
    }
)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Person implements Serializable {

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @OneToOne(mappedBy = "person")
    private Customer customer;

    @Column(name="NAME",unique=true,length=100)
    private String name;

    @Column(name="PASSWORD",length=200)
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name="BIRTH_DATE")
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS",length=20)
    private MaritalStatus status;

    @Lob
    @Column(name="PICTURE")
    private byte[] picture;

    @Lob
    @Column(name="REMARK")
    private String remark;

    @ManyToMany(mappedBy="persons")
    private Set<Role> roles;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public MaritalStatus getStatus() {
        return status;
    }

    public void setStatus(MaritalStatus status) {
        this.status = status;
    }


}
