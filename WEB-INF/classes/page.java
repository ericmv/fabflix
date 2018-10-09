

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import fabflix.Pair;

/**
 * Servlet implementation class page
 */
@WebServlet("/page")
public class page extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public page() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//String title = "Logging In";
		
		PreparedStatement movie = null;
		PreparedStatement star = null;
		PreparedStatement genre = null;
		PreparedStatement movie2 = null;
		PreparedStatement starsinmovies = null;
		
		String movie_query = "SELECT * FROM movies m WHERE m.id = ?";
		String star_query = "SELECT * FROM stars s WHERE s.id = ?";
		String genre_query = "SELECT DISTINCT g.name FROM genres g, movies m, genres_in_movies gm WHERE gm.movie_id = ? AND gm.genre_id = g.id";
		String movie2_query = "SELECT DISTINCT m.title, m.id FROM movies m, stars s, stars_in_movies sm WHERE ? = sm.star_id AND m.id=sm.movie_id;";
		String starsinmovies_query = "SELECT DISTINCT s.first_name, s.id, s.last_name FROM movies m, stars s, stars_in_movies sm WHERE ? = sm.movie_id AND s.id=sm.star_id;";  
		ResultSet result = null;
		Context initCtx;
		Connection connection = null;
		try {
			initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/fabflix");
			try {
				connection = ds.getConnection();
				movie = connection.prepareStatement(movie_query);
				star = connection.prepareStatement(star_query);
				genre = connection.prepareStatement(genre_query);
				movie2 = connection.prepareStatement(movie2_query);
				starsinmovies = connection.prepareStatement(starsinmovies_query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		String mid = request.getParameter("id");

		if (request.getParameter("type").equals("movie")) {
			int newCount = 0;
			HttpSession session = request.getSession(false);
			@SuppressWarnings("unchecked")

			ArrayList<Pair<String, Integer>> cart = (ArrayList<Pair<String, Integer>>) session.getAttribute("cart");


	
				if (request.getParameter("cart").equals("true")) {
					
					
					
	
					if (session != null) {
						
						int exists = 0;
						for (int i =0; i < cart.size(); i++) {

							if (cart.get(i).getFirst().equals(mid)) {
								int second = cart.get(i).getSecond();
								cart.get(i).setSecond(second+1);
								exists = 1;
							}
						}
						if (exists == 0) {
							Pair<String, Integer> pair = new Pair<String, Integer>(mid, 1);
							cart.add(pair);
							
	
						}
						
//						session.setAttribute("cartSize", newCount);
						session.setAttribute("cart", cart);
						request.setAttribute("message", "Successfully added item to cart");
					}
				}
				for (int i = 0; i < cart.size(); i++) {
					newCount += cart.get(i).getSecond();
				}

			
			String mQuery = "SELECT * FROM movies m WHERE m.id = " + mid + ";";
			String genresQuery = "SELECT DISTINCT g.name FROM genres g, movies m, genres_in_movies gm WHERE gm.movie_id = "
					+ mid + " AND gm.genre_id = g.id;";
			String starsInMovie = "SELECT DISTINCT s.first_name, s.id, s.last_name FROM movies m, stars s, stars_in_movies sm WHERE "
					+ mid + "=sm.movie_id AND s.id=sm.star_id;";  
			try {
				Statement select = connection.createStatement();
				
				Statement starSelect = connection.createStatement();
				Statement genreSelect = connection.createStatement();
				
				movie.setString(1, mid);
				genre.setString(1, mid);
				starsinmovies.setString(1, mid);
				
				
				ResultSet mResult = movie.executeQuery();
				ResultSet starResults = starsinmovies.executeQuery();
				ResultSet genresOfMovie = genre.executeQuery();

				
				ArrayList<String> genres = new ArrayList<String>();
				while (genresOfMovie.next()) {
					genres.add(genresOfMovie.getString(1));
				}
				String stars = "";
				while (starResults.next()) {
					stars += "<a href=\"/fabflix/page?type=star&id=" + starResults.getString(2) + "\">" + starResults.getString(1) + " " + starResults.getString(3)+ "</a>, ";
					
				}
				mResult.next();
				String id = mResult.getString(1);
				String title = mResult.getString(2);
				String year = mResult.getString(3);
				String director = mResult.getString(4);
				String banner = mResult.getString(5);
				String trailer = mResult.getString(6);
				
				
				String path = request.getQueryString();
				String url = request.getRequestURI();
				String link = url+"?"+path;
				
				movie.close();
				starsinmovies.close();
				genresOfMovie.close();
				request.setAttribute("url", url);
				request.setAttribute("cartCount", newCount);
				request.setAttribute("path", path);
				request.setAttribute("id", id);
				request.setAttribute("title", title);
				request.setAttribute("year", year);
				request.setAttribute("director", director);
				request.setAttribute("banner", banner);
				request.setAttribute("genres", genres);
				request.setAttribute("stars", stars);
				request.setAttribute("trailer", trailer);
				
				request.getRequestDispatcher("/movie.jsp").forward(request, response);
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (request.getParameter("type").equals("star")) {
			String sid = request.getParameter("id");
			String sQuery = "SELECT * FROM stars s WHERE s.id = " + sid + ";";
			String movies = "SELECT DISTINCT m.title, m.id FROM movies m, stars s, stars_in_movies sm WHERE "
					+ sid + "=sm.star_id AND m.id=sm.movie_id;";  
			try {
				Statement select = connection.createStatement();
				Statement starSelect = connection.createStatement();
				
				
				star.setString(1, sid);
				movie2.setString(1, sid);
				
				ResultSet starResult = star.executeQuery();
				ResultSet moviesIn = movie2.executeQuery();
				
				
				starResult.next();
				String id = starResult.getString(1);
				String first_name = starResult.getString(2);
				String last_name = starResult.getString(3);
				String dob = starResult.getString(4);
				String photo = starResult.getString(5);
				
				String starredIn = "";
				while (moviesIn.next()) {
					starredIn += "<a href=\"/fabflix/page?type=movie&id=" + moviesIn.getString(2) + "&cart=false\">" + moviesIn.getString(1) + "</a>, ";
					
					
					
				}
				
				request.setAttribute("sid", id);
				request.setAttribute("first_name", first_name);
				request.setAttribute("last_name", last_name);
				request.setAttribute("dob", dob);
				request.setAttribute("photo", photo);
				request.setAttribute("movies", starredIn);
				
				connection.close();

				request.getRequestDispatcher("/star.jsp").forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
