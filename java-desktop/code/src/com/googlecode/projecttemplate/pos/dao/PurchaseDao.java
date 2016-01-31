/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.dao;

import com.googlecode.projecttemplate.pos.model.Purchase;
import com.googlecode.projecttemplate.pos.report.DailySalesReport;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class PurchaseDao {


    @Autowired private SessionFactory sessionFactory;

    public List<Purchase> getAll(){
        return sessionFactory.getCurrentSession()
                .createQuery("from Purchase p")
                .list();
    }

    public List<DailySalesReport> getDailySalesReports(){
        return sessionFactory.getCurrentSession()
                .createQuery("")
                .setResultTransformer(Transformers.aliasToBean(DailySalesReport.class))
                .list();
    }

}
