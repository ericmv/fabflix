<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/fabflix/style.css">
<script type="text/javascript">




function autoSearch(params) {
	var ajaxRequest;  // The variable that makes Ajax possible!

	try{
		// Opera 8.0+, Firefox, Safari
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}
	
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4){
			document.getElementById('test').innerHTML = this.responseText;
		}
	}
	
	
	ajaxRequest.open("GET", "/fabflix/autosearch?title=" + params, true);
	ajaxRequest.send();
	
	
	
}
function addToCart(id) {
	window.location = "/fabflix/page?type=movie&id=" + id +"&cart=true";
}
function display(mid) {
	var ajaxRequest;  // The variable that makes Ajax possible!

	try{
		// Opera 8.0+, Firefox, Safari
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}
	
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4){
		
 			document.getElementById(mid).style.display = "inline";
 			document.getElementById(mid).innerHTML = this.responseText;
		}
	}
	
	
	ajaxRequest.open("GET", "/fabflix/hover?id=" + mid, true);
	ajaxRequest.send();
}



function toggleDisplay(mid) {
	if (document.getElementById(mid).style.display == "inline") {
/* 		document.getElementById(mid).innerHTML = "<br>";
 */
		document.getElementById(mid).style.display = "none";
	}
	
}
function onDisplay(mid) {
	document.getElementById(mid).style.display = "inline";
}
function displayHover(mid) {
/* 	alert(document.getElementById(mid).style.display)
 */
 	if (document.getElementById(mid).style.display=="none") {
 		document.getElementById(mid).style.display = "inline";
 	}
 	var ajaxRequest;  // The variable that makes Ajax possible!

	try{
		// Opera 8.0+, Firefox, Safari
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}
	
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4){
			document.getElementById(mid).innerHTML = this.responseText;
		}
	}
	
	
	ajaxRequest.open("GET", "/fabflix/hover?id=" + mid, true);
	ajaxRequest.send();
	
}
function action(action, param, page) {
	if (action == "genre") {
		window.location = "/fabflix/Browse?action="+action+"&genre="+param+"&sort=title"+"&show=10" + "&order=asc"+"&pagenum="+page;
		
	}
	if (action=="letter") {
		if (param == "#") {
			param = "num";
		}
		window.location.href = "/fabflix/Browse?action="+action+"&startsWith="+param+"&sort=title"+"&order=asc"+"&show=10"+"&pagenum="+page;
	}
	
}

function displayBar() {
	var alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var bar = document.getElementById("letterBar");
	for (var i = 0; i < alphabet.length; i++) {
		bar.innerHTML += "<a href=\"#\" onclick=\"action('letter', this.innerHTML, '1')\">" + alphabet[i] + "</a>";
		bar.innerHTML += " | ";
	}
}
</script>
<title>Fabflix | Main</title>
</head>

<body onload="displayBar()">
<%
session = request.getSession();
String heading;
if (session == null) {
	request.getRequestDispatcher("/index.jsp").forward(request, response);

} else {
    String user = (String) session.getAttribute("user");

	if (user == null) {
		request.setAttribute("user", user);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	else {
	}


}
%>
<%-- <jsp:include page="/session" />
 --%>
 
<div id="pageHeader"><a href="/fabflix/main">FabFlix</a></div>
<div id="logout"><a href="/fabflix/invalidate">Log out</a></div>
<form name='search'>

<div id="searchBar">Search<input type="text" onkeydown="autoSearch(this.value);"></input><div id="test"></div>
</div>
</form>
<div><a href="/fabflix/search">Advanced Search</a></div>


<div class="bodyContainer">


	<p style="padding:10px 5px 0px 5px; text-align: center; font-size: 18px">Browse Movies By Title</p>
	<hr>	

	<div id="letterBar"></div>
	<!-- <p style="text-align: center"># | A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | W | X | Y | Z</p> -->
	<hr>

	<p style="padding:10px 5px 0px 5px; text-align: center; font-size: 18px">Browse Movies By Genre</p>

	<a href="#" onclick="action('genre', this.innerHTML, 1)" class="grid">Action</a>
	<a href="#" onclick="action('genre', this.innerHTML, 1)" class="grid">Adventure</a>
	<a href="#" onclick="action('genre', this.innerHTML, 1)" class="grid">Comedy</a>
	<a href="#" onclick="action('genre', this.innerHTML, 1)" class="grid">Drama</a>

	<br></br><br></br>
	<a href="#" onclick="action('genre', this.innerHTML, 1)" class="grid">Film-noir</a>
	<a href="#" onclick="action('genre', this.innerHTML, 1)" class="grid">Horror</a>
	<a href="#" onclick="action('genre', this.innerHTML, 1)" class="grid">Romance</a>
	<a href="#" onclick="action('genre', this.innerHTML, 1)" class="grid">Sci-Fi</a>


</div>
</body>
</html>