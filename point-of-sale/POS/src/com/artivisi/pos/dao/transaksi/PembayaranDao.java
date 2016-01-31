/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.transaksi;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.transaksi.Pembayaran;
import com.artivisi.pos.model.transaksi.SesiKassa;
import com.artivisi.pos.model.transaksi.constant.JenisPembayaran;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class PembayaranDao extends BaseDaoHibernate<Pembayaran> {

    public Pembayaran cariBerdasarId(String id){
        return (Pembayaran) sessionFactory.getCurrentSession().get(Pembayaran.class, id);
    }

    public List<Pembayaran> cariBerdasarJenisDanSesi(SesiKassa sesi, JenisPembayaran jenis){
        return sessionFactory.getCurrentSession()
                .createQuery("from Pembayaran p " +
                "where p.penjualan.sesiKassa.id = :sesi " +
                "and p.jenisPembayaran=:jenis ")
                .setString("sesi", sesi.getId())
                .setParameter("jenis", jenis)
                .list();
    }

}
