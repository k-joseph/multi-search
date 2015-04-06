package com.k_joseph.apps.multisearch.api.impl;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.k_joseph.apps.multisearch.api.MultiSearchService;
import com.k_joseph.apps.multisearch.solr.SearchProject;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	public static SessionFactory SetSessionFactory(String url, String user, String pass) {
		try {
			Configuration conf = new Configuration();
			
			// <!-- Database connection settings -->
			conf.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
			conf.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			conf.setProperty("hibernate.current_session_context_class", "thread");
			conf.setProperty("hibernate.connection.url", url);
			conf.setProperty("hibernate.connection.username", user);
			conf.setProperty("hibernate.connection.password", pass);
			
			//All annotated classes need to be added here
			conf.addAnnotatedClass(SearchProject.class);
			
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
			        .applySettings(conf.getProperties());
			
			sessionFactory = conf.buildSessionFactory(builder.build());
			
		}
		catch (Throwable ex) {
			// Log exception!
			throw new ExceptionInInitializerError(ex);
		}
		return sessionFactory;
	}
	
	@SuppressWarnings("resource")
	public static MultiSearchService fetchMultiSearchServiceBean() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
		        "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml");
		return (MultiSearchService) context.getBean("multiSearchService");
	}
}
