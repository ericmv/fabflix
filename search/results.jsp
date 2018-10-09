<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/fabflix/style.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script type="text/javascript" src="jquery.js"></script>
<script>
function goto(page) {
	//alert( type="text/javascript");
	window.location.search = $.query.set("pagenum", page);
	//var search = window.location.search;
	//document.getElementById("search").innerHTML = "<p>"+search+"</p>";
}
function show(amount) {
	window.location.search = $.query.set("show", amount).set("pagenum", "1");
}
function sort(type, order) {
	window.location.search = $.query.set("sort", type).set("order", order);
}
function addToCart(id) {
	window.location = "/fabflix/page?type=movie&id=" + id +"&cart=true";
}
</script>
<title>Fabflix | Search Results</title>
</head>
<body>
<div id="pageHeader"><a href="/fabflix/main">FabFlix</a></div>
<div id="logout"><a href="/fabflix/invalidate">Log out</a></div>
<div class="list">
<div id="checkout"><a href="/fabflix/cart.jsp">Checkout</a></div>

<div class="dropdown">
  <button class="dropbtn">Show</button>
  <div class="dropdown-content">
  	<a href="javascript:show(10)">Show 10</a>
    <a href="javascript:show(25)">Show 25</a>
    <a href="javascript:show(50)">Show 50</a>
    <a href="javascript:show(100)">Show 100</a>
  </div>
</div>
<div class="dropdown">
  <button class="dropbtn">Sort by</button>
  <div class="dropdown-content">
    <a href="javascript:sort('title', 'asc')">Title (A-Z)</a>
    <a href="javascript:sort('title', 'desc')">Title (Z-A)</a>
    <a href="javascript:sort('year', 'asc')">Year (Oldest-Newest)</a>
    <a href="javascript:sort('year', 'desc')">Year (Newest-Oldest)</a>
  </div>
</div>

<% 


String code = (String) request.getAttribute("movieResults");




out.println(code);
String url = (String) request.getAttribute("url");
//out.print(url);
//out.print("?");
String path = (String) request.getAttribute("path");
//out.println(path);

int pagenum = (int) request.getAttribute("page");
double maxPage = (double) request.getAttribute("max");
/* int prev = (int) request.getAttribute("prevPage");
int next = (int) request.getAttribute("nextPage"); */
String link = url + "?" + path;
















int indexOfPageNum = link.indexOf("pagenum=");






if (pagenum > 1) {
	int prevPage = pagenum - 1;
	//String prevUrl = link.substring(0, indexOfPageNum) + "pagenum=" + prevPage;
	
	out.print("<a href=\"javascript:goto('" +prevPage + "')\">Prev</a>    ");

}
if (pagenum < maxPage) {
	int nextPage = pagenum + 1;
	//String nextUrl = link.substring(0, indexOfPageNum) + "pagenum=" + nextPage;
	out.print("<a href=\"javascript:goto('" +nextPage + "')\">Next</a>");
	//out.print("<a href=\"" + nextUrl +"\">Next</a>");

}

%>
</div>
</body>
</html>