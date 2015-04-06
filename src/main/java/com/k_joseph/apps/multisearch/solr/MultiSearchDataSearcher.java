package com.k_joseph.apps.multisearch.solr;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.k_joseph.apps.multisearch.api.MultiSearchService;
import com.k_joseph.apps.multisearch.solrServer.SolrServerSingleton;

public class MultiSearchDataSearcher {//TODO may need to add and access this as a bean

	private MultiSearchService multiSearchService;
	
	public SolrDocumentList getNonPatientDocumentList(String searchText, int projectId) {
		SolrServer solrServer = SolrServerSingleton.getSolrServer();
		SolrQuery query = new SolrQuery();
		QueryResponse response = null;
		
		if (StringUtils.isBlank(searchText)) {
			searchText = "*";
		}
		MultiSearchSyntax searchSyntax = new MultiSearchSyntax(searchText);
		searchText = searchSyntax.getSearchQuery();
		
		query.setQuery("text:" + searchText);
		query.addFilterQuery("project_id:" + projectId);
		query.setStart(0);
		query.setRows(999999999);
		query.setHighlight(true).setHighlightSnippets(1).setHighlightSimplePre("<b>").setHighlightSimplePost("</b>");
		query.setParam("hl.fl", "text");
		
		try {
			response = solrServer.query(query);
		}
		catch (SolrServerException e) {
			System.out.println("Error generated" + e);
		}
		SolrDocumentList results = response.getResults();
		/*NonPatientDataItem item = new NonPatientDataItem();
		List<ChartListItem> items = new ArrayList<ChartListItem>();
		Iterator<SolrDocument> iterator = results.iterator();
		while (iterator.hasNext()) {
			SolrDocument document = iterator.next();
			SearchProject project = chartSearchService.getSearchProject(projectId);
			List<String> columns = NonPatientDataIndexer.removeSpacesAndSplitLineUsingComma("");
			
		}*/
		
		return results;
	}
	
}
