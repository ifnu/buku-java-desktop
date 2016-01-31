/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos;

import com.googlecode.projecttemplate.pos.model.Person;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ifnu
 */
public class MainHibernate {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().buildSessionFactory();

        Person p = new Person();
        p.setName("dian");
        p.setPassword("pwddian");

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(p);
            session.getTransaction().commit();
        } catch (HibernateException hibernateException) {
            session.getTransaction().rollback();
        }
        session.close();

        session = sessionFactory.openSession();
        Query query = session.createQuery("from Person");
        List<Person> persons = query.list();
        for (Person person : persons) {
            System.out.println("id : " + person.getId());
            System.out.println("name : " + person.getName());
            System.out.println("password : " + person.getPassword());
        }

        session.close();
        sessionFactory.close();

    }
}
