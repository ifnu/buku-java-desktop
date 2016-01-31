package com.googlecode.projecttemplate.pos.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDaoHibernate<T> {

    @SuppressWarnings("unchecked")
    protected Class domainClass;

    @Autowired
    protected SessionFactory sessionFactory;
    @SuppressWarnings("unchecked")
    public BaseDaoHibernate() {
        this.domainClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
    public T save(T domain) {
        sessionFactory.getCurrentSession().saveOrUpdate(domain);
        return domain;
    }
    @SuppressWarnings("unchecked")
    public T getById(Long id) {
        return (T) sessionFactory.getCurrentSession().get(domainClass, id);
    }
    public T delete(T domain) {
        sessionFactory.getCurrentSession().delete(domain);
        return domain;
    }
    @SuppressWarnings("unchecked")
    public Long count() {
        return (Long) sessionFactory.getCurrentSession().createQuery(
                "select count(*) from " + domainClass.getName() + " x").uniqueResult();
    }
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from " + domainClass.getName())
                .list();
    }
    @SuppressWarnings("unchecked")
    public List<T> getAll(int start, int num) {
        return sessionFactory.getCurrentSession().createQuery("from " + domainClass.getName())
                .setFirstResult(start).setMaxResults(num)
                .list();
    }
}
