package com.k_joseph.apps.solrServer;


public class EmbeddedSolrProperties {
	
	private final String solrHome;
	
	private final String dbUrl;
	
	private final String dbUser;
	
	private final String dbPassword;
	
	public EmbeddedSolrProperties(String solrHome, String dbUrl, String dbUser, String dbPassword) {
		this.solrHome = solrHome;
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}
	
	public String getSolrHome() {
		return solrHome;
	}
	
	public String getDbUrl() {
		return dbUrl;
	}
	
	public String getDbUser() {
		return dbUser;
	}
	
	public String getDbPassword() {
		return dbPassword;
	}
	
	//SolrServer solrServer = SolrSingleton.getInstance().getServer();
}
