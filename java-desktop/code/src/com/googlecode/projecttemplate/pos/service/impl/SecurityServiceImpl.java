/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.service.impl;

import com.googlecode.projecttemplate.pos.dao.MenuDao;
import com.googlecode.projecttemplate.pos.dao.PersonDao;
import com.googlecode.projecttemplate.pos.dao.RoleDao;
import com.googlecode.projecttemplate.pos.model.Menu;
import com.googlecode.projecttemplate.pos.model.Person;
import com.googlecode.projecttemplate.pos.model.Role;
import com.googlecode.projecttemplate.pos.service.SecurityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ifnu
 */
@Service("securityService")
@Transactional(readOnly=true)
public class SecurityServiceImpl implements SecurityService{
    @Autowired private PersonDao personDao;
    @Autowired private MenuDao menuDao;
    @Autowired private RoleDao roleDao;
    @Transactional
    public Person save(Person person) {
        return personDao.save(person);
    }
    @Transactional
    public Person delete(Person person) {
        return personDao.delete(person);
    }
    public Person getPerson(Long id) {
        return personDao.getById(id);
    }
    public List<Person> getPersons() {
        return personDao.getAll();
    }
    @Transactional
    public Menu save(Menu menu) {
        return menuDao.save(menu);
    }
    @Transactional
    public Menu delete(Menu menu) {
        return menuDao.delete(menu);
    }
    public Menu getMenu(Long id) {
        return menuDao.getById(id);
    }
    public List<Menu> getMenus() {
        return menuDao.getAll();
    }
    @Transactional
    public Role save(Role role) {
        return roleDao.save(role);
    }
    @Transactional
    public Role delete(Role role) {
        return roleDao.delete(role);
    }
    public Role getRole(Long id) {
        return roleDao.getById(id);
    }
    public List<Role> getRoles() {
        return roleDao.getAll();
    }
}
