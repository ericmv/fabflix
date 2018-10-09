<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/fabflix/style.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script>
function updateCart(id) {
	var count = document.getElementById(id).value;
	window.location = "/fabflix/Checkout?id=" + id + "&count=" + count+"&function=update";
	
}
function removeItem(id) {
	window.location ="/fabflix/Checkout?id=" + id + "&function=remove";
}
function customerInfo() {
	window.location = "/fabflix/info.jsp";
}

</script>
<title>Fabflix | Cart</title>
</head>
<body>

<div id="pageHeader"><a href="/fabflix/main">FabFlix</a></div>
<div id="logout"><a href="/fabflix/invalidate">Log out</a></div>

<div class="container">
<h2>Shopping Cart:</h2>
<hr>
<%@ page import="java.util.*" %>
<%@ page import="fabflix.Pair" %>
<%@ page import="java.sql.*" %>
<%@ page import ="javax.naming.Context" %>
<%@ page import ="javax.naming.NamingException" %>
<%@ page import ="javax.naming.InitialContext" %>
<%@ page import ="javax.sql.DataSource" %>
<table>
	<tr>
		<th width=300px style="align:left">Movie Title</th>
		<th>Price</th>
		<th>Qty</th>
	</tr>
<%
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

String query = "SELECT m.title FROM movies m WHERE m.id = ?;";
PreparedStatement atc = connection.prepareStatement(query);
Statement select = connection.createStatement();
@SuppressWarnings("unchecked")
ArrayList<Pair<String, Integer>> cart = (ArrayList<Pair<String,Integer>>)session.getAttribute("cart");

int count = 0;
for (int i = 0; i < cart.size(); i++) {
	try {
		String id = cart.get(i).getFirst();
		atc.setString(1, id);
		ResultSet result = atc.executeQuery();
		result.next();
		String title = result.getString(1);
		int quantity = cart.get(i).getSecond();
		out.print("<tr><td>" + title + " (Qty: " + quantity + ")</td><td>$11.99</td><td><input id = \"" + id + "\" type=\"text\" name=movie ></input></td><td><button type=\"button\" onclick = \"updateCart('" + cart.get(i).getFirst() + "')\">Update</button></td><td><button type=\"button\" onclick=\"removeItem('" + cart.get(i).getFirst()+ "')\">Remove</button></td>");
		count += cart.get(i).getSecond();
	}
	catch (SQLException e) {
		out.println("Could not obtain movies in cart");
	}

}
double totalPrice = count * 11.99;
session.setAttribute("totalPrice", totalPrice);
connection.close();

%>
</table>
<hr>
  <center><input type="submit" value="Check out" onclick="customerInfo()"><p>(<%=totalPrice%>)</center>

</div>

</body>
</html>