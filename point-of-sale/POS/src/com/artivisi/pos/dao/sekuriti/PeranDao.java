/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.sekuriti;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.sekuriti.Peran;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class PeranDao extends BaseDaoHibernate<Peran>{

    public Peran berdasarId(String id){
        Peran peran = (Peran) sessionFactory.getCurrentSession().get(Peran.class, id);
        Hibernate.initialize(peran.getMenus());
        Hibernate.initialize(peran.getPenggunas());
        return peran;
    }

}
