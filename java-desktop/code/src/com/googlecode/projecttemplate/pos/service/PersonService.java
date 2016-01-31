/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.service;

import com.googlecode.projecttemplate.pos.model.Person;
import java.util.List;

/**
 *
 * @author ifnu
 */
public interface PersonService {

    void save(Person person);
    void delete(Person person);
    Long count();
    Person getPerson(Long id);
    List<Person> getPersons();
    List<Person> getPersons(int start, int num);

}
