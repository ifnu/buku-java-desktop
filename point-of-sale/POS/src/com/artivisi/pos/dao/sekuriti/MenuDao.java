/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao.sekuriti;

import com.artivisi.pos.dao.BaseDaoHibernate;
import com.artivisi.pos.model.sekuriti.Menu;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class MenuDao extends BaseDaoHibernate<Menu>{
    public Integer maximumMenuLevel(){
        return (Integer) sessionFactory.getCurrentSession().createQuery("select max(menuLevel) from Menu").uniqueResult();
    }

    public Menu berdasarId(String id){
        Menu menu = (Menu) sessionFactory.getCurrentSession().get(Menu.class, id);
        Hibernate.initialize(menu.getChilds());
        return menu;
    }

}
