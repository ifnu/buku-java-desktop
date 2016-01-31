/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.service.impl;

import com.googlecode.projecttemplate.pos.dao.PurchaseDao;
import com.googlecode.projecttemplate.pos.model.Purchase;
import com.googlecode.projecttemplate.pos.service.PurchaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ifnu
 */
@Service(value="purchaseService")
@Transactional(readOnly=true)
public class PurchaseServiceImpl implements PurchaseService{


    @Autowired private PurchaseDao purchaseDao;

    public List<Purchase> getPurchases() {
        return purchaseDao.getAll();
    }

}
