/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.master;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.master.Produk;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author endy
 */
@Repository
public class ProdukDao extends BaseDaoHibernate<Produk>{

    public Produk cariBerdasarId(String id){
        Produk p = (Produk) sessionFactory.getCurrentSession().get(Produk.class, id);
        if(p!=null){
            Hibernate.initialize(p.getProdukSatuans());
        }
        return p;
    }

    public List<Produk> semuaNonPulsaElektrik() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Produk p where p.pulsaElektrik=:pulsa order by p.id")
                .setBoolean("pulsa", Boolean.FALSE)
                .list();
    }

}
