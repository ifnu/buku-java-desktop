/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.dao;

import com.googlecode.projecttemplate.pos.model.Person;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class PersonDao {
    @Autowired private SessionFactory sessionFactory;
    public Person save(Person person){
        sessionFactory.getCurrentSession().saveOrUpdate(person);
        return person;
    }
    public Person delete(Person person){
        sessionFactory.getCurrentSession().delete(person);
        return person;
    }
    public Long count(){
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("select count(*) from Person p")
                .uniqueResult();
    }
    public Person getById(Long id){
        return (Person) sessionFactory.getCurrentSession()
                .createQuery("from Person p where p.id=:id")
                .setParameter("id", id)
                .uniqueResult();
    }

    public List<Person> getAll(){
        return sessionFactory.getCurrentSession()
                .createQuery("from Person p")
                .list();
    }

    public List<Person> getAll(int start, int num){
        return sessionFactory.getCurrentSession()
                .createQuery("from Person p")
                .setFirstResult(start)
                .setFetchSize(num)
                .list();
    }
}
