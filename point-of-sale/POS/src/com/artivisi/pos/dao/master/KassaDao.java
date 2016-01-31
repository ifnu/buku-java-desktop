/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.master;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.master.Kassa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class KassaDao extends BaseDaoHibernate<Kassa> {

    public Kassa cariBerdasarId(String id){
        return (Kassa) sessionFactory.getCurrentSession().get(Kassa.class, id);
    }

}
