<div class="customIndexerSection">
	<p class="sub-p-header">
		<span class="ui-icon ui-icon-circle-arrow-s" id="new-indexer-span"></span>
		<label id="new-indexer-label">Index data from a New Search
			Project</label>
	</p>
	<br />
	<div class="customIndexerSubSection1">
		<div id="enter-required-fields"></div>
		<form id="index-new-project-data">
			<p>
				<input name="projectName" class="project_name"
					placeholder="Unique Project Name eg: 'Chart Search'" type="text"></input><br />
				<input name="projectDesc"
					placeholder="Project Description eg: 'openmrs search tool'"
					type="text"></input><br />
				<textarea name="databaseQuery" class="mysql_query"
					placeholder="Valid Database Query; use AS to unify columns, eg: 'SELECT cs.n AS cs_name, cs.f_q AS cs_filter_query, cs.dsc AS  cs_description FROM omrs.cs_cats cs'"></textarea>
				<br />
				<textarea name="columns" class="column_names"
					placeholder="Unique Comma separated column names or solrFields, should match with those in query above eg. 'cs_name, cs_description, cs_filter_query'; NOT EDITABLE AFTER SAVING"></textarea>
				<br />
			</p>
			<p>
				<input id="non-multisearch-db" type="checkbox"></input> <label>Non
					Multi-Search database</label>
			</p>
			<p id="non-multisearch-db-name">
				<input name="databaseName" class="db_name"
					placeholder="Database Name eg: multisearch" type="text"></input><br />
				<input name="databaseUser" class="db_user"
					placeholder="Database user name eg: root" type="text"></input><br />
				<input name="databaseUserPassword" class="db_password"
					placeholder="Database password eg: test" type="password"></input><br />
				<input name="databaseServer" class="db_server"
					placeholder="Database Server eg: localhost or 127.0.0.1"
					type="text"></input><br /> <input name="databaseManager"
					class="db_manager" placeholder="Database Manager eg: mysql"
					type="text"></input><br /> <input name="databasePortNumber"
					class="db_port" placeholder="Database port number eg: 3306"
					type="text"></input><br />
			</p>
			<input id="index-project-data" type="submit"
				value="Save & Index Project Data"></input>
		</form>
	</div>
	<br />
	<p class="sub-p-header">
		<span class="ui-icon ui-icon-circle-arrow-e"
			id="existing-indexer-span"></span><label id="existing-indexer-label">Update
			previously Added Project's index</label>
	</p>
	<br />
	<div class="customIndexerSubSection2">
		<p>
			<select id="installed-search-projects">
				<option value="" name="selectedProject">Choose Search
					Project</option>
				<!-- TODO Display all projectts and get their details here -->
				<option class="installed-selected-search-projects"></option>
			</select>
		</p>
		<p>
			<br /> <b>NOTICE:</b> <br />Currently, you have no way of modifying
			columns names<br /> <br />
		</p>
		<form id="update-project-data-index">
			<div id="updating-installed-search-projects"></div>
			<input id="update-index-project-data" type="submit"
				value="Save And Update Index"></input>
		</form>
	</div>
</div>