package com.k_joseph.apps.multisearch.api;

import java.util.List;

import com.k_joseph.apps.multisearch.solr.SearchProject;

public interface MultiSearchService {
	
	/**
	 * @should save a new search project
	 * @should update a search project
	 * @param project
	 */
	public void saveSearchProject(SearchProject project);
	
	public SearchProject getSearchProject(Integer projectId);
	
	public List<SearchProject> getAllSearchProjects();
	
	String getAllColumnNamesFromAllProjectsSeperatedByCommaAndSpace();
	
	boolean checkIfColumnExists(String columnName);
	
	public SearchProject getSearchProjectByUuid(String uuid);
	
	public String getAllFieldsSetInSchemaByDefault();

	public void deleteSearchProject(SearchProject searchProject);
}
