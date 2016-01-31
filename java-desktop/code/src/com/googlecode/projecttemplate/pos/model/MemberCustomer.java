package com.googlecode.projecttemplate.pos.model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
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
@Table(name="T_MEMBER_CUSTOMER")
@PrimaryKeyJoinColumn(name="CUSTOMER_ID")
public class MemberCustomer extends Customer implements Serializable{

    @Column(name="MEMBER_ID",unique=true)
    private String memberId;

    @Column(name="POINTS")
    private Long points;


}
