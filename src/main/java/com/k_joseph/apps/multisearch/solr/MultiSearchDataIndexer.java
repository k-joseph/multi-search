/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package com.k_joseph.apps.multisearch.solr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import com.k_joseph.apps.multisearch.api.MultiSearchService;
import com.k_joseph.apps.multisearch.solrServer.SolrServerSingleton;

public class MultiSearchDataIndexer {//TODO may need to add and access this as a bean

	private MultiSearchService multiSearchService;
	
	/**
	 * generates the solr documents from the data fetched from a database, adds it to SolrServer for
	 * indexing
	 * 
	 * @param solrServer
	 * @param projectId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection generateDocumentsAndAddFieldsAndCommitToSolr(int projectId) {
		SolrServer solrServer = SolrServerSingleton.getSolrServer();
		SearchProject project = multiSearchService.getSearchProject(projectId);
		String sql = project.getSqlQuery();
		String columnNamesWithCommas = project.getColumnNames();
		List<String> columnNamesFromCS = removeSpacesAndSplitLineUsingComma(columnNamesWithCommas);
		Collection docs = new ArrayList();
		
		String dbUser = project.getDatabaseUser();
		String dbPassword = project.getDatabaseUSerPassword();
		String serverName = project.getServerName();
		String dbms = project.getDbms();
		String dbName = project.getDatabase();
		String portNumber = project.getPortNumber();
		try {
			Connection connection = getConnection(serverName, dbms, dbUser, dbPassword, dbName, portNumber);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				SolrInputDocument doc = new SolrInputDocument();
				addBasicFieldsToSolrDoc(project, rs.getRow(), doc);
				for (int i = 0; i < columnNamesFromCS.size(); i++) {
					//Object object = rs.getString(columnNamesFromCS.get(i));
					doc.addField(columnNamesFromCS.get(i), rs.getString(columnNamesFromCS.get(i)));
				}
				docs.add(doc);
			}
		}
		catch (SQLException e) {
			System.out.println("Error generated" + e);
		}//connection.url
		
		try {
			if (docs.size() > 1000) {
				// Commit within 5 minutes.
				UpdateResponse resp = solrServer.add(docs, 300000);
				printFailureIfResponseIsNotZero(resp);
			} else {
				UpdateResponse resp = solrServer.add(docs);//TODO fix: https://groups.google.com/a/openmrs.org/forum/#!topic/dev/N4l1Hj77j98
				printFailureIfResponseIsNotZero(resp);
			}
			solrServer.commit();
		}
		catch (SolrServerException e) {
			System.out.println("Error generated" + e);
		}
		catch (IOException e) {
			System.out.println("Error generated" + e);
		}
		return docs;
	}
	
	private void addBasicFieldsToSolrDoc(SearchProject project, int i, SolrInputDocument doc) {
		doc.addField("id", i);
		doc.addField("project_id", i);
		doc.addField("project_name", project.getProjectName());
		doc.addField("project_db_name", project.getDatabase());
		doc.addField("project_description", project.getProjectDescription());
		doc.addField("project_uuid", project.getUuid());
	}
	
	private void printFailureIfResponseIsNotZero(UpdateResponse resp) {
		if (resp.getStatus() != 0) {
			System.out.println("Some error has occurred, status is: " + resp.getStatus());
		}
	}
	
	/**
	 * Removes space(s) either at the start or end of a sentence/line and then split it using commas
	 * 
	 * @param line
	 * @return list of words split from the line
	 */
	public List<String> removeSpacesAndSplitLineUsingComma(String line) {
		//remove space(s) either at the start or end or line and then split it using commas
		String[] result = line.replaceAll(" ", "").split(",");
		List<String> words = new ArrayList<String>();
		words.addAll(Arrays.asList(result));
		return words;
	}
	
	/**
	 * Only to be used when indexing data outside openmrs current running database
	 */
	public static Connection getConnection(String serverName, String dbms, String dbUser, String dbPassword, String dbName,
	                                String portNumber) throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", dbUser);
		connectionProps.put("password", dbPassword);
		
		//TODO may be modified to support several other database managers
		if (dbms.equals("mysql")) {
			conn = (Connection) DriverManager.getConnection("jdbc:" + dbms + "://" + serverName + ":" + portNumber + "/",
			    connectionProps);
		} else if (dbms.equals("derby")) {
			conn = (Connection) DriverManager.getConnection("jdbc:" + dbms + ":" + dbName + ";create=true", connectionProps);
		}
		System.out.println("Connected to database: " + dbName);
		return conn;
	}
}
