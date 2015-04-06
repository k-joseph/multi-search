package com.k_joseph.apps.multisearch.api.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.k_joseph.apps.multisearch.api.MultiSearchService;
import com.k_joseph.apps.multisearch.solr.SearchProject;
import com.k_joseph.apps.multisearch.solrServer.EmbeddedSolrServerPropertiesInitializer;

/**
 * Handles all DAO functions
 */
public class MultiSearchServiceImpl implements MultiSearchService {
	
	static Transaction transaction;
	
	public static Session generateCurrentSession() {
		JSONObject json = EmbeddedSolrServerPropertiesInitializer.fetchPropertiesFromPropsFile();
		String userN = json.getString("connection.user");
		String userP = json.getString("connection.password");
		
		SessionFactory sessionFactory = HibernateUtil.SetSessionFactory(json.getString("connection.url"), userN, userP);
		sessionFactory.openSession();
		
		Session curSession = sessionFactory.getCurrentSession();
		transaction = curSession.beginTransaction();
		
		return curSession;
	}
	
	public void createMultiSearchDatabase(String dbName) {
		
	}
	
	public SearchProject getSearchProject(Integer projectId) {
		Session currentSession = generateCurrentSession();
		SearchProject searchProject = (SearchProject) currentSession.get(SearchProject.class, projectId);
		
		System.out.println("Getting a search project whose id is: " + projectId);
		return searchProject;
	}
	
	@SuppressWarnings("unchecked")
	public List<SearchProject> getAllSearchProjects() {
		System.out.println("Getting all Search projects from the database");
		
		Session currentSession = generateCurrentSession();
		List<SearchProject> projects = currentSession.createCriteria(SearchProject.class).list();
		
		return projects;
	}
	
	/**
	 * Saves either a new or an edited search projects, a Search project is a description of like a
	 * module etc
	 * 
	 * @param project
	 */
	public void saveSearchProject(SearchProject project) {
		Session currentSession = generateCurrentSession();
		currentSession.saveOrUpdate(project);
		transaction.commit();
	}
	
	public void deleteSearchProject(SearchProject project) {
		Session currentSession = generateCurrentSession();
		currentSession.delete(project);
		transaction.commit();
	}
	
	public SearchProject getSearchProjectByUuid(String uuid) {
		SearchProject p = null;
		Session currentSession = generateCurrentSession();
		
		p = (SearchProject) currentSession.createQuery("from SearchProject p where p.uuid = :uuid").setString("uuid", uuid)
		        .uniqueResult();
		transaction.commit();
		
		return p;
	}
	
	public String getAllColumnNamesFromAllProjectsSeperatedByCommaAndSpace() {
		List<SearchProject> allProjects = getAllSearchProjects();
		String columnsFromAllProject = "";
		
		for (int i = 0; i < allProjects.size(); i++) {
			if (!columnsFromAllProject.contains(allProjects.get(i).getColumnNames())) {
				if (i == allProjects.size() - 1) {//if on the last project
					columnsFromAllProject += allProjects.get(i).getColumnNames();
				} else {
					columnsFromAllProject += allProjects.get(i).getColumnNames() + ", ";
				}
			}
		}
		return columnsFromAllProject;
	}
	
	public String getAllFieldsSetInSchemaByDefault() {
		return "id, cc_name, cc_filter_query, cc_description";
	}
	
	/**
	 * Gets all column names from all projects of all projects and checks whether the passed in
	 * column name is one of the existing columns, should be checked before adding any new column
	 * 
	 * @see org.openmrs.module.chartsearch.api.ChartSearchService#checkIfColumnExists(java.lang.String)
	 */
	public boolean checkIfColumnExists(String columnName) {
		String allColumnsFromAllProjects = getAllColumnNamesFromAllProjectsSeperatedByCommaAndSpace();
		if (allColumnsFromAllProjects.contains(columnName)) {
			return true;
		} else
			return false;
	}
}
