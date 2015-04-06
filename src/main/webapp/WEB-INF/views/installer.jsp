<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="multisearch.index.title"></spring:message>
	Installation</title>
</head>
<body>
	<center>
		<h2>INSTALLING Multi-Search Web Application ...</h2>
		<form action="/multisearch/installer" method="post">
			<input name="databaseName" class="db_name" type="hidden" value="multisearch"> </input>
			<input name="databaseUser" class="db_user"
				placeholder="Database User" type="text"> </input> <input
				name="databaseUserPassword" class="db_password"
				placeholder="Database Password" type="password"></input> <input
				type="submit"></input>

		</form>
	</center>
</body>
</html>