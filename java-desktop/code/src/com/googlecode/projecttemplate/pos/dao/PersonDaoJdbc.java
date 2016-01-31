/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.projecttemplate.pos.dao;

import com.googlecode.projecttemplate.pos.model.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ifnu
 */

public class PersonDaoJdbc {
    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getAllStatement;
    private final String insertQuery = "insert into T_PERSON(name,password)" +
	" values(?,?)";
    private final String updateQuery = "update T_PERSON set name=?, " +
	"password=? where id=?";
    private final String deleteQuery = "delete from T_PERSON where id=?";
    private final String getByIdQuery = "select * from T_PERSON where id =?";
    private final String getAllQuery = "select * from T_PERSON";

    public void setConnection(Connection connection) throws SQLException{
      this.connection = connection;
	insertStatement = this.connection.prepareStatement(insertQuery,
		Statement.RETURN_GENERATED_KEYS);
	updateStatement = this.connection.prepareStatement(updateQuery);
	deleteStatement = this.connection.prepareStatement(deleteQuery);
	getByIdStatement = this.connection.prepareStatement(getByIdQuery);
	getAllStatement = this.connection.prepareStatement(getAllQuery);
    }

    public Person save(Person person) throws SQLException{
	if (person.getId() == null) {
	  insertStatement.setString(1, person.getName());
	  insertStatement.setString(2, person.getPassword());
	  long id = insertStatement.executeUpdate();
	  person.setId(id);
	} else {
	  updateStatement.setString(1, person.getName());
	  updateStatement.setString(2, person.getPassword());
	  updateStatement.setLong(3, person.getId());
	  updateStatement.executeUpdate();
	}
	return person;
    }

    public Person delete(Person person) throws SQLException{
	deleteStatement.setLong(1, person.getId());
	deleteStatement.executeUpdate();
	return person;
    }

    public Person getById(Long id) throws SQLException{
	getByIdStatement.setLong(1, id);
	ResultSet rs = getByIdStatement.executeQuery();
	//proses mapping dari relational ke object
	if (rs.next()) {
	  Person person = new Person();
	  person.setId(rs.getLong("id"));
	  person.setName(rs.getString("name"));
	  person.setPassword(rs.getString("password"));
	  return person;
	}
        return null;
    }
    public List<Person> getAll() throws SQLException{
	List<Person> persons =
		new ArrayList<Person>();
	ResultSet rs = getAllStatement.executeQuery();
	while(rs.next()){
	  Person person = new Person();
	  person.setId(rs.getLong("id"));
	  person.setName(rs.getString("name"));
	  person.setPassword(rs.getString("password"));
	  persons.add(person);
	}
	return persons;
    }
}
