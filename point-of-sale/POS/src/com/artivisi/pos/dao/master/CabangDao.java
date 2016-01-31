/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.master;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.master.Cabang;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class CabangDao extends BaseDaoHibernate<Cabang>{

    public Cabang cariBerdasarId(String id) {
        return (Cabang) sessionFactory.getCurrentSession().createQuery("from Cabang where id=:id")
                .setString("id", id)
                .uniqueResult();
    }
}
