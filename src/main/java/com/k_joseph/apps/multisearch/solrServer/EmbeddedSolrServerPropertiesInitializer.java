package com.k_joseph.apps.multisearch.solrServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;

public class EmbeddedSolrServerPropertiesInitializer {
	
	private static String propsFilePath = System.getProperty("user.home") + File.separator + ".multi-search"
	        + File.separator + "multisearch.properties";
	
	public EmbeddedSolrServerPropertiesInitializer(String solrHomePath, String dbUrl, String dbUser, String dbPassword) {
		createPropertiesFile(solrHomePath, dbUrl, dbUser, dbPassword);
	}
	
	public String createPropertiesFile(String solrHomePath, String dbUrl, String dbUser, String dbPassword) {
		if (!StringUtils.isEmpty(solrHomePath) && !StringUtils.isEmpty(dbUrl) && !StringUtils.isEmpty(dbUser)) {
			File solrHome = new File(solrHomePath);
			
			if (StringUtils.isEmpty(dbPassword)) {
				dbPassword = "<=None";
			}
			
			String content = "#The content of this file are used by multi search application.\n\nconnection.url=>" + dbUrl
			        + "\nconnection.username=>" + dbUser + "\nconnection.password=>" + dbPassword;
			if (!solrHome.exists()) {
				solrHome.mkdir();
			}
			
			File props = new File(propsFilePath);
			FileWriter fw;
			try {
				fw = new FileWriter(props.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();
				System.out.println("Done Creating and writing to multisearch.properties file");
			}
			catch (IOException e) {
				System.out.println("Error generated" + e);
			}
			return propsFilePath;
		} else
			return null;
	}
	
	/**
	 * Should only be invoked after initializing
	 * {@link EmbeddedSolrServerPropertiesInitializer#EmbeddedSolrServerPropertiesInitializer(String, String, String, String)}
	 * 
	 * @param propsFilePath
	 * @return
	 */
	public static JSONObject fetchPropertiesFromPropsFile() {
		JSONObject json = null;
		String url = null;
		String user = null;
		String pswd = null;
		
		try {
			File file = new File(propsFilePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				List<String> linephrases = Arrays.asList(line.split("=>"));
				if (line.startsWith("connection.url=>")) {
					url = linephrases.get(1);//gets the value
				}
				if (line.startsWith("connection.username=>")) {
					user = linephrases.get(1);
				}
				if (line.startsWith("connection.password=>")) {
					if (line.endsWith("=><=None")) {
						pswd = "";
					} else
						pswd = linephrases.get(1);
				}
			}
			fileReader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		if (!StringUtils.isEmpty(url) && !StringUtils.isEmpty(user) && null != pswd) {
			json = new JSONObject();
			
			json.put("connection.url", url);
			json.put("connection.user", user);
			json.put("connection.password", pswd);
			json.put("pathToPropsFile", propsFilePath);
		}
		return json;
	}
}
