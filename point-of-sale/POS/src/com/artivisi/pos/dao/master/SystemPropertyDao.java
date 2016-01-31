/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.master;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.master.Cabang;
import com.artivisi.pos.model.master.SystemProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class SystemPropertyDao extends BaseDaoHibernate<SystemProperty>{

    public SystemProperty cariBerdasarId(String id){

        SystemProperty systemProperty = (SystemProperty) sessionFactory.getCurrentSession()
                .get(SystemProperty.class, id);
        if(systemProperty == null && id.equals(SystemProperty.TANGGAL_FORMAT)){
            systemProperty = new SystemProperty();
            systemProperty.setId(id);
            systemProperty.setVal("dd-MM-yyyy");
            simpan(systemProperty);
        }
        if(systemProperty == null && id.equals(SystemProperty.TANGGAL_TRANSAKSI)){
            SystemProperty formatTanggal = (SystemProperty) sessionFactory.getCurrentSession()
                    .get(SystemProperty.class, SystemProperty.TANGGAL_FORMAT);
            if(formatTanggal == null){
                formatTanggal = new SystemProperty();
                formatTanggal.setId(SystemProperty.TANGGAL_FORMAT);
                formatTanggal.setVal("dd-MM-yyyy");
                simpan(formatTanggal);
            }
            systemProperty = new SystemProperty();
            systemProperty.setId(id);
            systemProperty.setVal(new SimpleDateFormat(formatTanggal.getVal()).format(new Date()));
            simpan(systemProperty);
        }
        if(systemProperty == null && id.equals(SystemProperty.CABANG)){
            Cabang cabang = new Cabang();
            cabang.setId("SYS");
            cabang.setName("Cabang Default");
            sessionFactory.getCurrentSession().saveOrUpdate(cabang);
            systemProperty = new SystemProperty();
            systemProperty.setId(SystemProperty.CABANG);
            systemProperty.setVal(cabang.getId());
            simpan(systemProperty);
        }
        return systemProperty;
    }

    public Date tanggalKerja(){
        SystemProperty tanggalKerja = (SystemProperty) sessionFactory.getCurrentSession()
                .get(SystemProperty.class, SystemProperty.TANGGAL_TRANSAKSI);
        SystemProperty formatTanggal = (SystemProperty) sessionFactory.getCurrentSession()
                .get(SystemProperty.class, SystemProperty.TANGGAL_FORMAT);
        if(tanggalKerja == null ){
            if(formatTanggal == null){
                formatTanggal = new SystemProperty();
                formatTanggal.setId(SystemProperty.TANGGAL_FORMAT);
                formatTanggal.setVal("dd-MM-yyyy");
                simpan(formatTanggal);
            }
            tanggalKerja = new SystemProperty();
            tanggalKerja.setId(SystemProperty.TANGGAL_TRANSAKSI);
            tanggalKerja.setVal(new SimpleDateFormat(formatTanggal.getVal()).format(new Date()));
            simpan(tanggalKerja);
        }
        try {
            return new SimpleDateFormat(formatTanggal.getVal()).parse(tanggalKerja.getVal());
        } catch (ParseException ex) {
            Logger.getLogger(SystemPropertyDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }
}
