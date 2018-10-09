<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/fabflix/style.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix | Payment Info</title>
</head>
<body>
<div id="logout"><a href="/fabflix/invalidate">Log out</a></div>

<div id="pageHeader"><a href="/fabflix/main">FabFlix</a></div>

<div class="container">
	<div style="color: #FF0000;">${errorMessage}</div>
	
	<form action = "/fabflix/payment" method="post">
	First Name <input type="text" name="first_name"></input> <br>
	Last Name <input type="text" name="last_name"></input> <br>
	Credit Card Number <input type="text" name="cardnum"> <br>
	Expiration Date <input type="text" name="exp"></input> <br>
	
	<input type="submit">
	</form>
</div>
</body>
</html>