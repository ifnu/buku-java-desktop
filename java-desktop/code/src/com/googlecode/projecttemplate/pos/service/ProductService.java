/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.service;

import com.googlecode.projecttemplate.pos.model.Product;
import java.util.List;

/**
 *
 * @author ifnu
 */
public interface ProductService {
    
    Product save(Product product);
    Product delete(Product product);
    Product getProduct(Long id);
    List<Product> getProduct();
    List<Product> getProduct(int start, int num);

}
