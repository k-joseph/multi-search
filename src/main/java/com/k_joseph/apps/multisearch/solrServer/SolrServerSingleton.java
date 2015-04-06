package com.k_joseph.apps.multisearch.solrServer;

import java.io.File;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrServer;

public class SolrServerSingleton {
	
	public static SolrServer getSolrServer() {
		String homeDir = MultiSearchHome.getMultiSearchHomeDirectory() + File.separator;
		JSONObject jsonProps = EmbeddedSolrServerPropertiesInitializer.fetchPropertiesFromPropsFile();
		
		if (jsonProps != null) {
			EmbeddedSolrProperties props = new EmbeddedSolrProperties(homeDir, jsonProps.getString("connection.url"),
			        jsonProps.getString("connection.user"), jsonProps.getString("connection.password"));
			EmbeddedSolrServerCreator creator = new EmbeddedSolrServerCreator(props);
			SolrServer solrServer = creator.createSolrServer();
			return solrServer;
		} else
			return null;
	}
}
