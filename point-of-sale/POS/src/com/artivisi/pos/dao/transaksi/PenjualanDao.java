/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.transaksi;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.transaksi.Penjualan;
import com.artivisi.pos.model.transaksi.PenjualanDetail;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;



/**
 *
 * @author ifnu
 */
@Repository
public class PenjualanDao extends BaseDaoHibernate<Penjualan> {

    public Penjualan cariBerdasarId(String id) {
        Penjualan penjualan = (Penjualan) sessionFactory.getCurrentSession().get(Penjualan.class, id);
        if(penjualan!=null){
            Hibernate.initialize(penjualan.getDetails());
            for(PenjualanDetail d : penjualan.getDetails()){
                Hibernate.initialize(d.getProduk());
            }
        }
        return penjualan;
    }

    @Override
    public List<Penjualan> semua() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Penjualan p order by p.id desc").list();
    }

}
