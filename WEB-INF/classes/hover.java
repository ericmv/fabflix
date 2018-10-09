

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class hover
 */
@WebServlet("/hover")
public class hover extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public hover() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String mid = request.getParameter("id");
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		//String title = "Logging In";
		
		String hover_query = "SELECT * FROM movies m WHERE m.id = ?";
		ResultSet result = null;
		PreparedStatement hover = null;

		Context initCtx;
		Connection connection = null;
		try {
			initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/fabflix");
			try {
				connection = ds.getConnection();
				hover = connection.prepareStatement(hover_query);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("hsdjfbsdskjfhsdj");
				e.printStackTrace();
			}
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Statement select;
		
		
		String query = "SELECT * FROM movies m WHERE m.id = '" + mid + "';";
		String code = "";
		
		try {
			select = connection.createStatement();
			hover.setString(1, mid);
			result = hover.executeQuery();
			result.next();
			code += "<table style=\"position:relative;\">" 
					+ "<tr style=\"height:25px\">"
					+ "<td>Title: </td>"
					+ "<td><a href=\"/fabflix/page?&type=movie&id=" + result.getString(1) + "&cart=false\">" + result.getString(2) + "</a></td>"
							+ "</tr>"
							+ "<tr style=\"height:25px\">"
							+ "<td>Year: </td>"
							+ "<td>" + result.getString(3) + "</td>"
									+ "</tr>"
									+ "<tr style=\"height:25px\">"
									+ "<td>Director: </td>"
									+ "<td>" + result.getString(4) + "</td>"
											+ "</tr>"
											+ "<tr style=\"height:25px\">"
											+ "<td>ID: </td>"
											+ "<td>" + result.getString(1) + "</td>"
													+ "</tr><tr style=\"height:25px\"><td>Price: </td><td>$11.99</td></tr><tr><td><center><button type=\"button\" onclick=\"addToCart('" + result.getString(1) + "')\">Add To Cart</button></center></td></tr><table>";
			
			connection.close();

			out.print(code);
													

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
