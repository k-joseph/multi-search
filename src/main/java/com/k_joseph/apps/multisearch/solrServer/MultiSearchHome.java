package com.k_joseph.apps.multisearch.solrServer;

public class MultiSearchHome {
	
	private static String homeDirectory;
	
	public MultiSearchHome(String homeDirectory) {
		MultiSearchHome.homeDirectory = homeDirectory;
	}
	
	/**
	 * TODO, initialize value on installation of project
	 * 
	 * @return
	 */
	public static String getMultiSearchHomeDirectory() {
		return MultiSearchHome.homeDirectory;
	}
	
}
