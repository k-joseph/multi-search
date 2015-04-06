package com.k_joseph.apps.multisearch.installer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.ibatis.jdbc.ScriptRunner;

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
		String connString = DB_URL + dbName
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
		
		setUpMultiSearchDatabaseLocally(generateConnectionString(dbName), dbUser, dbPassword);
		
		if (!propertiesFile.exists()) {
			EmbeddedSolrServerPropertiesInitializer solrServerInit = new EmbeddedSolrServerPropertiesInitializer(
			        home.getMultiSearchHomeDirectory(), generateConnectionString(dbName), dbUser, dbPassword);
			return solrServerInit.fetchPropertiesFromPropsFile();
		} else
			return null;
		
	}
	
	/**
	 * This uses ScriptRunner to run sql source file, i used this to overcome issues i had with
	 * hibernate: However this is a bad practice for heavy data external file sources
	 * 
	 * @param connString
	 * @param dbUser
	 * @param dbPassword
	 */
	public void setUpMultiSearchDatabaseLocally(String connString, String dbUser, String dbPassword) {
		//TODO probably this functionality should be moved over MultiSearchService
		URL url = getClass().getClassLoader().getResource("multisearch.sql");
		String sqlScriptFilePath = url.getPath();
		
		try {
			Class.forName(JDBC_DRIVER);
			Connection con = DriverManager.getConnection(connString, dbUser, dbPassword);
			try {
				// Initialize object for ScriptRunner and it's reader
				ScriptRunner sr = new ScriptRunner(con);
				Reader reader = new BufferedReader(new FileReader(sqlScriptFilePath));
				
				sr.runScript(reader);
				
			}
			catch (Exception e) {
				System.err.println("Failed to Execute" + sqlScriptFilePath + " The error is " + e.getMessage());
			}
		}
		catch (SQLException e1) {
			System.out.println("Error generated" + e1);
		}
		catch (ClassNotFoundException e1) {
			System.out.println("Error generated" + e1);
		}
	}
}
