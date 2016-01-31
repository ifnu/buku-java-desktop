/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.master;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.master.KartuPembayaran;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class KartuPembayaranDao extends BaseDaoHibernate<KartuPembayaran>{
    public KartuPembayaran cariBerdasarId(String id){
        return (KartuPembayaran) sessionFactory.getCurrentSession().get(KartuPembayaran.class,id);
    }

}
