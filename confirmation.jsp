<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/fabflix/style.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix | Payment Confirmation</title>
</head>
<body>
<div id="pageHeader"><a href="/fabflix/main">FabFlix</a></div>
<div id="logout"><a href="/fabflix/invalidate">Log out</a></div>

<div class="container">
<h1>Checkout Successful!</h1>
<h4>Thank you for your purchase ${first_name } ${last_name }! Below is your order summary.</h4>
<h3><u>Order Summary</u></h3>
<table>
<tr>
	<th>Item</th>
	<th>Qty</th>
	<th>Price</th>
</tr>
<%@ page import="fabflix.Pair" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import ="javax.naming.Context" %>
<%@ page import ="javax.naming.NamingException" %>
<%@ page import ="javax.naming.InitialContext" %>
<%@ page import ="javax.sql.DataSource" %>

<% 


String query = "SELECT m.title FROM movies m WHERE m.id = ?;";

PreparedStatement movies=null;
Context initCtx;
Connection connection = null;
try {
	initCtx = new InitialContext();
	Context envCtx = (Context) initCtx.lookup("java:comp/env");
	DataSource ds = (DataSource) envCtx.lookup("jdbc/fabflix");
	try {
		connection = ds.getConnection();
		movies = connection.prepareStatement(query);

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("hsdjfbsdskjfhsdj");
		e.printStackTrace();
	}
} catch (NamingException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

@SuppressWarnings("unchecked")
ArrayList<Pair<String, Integer>> cart = (ArrayList<Pair<String,Integer>>)session.getAttribute("cart");
for (int i = 0; i < cart.size(); i++) {
	try {
		String id = cart.get(i).getFirst();
		Statement select = connection.createStatement();
		movies.setString(1, cart.get(i).getFirst());
		ResultSet result = movies.executeQuery();
		result.next();
		String title = result.getString(1);
		int quantity = cart.get(i).getSecond();
		
		out.print("<tr><td>" + title + "</td><td>" + quantity + "</td><td>$11.99</td></tr>");
	
	}
	catch (SQLException e) {
		out.println("Could not obtain movies in cart");
	}
}

connection.close();


%>
</table>
<hr>
<h2>Total Price: $${price}</h2>
<h2><a href="/fabflix/main">Return To Main</a></h2>




</div>
</body>
</html>