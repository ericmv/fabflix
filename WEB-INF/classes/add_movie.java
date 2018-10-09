

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class add_movie
 */
@WebServlet("/add_movie")
public class add_movie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public add_movie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();

		
		response.setContentType("text/html");
		Context initCtx;
		Connection connection = null;
		try {
			initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/write");
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
		
		
		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		
		String genre = request.getParameter("genre");
		
		
		String query = "{CALL add_movie(?,?,?,?,?,?)}";
		
		
		try {
			CallableStatement stmt = connection.prepareCall(query);
			stmt.setString(1, title);
			stmt.setInt(2, Integer.parseInt(year));
			stmt.setString(3, director);
			stmt.setString(4, first_name);
			stmt.setString(5, last_name);
			stmt.setString(6, genre);
			
			ResultSet result = stmt.executeQuery();
			
			out.println("<p>Successfully linked Movie: " + title + " with Star: " + first_name + " " + last_name + " and Genre: " + genre + "</p>");
			out.println("<a href=\"/fabflix/_dashboard/main/\">Click Here to return to dashboard</a>");
			
			connection.close();
			
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
