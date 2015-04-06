package com.k_joseph.apps.multisearch.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.k_joseph.apps.multisearch.api.impl.HibernateUtil;
import com.k_joseph.apps.multisearch.solr.SearchProject;

/**
 * Works pretty well, Better to run this test immediately after installing the application so as to
 * get one search project; Chart Search added to your database
 */
@Ignore
public class MultiSearchServiceTest {
	
	public MultiSearchService getMultiSearchService() {
		return HibernateUtil.fetchMultiSearchServiceBean();
	}
	
	List<SearchProject> projects = getMultiSearchService().getAllSearchProjects();
	
	@Before
	public void runBeforeAllTests() throws Exception {
		Assert.assertNotNull(getMultiSearchService());
	}
	
	@Test
	public void testSavingGettingProjectAndDeletingExistingAfterRunningTheFirstTime() {
		List<String> columnNamesList = new ArrayList<String>();
		columnNamesList.add("cc_name");
		columnNamesList.add("cc_filter_query");
		columnNamesList.add("cc_description");
		
		SearchProject project = new SearchProject(
		        "Chart Search",
		        "SELECT name AS cc_name, filter_query AS cc_filter_query, description AS cc_description FROM chartsearch_categories",
		        columnNamesList, null);
		project.setUuid("66f0081c-93e9-4c54-ad73-caf34b104a9c");
		project.setFieldsExistInSchema(true);
		project.setColumnNamesList(columnNamesList);
		
		if (projects.isEmpty()) {
			saveAndVerifySavedSearchProject(project, "66f0081c-93e9-4c54-ad73-caf34b104a9c");
		} else {
			for (int i = 0; i < projects.size(); i++) {
				if (("Chart Search").equals(projects.get(i).getProjectName())) {
					SearchProject chartSearch = projects.get(i);
					Assert.assertNotNull(getMultiSearchService().getSearchProject(chartSearch.getProjectId()));
					getMultiSearchService().deleteSearchProject(chartSearch);//delete it before adding a new similar one
					Assert.assertNull(getMultiSearchService().getSearchProjectByUuid("66f0081c-93e9-4c54-ad73-caf34b104a9c"));
					
					saveAndVerifySavedSearchProject(project, "66f0081c-93e9-4c54-ad73-caf34b104a9c");
				}
			}
			
		}
	}
	
	private void saveAndVerifySavedSearchProject(SearchProject project, String uuid) {
		getMultiSearchService().saveSearchProject(project);
		SearchProject savedProject = getMultiSearchService().getSearchProjectByUuid(uuid);
		
		Assert.assertNotNull(savedProject);
		Assert.assertEquals(uuid, savedProject.getUuid());
	}
	
	@Test
	public void runPasses() {
		Assert.assertEquals("", "");
	}
}
