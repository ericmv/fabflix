

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fabflix.Pair;
import fabflix.VerifyUtils;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.sql.DataSource;


/**
 * Servlet implementation class FabflixLogin
 */
@WebServlet("/FabflixLogin")
public class FabflixLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FabflixLogin() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();

		
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
		
		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
		if (!valid) {
		    //errorString = "Captcha invalid!";
		    out.println("<HTML>" +
				"<HEAD><TITLE>" +
				"MovieDB: Error" +
				"</TITLE></HEAD>\n<BODY>" +
				"<P>Recaptcha WRONG!!!! </P></BODY></HTML>");
		    return;
		}
		
		response.setContentType("text/html");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//String title = "Logging In";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Context initCtx;
		Connection connection = null;
		try {
			initCtx = new InitialContext();
			
			if (initCtx == null) {
				System.out.println("hello world");
			}
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			if (envCtx == null) {
				System.out.println("123456");
			} 
			DataSource ds = (DataSource) envCtx.lookup("jdbc/fabflix");
			if (ds == null) {
				System.out.println("asdf");
			}
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


				
		try {
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT * FROM customers c WHERE c.email = '" + username + "'"
					+ " AND c.password = '" + password + "';");
			
			if (!result.next()) {
				request.setAttribute("errorMessage", "Login failed");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			else {
				//result.beforeFirst();
				

				HttpSession session = request.getSession();
				session.setAttribute("user", result.getString(1));

				ArrayList<Pair<String, Integer>> cart = new ArrayList<Pair<String, Integer>>();
				session.setAttribute("cart", cart);
				session.setAttribute("cartSize", 0);
				connection.close();
				request.getRequestDispatcher("/main").forward(request, response);
				//response.sendRedirect("/fabflix/main");
//				out.println("<body>\n" +
//						"<h1>Login successful</h1></body><html>");
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
