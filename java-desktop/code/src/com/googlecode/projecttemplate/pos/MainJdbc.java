/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos;

import com.googlecode.projecttemplate.pos.model.Person;
import com.googlecode.projecttemplate.pos.service.ServiceJdbc;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.SQLException;

/**
 *
 * @author ifnu
 */
public class MainJdbc {
    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("");
        dataSource.setDatabaseName("latihan");
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(3306);

        ServiceJdbc service = new ServiceJdbc();
        service.setDataSource(dataSource);

        Person person = new Person();
        person.setName("administrator");
        person.setPassword("pwd");
        service.save(person);

        System.out.println("id : " + person.getId());
        System.out.println("name: " +
		person.getName());

        try {
            dataSource.getConnection().close();
        } catch (SQLException ex) {
	      ex.printStackTrace();
        }
    }
}
