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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="T_ROLE")
public class Role implements Serializable{

    @Id @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(name="NAME",length=50,unique=true)
    private String name;

    @ManyToMany
    @JoinTable(name="T_ROLE_PERSON",
        joinColumns={@JoinColumn(name="ROLE_ID")},
        inverseJoinColumns={@JoinColumn(name="PERSON_ID")})
    private Set<Person> persons;

    @ManyToMany
    @JoinTable(name="T_ROLE_MENU",
        joinColumns={@JoinColumn(name="ROLE_ID")},
        inverseJoinColumns={@JoinColumn(name="MENU_ID")})
    private Set<Menu> menus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Role{" + "name=" + name + '}';
    }


}
