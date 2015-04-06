package com.k_joseph.apps.multisearch.installer;

import java.io.File;

import net.sf.json.JSONObject;

import com.k_joseph.apps.multisearch.solrServer.EmbeddedSolrServerPropertiesInitializer;
import com.k_joseph.apps.multisearch.solrServer.MultiSearchHome;

public class RunInstaller {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	static final String DB_URL = "jdbc:mysql://localhost:3306/";//port can be changed from properties file
	
	private String multiSearchHomeFolder = System.getProperty("user.home") + File.separator + ".multi-search";
	
	public boolean checkWhetherInstalled() {
		File installFile = new File(multiSearchHomeFolder + File.separator + "multisearch.properties");
		if (installFile.exists()) {
			return true;
		} else
			return false;
	}
	
	public String generateConnectionString(String dbName) {
		String connString = DB_URL
		        + dbName
		        + "?autoReconnect=true&createDatabaseIfNotExist=true&server.character-set-server=utf8";
		return connString;
	}
	
	/**
	 * Creates properties file
	 * 
	 * @param dbName
	 * @param dbUser
	 * @param dbPassword
	 * @return
	 */
	@SuppressWarnings("static-access")
	public JSONObject runNewInstallation(String dbName, String dbUser, String dbPassword) {
		MultiSearchHome home = new MultiSearchHome(multiSearchHomeFolder);
		File propertiesFile = new File(home.getMultiSearchHomeDirectory() + File.separator + "multisearch.properties");
		
		if (!propertiesFile.exists()) {
			EmbeddedSolrServerPropertiesInitializer solrServerInit = new EmbeddedSolrServerPropertiesInitializer(
			        home.getMultiSearchHomeDirectory(), generateConnectionString(dbName), dbUser, dbPassword);
			return solrServerInit.fetchPropertiesFromPropsFile();
		} else
			return null;
		
	}
}
