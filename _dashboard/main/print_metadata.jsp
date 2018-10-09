<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix | Metadata</title>
</head>
<body>
<h1>Fabflix Metadata</h1>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import ="javax.naming.Context" %>
<%@ page import ="javax.naming.NamingException" %>
<%@ page import ="javax.naming.InitialContext" %>
<%@ page import ="javax.sql.DataSource" %>


<%

/* PrintWriter out = response.getWriter();
 */

response.setContentType("text/html");
//String title = "Logging In";


Context initCtx;
Connection connection = null;
try {
	initCtx = new InitialContext();
	Context envCtx = (Context) initCtx.lookup("java:comp/env");
	DataSource ds = (DataSource) envCtx.lookup("jdbc/fabflix");
	try {
		connection = ds.getConnection();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("hsdjfbsdskjfhsdj");
		e.printStackTrace();
	}
} catch (NamingException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}


DatabaseMetaData metadata;
try {
	metadata = connection.getMetaData();
	ResultSet tables = metadata.getTables(null, null, null, null);
	while (tables.next()) {
		%>
		<h2>Table Name: <%=tables.getString(3) %></h2>
		<% 
		ResultSet r = metadata.getColumns(null, null, tables.getString(3), null);
		while (r.next()) {
			%>
			<p><%=r.getString(4) + ": " + r.getString(6)%></p>
			<%
		}
	}
	connection.close();

	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}




%>
</body>
</html>