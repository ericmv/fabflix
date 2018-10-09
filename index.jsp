<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fabflix | Login</title>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
<form action ="/fabflix/FabflixLogin" method = "post">

<h1><a href="/fabflix/main">Fabflix Home</a></h1>
<div style="color: #FF0000;">${errorMessage}</div>
<p>Username:</p><input type = "text" name = "username">
<br>
<p>Password:</p><input type = "password" name = "password">
<br>
<div class="g-recaptcha" data-sitekey="6LfqwSAUAAAAANkHM-0CBsEr3IuHuaNfh9UEw1xL"></div>
<input type="submit">

</form>
</body>
</html>