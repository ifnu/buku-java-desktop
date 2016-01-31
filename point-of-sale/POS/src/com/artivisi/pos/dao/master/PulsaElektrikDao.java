/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.master;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.master.PulsaElektrik;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class PulsaElektrikDao extends BaseDaoHibernate<PulsaElektrik>{

    public PulsaElektrik cariBerdasarId(String id){
        return (PulsaElektrik) sessionFactory.getCurrentSession().get(PulsaElektrik.class, id);
    }

    @Override
    public List<PulsaElektrik> semua() {
        return sessionFactory.getCurrentSession()
                .createQuery("from PulsaElektrik p order by p.id")
                .list();
    }


}
