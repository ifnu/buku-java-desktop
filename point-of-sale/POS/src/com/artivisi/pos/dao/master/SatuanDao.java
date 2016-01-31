/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.master;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.master.Satuan;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class SatuanDao extends BaseDaoHibernate<Satuan>{

    public Satuan cariBerdasarId(String id){
        return (Satuan) sessionFactory.getCurrentSession()
                .createQuery("from Satuan s " +
                "where s.id=:id")
                .setString("id", id)
                .uniqueResult();
    }

}
