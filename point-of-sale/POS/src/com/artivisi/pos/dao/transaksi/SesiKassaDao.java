/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.transaksi;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.transaksi.SesiKassa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class SesiKassaDao extends BaseDaoHibernate<SesiKassa>{

    public SesiKassa cariBardasarId(String id){
        return (SesiKassa) sessionFactory.getCurrentSession().get(SesiKassa.class, id);
    }

}
