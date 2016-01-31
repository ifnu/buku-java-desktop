/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.master;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.master.Shift;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class ShiftDao extends BaseDaoHibernate<Shift> {

    public Shift cariBerdasarId(String id){
        return (Shift) sessionFactory.getCurrentSession().get(Shift.class,id);
    }

    public Shift shiftSekarang() {
        DateTime dateTime = new DateTime();
        for(Shift s : semua()){
            DateTime jamMulai = new DateTime(s.getJamMulai());
            DateTime jamSelesai = new DateTime(s.getJamSelesai());
            if(dateTime.getMinuteOfDay() >= jamMulai.getMinuteOfDay()
                    && dateTime.getMinuteOfDay() <= jamSelesai.getMinuteOfDay()){
                return s;
            }
        }
        return null;
    }

}
