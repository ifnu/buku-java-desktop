/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos;

import com.googlecode.projecttemplate.pos.model.Product;
import com.googlecode.projecttemplate.pos.model.Purchase;
import com.googlecode.projecttemplate.pos.service.PurchaseService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ifnu
 */
public class MainLIE {
    public static void main(String[] args) {
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        PurchaseService purchaseService =
                (PurchaseService) appContext.getBean("purchaseService");
        List<Purchase> purchases =
                purchaseService.getPurchases();
        Product p = new Product();
        
        for (Purchase purchase : purchases) {
            purchase.getPurchaseDetail();
        }

    }
}
