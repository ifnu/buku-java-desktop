/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.service.impl;

import com.artivisi.pos.dao.sekuriti.PenggunaDao;
import com.artivisi.pos.model.sekuriti.Menu;
import com.artivisi.pos.model.sekuriti.Pengguna;
import com.artivisi.pos.model.sekuriti.Peran;
import com.artivisi.pos.dao.sekuriti.MenuDao;
import com.artivisi.pos.dao.sekuriti.PeranDao;
import com.artivisi.pos.service.SekuritiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ifnu
 */
@Service("sekuritiService")
@Transactional(readOnly=true)
public class SekuritiServiceImpl implements SekuritiService{

    @Autowired private PenggunaDao penggunaDao;
    @Autowired private MenuDao menuDao;
    @Autowired private PeranDao peranDao;

    @Transactional
    public void hapus(Peran p) {
    }

    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void simpan(Peran p) {
        peranDao.simpan(p);
    }

    public List<Peran> semuaPeran() {
        return peranDao.semua();
    }

    public Peran peranBerdasarId(String id) {
        return peranDao.berdasarId(id);
    }

    @Transactional
    public void hapus(Pengguna p) {
        penggunaDao.hapus(p);
    }

    @Transactional(isolation=Isolation.SERIALIZABLE)
    public boolean simpan(Pengguna p) {
        try{
            penggunaDao.simpan(p);
            return true;
        } catch(Throwable th){
            return false;
        }
    }

    public List<Pengguna> semuaPengguna() {
        return penggunaDao.semua();
    }

    public Pengguna penggunaBerdasarId(String id) {
        return penggunaDao.penggunaBerdasarId(id);
    }

    @Transactional
    public void hapus(Menu m) {
        menuDao.hapus(m);
    }

    @Transactional
    public void hapus(List<Menu> menus) {
        for(Menu menu : menus){
            menuDao.hapus(menu);
        }
    }

    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void simpan(Menu p) {
        menuDao.simpan(p);
    }

    public List<Menu> semuaMenu() {
        return menuDao.semua();
    }

    public Menu menuBerdasarId(String id) {
        return menuDao.berdasarId(id);
    }

    public Integer maximumMenuLevel() {
        return menuDao.maximumMenuLevel();
    }

    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void simpan(List<Menu> menus) {
        for(Menu menu : menus){
            menuDao.simpan(menu);
        }
    }

}
