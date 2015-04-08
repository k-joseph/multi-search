<%@ include file="header.jsp"%>
<title><spring:message code="multisearch.index.title"></spring:message>
	Installation</title>
</head>
<body>
	<center>
		<h2>INSTALLING Multi-Search Web Application ...</h2>
		<form action="/installer" method="post">
			<input name="databaseName" class="db_name" type="hidden"
				value="multisearch"> </input><br /> <input name="databaseUser"
				class="db_user" placeholder="Database User" type="text"> </input><br />
			<input name="databaseUserPassword" class="db_password"
				placeholder="Database Password" type="password"></input><br /> <input
				type="submit"></input>

		</form>
	<%@ include file="footer.jsp"%>