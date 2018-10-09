

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.*;
import java.util.*;



/**
 * Servlet implementation class Browse
 */
@WebServlet("/Browse")
public class Browse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Browse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
//		
		File log = new File("./log.txt");
//		File log = new File("/home/ubuntu/tomcat/webapps/fabflix/log.txt");
		PrintWriter writer = new PrintWriter(new FileWriter(log, true));
		Context initCtx = null;
		Context envCtx = null;
		Connection connection = null;
		try {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
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
//		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//String title = "Logging In";
		
		
//		PreparedStatement browseGenre = null;
//		PreparedStatement browseLetter = null;
		PreparedStatement search = null;
		PreparedStatement psSearchOffset = null;
		
//		String genreQuery = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url FROM movies m, genres g, genres_in_movies gm "
//				+ "WHERE m.id = gm.movie_id AND g.id ="
//				+ "gm.genre_id AND"
//				+ " g.name = ?";
//		
//		String letterQuery = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url FROM movies m WHERE m.title LIKE ?";
//		
//		String numberQuery = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url FROM movies m WHERE m.title "
//				+ "REGEXP ?";
		
		String searchQuery = "SELECT m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url FROM movies m";
				
		
		ArrayList<String> parameters = new ArrayList<String>();
		int paramCount = 1;
		
		if (request.getParameter("action").equals("genre")) {
			//select = connection.createStatement();
			
			searchQuery += ", genres g, genres_in_movies gm "
					+ "WHERE m.id = gm.movie_id AND g.id ="
					+ "gm.genre_id AND";
			searchQuery += " g.name = ?";
			parameters.add(request.getParameter("genre"));
			paramCount++;
		}
		
		if (request.getParameter("action").equals("letter")) {
			//select = connection.createStatement();
			if (request.getParameter("startsWith").equals("num")) {
				
				searchQuery += " WHERE m.title REGEXP ?";
				parameters.add("^[0-9]");
				paramCount++;
				
			}
			else {
				
				
				searchQuery += " WHERE m.title LIKE ?";
				parameters.add(request.getParameter("startsWith") + "%");
				paramCount++;
				
			}
			
		}
		long ts = System.nanoTime();
//		long current_ts;
		long tj;
//		long current_tj;
		if (request.getParameter("action").equals("search")) {
			


			//	select=connection.createStatement();
				
				if (request.getParameter("title") != null && !request.getParameter("title").isEmpty()) {
					String title = request.getParameter("title");
					String[] arr = title.split(" \n");
					ArrayList<String> words = new ArrayList<String>();
					Collections.addAll(words, arr);
					words.trimToSize();
					
					
					
					searchQuery += " INNER JOIN(";
					searchQuery += "SELECT mov.id, mov.title, mov.year, mov.director, mov.banner_url, mov.trailer_url "
							+ "FROM movies mov WHERE (mov.title LIKE ?";
					parameters.add("%" + words.get(0) + "%");
					paramCount++;
					
					for (int i = 1; i < words.size(); i++) {
						searchQuery += " OR m.title LIKE ?";
						parameters.add("%" + words.get(i) + "%");
						paramCount++;
					}
					searchQuery += ")) a ON a.title = m.title";
				}
				if (request.getParameter("y1") != null && !request.getParameter("y1").isEmpty()){
				
					
					searchQuery += " INNER JOIN(";
					searchQuery += "SELECT mov.id, mov.title, mov.year, mov.director, mov.banner_url, mov.trailer_url FROM movies mov WHERE ? <= mov.year";
					searchQuery += ") b ON b.id = m.id";
					parameters.add(request.getParameter("y1"));
					paramCount++;
				}
				if (request.getParameter("y2") != null && !request.getParameter("y2").isEmpty()){
				
					searchQuery += " INNER JOIN(";
					searchQuery += "SELECT mov.id, mov.title, mov.year, mov.director, mov.banner_url, mov.trailer_url FROM movies mov WHERE ? >= mov.year";
					searchQuery += ") b ON b.id = m.id";
					parameters.add(request.getParameter("y2"));
					paramCount++;
				}
				if (request.getParameter("director") != null && !request.getParameter("director").isEmpty()) {
					
					searchQuery += " INNER JOIN(";
					searchQuery += "SELECT mov.id, mov.title, mov.year, mov.director, mov.banner_url, mov.trailer_url FROM movies mov WHERE mov.director LIKE ?";
					searchQuery += ") d ON d.id = m.id";
					parameters.add("%" + request.getParameter("director") + "%");
					paramCount++;
				}
				
				if (request.getParameter("genre") != null && !request.getParameter("genre").isEmpty()) {
					String[] array = request.getParameterValues("genre");
					ArrayList<String> genres = new ArrayList<String>();
					Collections.addAll(genres, array);
					genres.trimToSize();
					
					
					
					
					searchQuery += " INNER JOIN(";
					searchQuery += "SELECT mov.id, mov.title, mov.year, mov.director, mov.banner_url, mov.trailer_url FROM movies mov, genres g, genres_in_movies gm WHERE g.id = gm.genre_id AND mov.id = gm.movie_id";
					searchQuery += " AND (g.name = ?";
					parameters.add(genres.get(0));
					paramCount++;
					
					for (int i = 1; i < genres.size(); i++) {
						searchQuery += " OR g.name = ?";
						parameters.add(genres.get(i));
						paramCount++;
					}
					searchQuery += ")";
					searchQuery += ") e ON e.id = m.id";
					
					
				}
				if (request.getParameter("firstName") != null && !request.getParameter("firstName").isEmpty()) {
					
					
					searchQuery += " INNER JOIN(";
					searchQuery += "SELECT mov.id, mov.title, mov.year, mov.director, mov.banner_url, mov.trailer_url FROM movies mov, stars s, stars_in_movies sm WHERE s.id = sm.star_id AND mov.id = sm.movie_id AND s.first_name LIKE ?";
					searchQuery += ") f ON f.id = m.id";
					parameters.add("%" + request.getParameter("firstName") + "%");
					paramCount++;
				}
				if (request.getParameter("lastName") != null && !request.getParameter("lastName").isEmpty()) {
					
					searchQuery += " INNER JOIN(";
					searchQuery += "SELECT mov.id, mov.title, mov.year, mov.director, mov.banner_url, mov.trailer_url FROM movies mov, stars s, stars_in_movies sm WHERE s.id = sm.star_id AND mov.id = sm.movie_id AND s.last_name LIKE ?";
					searchQuery += ") f ON f.id = m.id";
					parameters.add("%" + request.getParameter("lastName") + "%");
					paramCount++;
				}
				//result = select.executeQuery(query);
		}
		try {
			if (request.getParameter("sort").equals("title")) {

				
				searchQuery += " ORDER BY m.title " + request.getParameter("order") + " ";
//				parameters.add(request.getParameter("order"));
//				paramCount++;


			}
			else if (request.getParameter("sort").equals("year")) {
				

				
				searchQuery += " ORDER BY m.year " + request.getParameter("order") + " ";

				

			}
			
			String searchQueryWithOffset = searchQuery;

			
			searchQuery += ";";
			tj = System.nanoTime();

			search = connection.prepareStatement(searchQuery);
			for (int i = 1; i < paramCount; i++) {
				search.setString(i, parameters.get(i-1));
			}
			
			ResultSet searchResult = search.executeQuery();
//			current_tj = System.nanoTime();
//			current_ts = System.nanoTime();
//			
//			current_ts = current_ts - ts;
//			current_tj = current_tj - tj;
			
//			writer.println(current_ts + ";" + current_tj);
//			
//			ts = System.nanoTime();
//			
			
			
			int size = 0;
			int perPage = Integer.parseInt(request.getParameter("show"));
			
			if (searchResult != null) {
				searchResult.last();
				size = searchResult.getRow();
			}
			

			
			searchResult.beforeFirst();
			search.close();

			double numberOfPages = Math.ceil((double) size/(double) perPage);
			int currentPage = Integer.parseInt(request.getParameter("pagenum"));
			int offset = (currentPage-1) * perPage;
			
			
			
			searchQueryWithOffset += "LIMIT ? OFFSET ? ;";
			parameters.add(Integer.toString(perPage));
			paramCount++;
			parameters.add(Integer.toString(offset));
			paramCount++;
			
			
			psSearchOffset = connection.prepareStatement(searchQueryWithOffset);
			for (int i = 1; i < paramCount; i++) {
				if (i > paramCount - 3) {
					psSearchOffset.setInt(i, Integer.parseInt(parameters.get(i - 1)));
				}
				else {
					psSearchOffset.setString(i, parameters.get(i - 1));

				}
			}
//			tj = System.nanoTime();
			ResultSet preparedPageResults = psSearchOffset.executeQuery(); 
//			current_tj = System.nanoTime();
//			current_ts = System.nanoTime();
			long tj_end = System.nanoTime();
			long tj_elapsed = tj_end - tj;
//		
//			
//			
//			current_ts = current_ts - ts;
//			current_tj = current_tj - tj;
//			writer.println(current_ts + ";" + current_tj);
			
			
			

			
			
			
			
			
			
			
			String newCode = "";
			preparedPageResults.beforeFirst();
			long tj_add = 0;
			//String code = "<table><tr><th>Title</th><th>ID</th><th>Director</th><th>Year></th></tr>";
			while (preparedPageResults.next()) {
				long tj_while = System.nanoTime();
//				ts = System.nanoTime();
//				tj = System.nanoTime();
				PreparedStatement psStarsQuery = null;
				String preparedStarsQuery = "SELECT DISTINCT s.id, s.first_name, s.last_name FROM stars s, movies m, stars_in_movies sm WHERE s.id = sm.star_id AND "
						+  "? = sm.movie_id;";
				psStarsQuery = connection.prepareStatement(preparedStarsQuery);

				
				psStarsQuery.setString(1, preparedPageResults.getString(1));
				ResultSet preparedStars = psStarsQuery.executeQuery();
//				current_tj = System.nanoTime();
				
				
				
				PreparedStatement psGenresQuery = null;
				String preparedGenresQuery = "SELECT DISTINCT g.name FROM genres g, movies m, genres_in_movies gm WHERE"
						+ " g.id = gm.genre_id AND ? =gm.movie_id;";
				psGenresQuery = connection.prepareStatement(preparedGenresQuery);
				

				
				psGenresQuery.setString(1, preparedPageResults.getString(1));
				ResultSet preparedGenres = psGenresQuery.executeQuery();
				long tj_while_end = System.nanoTime();
				tj_add = tj_while_end - tj_while;
				tj_elapsed += tj_add;
				newCode += "<table style=\"position:relative; top:40px\"><tr><th rowspan=8 width=300px>"
						+ "<img src=\"" + preparedPageResults.getString(5) + "\" style=\"height:200px\"></img>"
								+ "</th>"
								+ "</tr>"
								+ "<tr style=\"height:25px\">"
								+ "<td>Title: </td>"
								+ "<td><a href=\"/fabflix/page?type=movie&id=" + preparedPageResults.getString(1) + "&cart=false\">" + preparedPageResults.getString(2) + "</a></td>"
										+ "</tr>"
										+ "<tr style=\"height:25px\">"
										+ "<td>Year: </td>"
										+ "<td>" + preparedPageResults.getString(3) + "</td>"
												+ "</tr>"
												+ "<tr style=\"height:25px\">"
												+ "<td>Director: </td>"
												+ "<td>" + preparedPageResults.getString(4) + "</td>"
														+ "</tr>"
														+ "<tr style=\"height:25px\">"
														+ "<td>ID: </td>"
														+ "<td>" + preparedPageResults.getString(1) + "</td>"
																+ "</tr>"
																+ "<tr style=\"height:25px\"><td>Stars: </td><td>";
				
				while (preparedStars.next()) {
					newCode += "<a href=\"/fabflix/page?type=star&id=" + preparedStars.getString(1) + "\">" + preparedStars.getString(2) + " " + preparedStars.getString(3)+ "</a>, ";
				}
				psStarsQuery.close();

				newCode += "</td></tr>"
						+ "<tr style=\"height:25px\"><td>Genres: </td><td>";
								
				while (preparedGenres.next()) {
					newCode += "<a href=\"/fabflix/Browse?action=genre&sort=title&show=10&order=asc&pagenum=1&genre="  + preparedGenres.getString(1) + "\">" + preparedGenres.getString(1) + "</a>, ";
				}
				psGenresQuery.close();

				newCode += "</td></tr>";
				newCode += "<tr style=\"height:25px\"><td>Price: </td><td>$11.99</td></tr><tr><td><center><button type=\"button\" onclick=\"addToCart('" + preparedPageResults.getString(1) + "')\">Add To Cart</button></center></td></tr></table>";
						
				newCode += "<br><br><br><hr>";
				
//				current_ts = System.nanoTime();
//				current_ts = current_ts - ts;
//				current_tj = current_tj - tj;
//				writer.println(current_ts + ";" + current_tj);
			}
			
			//code += "</table>";
			String url = request.getQueryString();
			request.setAttribute("movieResults", newCode);
			request.setAttribute("path", url);
			

			request.setAttribute("url", request.getRequestURI());
			request.setAttribute("page", currentPage);
			request.setAttribute("max", numberOfPages);
			psSearchOffset.close();
			long ts_end = System.nanoTime();
			long elapsed_ts = ts_end - ts;
			writer.println(elapsed_ts + ";" + tj_elapsed);
			
			try {			
				initCtx.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection.close();

			request.getRequestDispatcher("/search/results.jsp").forward(request, response);
			writer.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
