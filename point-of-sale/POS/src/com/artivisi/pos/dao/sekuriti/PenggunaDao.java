/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.sekuriti;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.sekuriti.Menu;
import com.artivisi.pos.model.sekuriti.Pengguna;
import com.artivisi.pos.model.sekuriti.Peran;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class PenggunaDao extends BaseDaoHibernate<Pengguna>{

    public Pengguna penggunaBerdasarId(String id){
        Pengguna p = (Pengguna) sessionFactory.getCurrentSession().get(Pengguna.class, id);
        if(p!=null){
            Hibernate.initialize(p.getPerans());
            for(Peran peran : p.getPerans()){
                Hibernate.initialize(peran.getMenus());
                for(Menu m : peran.getMenus()){
                    Hibernate.initialize(m.getParent());
                }
            }
        }
        return p;
    }

}
