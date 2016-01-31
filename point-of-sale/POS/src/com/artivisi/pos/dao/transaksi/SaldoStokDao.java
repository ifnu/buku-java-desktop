/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.transaksi;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.master.Produk;
import com.artivisi.pos.model.transaksi.SaldoStok;
import com.artivisi.pos.model.transaksi.SaldoStok;
import com.artivisi.pos.util.StringUtils;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class SaldoStokDao extends BaseDaoHibernate<SaldoStok>{

    public SaldoStok cari(Produk p){
        DateTime dateTime = new DateTime();
        String bulan = StringUtils.bulanDuaDigit(dateTime.toDate());
        String tahun = String.valueOf(dateTime.getYear());
        SaldoStok s = cari(p, tahun, bulan);
        if(s == null){
            //cari satu bulan sebelumnya
            bulan  = StringUtils.bulanDuaDigit(dateTime.minusMonths(1).toDate());
            SaldoStok bulanSebelumnya = cari(p, tahun, bulan);
            if(bulanSebelumnya==null){
                List<SaldoStok> saldoStoks =
                    sessionFactory.getCurrentSession()
                    .createQuery("from SaldoStok s " +
                    "where s.produk.id=:id " +
                    "order by s.tahun desc, s.bulan desc")
                    .setString("id", p.getId())
                    .list();
                if(!saldoStoks.isEmpty()){
                    bulanSebelumnya = saldoStoks.get(0);
                }
            }
            s = new SaldoStok();
            s.setProduk(p);
            if(bulanSebelumnya!=null){
                s.setSaldoAwal(bulanSebelumnya.getBeli()
                        + bulanSebelumnya.getReturJual()
                        - bulanSebelumnya.getJual()
                        - bulanSebelumnya.getReturBeli()
                        + bulanSebelumnya.getSaldoAwal());
            }
            simpan(s);
        }
        return s;
    }

    public SaldoStok cari(Produk p, String tahun, String bulan){
        return (SaldoStok) sessionFactory.getCurrentSession()
                .createQuery("from SaldoStok s " +
                "where s.bulan=:bulan " +
                "and s.tahun=:tahun " +
                "and s.produk.id = :id ")
                .setString("tahun", tahun)
                .setString("bulan", bulan)
                .setString("id", p.getId())
                .uniqueResult();
    }

}
