package com.k_joseph.apps.multisearch.solr;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

/**
 * A web project that has database data attached to it. Such as Chart Search Module et-cetera
 */
@Entity
@Table(name = "multisearch_project")
public class SearchProject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "project_id")
	private int projectId;
	
	@Column(name = "name", length = 38, nullable = false, unique = true)
	private String projectName;
	
	@Column(name = "uuid", unique = true)
	private String uuid;
	
	@Column(name = "description")
	private String projectDescription;
	
	/**
	 * The SQL Query to be run to return the data that need to be indexed for this particular
	 * project
	 */
	@Column(name = "sql_query", length = 65535, nullable = false)
	private String sqlQuery;
	
	/**
	 * column or solr field names separated by commas, these should be unique from one another and
	 * the same ones as mentioned in the sqlQuery, take use of AS key word in MySQL to make them
	 * unique
	 */
	@Column(name = "field_names", length = 255, nullable = false)
	private String columnNames;
	
	@Type(type = "boolean")
	@Column(name = "fields_exist_in_schema")
	private boolean fieldsExistInSchema;
	
	@Column(name = "db_name", length = 15)
	private String database;
	
	@Column(name = "db_user", length = 15)
	private String databaseUser;
	
	@Column(name = "db_user_password", length = 38)
	private String databaseUSerPassword;
	
	@Column(name = "server_name", length = 15)
	private String serverName;
	
	@Column(name = "dbms", length = 8)
	private String dbms;
	
	@Column(name = "port_number", length = 5)
	private String portNumber;
	
	/**
	 * List of column names that need to be added as fields obtained from the client
	 */
	@Transient
	private List<String> columnNamesList;
	
	/**
	 * Creates a SearchProject object when registering a project, must always use this when creating
	 * this object
	 * 
	 * @param projectName
	 * @param sqlQuery
	 * @param columnNamesList
	 */
	public SearchProject(String projectName, String sqlQuery, List<String> columnNamesList, String database) {
		setUuid(UUID.randomUUID().toString());
		setProjectName(projectName);
		setSqlQuery(sqlQuery);
		setColumnNamesList(columnNamesList);
		setColumnNames(getColumnNamesSeparatedWithCommas());
		setDatabase(database);
	}
	
	/**
	 * AVOID USING THIS, preferably use: {@link #SearchProject(String, String, List, String)}
	 */
	public SearchProject() {
	}
	
	public String getUuid() {
		return this.uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectDescription() {
		if (StringUtils.isBlank(projectDescription)) {
			return "";
		} else
			return projectDescription;
	}
	
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	
	public int getProjectId() {
		return projectId;
	}
	
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public String getSqlQuery() {
		return sqlQuery;
	}
	
	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}
	
	/**
	 * Doesn't return from the database, use {@link #getColumnNames()}
	 * 
	 * @return
	 */
	public List<String> getColumnNamesList() {
		return columnNamesList;
	}
	
	public void setColumnNamesList(List<String> columnNamesList) {
		this.columnNamesList = columnNamesList;
	}
	
	/**
	 * Read only - Returns all columns separated by a comma and space(, ) from the the
	 * {@link #getColumnNamesList()} Auto generated method comment
	 * 
	 * @return comma separated column names otherwise NULL if no column name exists
	 */
	public String getColumnNamesSeparatedWithCommas() {
		String commaSeperateColumnNames = "";
		List<String> columnNames = getColumnNamesList();
		for (int i = 0; i < columnNames.size(); i++) {
			if (i != columnNames.size() - 1) {
				commaSeperateColumnNames += columnNames.get(i) + ", ";
			} else
				commaSeperateColumnNames += columnNames.get(i);
		}
		if (StringUtils.isBlank(commaSeperateColumnNames)) {
			commaSeperateColumnNames = null;
		}
		return commaSeperateColumnNames;
	}
	
	/**
	 * Gets column names separated commas
	 * 
	 * @see {@link #getColumnNamesSeparatedWithCommas()}
	 */
	public String getColumnNames() {
		return columnNames;
	}
	
	/**
	 * @see {@link #getColumnNamesSeparatedWithCommas()}
	 */
	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}
	
	public String getDatabase() {
		return database;
	}
	
	/**
	 * Sets the database name, if it is empty or whitespace or bull, it sets it to the multisearch
	 * database
	 * 
	 * @param database
	 */
	public void setDatabase(String database) {
		if (StringUtils.isBlank(database)) {
			database = "multisearch";
		}
		this.database = database;
	}
	
	public String getDatabaseUser() {
		if (StringUtils.isBlank(databaseUser)) {
			return "";
		} else
			return databaseUser;
	}
	
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}
	
	public String getDatabaseUSerPassword() {
		if (StringUtils.isBlank(databaseUSerPassword)) {
			return "";
		} else
			return databaseUSerPassword;
	}
	
	public void setDatabaseUSerPassword(String databaseUSerPassword) {
		this.databaseUSerPassword = databaseUSerPassword;
	}
	
	public String getServerName() {
		if (StringUtils.isBlank(serverName)) {
			return "";
		} else
			return serverName;
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public String getDbms() {
		if (StringUtils.isBlank(dbms)) {
			return "";
		} else
			return dbms;
	}
	
	public void setDbms(String dbms) {
		this.dbms = dbms;
	}
	
	public String getPortNumber() {
		if (StringUtils.isBlank(portNumber)) {
			return "";
		} else
			return portNumber;
	}
	
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}
	
	public boolean fieldsExistInSchema() {
		return fieldsExistInSchema;
	}
	
	public void setFieldsExistInSchema(boolean fieldsExistInSchema) {
		this.fieldsExistInSchema = fieldsExistInSchema;
	}
}
