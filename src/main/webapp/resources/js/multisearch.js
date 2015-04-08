var jq = jQuery;

jq(document).ready(function() {
    jq("#non-multisearch-db").click(function() {
        if (true == jq(this).prop("checked")) jq("#non-multisearch-db-name").show("slow"); else if (false == jq(this).prop("checked")) jq("#non-multisearch-db-name").hide("fast");
    });
    jq("#index-project-data").click(function(a) {
        var b = "";
        var c = jq(".project_name").val();
        var d = jq(".mysql_query").val();
        var e = jq(".column_names").val();
        if (!c) b += "Project Name is Required<br />";
        if (!d) b += "Database Query is Required<br />";
        if (!e) b += "Column Names are Required<br />";
        if (!c || !d || !e) {
            if (true == jq("#non-multisearch-db").prop("checked")) if (!jq(".db_name").val() || !jq(".db_user").val() || !jq(".db_password").val() || !jq(".db_server").val() || !jq(".db_manager").val() || !jq(".db_port").val()) b += "All Fields for Non MultiSearch Database are Required<br />";
            jq("#enter-required-fields").html(b + "<br />");
            b = "";
            jq("html, body").animate({
                scrollTop: 0
            }, "slow");
        } else submitNewProjectIndexingFormWithAjax();
        return false;
    });
    jq("#new-indexer-span").click(function() {
        if (jq("#new-indexer-span").hasClass("ui-icon-circle-arrow-s")) hideIndexingSection1(); else if (jq("#new-indexer-span").hasClass("ui-icon-circle-arrow-e")) expandIndexingSection1();
    });
    jq("#existing-indexer-span").click(function() {
        if (jq("#existing-indexer-span").hasClass("ui-icon-circle-arrow-s")) hideIndexingSection2(); else if (jq("#existing-indexer-span").hasClass("ui-icon-circle-arrow-e")) expandIndexingSection2();
    });
    jq("#installed-search-projects").change(function() {
        fetchDetailsOfSelectSearchProject();
    });
    jq("#update-index-project-data").click(function(a) {
        submitExistingSearchProjectUpdate();
        return false;
    });
});

function hideIndexingSection2() {
    jq("#existing-indexer-span").addClass("ui-icon-circle-arrow-e");
    jq("#existing-indexer-span").removeClass("ui-icon-circle-arrow-s");
    jq(".customIndexerSubSection2").hide("fast");
}

function expandIndexingSection2() {
    jq("#existing-indexer-span").removeClass("ui-icon-circle-arrow-e");
    jq("#existing-indexer-span").addClass("ui-icon-circle-arrow-s");
    jq(".customIndexerSubSection2").show("fast");
    hideIndexingSection1();
}

function hideIndexingSection1() {
    jq("#new-indexer-span").addClass("ui-icon-circle-arrow-e");
    jq("#new-indexer-span").removeClass("ui-icon-circle-arrow-s");
    jq(".customIndexerSubSection1").hide("fast");
}

function expandIndexingSection1() {
    jq("#new-indexer-span").addClass("ui-icon-circle-arrow-s");
    jq("#new-indexer-span").removeClass("ui-icon-circle-arrow-e");
    jq(".customIndexerSubSection1").show("fast");
    hideIndexingSection2();
}

function submitNewProjectIndexingFormWithAjax() {
    jq("#enter-required-fields").html('<img src="<c:url value="/resources/images/loading.gif"/>">');
    jq("#index-new-project-data :input").prop("disabled", true);
    jq.ajax({
        type: "POST",
        url: "${ ui.actionLink('indexDataForANewProject') }",
        data: jq("#index-new-project-data").serialize(),
        dataType: "json",
        success: function(a) {
            jq("#index-new-project-data :input").prop("disabled", false);
            if (!a.failureMessage) {
                alert("Saving the project took: " + a.savingTime + " seconds*****Indexing project data took: " + a.indexingTime + " seconds*****Indexed: " + a.numberOfIndexedDocs + " Documents*****Project assigned UUID is: " + a.projectUuid);
                jq("#index-new-project-data").trigger("reset");
                hideIndexingSection1();
                expandIndexingSection2();
            } else alert(a.failureMessage);
        },
        error: function(a) {}
    });
}

function displaySearchProjectDetailsFromServer(searchProject) {
    var b = "";
    if (searchProject) {
        b += "<p>";
        b += '<b>Name:</b> <input name="projectName" class="project_name" type="text" value="' + searchProject.projectName + '"></input><br />';
        b += '<b>Description:</b> <input name="projectDesc" type="text" value="' + searchProject.projectDescription + '"></input><br />';
        b += '<b>Database Query:</b> <textarea name="databaseQuery" class="mysql_query">' + searchProject.projectDatabaseQuery + "</textarea><br />";
        b += "<b>Solr Field/Column Names:</b> " + searchProject.projectSolrFields + "<br />";
        b += "</p>";
        if ("multisearch" != searchProject.projectDB) {
            b += "<p>";
            b += '<input name="databaseName" class="db_name" type="text" value="' + searchProject.projectDB + '"></input><br />';
            b += '<input name="databaseUser" class="db_user" type="text" value="' + searchProject.projectDBUser + ' "></input><br />';
            b += '<input name="databaseUserPassword" class="db_password" type="password" value="' + searchProject.projectDBUserPassword + '"></input><br />';
            b += '<input name="databaseServer" class="db_server" type="text" value="' + searchProject.projectServerName + '"></input><br />';
            b += '<input name="databaseManager" class="db_manager" type="text" value="' + searchProject.projectDBManager + '"></input><br />';
            b += ' <input name="databasePortNumber" class="db_port" type="text" value="' + searchProject.projectDBPortNumber + '"></input><br />';
            b += "</p>";
        }
        jq("#update-index-project-data").show();
    }
    jq("#updating-installed-search-projects").html(b);
}

function fetchDetailsOfSelectSearchProject() {
    var a = jq("#installed-search-projects").val();
    if (a) jq.ajax({
        type: "POST",
        url: "${ ui.actionLink('fetchDetailsOfASelectedSearchProject') }",
        data: {
            selectedSearchProject: a
        },
        dataType: "json",
        success: function(a) {
            if (a) displaySearchProjectDetailsFromServer(a);
        },
        error: function(a) {}
    }); else {
        jq("#updating-installed-search-projects").html("");
        jq("#update-index-project-data").hide();
    }
}

function uploadSQLFileToServer() {
    jq.ajax({
        type: "POST",
        url: "/uploadSQLFile",
        data: jq("#sql-file-upload-form").serialize(),
        dataType: "json",
        success: function(a) {
            jq("#sql-upload-feedback").html(a);
        },
        error: function(a) {}
    });
}

function submitExistingSearchProjectUpdate() {
    alert("Saving update will soon be supported! WORK IN PROGRESS :-)");
}