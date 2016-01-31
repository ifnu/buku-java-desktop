/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.projecttemplate.pos.service;

import com.googlecode.projecttemplate.pos.dao.PersonDaoJdbc;
import com.googlecode.projecttemplate.pos.model.Person;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author ifnu
 */
public class ServiceJdbc {

    private PersonDaoJdbc personDao;
    private Connection connection;

    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            personDao = new PersonDaoJdbc();
            personDao.setConnection(connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Person save(Person person) {
        try {
            connection.setAutoCommit(false);
            personDao.save(person);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return person;
    }

    public Person delete(Person person) {
        try {
            connection.setAutoCommit(false);
            personDao.save(person);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return person;
    }

    public Person getPerson(Long id) {
        try {
            return personDao.getById(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Person> getPersons() {
        try {
            return personDao.getAll();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Person>();
    }
}
