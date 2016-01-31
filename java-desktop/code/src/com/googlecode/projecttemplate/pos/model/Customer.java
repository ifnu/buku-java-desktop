package com.googlecode.projecttemplate.pos.model;


import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.IndexColumn;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author ifnu
 */
@Entity
@Table(name="T_CUSTOMER")
@Inheritance(strategy=InheritanceType.JOINED)
public class Customer implements Serializable{

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(name="FIRST_NAME",nullable=false,length=100)
    private String firstName;
    
    @Column(name="SECOND_NAME",length=150)
    private String secondName;

    private Address address;

    @CollectionOfElements(targetElement=String.class)
    @IndexColumn(name="emails_index")
    private List<String> emails;

    @Column(name="PHONE",nullable=false,length=15)
    private String phone;

    @OneToOne
    @JoinColumn(name="PERSON_ID")
    private Person person;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

}
