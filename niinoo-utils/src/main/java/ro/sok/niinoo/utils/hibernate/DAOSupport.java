package ro.sok.niinoo.utils.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface DAOSupport {

	SessionFactory getSessionFactory();

	DataSource getDataSource();

	Session getSession();

	Connection getConnection();

}
