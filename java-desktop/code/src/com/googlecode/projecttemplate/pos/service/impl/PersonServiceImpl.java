/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.service.impl;

import com.googlecode.projecttemplate.pos.model.Person;
import com.googlecode.projecttemplate.pos.dao.PersonDao;
import com.googlecode.projecttemplate.pos.service.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ifnu
 */
@Service("personService")
@Transactional(readOnly=true)
public class PersonServiceImpl implements PersonService{
    @Autowired private PersonDao personDao;
    @Transactional(readOnly=false)
    public void save(Person person) {
        personDao.save(person);
    }
    @Transactional(readOnly=false)
    public void delete(Person person) {
        personDao.delete(person);
    }
    public Long count() {
        return personDao.count();
    }
    public Person getPerson(Long id) {
        return personDao.getById(id);
    }
    public List<Person> getPersons() {
        return personDao.getAll();
    }
    public List<Person> getPersons(int start, int num) {
        return personDao.getAll(start, num);
    }
}
