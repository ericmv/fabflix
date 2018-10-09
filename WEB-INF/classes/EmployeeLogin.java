

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


/**
 * Servlet implementation class EmployeeLogin
 */
@WebServlet("/EmployeeLogin")
public class EmployeeLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeLogin() {
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
		Context initCtx;
		Connection connection = null;
		PreparedStatement checkLogin = null;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username);
		System.out.println(password);
		String query = "SELECT * FROM employees e WHERE e.email = ? AND e.password = ?;";

		try {
			initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/fabflix");
			try {
				connection = ds.getConnection();
				checkLogin = connection.prepareStatement(query);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("hsdjfbsdskjfhsdj");
				e.printStackTrace();
			}
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("text/html");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//String title = "Logging In";

		

		try {
			checkLogin.setString(1, username);
			checkLogin.setString(2, password);
//			Statement select = connection.createStatement();
			
			ResultSet result = checkLogin.executeQuery();
//					select.executeQuery("SELECT * FROM employees e WHERE e.email = '" + username + "'"
//							+ " AND e.password = '" + password + "';");
//			
			
			if (!result.next()) {
				request.setAttribute("errorMessage", "Login failed");
				request.getRequestDispatcher("/_dashboard/index.jsp").forward(request, response);
			}
			else {
				

				HttpSession session = request.getSession();
				session.setAttribute("employee", result.getString(1));

				
				connection.close();

				request.getRequestDispatcher("/_dashboard/main/").forward(request, response);

			}

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
