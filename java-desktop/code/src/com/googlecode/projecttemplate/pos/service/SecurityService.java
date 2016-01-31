/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.service;

import com.googlecode.projecttemplate.pos.model.Menu;
import com.googlecode.projecttemplate.pos.model.Person;
import com.googlecode.projecttemplate.pos.model.Role;
import java.util.List;

/**
 *
 * @author ifnu
 */
public interface SecurityService {

    public Person save(Person person);
    public Person delete(Person person);
    public Person getPerson(Long id);
    public List<Person> getPersons();

    public Menu save(Menu menu);
    public Menu delete(Menu menu);
    public Menu getMenu(Long id);
    public List<Menu> getMenus();

    public Role save(Role role);
    public Role delete(Role role);
    public Role getRole(Long id);
    public List<Role> getRoles();

}
