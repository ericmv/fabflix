The given war file contains multiple servlets:
	FabflixLogin: Handles logging in and creates first user session
	Invalidate: Handles logout
	page: Handles single movie and single star queries
	Browse: Handles searching and browsing by genre, title, years, etc.
	Checkout: Handles updating cart with quantities
	payment: Handles creditcard checking and processing of payments
	
The following classes were created for the project:
	Pair: Class that creates tuple of two elements
	
Deploy the .war file onto tomcat and access it through public_ip:8080/fabflix/main