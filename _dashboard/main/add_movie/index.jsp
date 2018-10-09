<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix | Add Movie</title>
</head>
<body>
<form action="/fabflix/add_movie" method="get">
<h2>Add Movie to Fabflix Database</h4>

<h3>Movie Info</h3>
<h4>Movie Title<input type="text" name="title"></input></h4>
<h4>Year<input type="text" name="year"></input></h4>
<h4>Director<input type="text" name="director"></input></h4>

<br>

<h3>Star</h3>
<h4>Star First Name<input type="text" name="first_name"></input></h4>
<h4>Star Last Name<input type="text" name="last_name"></input></h4>


<br>

<h3>Genre</h3>
<h4>Genre<input type="text" name="genre"></input></h4>

<input type="submit">


</form>
</body>
</html>