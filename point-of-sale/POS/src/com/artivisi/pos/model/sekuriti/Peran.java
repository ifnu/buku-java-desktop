/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.model.sekuriti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author ifnu
 */
@Entity
@Table(name="SEC_PERAN")
public class Peran implements Serializable{

    @Id
    @Column(name="ID_PERAN",length=20)
    private String id;
    
    @Column(name="DESKRIPSI")
    private String deskripsi;

    @ManyToMany(cascade=CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinTable(name="SEC_PERAN_PENGGUNA",joinColumns={@JoinColumn(name="ID_PERAN",nullable=false)},
    inverseJoinColumns={@JoinColumn(name="ID_PENGGUNA",nullable=false)})
    private List<Pengguna> penggunas;

    @ManyToMany(cascade=CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinTable(name="SEC_PERAN_MENU",joinColumns={@JoinColumn(name="ID_PERAN",nullable=false)},
    inverseJoinColumns={@JoinColumn(name="ID_MENU",nullable=false)})
    private List<Menu> menus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Pengguna> getPenggunas() {
        return penggunas;
    }

    public void setPenggunas(List<Pengguna> penggunas) {
        this.penggunas = penggunas;
    }

    public void addMenu(Menu menu){
        if(menus == null){
            menus = new ArrayList<Menu>();
        }
        menus.add(menu);
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
}
