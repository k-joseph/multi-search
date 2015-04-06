<%@page import="java.io.File"%>

<%
	//Redirect to installer page if not yet installed
	File readMe = new File(System.getProperty("user.home") + File.separator + ".multi-search" + File.separator
	        + "multisearch.properties");
	if (null != readMe && !readMe.exists()) {
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", "installer");
	}
%>