<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/fabflix/style.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix | Stars</title>
</head>
<body>
<div id="logout"><a href="/fabflix/invalidate">Log out</a></div>
<div id="pageHeader"><a href="/fabflix/main">FabFlix</a></div>

<div class="container">

		
		<div id="checkout"><a href="/fabflix/cart.jsp">Checkout</a></div>

		<table style="position:relative; top:40px">
			<tr>
				<th rowspan=5 width=250px><img src=${photo} style="height:250px"></img></th>
			</tr>
			
			<tr style="height:25px">
				<td>Name: </td>
				<td><a href="http://www.google.com">${first_name} ${last_name}</a></td>
			</tr>
			<tr style="height:25px">
				<td>ID:  </td>
				<td>${sid}</td>
			</tr>
			<tr style="height:25px">
				<td>Born: </td>
				<td>${dob}</td>
			</tr>
			<tr style="height:25px">
				<td>Movies: </td>
				<td>
				<%
					out.print((String)request.getAttribute("movies"));
				%>
				 </td>
			</tr>
		
		</table>
	
</div>


</body>
</html>