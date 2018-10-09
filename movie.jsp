<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/fabflix/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript">
function addToCart() {
	window.location.search = $.query.set("cart", "true");
}
function reset() {
	if ($.query.get("cart") == "true") {
		window.location.search = $.query.set("cart", "false");

	}
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix | Movies</title>
</head>
<body>

<div id="pageHeader"><a href="/fabflix/main">FabFlix</a></div>
<div id="logout"><a href="/fabflix/invalidate">Log out</a></div>
<div class="container">
	
		<div id="checkout"><a href="/fabflix/cart.jsp">Checkout (${cartCount})</a></div>
		<p style="color:#32cd32; position:relative; left:57px; top:20px">${message}</p>
		<table style="position:relative; top:40px">
			<tr>
				<th rowspan=9 width=300px>
					<img src=${banner} style="height:250px"></img>
				</th>
			
			</tr>
			
			<tr style="height:25px">
				<td>Title: </td>
				<td><a href="">${title}</a></td>
			</tr>
			<tr style="height:25px">
				<td>Year: </td>
				<td>${year}</td>
			</tr>
			<tr style="height:25px">
				<td>Director: </td>
				<td>${director}</td>
			</tr>
			<tr style="height:25px">
				<td>ID: </td>
				<td>${id}</td>
			</tr>
			<tr style="height:25px">
				<td>Stars: </td>
				<td>
				<%
					out.print((String)request.getAttribute("stars"));
				%>
				 </td>
			</tr>
			<tr style="height:25px">
				<td>Genres: </td>
				<td>
				<%@ page import="java.util.*" %>

				<%
					@SuppressWarnings("unchecked")

					ArrayList<String> genres = (ArrayList<String>)request.getAttribute("genres");
					for (int i = 0; i < genres.size(); i++) {
						out.print("<a href=\"/fabflix/Browse?action=genre&sort=title&show=10&order=asc&pagenum=1&genre="  + genres.get(i) + "\">" + genres.get(i) + "</a>, ");
					}
				
				
				%>

				
				</td>
			</tr>
			<tr style="height:25px">
				<td>Trailer URL: </td>
				<td>
				<a href="${trailer}">${trailer}</a>
				 </td>
			</tr>
			<tr style="height:25px">
				<td>Price: </td>
				<td>$11.99</td>
			</tr>
			<tr>
				<td>
				<%
					String id = (String) request.getAttribute("id");
				
					String query = (String) request.getAttribute("path");
					String url = (String) request.getAttribute("url");
					String link = url + "?" + query;
					out.println("<center><button type=\"button\" onclick=\"addToCart()\">Add To Cart</button></center>");
				
				%>
				</td>
			</tr>
			

		</table>
		<%@ page import="fabflix.Pair" %>
		<%
		
				@SuppressWarnings("unchecked")

	 			ArrayList<Pair<String, Integer>> cart = (ArrayList<Pair<String, Integer>>) session.getAttribute("cart");
				int size = (int) request.getAttribute("cartCount");
			
		%>

</div>






</body>
</html>