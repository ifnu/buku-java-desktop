/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.launcher;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author endy
 */
public class GenerateDatabase {
    public static void main(String[] args) throws SQLException {
        AbstractApplicationContext ctx =
                new ClassPathXmlApplicationContext
                ("classpath:applicationContext.xml");

        DataSource dataSource = (DataSource) ctx.getBean("dataSource");

        Configuration cfg = new AnnotationConfiguration()
                .configure("hibernate.cfg.xml")
                .setProperty("hibernate.dialect",
                "org.hibernate.dialect.MySQLInnoDBDialect");

        Connection conn = dataSource.getConnection();
        new SchemaExport(cfg, conn).create(true, true);

        conn.close();
        ctx.registerShutdownHook();

    }
}
