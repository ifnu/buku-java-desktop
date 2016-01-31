/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.master;

import com.artivisi.pos.model.master.RunningNumber;
import com.artivisi.pos.model.master.SystemProperty;
import com.artivisi.pos.model.master.constant.MasterRunningNumberEnum;
import com.artivisi.pos.model.master.constant.TransaksiRunningNumberEnum;
import com.artivisi.pos.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class RunningNumberDao {

    @Autowired private SessionFactory sessionFactory;
    @Autowired private SystemPropertyDao systemPropertyDao;

    public void simpan(RunningNumber p){
        sessionFactory.getCurrentSession()
                .saveOrUpdate(p);
    }

    public List<RunningNumber> semua(){
        return sessionFactory.getCurrentSession()
                .createCriteria(RunningNumber.class)
                .list();
    }

    public void hapus(RunningNumber p){
        sessionFactory.getCurrentSession().delete(p);
    }

    public String ambilBerikutnya(MasterRunningNumberEnum id, String idCabang){
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId());
        if(r==null){
            r = new RunningNumber();
            r.setId(id.getId());
            r.setNumber(0);
            sessionFactory.getCurrentSession().save(r);
        }
        return id.getPrefix() + idCabang + StringUtils.padWithZero(r.getNumber() + 1, id.getDigit());
    }

    public String ambilBerikutnya(TransaksiRunningNumberEnum id,Date tanggal, String idCabang){
        String strTanggal =  new SimpleDateFormat("yyMM").format(tanggal);
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId() + strTanggal);
        if(r==null){
            r = new RunningNumber();
            r.setId(id.getId() + strTanggal);
            r.setNumber(0);
            simpan(r);
        }
        return id.getPrefix() + idCabang + strTanggal + StringUtils.padWithZero(r.getNumber() + 1, id.getDigit());
    }

    public String ambilBerikutnyaDanSimpan(MasterRunningNumberEnum id, String idCabang) {
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId());
        if(r == null){
            r = new RunningNumber();
            r.setId(id.getId());
            r.setNumber(1);
        } else {
            r.setNumber(r.getNumber()+1);
        }
        sessionFactory.getCurrentSession().saveOrUpdate(r);
        return id.getPrefix() + idCabang + StringUtils.padWithZero(r.getNumber(), id.getDigit());
    }

    public String ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum id,Date tanggal, String idCabang){
        String strTanggal =  new SimpleDateFormat("yyMM").format(tanggal);
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId() + strTanggal);
        if(r==null){
            r = new RunningNumber();
            r.setId(id.getId() + strTanggal);
            r.setNumber(1);
        } else {
            r.setNumber(r.getNumber()+1);
        }
        sessionFactory.getCurrentSession().saveOrUpdate(r);
        return id.getPrefix() + idCabang + strTanggal + StringUtils.padWithZero(r.getNumber(), id.getDigit());
    }
    
    public String ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum id){
        Date tanggal = systemPropertyDao.tanggalKerja();
        String idCabang = systemPropertyDao.cariBerdasarId(SystemProperty.CABANG).getVal();
        String strTanggal =  new SimpleDateFormat("yyMM").format(tanggal);
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId() + strTanggal);
        if(r==null){
            r = new RunningNumber();
            r.setId(id.getId() + strTanggal);
            r.setNumber(1);
        } else {
            r.setNumber(r.getNumber()+1);
        }
        sessionFactory.getCurrentSession().saveOrUpdate(r);
        return id.getPrefix() + idCabang + strTanggal + StringUtils.padWithZero(r.getNumber(), id.getDigit());
    }

}
