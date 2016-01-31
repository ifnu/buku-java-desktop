/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.service.impl;

import com.googlecode.projecttemplate.pos.report.DailySalesReport;
import com.googlecode.projecttemplate.pos.service.ReportService;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ifnu
 */
@Service("reportService")
@Transactional(readOnly=true)
public class ReportServiceImpl implements ReportService{

    private static final Logger log = Logger.getLogger(ReportServiceImpl.class);
    @Autowired private SessionFactory sessionFactory;

    public JasperPrint getDailySalesReport(Date date) {
        try{
            List<DailySalesReport> dailySalesReports =
                sessionFactory.getCurrentSession()
                    .createQuery("select s.product.name as productName, sum(s.quantity) as quantity, "
                    + " sum(s.subtotal) as subTotal from SalesDetail s "
                    + " where day(s.sales.salesDate) = day(:date) "
                    + " group by s.product.name order by s.product.name")
                    .setParameter("date", date)
                    .setResultTransformer(
                        Transformers.aliasToBean(DailySalesReport.class))
                    .list();

            InputStream is = ReportServiceImpl.class
                    .getResourceAsStream("/reports/DailySalesReport.jasper");

            Map<String,Object> parameters = new HashMap<String, Object>();
            parameters.put("date", date);

            return JasperFillManager.fillReport(is, parameters, 
                    new JRBeanCollectionDataSource(dailySalesReports));
        }catch(JRException ex){
            log.error("error generate DailySalesReport", ex);
        }
        return null;
    }

}
