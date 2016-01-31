/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.service;

import com.googlecode.projecttemplate.pos.model.Sales;
import java.util.List;

/**
 *
 * @author ifnu
 */
public interface SalesService {

    Sales save(Sales sales);
    Sales delete(Sales sales);
    Sales getSales(Long id);
    List<Sales> getSales();
    List<Sales> getSales(int start, int num);

}
