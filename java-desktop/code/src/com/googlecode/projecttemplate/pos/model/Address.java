package com.googlecode.projecttemplate.pos.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ifnu
 */
@Embeddable
public class Address implements Serializable{

    @Column(name="ADDRESS1",length=180,nullable=false)
    private String address1;

    @Column(name="ADDRESS2",length=180)
    private String address2;

    @Column(name="CITY",length=50,nullable=false)
    private String city;

    @Column(name="PROVINCE",length=50,nullable=false)
    private String province;

    @Column(name="POST_CODE",length=50)
    private String postCode;

    @Column(name="COUNTRY",length=50,nullable=false)
    private String country;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
