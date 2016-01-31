/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.service;

import com.googlecode.projecttemplate.pos.model.Purchase;
import java.util.List;

/**
 *
 * @author ifnu
 */
public interface PurchaseService {

    public List<Purchase> getPurchases();

}
