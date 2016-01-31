/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos;

import com.googlecode.projecttemplate.pos.model.Person;
import com.googlecode.projecttemplate.pos.service.PersonService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ifnu
 */
public class MainSpring {
    public static void main(String[] args) {

        ApplicationContext appContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        PersonService personService =
                (PersonService) appContext.getBean("personService");

        Person person = new Person();
        person.setName("ifnu");
        person.setPassword("pwdifnu");
        personService.save(person);

        List<Person> persons = personService.getPersons();
        for (Person p : persons) {
            System.out.println("name:" + p.getName() +
                    ", password:"+p.getPassword());
        }
    }
}
