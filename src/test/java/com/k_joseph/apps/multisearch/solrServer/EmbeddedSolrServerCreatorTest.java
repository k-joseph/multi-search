package com.k_joseph.apps.multisearch.solrServer;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.k_joseph.apps.multisearch.solrServer.EmbeddedSolrServerPropertiesInitializer;
import com.k_joseph.apps.multisearch.solrServer.MultiSearchHome;
import com.k_joseph.apps.multisearch.solrServer.SolrServerSingleton;

public class EmbeddedSolrServerCreatorTest {
	
	EmbeddedSolrServerPropertiesInitializer solrServerInit;
	
	String homePath = System.getProperty("user.home") + File.separator + ".multi-search";
	
	@SuppressWarnings("static-access")
	@Before
	public void setup() {
		File homeInit = new File(homePath);
		if (!homeInit.exists()) {
			homeInit.mkdir();
		}
		MultiSearchHome home = new MultiSearchHome(homePath + File.separator + "test");
		solrServerInit = new EmbeddedSolrServerPropertiesInitializer(
		        home.getMultiSearchHomeDirectory(),
		        "jdbc:mysql://localhost:3316/multisearch?autoReconnect=true&server.initialize-user=true&createDatabaseIfNotExist=true&server.basedir=database&server.datadir=database/data&server.collation-server=utf8_general_ci&server.character-set-server=utf8",
		        "root", "test");
	}
	
	@Test
	public void testEmbeddedSolrCreator() {
		SolrServer solrServer = SolrServerSingleton.getSolrServer();
		
		System.out.println(solrServer.toString());
		Assert.assertNotNull(solrServer);
		File testFolder = new File(homePath + File.separator + "test");
		if (testFolder.exists())
			try {
				FileUtils.deleteDirectory(testFolder);
			}
			catch (IOException e) {
				System.out.println("Error generated" + e);
			}
	}
}
