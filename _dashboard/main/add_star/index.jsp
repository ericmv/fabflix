<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fabflix | Employee Main</title>

</head>
<body>
<form action ="/fabflix/add_star" method = "post">
<h4>Add Star to Fabflix Database</h4>
<h4>Star First Name<input type="text" name="first_name"></input></h4>
<h4>Star Last Name<input type="text" name="last_name"></input></h4>

<h4>DOB<input type="text" name="dob"></input></h4>
<h4>Photo URL<input type="text" name="url"></input></h4>

<input type="submit">
</form>
</body>
</html>