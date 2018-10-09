

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

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.sql.DataSource;

/**
 * Servlet implementation class autosearch
 */
@WebServlet("/autosearch")
public class autosearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public autosearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Context initCtx;
		Connection connection = null;
		try {
			initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
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
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		//String title = "Logging In";
		
		PreparedStatement search = null;
		String auto_query = "SELECT * FROM movies m WHERE MATCH (m.title) AGAINST ("; 
		ResultSet result = null;

		Statement select;
		
		String query_string = request.getParameter("title");
		String[] tokenized = query_string.split(" ");
		for (int i = 0; i < tokenized.length; i++) {
			String token = "+" + tokenized[i];
			if (i == tokenized.length - 1) {
				token += "*"; 
			}
			tokenized[i] = token;
		}
		ArrayList<String> parameters = new ArrayList<String>();
//		double numberOfPages = Math.ceil((double) size/(double) perPage);
		String query = "SELECT * FROM movies m WHERE MATCH (m.title) AGAINST ('" + query_string + "');";
		
		String token_query = "SELECT * FROM movies m WHERE MATCH (m.title) AGAINST ('";
		for (int i = 0; i < tokenized.length; i++) {
			auto_query += "?";
			token_query += tokenized[i];
			if (i != tokenized.length - 1) {
				auto_query += " ";
				token_query += " ";
			}
		}
		token_query += "' IN BOOLEAN MODE);";
		auto_query += " IN BOOLEAN MODE);";
		System.out.println(auto_query);

		try {
			search = connection.prepareStatement(auto_query);
			for (int i = 0; i < tokenized.length; i++) {
				search.setString(i+1, tokenized[i]);
			}
			select = connection.createStatement();
			result = search.executeQuery();
			out.println("<div class=\"dropdown-content1\">");
			while (result.next()) {
				System.out.println(result.getString(2));
				String mid = result.getString(1);
				String code = "<table style=\"position:relative;\"><tr><th rowspan=8 width=300px>"
						+ "<img src=\"" + result.getString(5) + "\" style=\"height:50px\"></img>"
						+ "</th>"
						+ "</tr>"
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
				
				
				
				
				
				out.println("<a href=\"#\" onmouseover = \"display(" + mid +  ",'helloworld')\" >" + result.getString(2) + "<br><br></a><div class =\"pop\" id=\"" + mid + "\" onmouseover=\"onDisplay(" + mid + ")\" onmouseout=\"toggleDisplay(" + mid + ")\" background-color=\"white\"></div>");
//				out.println("<p onmouseover=\"displayHover(" + mid + ")\">" + result.getString(2) + "</p><div "
//						+ "id=\"" + mid + "\" onmouseout=\"toggleDisplay(" + mid + ")\" "
//								+ " onmouseover=\"onDisplay(" + mid + ")\" style=\"display: inline\"></div>" );

				
			}
			out.println("</div>");
			
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
