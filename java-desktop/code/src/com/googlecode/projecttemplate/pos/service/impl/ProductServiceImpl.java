/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.service.impl;

import com.googlecode.projecttemplate.pos.dao.ProductDao;
import com.googlecode.projecttemplate.pos.model.Product;
import com.googlecode.projecttemplate.pos.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ifnu
 */
@Service("productService")
@Transactional(readOnly=true)
public class ProductServiceImpl implements ProductService{

    @Autowired private ProductDao productDao;

    @Transactional
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Transactional
    public Product delete(Product product) {
        return productDao.delete(product);
    }

    public Product getProduct(Long id) {
        return productDao.getById(id);
    }

    public List<Product> getProduct() {
        return productDao.getAll();
    }

    public List<Product> getProduct(int start, int num) {
        return productDao.getAll(start, num);
    }

}
