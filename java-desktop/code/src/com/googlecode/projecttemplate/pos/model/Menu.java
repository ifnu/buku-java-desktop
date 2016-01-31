package com.googlecode.projecttemplate.pos.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="T_MENU")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Menu implements Serializable{

    @Id @GeneratedValue
    @Column(name="MENU_ID")
    private Long id;

    private String name;

    @ManyToMany(mappedBy="menus")
    private Set<Role> roles;

}
