/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.util;

import com.googlecode.projecttemplate.pos.model.Address;
import com.googlecode.projecttemplate.pos.model.Customer;
import com.googlecode.projecttemplate.pos.model.Person;
import com.googlecode.projecttemplate.pos.service.PersonService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ifnu
 */
public class GenerateTables {
    public static void main(String[] args) throws SQLException {
        AbstractApplicationContext appContext =
                new ClassPathXmlApplicationContext
                ("classpath:applicationContext.xml");

        DataSource dataSource = (DataSource) appContext.getBean("dataSource");

        Configuration cfg = new AnnotationConfiguration()
                .configure("hibernate.cfg.xml");
        Connection conn = dataSource.getConnection();
        new SchemaExport(cfg, conn).create(true, true);

        PersonService personService =
                (PersonService) appContext.getBean("personService");
        SessionFactory sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
        Session s = sessionFactory.openSession();
        s.getTransaction().begin();
        for(int i=0;i<2;i++){
            Person person = new Person();
            person.setName("ifnu" + i);
            person.setPassword("pwdifnu");
            personService.save(person);

            Customer c = new Customer();
            Address a = new Address();
            a.setAddress1("Warung Buncit Raya no 36");
            a.setAddress2("Jakarta Selatan");
            a.setCity("Jakarta");
            a.setCountry("Indonesia");
            a.setPostCode("65968");
            a.setProvince("DKI Jakarta");
            c.setAddress(a);
            List<String> emails = new ArrayList<String>();
            emails.add("a@world.com");
            emails.add("b@world.com");
            c.setEmails(emails);
            c.setFirstName("Me");
            c.setPhone("234");
            c.setSecondName("You");
            s.save(c);
        }
        s.getTransaction().commit();

        List<Person> persons = s.createQuery("from Person p")
                .list();

        s.close();


        System.exit(0);
    }
}
